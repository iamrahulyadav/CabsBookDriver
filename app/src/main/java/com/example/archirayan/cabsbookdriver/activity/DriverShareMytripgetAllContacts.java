package com.example.archirayan.cabsbookdriver.activity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.database;
import com.example.archirayan.cabsbookdriver.adapter.AllContactsAdapter;
import com.example.archirayan.cabsbookdriver.model.ContactListItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.archirayan.cabsbookdriver.adapter.AllSelectContactsAdapter.strCreditId;


public class DriverShareMytripgetAllContacts extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final int
            MY_PERMISSIONS_REQUEST_READ_CONTACTS = 99;
    public EditText search;
    public ImageView img_back_acceptance_tital;
    public Button btn_next;
    public Context context = this;
    RecyclerView rvContacts;
    database myDB;
    Boolean permission = false;
    int exist;
    List<ContactListItem> contactList;
    ContactListItem contactListItem;
    ArrayList<ContactListItem> searchedArraylist;
    AllContactsAdapter contactAdapter;
    ProgressDialog pd;
    private String mOrderBy =
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_share_mytripget_all_contacts);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);


        btn_next = findViewById(R.id.btn_next);
        search = (EditText) findViewById(R.id.search);
        myDB = new database(this);
        exist = myDB.tableExists();
        rvContacts = (RecyclerView)
                findViewById(R.id.rvContacts);
        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.M) {
            permission = checkContactsPermission();
            if (permission) {
                if (exist == 0) {
                    getLoaderManager().initLoader(1, null, this);
                    displayAllContacts();
                }
            }
        } else {
            if (exist == 0) {
                getLoaderManager().initLoader(1, null, this);
                displayAllContacts();
            }
        }
        displayAllContacts();

        img_back_acceptance_tital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (strCreditId.size() > 0)
        {
            for (int i = 0; i < strCreditId.size(); i++)
            {
                search.setText(strCreditId.get(i).getContactName());
            }
        } else {

        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverShareMytripgetAllContacts.this, DriverShareMytripMain.class));
            }
        });


    }


    private void displayAllContacts() {
        pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.show();
        contactList = new ArrayList();
        Cursor c = myDB.getAllData();
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String name = c.getString(1);
                String phoneNo = c.getString(0);
                contactListItem = new ContactListItem();
                contactListItem.setContactName(name);
                contactListItem.setContactNumber(phoneNo);
                contactList.add(contactListItem);
            }
        }

        if (contactList.size() > 0) {
            pd.dismiss();
            contactAdapter = new
                    AllContactsAdapter(contactList, getApplicationContext());
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
            rvContacts.setAdapter(contactAdapter);
            initViews();
        } else {
            pd.dismiss();
            Toast.makeText(DriverShareMytripgetAllContacts.this, R.string.no_result_found, Toast.LENGTH_SHORT).show();
        }
    }


    private void initViews() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (contactList.size() > 0) {
                    String searchFor = search.getText().toString();
                    searchedArraylist = new ArrayList<>();
                    for (int j = 0; j < contactList.size(); j++) {
                        if (contactList.get(j).getContactName().toLowerCase().contains(searchFor.toLowerCase()) || contactList.get(j).getContactNumber().toLowerCase().contains(searchFor.toLowerCase())) {
                            searchedArraylist.add(contactList.get(j));
                        }
                    }

                    if (searchedArraylist.size() > 0) {
                        AllContactsAdapter contactAdapter = new
                                AllContactsAdapter(searchedArraylist, getApplicationContext());
                        rvContacts.setLayoutManager(new LinearLayoutManager(DriverShareMytripgetAllContacts.this));
                        rvContacts.setAdapter(contactAdapter);
                        contactAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DriverShareMytripgetAllContacts.this, R.string.no_result_found, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DriverShareMytripgetAllContacts.this, R.string.no_result_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (i == 1) {
            return new CursorLoader(this,
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    "upper(" +
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +
                            ") ASC");
        }
        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString
                        (cursor.getColumnIndex
                                (ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    ContentResolver contentResolver = getContentResolver();
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
                                    " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString
                                (phoneCursor.getColumnIndex
                                        (ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneCursor.close();
                        myDB.addContact(name, phoneNumber);
                    }
                }
            }
            displayAllContacts();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public boolean checkContactsPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.READ_CONTACTS)
                            == PackageManager.PERMISSION_GRANTED) {
                        exist = myDB.tableExists();
                        if (exist == 0) {
                            getLoaderManager().initLoader(1, null, this);
                        }
                        return;
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
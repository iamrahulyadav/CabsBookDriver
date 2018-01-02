package com.example.archirayan.cabsbookdriver.firebase;

/**
 * Created by archirayan on 22/11/17.
 */


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyAndroidFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyAndroidFCMIIDService";

    @Override
    public void onTokenRefresh() {
        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG,"Refreshed token " + refreshedToken);
        // Saving reg id to shared preferences


        // Notify UI that registration has completed, so the progress indicator can be hidden.

    }

}

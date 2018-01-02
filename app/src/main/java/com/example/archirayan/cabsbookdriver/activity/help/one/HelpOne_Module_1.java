package com.example.archirayan.cabsbookdriver.activity.help.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;

public class HelpOne_Module_1 extends AppCompatActivity {

    private LinearLayout linear_msgus,linear_msgus_activity,linear_txt_getintouch,linear_visitus;
    private View view_helpsub_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_one__module_1);

        linear_msgus = (LinearLayout) findViewById(R.id.linear_msgus);
        linear_msgus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_msgus_activity.setVisibility(View.VISIBLE);
                linear_txt_getintouch.setVisibility(View.GONE);
                view_helpsub_1.setVisibility(View.GONE);
                linear_visitus.setVisibility(View.GONE);
                linear_msgus.setVisibility(View.GONE);

            }
        });
        linear_msgus_activity = (LinearLayout) findViewById(R.id.linear_msgus_activity);
        linear_txt_getintouch = (LinearLayout) findViewById(R.id.linear_txt_getintouch);
        linear_visitus = (LinearLayout) findViewById(R.id.linear_visitus);

        view_helpsub_1 = (View) findViewById(R.id.view_helpsub_1);
    }
}

package com.example.archirayan.cabsbookdriver.firebase;

/**
 * Created by archirayan on 22/11/17.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.CurrentTrips;
import com.example.archirayan.cabsbookdriver.activity.DriverMainPage;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    //private static String event_title;
    //private static String event_date;
    String field;
    private String str_Id, str_Img;
    private static String str_type;
    private String event_title, event_date;
    private String str_id,str_name,str_image,str_msg,latitude,longituted,mobile_num,detination,trip_id;
    String longText = "...";
    private File file;
    private Bitmap bitmap;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /*// TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d("Message_data_payload", "" + remoteMessage.getData());
            String jsonstring = remoteMessage.getData().toString();
            Log.e("str",""+jsonstring);
            try {
                JSONObject mainObject = new JSONObject(jsonstring);
                Log.d("MAINA@@", "" + mainObject);
                event_title = mainObject.getString(remoteMessage.getNotification().getBody());
                Log.e("msg",""+event_title);
                event_date = mainObject.getString("push_title").toString();
                Log.e("date",""+event_date);

            }
            catch (JSONException e)
            {
                Log.d("Error", e.toString());
            }
            sendNotification(remoteMessage.getData().toString());
        }*/

        // TODO(developer): Handle FCM messages here.
        Log.e(TAG, "driver_From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {

            Log.e(TAG,"Message_data_payload_driver" + remoteMessage.getData());
            event_title = remoteMessage.getData().get("push_title").toString();
            event_date = remoteMessage.getData().get("push_message");

            try {
                JSONObject mainObject = new JSONObject(event_date);
                Log.e("MAINA@@", "" + mainObject);
                str_id = mainObject.getString("id");
                Utils.WriteSharePrefrence(this, Constant.USERID,str_id);
                String usrid = Utils.ReadSharePrefrence(this,Constant.USERID);
                str_name = mainObject.getString("name").toString();
                Utils.WriteSharePrefrence(this, Constant.NAME,str_name);
                String name = Utils.ReadSharePrefrence(this,Constant.NAME);
                str_image = mainObject.getString("image").toString();
                Utils.WriteSharePrefrence(this, Constant.IMAGE,str_image);
                String image = Utils.ReadSharePrefrence(this,Constant.IMAGE);
                str_msg = mainObject.getString("msg").toString();
                Utils.WriteSharePrefrence(this, Constant.MAG,str_msg);
                String msg = Utils.ReadSharePrefrence(this,Constant.MAG);
                latitude = mainObject.getString("latitude").toString();
                Utils.WriteSharePrefrence(this,Constant.USER_LATITUDE,latitude);
                String lat = Utils.ReadSharePrefrence(this,Constant.USER_LATITUDE);
                longituted = mainObject.getString("longitude").toString();
                Utils.WriteSharePrefrence(this,Constant.USER_LONGITUTED,longituted);
                String lng = Utils.ReadSharePrefrence(this,Constant.USER_LONGITUTED);
                mobile_num = mainObject.getString("mobile").toString();
                Utils.WriteSharePrefrence(this,Constant.USER_mobile_num,mobile_num);
                String mobile_num = Utils.ReadSharePrefrence(this,Constant.USER_mobile_num);
                detination = mainObject.getString("destination").toString();
                Utils.WriteSharePrefrence(this,Constant.USER_DETINATION,detination);
                String detination = Utils.ReadSharePrefrence(this,Constant.USER_DETINATION);
                trip_id = mainObject.getString("trip_id").toString();
                Utils.WriteSharePrefrence(this,Constant.TRIP_ID,trip_id);
                String trip_id = Utils.ReadSharePrefrence(this,Constant.TRIP_ID);
                //str_type = mainObject.getString("type");
                Log.e("event_title", "===== Data==" +
                        "==" + event_title);
            } catch (JSONException e) {
                Log.d("Error", e.toString());
            }
            sendNotification(remoteMessage.getData().toString());
        }



        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body:" + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

/*        if (remoteMessage.getNotification() != null)
        {
            Log.d(TAG, "Message Notification Body:" + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }*/
    }



    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /*private void sendNotification(String messageBody) {

            Intent intent = new Intent(this, ReqestFordriver.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("message",str_Id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.appicon)
                    .setContentTitle("Cabs Book")
                    .setContentText(event_title + "\n" + event_date)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());

    }*/


    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, CurrentTrips.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent intentConfirm = new Intent(this, DriverMainPage.class);
        intentConfirm.setAction("CONFIRM");
        intentConfirm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent intentCancel = new Intent(this, DriverMainPage.class);
        intentCancel.setAction("CANCEL");
        intentCancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntentConfirm = PendingIntent.getBroadcast(this, 0, intentConfirm, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(this, 1, intentCancel, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Cabs Book Driver")
                .setContentText(str_name+"\n"+str_msg)
                //.addAction(R.drawable.ic_action_click, "Confirm", pendingIntentConfirm)
                //.addAction(R.drawable.ic_cancle_blue, "Cancel", pendingIntentCancel)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(str_name+"\n"+str_msg))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
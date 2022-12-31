package com.expresstemplate.dictionary;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyAlarmService extends Service {

    Random random = new Random();
    boolean check=false;
   
    @Override
     public IBinder onBind(Intent arg0) {
      return null;
    }

    @SuppressWarnings({ "deprecation", "static-access" })
	@Override
    public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);


    }

 /*   @Override
    public void onDestroy() {

    	//Logger.error("Alam Services Destroyed");
    	Toast.makeText(MyAlarmService.this, "Alarm received!", Toast.LENGTH_LONG).show();
    //	super.onDestroy();

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        mManager.cancel(666);
}*/


}
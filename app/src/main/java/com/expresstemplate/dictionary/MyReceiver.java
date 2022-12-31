package com.expresstemplate.dictionary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


public class MyReceiver extends BroadcastReceiver {

	@Override
	 public void onReceive(Context context, Intent intent)
	{
		String MyWord = intent.getStringExtra("Word");
		String MyMeaning = intent.getStringExtra("Meaning");
		NotificationManager mManager = (NotificationManager) context.getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);

		Intent intent1 = new Intent(context,MainActivity.class);
		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


		PendingIntent pendingNotificationIntent;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			pendingNotificationIntent = PendingIntent.getActivity(context,
					0, intent1, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
		}else {
			pendingNotificationIntent = PendingIntent.getActivity(context,
					0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
		}

		Notification.Builder builder;

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
		{
			NotificationChannel channel=new NotificationChannel(context.getPackageName(),context.getString(R.string.app_name),NotificationManager.IMPORTANCE_DEFAULT);
			channel.enableVibration(true);
			channel.setVibrationPattern(new long[]{500});
			mManager.createNotificationChannel(channel);
			builder=new Notification.Builder(context,channel.getId());
		}
		else
			builder= new Notification.Builder(context);

		builder.setContentTitle("Dictionary")
				.setContentText(MyWord+"  "+MyMeaning)
				.setAutoCancel(true)
				.setSmallIcon(R.drawable.icon_72)
				.setContentIntent(pendingNotificationIntent);


		mManager.notify(0, builder.build());


	}
}

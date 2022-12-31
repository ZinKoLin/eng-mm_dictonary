package com.expresstemplate.dictionary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.net.ConnectivityManager;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class Util {
	public static ColorFilter changeImageColor(ImageView img) {
		ColorFilter cf = new PorterDuffColorFilter(Color.rgb(255, 255, 255), Mode.SRC_IN);
		img.setColorFilter(cf);
		return cf;
	}
	public static boolean isNetworkAvailable(final Context context) {
	    final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
	    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
	}
	public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static int generateRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min+1)+min;
    }

}

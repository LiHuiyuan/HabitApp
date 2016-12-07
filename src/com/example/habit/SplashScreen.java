package com.example.habit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;


public class SplashScreen extends ActivityCtrl {
    /**
     * Called when the activity is first created.
     */

	Context context = this;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        setContentView(R.layout.splashscreen);
       
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
            	 //先判断登录状态，有已登录用户则直接跳转主界面
                User user = new User(context);
              	user.setUserName("");
              	if(user.getCurrentUser() != null) {
              		Log.w("验证登录","有");
              		currentUser = user.getCurrentUser();
              		returnMainAct(SplashScreen.this);
              		(SplashScreen.this).finish();
              	} else{
              		gotoLoginAct(SplashScreen.this);
                    (SplashScreen.this).finish();

              	}
                            }
        }, 2900); //2900 for release

    }
}
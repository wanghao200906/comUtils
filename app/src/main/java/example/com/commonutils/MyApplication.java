package example.com.commonutils;

import android.app.Application;

import example.com.CommonApplication;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonApplication.createInstance(this, new CommonApplication.Config());
    }
}

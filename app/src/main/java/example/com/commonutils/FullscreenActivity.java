package example.com.commonutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import broadcast.TimeReceiver;
import example.com.commutils.receive.ScreenReceiver;


public class FullscreenActivity extends AppCompatActivity {
    TimeReceiver timeReceiver;
    private static final String TAG = "FullscreenActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timeReceiver = new TimeReceiver();
        timeReceiver.registerReceiver(this, new TimeReceiver.TimeListener() {
            @Override
            public void onTimeZoneChanged() {
                Log.e(TAG, "onTimeZoneChanged: " );
            }

            @Override
            public void onTimeChanged() {
                Log.e(TAG, "onTimeChanged: " );

            }

            @Override
            public void onTimeTick() {
                Log.e(TAG, "onTimeTick: " );

            }
        });

        ScreenReceiver screenReceiver = new ScreenReceiver();
        screenReceiver.registerScreenReceiver(this, new ScreenReceiver.ScreenListener() {
            @Override
            public void screenOn() {
                Log.e(TAG, "screenOn: ");
            }

            @Override
            public void screenOff() {
                Log.e(TAG, "screenOff: ");

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeReceiver.unRegisterReceiver(this);
    }
}

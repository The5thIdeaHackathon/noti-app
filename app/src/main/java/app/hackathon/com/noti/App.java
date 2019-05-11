package app.hackathon.com.noti;

import android.app.Application;
import android.content.Intent;
import app.hackathon.com.noti.service.NotiListenerService;
import app.hackathon.com.noti.utill.SharedPreferencesUtil;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesUtil.initialize(this);
        startService(new Intent(this, NotiListenerService.class));
    }
}

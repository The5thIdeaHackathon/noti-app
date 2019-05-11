package app.hackathon.com.noti.view.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import app.hackathon.com.noti.R;
import app.hackathon.com.noti.model.Noti;
import app.hackathon.com.noti.utill.SharedPreferencesUtil;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private SharedPreferencesUtil preferencesUtil;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String[] otherApp = {"kakao", "gmail"};

    private boolean flag = false;

    private Toolbar mToolbar;
    private ImageView iconIv;
    private Button checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencesUtil = SharedPreferencesUtil.getInstance();

        boolean isPermissionAllowed = isNotiPermissionAllowed();

        if (!isPermissionAllowed) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }

        mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        iconIv = findViewById(R.id.icon_iv);
        checkBtn = findViewById(R.id.check_btn);

        if(preferencesUtil.getFlag() != null) {
            setContentView();
        } else {
            preferencesUtil.setFlag("꺼짐");
            setContentView();
        }


        checkBtn.setOnClickListener( v -> {
            if(preferencesUtil.getFlag() == "꺼짐") {
                preferencesUtil.setFlag("켜짐");
            } else {
                preferencesUtil.setFlag("꺼짐");
            }
            setContentView();
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
    }

    private void setContentView() {
        if(preferencesUtil.getFlag() != "꺼짐") {
            iconIv.setImageResource(R.drawable.bean_on);
            checkBtn.setText("Turn Off");
        } else {
            iconIv.setImageResource(R.drawable.bean_off);
            checkBtn.setText("Turn On");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNotiPermissionAllowed() {
        Set<String> notiListenerSet = NotificationManagerCompat.getEnabledListenerPackages(this);
        String myPackageName = getPackageName();

        for(String packageName : notiListenerSet) {
            if(packageName == null) {
                continue;
            }
            if(packageName.equals(myPackageName)) {
                return true;
            }
        }

        return false;
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            String postTime = intent.getStringExtra("posttime");

            String msg = title + " " + text;
            Noti model = new Noti(msg);

            try {
                byte[] byteArray =intent.getByteArrayExtra("icon");
                Bitmap bmp = null;
                if(byteArray !=null) {
                    bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }

                for(String s : otherApp) {
                    if(pack.contains(s) && !msg.contains("null") && preferencesUtil.getFlag() != null && preferencesUtil.getFlag() == "켜짐") {
                        db.collection(s).document("10").set(model);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}

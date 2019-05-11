package app.hackathon.com.noti.view.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import app.hackathon.com.noti.R;

public class SettingActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mToolbar = findViewById(R.id.my_toolbar);
        mToolbar.setTitle("Setting");
        setSupportActionBar(mToolbar);
    }

    public void onSelectKeyword(View v) {
        startActivity(new Intent(this, SelectKeywordActivity.class));
    }
}

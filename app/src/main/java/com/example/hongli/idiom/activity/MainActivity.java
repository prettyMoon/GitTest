package com.example.hongli.idiom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongli.idiom.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart;
    private ImageView imgLeft;
    private TextView btnRight;
    private TextView btnMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnMiddle = this.findViewById(R.id.title_middle);
        btnRight = this.findViewById(R.id.title_right);
        imgLeft = this.findViewById(R.id.title_left);
        //sperate line
        btnStart = this.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        imgLeft.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_right:
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_start:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                break;
        }
    }
}

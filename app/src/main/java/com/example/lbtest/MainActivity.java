package com.example.lbtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lbtest.lb.DBASActivity;
import com.example.lbtest.lb.EvaluteSelfPSASActivity;
import com.example.lbtest.lb.FirstActivity;
import com.example.lbtest.lb.SHPSActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button dbas;
    private Button psas;
    private Button first;
    private Button shps;
    private Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dbas=(Button)findViewById(R.id.btn_dbas);
        dbas.setOnClickListener(this);
        psas=(Button)findViewById(R.id.btn_psas);
        psas.setOnClickListener(this);
        first=(Button)findViewById(R.id.btn_first);
        first.setOnClickListener(this);
        shps=(Button)findViewById(R.id.btn_shps);
        shps.setOnClickListener(this);
        set=(Button)findViewById(R.id.set);
        set.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dbas:
                Intent intent=new Intent(MainActivity.this, DBASActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_psas:
                Intent intent1=new Intent(MainActivity.this, EvaluteSelfPSASActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_first:
                Intent intent2=new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_shps:
                Intent intent3=new Intent(MainActivity.this, SHPSActivity.class);
                startActivity(intent3);
                break;
            case R.id.set:
                Intent intent4=new Intent(MainActivity.this, SHPSActivity.class);
                startActivity(intent4);
                break;
        }
    }
}

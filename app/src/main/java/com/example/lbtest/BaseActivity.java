package com.example.lbtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity{
    public static Typeface typeface;
    public static Typeface typeface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getAssets(), "SIMYOU.TTF");
        }
        if (typeface1 == null) {
            typeface1 = Typeface.createFromAsset(getAssets(), "SIMYOU.TTF");
        }
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);

                if (view != null && (view instanceof TextView)) {
                    ((TextView) view).setTypeface(typeface1);
                }
                if (view != null && (view instanceof EditText)) {
                    ((EditText) view).setTypeface(typeface);
                }
                return view;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // changeTextSize(MainActivity.this,2);
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
    }
    }

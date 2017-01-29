package com.anwesome.ui.clippyrotimagedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.clippyrotimage.ClippyRotImage;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClippyRotImage clippyRotImage = new ClippyRotImage(this,R.drawable.ford);
        clippyRotImage.show(300,300);
    }

}

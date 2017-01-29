package com.anwesome.ui.clippyrotimagedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anwesome.ui.clippyrotimage.ClippyRotImage;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClippyRotImage clippyRotImage = new ClippyRotImage(this,R.drawable.ford);
        clippyRotImage.show(300,300);
        clippyRotImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

}

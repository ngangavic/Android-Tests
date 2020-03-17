package com.ngangavic.test.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ngangavic.test.R;

public class FragmentActivity extends AppCompatActivity {
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        // viewPager = (ViewPager) findViewById(R.id.viewPager);
        //viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(viewPagerAdapter);
    }

//    @Override
//    public void sendData(String message) {
//       // String tag = "android:switcher:" + R.id.viewPager + ":" + 1;
//        FragmentTwo f = (FragmentTwo) getSupportFragmentManager().findFragmentById(R.id.f2);
//        f.displayReceivedData(message);
//    }

}

package cn.d41216.mario.cat;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;


public class MainActivity extends AppCompatActivity {

    public static final String TU_APP_ID = "10374001"; //App ID
    public static final String TU_API_KEY = "M2FlzNFDnAO5lSVCvNO3DWZ8"; //Api Key
    public static final String TU_SECRET_KEY = "P0aBCzOTuY1v1XybmZzOKA2kHXhnY3Du"; //Secret Key



    private TextView mTextMessage;
    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private MenuItem menuItem;


    public static Application application;
    public static Context context;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        application  = this.getApplication();
        context = this.getApplicationContext();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_tu:

                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_wen:

                        viewPager.setCurrentItem(1);
                        return true;
                   /*
                    case R.id.navigation_miao:

                        viewPager.setCurrentItem(2);
                        return true;
                       */
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 释放内存资源
        OCR.getInstance().release();
    }
}

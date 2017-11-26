package com.example.user.glad_driver;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected FrameLayout frameLayout;
    private  Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * 重写setContentView，以便于在保留侧滑菜单的同时，让子Activity根据需要加载不同的界面布局
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID){
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        frameLayout = (FrameLayout) drawerLayout.findViewById(R.id.content_frame);
        // 将传入的layout加载到activity_base的content_frame里面
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(drawerLayout);

        setUpNavigation();
        setUpToolBar();

    }

    //設定Navigation設定
    private void setUpNavigation(){
        //選單 -> 個人資料
        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent();
                //i.setClass(getApplicationContext(), PersonInfoAty.class);
                //startActivity(i);
            }
        });
        //選單 -> 最新消息
        findViewById(R.id.latest_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent();
                // i.setClass(getApplicationContext(), LatestNewsAty.class);
                // startActivity(i);
            }
        });
        //選單 -> 接單紀錄
        findViewById(R.id.order_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent i = new Intent();
                // i.setClass(getApplicationContext(), OrderRecordAty.class);
                //  startActivity(i);
            }
        });
        //選單 -> 服務中心
        findViewById(R.id.service_center_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent();
                // i.setClass(getApplicationContext(), ServiceCenterAty.class);
                // startActivity(i);
            }
        });
        //選單 -> 服務中心
        findViewById(R.id.reservation_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent i = new Intent();
                // i.setClass(getApplicationContext(), ReservationAty.class);
                //startActivity(i);
            }
        });
    }


    //ToolBar初始化
    private  void setUpToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
    }


    //点击ToolBar的home按钮打开Navigation Drawer省略ActionBarDrawerToggle的部分了，影响不是特别大。实际应用中再去添加。
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}

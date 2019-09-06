package com.example.android_dome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private ImageView titile_iv;
    private TextView tile_tv;
    private mainFragment mainFragment;
    private user user;
    private FrameLayout frameLayout;
    private RelativeLayout mainlayout;
    private RelativeLayout userlatout;
    private ImageView f_image;
    private ImageView s_image;
    private TextView f_text;
    private TextView s_text;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initView();
        setChioceItem(0);


        SharedPreferences preferences = getSharedPreferences("count",MODE_PRIVATE);
        int count = preferences.getInt("count",0);
        if(count == 0)
        {
            initquestionDB();

        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count",0);
        editor.commit();




    }

    private void initView(){
        titile_iv = findViewById(R.id.title_i);
        tile_tv = findViewById(R.id.title_t);

        f_image = findViewById(R.id.f_image);
        f_text = findViewById(R.id.f_text);
        s_image = findViewById(R.id.s_image);
        s_text =findViewById(R.id.s_text);
        mainlayout =findViewById(R.id.first_l);
        userlatout = findViewById(R.id.second_l);
        mainlayout.setOnClickListener(MainActivity.this);
        userlatout.setOnClickListener(MainActivity.this);



    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.first_l:
                setChioceItem(0);
                break;
            case R.id.second_l:
                setChioceItem(1);
                break;
        }
    }

    private void setChioceItem(int index){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); //清空, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        switch (index){
            case 0:
                f_image.setBackgroundColor(getResources().getColor(R.color.tubiao));
                f_text.setTextColor(getResources().getColor(R.color.tubiao));
                s_image.setBackgroundColor(getResources().getColor(R.color.mr));

                s_text.setTextColor(getResources().getColor(R.color.ziti));
                if(mainFragment == null){
                    mainFragment = new mainFragment();
                    fragmentTransaction.add(R.id.content, mainFragment);
                }
                else {
                    fragmentTransaction.show(mainFragment);
                }
                break;
            case 1:
                s_image.setBackgroundColor(getResources().getColor(R.color.tubiao));
                s_text.setTextColor(getResources().getColor(R.color.tubiao));
                f_image.setBackgroundColor(getResources().getColor(R.color.mr));

                f_text.setTextColor(getResources().getColor(R.color.ziti));
                if(user==null){
                    user = new user();
                    fragmentTransaction.add(R.id.content, user);
                }
                else
                {

                    user = new user();
                    fragmentTransaction.add(R.id.content, user);
                    fragmentTransaction.show(user);
                }
                break;

        }
        fragmentTransaction.commit();

    }

    private void clearChioce(){

    }

    private void hideFragments(FragmentTransaction fragmentTransaction){
        if(mainFragment != null){
            fragmentTransaction.hide(mainFragment);
        }
        if(user !=null){
            fragmentTransaction.hide(user);
        }
    }
    public void initquestionDB(){
        String DB_PATH = "/data/data/com.example.android_dome/databases/";

        String DB_NAME = "androiddomeDBnew.db";

        // 当不存在数据目录时，创建目录

        if((new File(DB_PATH + DB_NAME).exists()) == false){
            File dir = new File(DB_PATH);
            if(!dir.exists()){
                dir.mkdir();
            }
        }

        // 从已创建的数据库中读取数据进默认数据库路径中
        try {
            InputStream in = getBaseContext().getAssets().open(DB_NAME);
            OutputStream out = new FileOutputStream(DB_PATH + DB_NAME);
            byte[]  buffer = new byte[in.available()];
            int length = in.read(buffer);
            if (length > 0){
                out.write(buffer, 0 ,length);
            }

            out.flush();
            out.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @Override
    public void onRestart(){
        super.onRestart();
        SharedPreferences preferences = getSharedPreferences("is",MODE_PRIVATE);
        int is = preferences.getInt("is",0);
        if(is !=0){
            setChioceItem(1);
        }


    }

}

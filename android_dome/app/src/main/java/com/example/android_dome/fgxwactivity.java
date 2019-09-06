package com.example.android_dome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class fgxwactivity extends AppCompatActivity implements fgxwfragment.FragmentInteraction{
    Button bt_pre;
    Button bt_next;
    int curren;
    int count;
    View view;
    ImageView ret;
    int is_right;
    TextView title;

    List<Question> listQuestion;
    private FragmentManager fragmentManager;
    fgxwfragment fgxwfragment;
    TextView expl;
    String explstring;
    TextView numdisplay;
    int is_click;
    String start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fgxwactivity);

        Intent intent = getIntent();
        start = intent.getStringExtra("start");

        fragmentManager = getSupportFragmentManager();

        final QuestionDBService questionDBService = new QuestionDBService();

        if (start.equals("犯规行为")) {

            listQuestion = questionDBService.getQuestion("犯规行为");
            title = findViewById(R.id.tv_fgxw);
            title.setText("犯规行为");
        }else if(start.equals("一般规定"))
        {
            listQuestion = questionDBService.getQuestion("一般规定");
            title = findViewById(R.id.tv_fgxw);
            title.setText("一般规定");
        }else if(start.equals("比赛定义")){
            title = findViewById(R.id.tv_fgxw);
            title.setText("比赛定义");
        }



        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fgxwfragment = new fgxwfragment();
        if(is_right==1)
        {
            delay(1000);
        }
        fragmentTransaction.add(R.id.fgxwquesitoncontent,fgxwfragment);
        fragmentTransaction.show(fgxwfragment);
        fragmentTransaction.commit();

        bt_pre = findViewById(R.id.upfgxwquestion);
        bt_next  = findViewById(R.id.downfgxwquesiton);
        ret = findViewById(R.id.fgxwtitle_tv);
        expl = findViewById(R.id.expltv_fgxw);
        numdisplay = findViewById(R.id.fgxwnumdisplay);
        Bundle bundle = new Bundle();
        bundle.putInt("index", 0);
        bundle.putInt("accept_right",0);
        bundle.putInt("is_click", is_click);
        bundle.putString("type",start);
        fgxwfragment.setArguments(bundle);

        bt_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_click = 1;
                if(curren == 0){
                    Toast.makeText(fgxwactivity.this,"已经是第一题",Toast.LENGTH_SHORT).show();
                }else {
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //hideFragments(fragmentTransaction);

                    com.example.android_dome.fgxwfragment q1 = new fgxwfragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("index", curren-1);
                    bundle.putInt("accept_right",0);
                    bundle.putInt("is_click", is_click);
                    bundle.putString("type",start);
                    q1.setArguments(bundle);

                    fragmentTransaction.replace(R.id.fgxwquesitoncontent,q1);
                    fragmentTransaction.commit();


                }


            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_click = 1;
                if(curren == count -1){
                    Toast.makeText(fgxwactivity.this, "已经是最后一题",Toast.LENGTH_SHORT).show();
                }else {

                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //hideFragments(fragmentTransaction);
                    com.example.android_dome.fgxwfragment q1 = new fgxwfragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("index", curren+1);
                    bundle.putInt("accept_right",0);
                    bundle.putInt("is_click", is_click);
                    bundle.putString("type", start);
                    q1.setArguments(bundle);



                    fragmentTransaction.replace(R.id.fgxwquesitoncontent,q1);


                    fragmentTransaction.commit();
                }
            }
        });

        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("is",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("is",0);
                editor.commit();
                finish();
            }
        });

        expl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView fgx = view.findViewById(R.id.fgxfgxw);
                TextView expl = view.findViewById(R.id.explfgxw);
                fgx.setVisibility(view.VISIBLE);
                expl.setText(explstring);

            }
        });





    }


    @Override
    public void send(int currenindex, int all,View view,String s,int is_right,int is_click){
        this.curren = currenindex;
        this.count = all;
        this.view = view;
        this.explstring = s;
        this.is_right = is_right;
        this.is_click = is_click;
        if(is_right == 1 && (curren+1) != count && is_click == 0){

            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //hideFragments(fragmentTransaction);
            com.example.android_dome.fgxwfragment q1 = new fgxwfragment();

            Bundle bundle = new Bundle();
            bundle.putInt("index", curren+1);
            bundle.putInt("accept_right",1);
            bundle.putInt("is_click", is_click);
            bundle.putString("type", start);
            q1.setArguments(bundle);



            fragmentTransaction.replace(R.id.fgxwquesitoncontent,q1);


            fragmentTransaction.commit();

        }
        numdisplay.setText(((curren+1)+"/"+count));

    }
    private void hideFragments(FragmentTransaction fragmentTransaction){
        if(fgxwfragment != null){
            fragmentTransaction.hide(fgxwfragment);
        }

    }

    public void delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);


        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}

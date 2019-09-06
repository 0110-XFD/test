package com.example.android_dome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class bstzctivity extends AppCompatActivity implements quesitonfragment.FragmentInteraction{
    Button bt_pre;
    Button bt_next;
    int curren;
    int count;
    View view;
    ImageView ret;
    int is_right;

    List<Question> listQuestion;
    private FragmentManager fragmentManager;
    quesitonfragment quesitonfragment;
    TextView expl;
    String explstring;
    TextView numdisplay;
    int is_click;
    //ViewPager viewPager;
    //List<Fragment> questionfragemnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bstzctivity);

        fragmentManager = getSupportFragmentManager();

        final QuestionDBService questionDBService = new QuestionDBService();

        listQuestion = questionDBService.getQuestion("比赛通则");



       // questionfragemnt = new ArrayList<Fragment>();
       // initquestionfragment();


//        viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(new questionApaper(getSupportFragmentManager()));
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        quesitonfragment = new quesitonfragment();
        if(is_right==1)
        {
            delay(1000);
        }
        fragmentTransaction.add(R.id.quesitoncontent,quesitonfragment);
        fragmentTransaction.show(quesitonfragment);
        fragmentTransaction.commit();

        bt_pre = findViewById(R.id.upquestion);
        bt_next  = findViewById(R.id.downquesiton);
        ret = findViewById(R.id.questiontitle_tv);
        expl = findViewById(R.id.expltv);
        numdisplay = findViewById(R.id.numdisplay);
        Bundle bundle = new Bundle();
        bundle.putInt("index", 0);
        bundle.putInt("accept_right",0);
        bundle.putInt("is_click", is_click);
        quesitonfragment.setArguments(bundle);

        bt_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_click = 1;
                if(curren == 0){
                    Toast.makeText(bstzctivity.this,"已经是第一题",Toast.LENGTH_SHORT).show();
                }else {
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //hideFragments(fragmentTransaction);

                    com.example.android_dome.quesitonfragment q1 = new quesitonfragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("index", curren-1);
                    bundle.putInt("accept_right",0);
                    bundle.putInt("is_click", is_click);
                    q1.setArguments(bundle);

                    fragmentTransaction.replace(R.id.quesitoncontent,q1);
                    fragmentTransaction.commit();


                }


            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_click = 1;
                if(curren == count -1){
                    Toast.makeText(bstzctivity.this, "已经是最后一题",Toast.LENGTH_SHORT).show();
                }else {

                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //hideFragments(fragmentTransaction);
                    com.example.android_dome.quesitonfragment q1 = new quesitonfragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("index", curren+1);
                    bundle.putInt("accept_right",0);
                    bundle.putInt("is_click", is_click);
                    q1.setArguments(bundle);



                    fragmentTransaction.replace(R.id.quesitoncontent,q1);


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
                TextView fgx = view.findViewById(R.id.fgx);
                TextView expl = view.findViewById(R.id.expl);
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
            com.example.android_dome.quesitonfragment q1 = new quesitonfragment();

            Bundle bundle = new Bundle();
            bundle.putInt("index", curren+1);
            bundle.putInt("accept_right",1);
            bundle.putInt("is_click", is_click);
            q1.setArguments(bundle);



            fragmentTransaction.replace(R.id.quesitoncontent,q1);


            fragmentTransaction.commit();

        }
        numdisplay.setText(((curren+1)+"/"+count));

    }
    private void hideFragments(FragmentTransaction fragmentTransaction){
        if(quesitonfragment != null){
            fragmentTransaction.hide(quesitonfragment);
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

//    public void initquestionfragment(){
//        for(int i=0;i< count;i++){
//            initBD(i);
//            questionfragemnt.add(new quesitonfragment());
//
//
//        }
//    }


//    public void initBD(int index){
//        questiontv = View.findViewById(R.id.questionnew);
//    radioButtons[0] = findViewById(R.id.answerAnew);
//    radioButtons[1] = findViewById(R.id.answerBnew);
//    radioButtons[2] = findViewById(R.id.answerCnew);
//    radioButtons[3] = findViewById(R.id.answerDnew);
//
//
//    Question q = listQuestion.get(index);
//
//        questiontv.setText(q.question);
//    expl.setText(q.explaination);
//    radioButtons[0].setText(q.quesA);
//    radioButtons[1].setText(q.quesB);
//    radioButtons[2].setText(q.quesC);
//    radioButtons[3].setText(q.quesD);







    }

//    class questionApaper extends FragmentPagerAdapter{
//        public questionApaper(FragmentManager fm){
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position){
//            return questionfragemnt.get(position);
//        }
//
//        @Override
//        public int getCount(){
//            return questionfragemnt.size();
//        }
//    }



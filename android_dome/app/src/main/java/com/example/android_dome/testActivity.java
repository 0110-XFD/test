package com.example.android_dome;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.ParcelUuid;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class testActivity extends AppCompatActivity implements testfragment.FragmentInteraction{
    Button bt_pre;
    Button bt_next;
    Button commit;
    ImageView ret;
    int curren;
    int count;
    View view;
    private FragmentManager fragmentManager;
    testfragment testfragment;
    TextView testnumdisplay;
    int TestId[] = new int[4];
    int useranswers[] = new int[4];
    int error = -1;
    int right = -1;
    int fail = -1;
    boolean success;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        int i = 0;
        while (true) {
            boolean exits = false;
            Random random = new Random();
            int num = random.nextInt(9) % (9 - 1 + 1) + 1;
            if (i == 4) {
                break;
            } else if (i == 0) {
                TestId[i] = num;
                i++;

            } else {
                for (int j = 0; j < i; j++) {
                    if (num == TestId[j]) {
                        exits = true;
                        break;
                    }
                }
                if (!exits) {
                    TestId[i] = num;
                    i++;
                }

            }
        }
        for(int j=0;j<4;j++){
            useranswers[j] = -1;
        }

        fragmentManager = getSupportFragmentManager();

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        testfragment = new testfragment();

        fragmentTransaction.add(R.id.testcontent,testfragment);
        fragmentTransaction.show(testfragment);
        fragmentTransaction.commit();

        bt_pre = findViewById(R.id.uptestquestion);
        bt_next = findViewById(R.id.downtestquesiton);
        testnumdisplay = findViewById(R.id.testnumdisplay);
        commit  = findViewById(R.id.commit);
        ret = findViewById(R.id.testtitle_tv);

        final Bundle bundle = new Bundle();
        bundle.putInt("index",0);
        bundle.putIntArray("ID_array",TestId);
        bundle.putIntArray("useranswer",useranswers);
        testfragment.setArguments(bundle);
        if(curren == count -1){
            commit.setVisibility(View.VISIBLE);
        }
        else
        {
            commit.setVisibility(View.INVISIBLE);
        }

        bt_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curren == 0){
                    Toast.makeText(testActivity.this,"已经是第一题",Toast.LENGTH_SHORT).show();
                }else {
                    final FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                    com.example.android_dome.testfragment t1 = new testfragment();

                    Bundle bundle1= new Bundle();
                    bundle.putInt("index",curren-1);
                    bundle.putIntArray("ID_array",TestId);
                    bundle.putIntArray("useranswer",useranswers);
                    t1.setArguments(bundle);
                    fragmentTransaction1.replace(R.id.testcontent,t1);
                    fragmentTransaction1.commit();

                }
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curren == count -1){
                    Toast.makeText(testActivity.this, "已经是最后一题",Toast.LENGTH_SHORT).show();
                }
                else {
                    final FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();

                    com.example.android_dome.testfragment t1 = new testfragment();

                    Bundle bundle1 = new Bundle();

                    bundle.putInt("index",curren+1);
                    bundle.putIntArray("ID_array",TestId);
                    bundle.putIntArray("useranswer",useranswers);
                    t1.setArguments(bundle);
                    fragmentTransaction1.replace(R.id.testcontent,t1);
                    fragmentTransaction1.commit();
                }
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 success = true;

                for(int i=0;i<count;i++){
                    if(useranswers[i] == -1){
                        success = false;
                        break;
                    }
                }
                if(success){
                    Intent intent = new Intent(testActivity.this,chcekService.class);
                    intent.putExtra("questionid",TestId);
                    intent.putExtra("answer",useranswers);
                    startService(intent);

                   MyReceiver receiver=new MyReceiver();
                    IntentFilter filter=new IntentFilter();
                    filter.addAction("com.example.android_dome.chcekService");
                    testActivity.this.registerReceiver(receiver,filter);
                    stopService(intent);
                }
                else {
                    AlertDialog dialog = new AlertDialog.Builder(testActivity.this)
                            .setIcon(R.mipmap.yiwen)
                            .setTitle("提交提示")
                            .setMessage("还有题目没有完成，就想交卷？")
                            .setNegativeButton("手抖，点错了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("对，太难了，做不下去了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(testActivity.this,chcekService.class);
                                    intent.putExtra("questionid",TestId);
                                    intent.putExtra("answer",useranswers);
                                    startService(intent);

                                    MyReceiver receiver=new MyReceiver();
                                    IntentFilter filter=new IntentFilter();
                                    filter.addAction("com.example.android_dome.chcekService");
                                    testActivity.this.registerReceiver(receiver,filter);
                                    stopService(intent);
                                }
                            }).create();
                    dialog.show();
                }
            }
        });
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(testActivity.this)
                        .setIcon(R.mipmap.yiwen)
                        .setTitle("离开提示")
                        .setMessage("答题还没结束，就想跑？")
                        .setNegativeButton("手抖，点错了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("对，我承认我菜，我要出去刷题了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(testActivity.this,"快去刷题",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                            }
                        }).create();
                dialog.show();
            }
        });




    }

    @Override
    public void send(int currenindex , int all, View view,int anser[]){
        this.count = all;
        this.curren = currenindex;
        this.view = view;
        this.useranswers = anser;
        testnumdisplay.setText((currenindex +1)+"/"+all);
        if(curren == count -1){
            commit.setVisibility(View.VISIBLE);
        }
        else
        {
            commit.setVisibility(View.INVISIBLE);
        }
    }
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
             error = bundle.getInt("error");
             right = bundle.getInt("right");
            fail = bundle.getInt("fail");
             if(error!=-1 && right!=-1 && success){
                 AlertDialog dialog = new AlertDialog.Builder(testActivity.this)
                         .setIcon(R.mipmap.sucess)
                         .setTitle("答卷情况")
                         .setMessage("错误题数："+error+"\n"+"正确题数："+right+"\n"+"温馨提示：错题已加入错题中，可查看")
                         .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 finish();
                             }
                         }).create();
                 dialog.show();
             }else if(error!=-1 && right!=-1 && (!success)){
                 AlertDialog dialog = new AlertDialog.Builder(testActivity.this)
                         .setIcon(R.mipmap.sb)
                         .setTitle("答卷情况")
                         .setMessage("未答题数："+fail+"\n"+"错误题数："+error+"\n"+"正确题数："+right+"\n"+"温馨提示：错题已加入错题中，可查看")
                         .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 finish();
                             }
                         }).create();
                 dialog.show();
             }

        }
    }
}

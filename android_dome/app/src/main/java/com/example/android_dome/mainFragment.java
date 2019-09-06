package com.example.android_dome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class mainFragment extends Fragment {


    Activity mactivity;


    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       final View view = inflater.inflate(R.layout.main_l, container,false);
       final Context context = view.getContext();
        FloatingActionButton fb = view.findViewById(R.id.bstz);
        FloatingActionButton fgxwfb = view.findViewById(R.id.fgxw);
        FloatingActionButton testfb = view.findViewById(R.id.test_fab_button);
        FloatingActionButton ybgdfb = view.findViewById(R.id.ybgd);
        FloatingActionButton bsdyfb = view.findViewById(R.id.bsdy);


       fb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context,bstzctivity.class);

               startActivity(intent);

           }
       });

       fgxwfb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, fgxwactivity.class);
               intent.putExtra("start","犯规行为");
               startActivity(intent);
           }
       });

       testfb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog dialog = new AlertDialog.Builder(context)
                       .setIcon(R.mipmap.yiwen)
                       .setTitle("这是测试,不能随便点！")
                       .setMessage("是不是手抖点到的？")
                       .setNegativeButton("不是,我是真的想测试", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Intent intent = new Intent(context,testActivity.class);
                               startActivity(intent);
                           }
                       })
                       .setPositiveButton("对对对,都怪我手抖", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(context,"快去刷题",Toast.LENGTH_SHORT).show();
                               dialog.dismiss();
                           }
                       }).create();
               dialog.show();

           }
       });

        ybgdfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, fgxwactivity.class);
                intent.putExtra("start","一般规定");
                startActivity(intent);
            }
        });

        bsdyfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,fgxwactivity.class);
                intent.putExtra("start","比赛定义");
                startActivity(intent);
            }
        });

       return view;
    }






}

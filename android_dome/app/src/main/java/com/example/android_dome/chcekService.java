package com.example.android_dome;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

public class chcekService extends Service {
    private int id[] = new int[4];
    private int answer[] = new int[4];
    private int error = 0;
    private int right = 0;
    private int fail = 0;
    List<Question> list;
    @Override
    public void onCreate(){
        super.onCreate();



    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        id = intent.getIntArrayExtra("questionid");
        answer = intent.getIntArrayExtra("answer");
        QuestionDBService questionDBService = new QuestionDBService();
        list = questionDBService.getTestQursiton(id);
        for(int i=0;i<4;i++){
            if(answer[i] == -1){
                fail++;

            }else if(answer[i] != list.get(i).answer){
                questionDBService.inserterror(answer[i],Integer.toString(list.get(i).ID));
                error++;
            }
            else {
                right++;
            }
        }

        Intent intent1 = new Intent();
        intent1.putExtra("error",error);
        intent1.putExtra("right",right);
        intent1.putExtra("fail",fail);
        intent1.setAction("com.example.android_dome.chcekService");
        sendBroadcast(intent1);

        return super.onStartCommand(intent,flags,startId);
    }
}

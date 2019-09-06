package com.example.android_dome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class fgxwfragment extends Fragment {
    private int count;
    private int current;
    List<Question> listQuestion;
    TextView questiontv;
    RadioButton[] radioButtons = new RadioButton[4];
    Button bt_prev;
    Button bt_next;
    private int accept = 0;
    TextView expl;
    TextView fgx;
    private int is_right = 0;
    private int accept_right;
    String ID;
    private int is_click = 0;
    ImageView fgxwtv;
    String type;

    private fgxwfragment.FragmentInteraction listterner;

//    在fragment中定义一个内部回调接口，再让包含该fragment的activity实现该回调接口，
//    这样fragment即可调用该回调方法将数据传给activity。

    public interface FragmentInteraction{
        void send(int curren , int count, View view,String str,int is_right,int is_click);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saveInstanceState) {


        final View view = inflater.inflate(R.layout.fgxwcontent, viewGroup, false);
        QuestionDBService questionDBService = new QuestionDBService();
        if(accept_right == 1)
        {
            delay(1000);
        }
         if(type.equals("犯规行为")){
             listQuestion = questionDBService.getQuestion("犯规行为");
         }else if(type.equals("一般规定")){
             listQuestion = questionDBService.getQuestion("一般规定");
         }else if(type.equals("比赛定义")){
             listQuestion = questionDBService.getQuestion("比赛定义");
         }


        count = listQuestion.size();

        initBD(accept,view);
        return view;
    }

    public void initBD(int index, final View view){

        questiontv = view.findViewById(R.id.questionfgxw);
        radioButtons[0] = view.findViewById(R.id.answerAfgxw);
        radioButtons[1] = view.findViewById(R.id.answerBfgxw);
        radioButtons[2] = view.findViewById(R.id.answerCfgxw);
        radioButtons[3] = view.findViewById(R.id.answerDfgxw);
        expl = view.findViewById(R.id.explfgxw);
        fgx = view.findViewById(R.id.fgxfgxw);
        fgxwtv = view.findViewById(R.id.fgxwimg);
        current = index;



        final Question q = listQuestion.get(index);
        ID = Integer.toString(q.ID);
        String th = Integer.toString(index+1);

        questiontv.setText((th + "、"+q.question));
        questiontv.setTextColor(getResources().getColor(R.color.black));

        radioButtons[0].setText(q.quesA);
        radioButtons[1].setText(q.quesB);
        radioButtons[2].setText(q.quesC);
        radioButtons[3].setText(q.quesD);
        if(q.ID != 4 && q.ID != 5){
            fgxwtv.setImageDrawable(null);
        }
        else if(q.ID == 4){
            fgxwtv.setImageResource(R.drawable.jq);
        }
        else if(q.ID == 5){
            fgxwtv.setImageResource(R.drawable.zd);
        }

        if(q.userselectanswer == -1)
        {
            for(int i=0;i<4;i++){
                radioButtons[i].setButtonDrawable(getResources().getDrawable(R.drawable.radiobutton_off_background));
                radioButtons[i].setChecked(false);
                radioButtons[i].setTextColor(getResources().getColor(R.color.black));
                radioButtons[i].setClickable(true);
            }
        }
        else {
            if(q.userselectanswer == q.answer){
                radioButtons[q.answer].setTextColor(getResources().getColor(R.color.right));
                radioButtons[q.answer].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                for(int i=0;i<4;i++){
                    radioButtons[i].setClickable(false);
                }
                is_right = 1;
            }
            else {
                final Context context = view.getContext();
                radioButtons[q.answer].setTextColor(getResources().getColor(R.color.right));
                radioButtons[q.answer].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                radioButtons[q.userselectanswer].setTextColor(getResources().getColor(R.color.error));
                radioButtons[q.userselectanswer].setButtonDrawable(getResources().getDrawable(R.drawable.error));
                fgx.setVisibility(view.VISIBLE);
                expl.setText(q.explaination);
                for(int i=0;i<4;i++){
                    radioButtons[i].setClickable(false);
                }
                is_right = 0;

            }

        }



        radioButtons[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QuestionDBService dbService = new QuestionDBService();
                if(0 != q.answer){
                    final Context context = view.getContext();
                    radioButtons[0].setTextColor(getResources().getColor(R.color.error));
                    radioButtons[q.answer].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                    radioButtons[q.answer].setTextColor(getResources().getColor(R.color.right));
                    radioButtons[0].setButtonDrawable(getResources().getDrawable(R.drawable.error));
                    fgx.setVisibility(view.VISIBLE);
                    expl.setText(q.explaination);
                    for(int i=0;i<4;i++){
                        if(i!=0){
                            radioButtons[i].setClickable(false);
                        }
                    }
                    is_right = 0;
                    if (dbService.write_userselect(0,ID) && dbService.inserterror(0,ID))
                    {

                        Toast.makeText(context,"答题错误，可在错题中查看该题",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    final Context context = view.getContext();
                    radioButtons[0].setTextColor(getResources().getColor(R.color.right));
                    radioButtons[0].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                    for(int i=0;i<4;i++){
                        if(i!=0){
                            radioButtons[i].setClickable(false);
                        }
                    }
                    is_right = 1;
                    if (dbService.write_userselect(0,ID)){
                        Toast.makeText(context,"答题正确",Toast.LENGTH_SHORT).show();
                    }

                }
                listterner.send( current,count,view,q.explaination,is_right,is_click);
            }
        });
        radioButtons[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QuestionDBService dbService = new QuestionDBService();

                if(1 != q.answer){
                    radioButtons[1].setTextColor(getResources().getColor(R.color.error));
                    radioButtons[q.answer].setTextColor(getResources().getColor(R.color.right));
                    fgx.setVisibility(view.VISIBLE);
                    expl.setText(q.explaination);
                    radioButtons[q.answer].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                    radioButtons[1].setButtonDrawable(getResources().getDrawable(R.drawable.error));
                    for(int i=0;i<4;i++){
                        if(i!=1){
                            radioButtons[i].setClickable(false);
                        }
                    }
                    is_right = 0;
                    if (dbService.write_userselect(1,ID) && dbService.inserterror(1,ID))
                    {
                        final Context context = view.getContext();
                        Toast.makeText(context,"答题错误，可在错题中查看该题",Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {

                    radioButtons[1].setTextColor(getResources().getColor(R.color.right));
                    radioButtons[1].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                    for(int i=0;i<4;i++){
                        if(i!=1){
                            radioButtons[i].setClickable(false);
                        }
                    }
                    is_right = 1;
                    if (dbService.write_userselect(1,ID)){
                        final Context context = view.getContext();
                        Toast.makeText(context,"答题正确",Toast.LENGTH_SHORT).show();

                    }

                }
                listterner.send( current,count,view,q.explaination,is_right,is_click);
            }
        });

        radioButtons[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QuestionDBService dbService = new QuestionDBService();

                if(2 != q.answer){
                    radioButtons[2].setTextColor(getResources().getColor(R.color.error));
                    radioButtons[q.answer].setTextColor(getResources().getColor(R.color.right));
                    fgx.setVisibility(view.VISIBLE);
                    expl.setText(q.explaination);
                    radioButtons[q.answer].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                    radioButtons[2].setButtonDrawable(getResources().getDrawable(R.drawable.error));
                    for(int i=0;i<4;i++){
                        if(i!=2){
                            radioButtons[i].setClickable(false);
                        }
                    }
                    is_right = 0;
                    if (dbService.write_userselect(2,ID) && dbService.inserterror(2,ID))
                    {
                        final Context context = view.getContext();
                        Toast.makeText(context,"答题错误，可在错题中查看该题",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {

                    radioButtons[2].setTextColor(getResources().getColor(R.color.right));
                    radioButtons[2].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                    for(int i=0;i<4;i++){
                        if(i!=2){
                            radioButtons[i].setClickable(false);
                        }
                    }
                    is_right = 1;
                    if (dbService.write_userselect(2,ID) )
                    {
                        final Context context = view.getContext();
                        Toast.makeText(context,"答题正确",Toast.LENGTH_SHORT).show();
                    }


                }
                listterner.send( current,count,view,q.explaination,is_right,is_click);
            }
        });

        radioButtons[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QuestionDBService dbService = new QuestionDBService();

                if(3 != q.answer){
                    radioButtons[3].setTextColor(getResources().getColor(R.color.error));
                    radioButtons[q.answer].setTextColor(getResources().getColor(R.color.right));
                    fgx.setVisibility(view.VISIBLE);
                    expl.setText(q.explaination);
                    radioButtons[q.answer].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                    radioButtons[3].setButtonDrawable(getResources().getDrawable(R.drawable.error));
                    for(int i=0;i<4;i++){
                        if(i!=3){
                            radioButtons[i].setClickable(false);
                        }
                    }
                    is_right = 0;
                    if (dbService.write_userselect(3,ID) && dbService.inserterror(3,ID))
                    {
                        final Context context = view.getContext();
                        Toast.makeText(context,"答题错误，可在错题中查看该题",Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {

                    radioButtons[3].setTextColor(getResources().getColor(R.color.right));
                    radioButtons[3].setButtonDrawable(getResources().getDrawable(R.drawable.right));
                    for(int i=0;i<4;i++){
                        if(i!=3){
                            radioButtons[i].setClickable(false);
                        }
                    }
                    is_right = 1;
                    if (dbService.write_userselect(3,ID) )
                    {
                        final Context context = view.getContext();
                        Toast.makeText(context,"答题正确",Toast.LENGTH_SHORT).show();
                    }

                }
                listterner.send( current,count,view,q.explaination,is_right,is_click);
            }
        });
        listterner.send(index,count,view,q.explaination,is_right,is_click);


    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        accept = (int) getArguments().get("index");
        accept_right = (int) getArguments().get("accept_right");
        is_click = (int) getArguments().get("is_click");
        type = (String) getArguments().get("type");

        if (activity instanceof fgxwfragment.FragmentInteraction) {
            listterner = (fgxwfragment.FragmentInteraction) activity;
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

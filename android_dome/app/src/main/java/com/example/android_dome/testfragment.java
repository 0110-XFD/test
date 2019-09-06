package com.example.android_dome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class testfragment extends Fragment {
    private int test_count;
    private int current;
    RadioButton[] radioButtons = new RadioButton[4];
    TextView questiontv;
    ImageView questioniv;
    int useranswers[] = new int[3];
    int testid[] = new int[3];
    int k;
    private int accept = 0;
    private testfragment.FragmentInteraction listterner;
    QuestionDBService questionDBService = new QuestionDBService();
    List<Question> listTestQuestion ;
    Context context;


    public interface FragmentInteraction{
        void send(int curren , int Tets_count, View view,int anserw[]);
    }



    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle saveInstanceState){
        final View view = layoutInflater.inflate(R.layout.testcontent,viewGroup,false);
        context = view.getContext();

        listTestQuestion = questionDBService.getTestQursiton(testid);
        test_count = listTestQuestion.size();
        initBD(accept,view);
        return view;
    }

    public void initBD(final int index, final View view){

        questiontv = view.findViewById(R.id.testquestion);
        questioniv = view.findViewById(R.id.testimg);
        radioButtons[0] = view.findViewById(R.id.testanswerAnew);
        radioButtons[1] = view.findViewById(R.id.testanswerBnew);
        radioButtons[2] = view.findViewById(R.id.testanswerCnew);
        radioButtons[3] = view.findViewById(R.id.testanswerDnew);
        current = index;

        final Question q = listTestQuestion.get(index);
        String questionth = Integer.toString(index+1);
        questiontv.setText(questionth+"、"+q.question);
        questiontv.setTextColor(getResources().getColor(R.color.black));
        radioButtons[0].setText(q.quesA);
        radioButtons[1].setText(q.quesB);
        radioButtons[2].setText(q.quesC);
        radioButtons[3].setText(q.quesD);


        if(q.ID !=4 && q.ID !=5){
            questioniv.setImageDrawable(null);
        }
        else if(q.ID == 4){
            questioniv.setImageResource(R.drawable.jq);
        }else if(q.ID == 5){
            questioniv.setImageResource(R.drawable.zd);
        }
        if(useranswers[index] != -1){
            radioButtons[useranswers[index]].setChecked(true);
        }

        radioButtons[0].setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   if(radioButtons[0].isChecked()){
                                                       useranswers[index] = 0;
                                                       for(int i=0;i<4;i++){
                                                           if(i!=0){
                                                               radioButtons[i].setChecked(false);
                                                           }
                                                       }
                                                   }
                                               }
                                           });

        radioButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButtons[1].isChecked()){
                    useranswers[index] = 1;
                    for(int i=0;i<4;i++){
                        if(i!=1){
                            radioButtons[i].setChecked(false);
                        }
                    }
                }
            }
        });
        radioButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButtons[2].isChecked()){
                    useranswers[index] = 2;
                    for(int i=0;i<4;i++){
                        if(i!=2){
                            radioButtons[i].setChecked(false);
                        }
                    }
                }
            }
        });
        radioButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButtons[3].isChecked()){
                    useranswers[index] = 3;
                    for(int i=0;i<4;i++){
                        if(i!=3){
                            radioButtons[i].setChecked(false);
                        }
                    }
                }
            }
        });


        listterner.send(current,test_count,view,useranswers);
        }


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        accept = (int) getArguments().get("index");
        testid =  getArguments().getIntArray("ID_array");
        useranswers = getArguments().getIntArray("useranswer");
        if(activity instanceof testfragment.FragmentInteraction){
            listterner = (testfragment.FragmentInteraction) activity;
        }
    }


}

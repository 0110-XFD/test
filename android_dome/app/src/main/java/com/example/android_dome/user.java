package com.example.android_dome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;


public class user extends Fragment {
    List<Question> listErrorQuestion;
    ListView tverror;
    List<String> errordisplay;
    List<String> IDlist;
    Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user, container,false);
        QuestionDBService dbService = new QuestionDBService();
        context = view.getContext();
        listErrorQuestion = dbService.getErrorQuestion();
        errordisplay = new ArrayList<String>();
        IDlist = new ArrayList<String>();

        for(int i =0;i<listErrorQuestion.size();i++){
            Question q = listErrorQuestion.get(i);
            errordisplay.add(q.ID + "、"+ q.question);
            IDlist.add(Integer.toString(q.ID));
        }

        tverror = view.findViewById(R.id.errorlist);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,errordisplay);
        tverror.setAdapter(adapter);

        tverror.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ErrordetailActivity.class);
                String ID = IDlist.get(position);
                intent.putExtra("ID",ID);

                startActivity(intent);

            }
        });



        return view;
    }
}

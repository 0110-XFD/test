package com.example.android_dome;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.PortUnreachableException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionDBService {
    private SQLiteDatabase db;
    String DB_PATH = "/data/data/com.example.android_dome/databases/";

    String DB_NAME = "androiddomeDBnew.db";
    Context context;




    public QuestionDBService(){
        db = SQLiteDatabase.openDatabase("/data/data/com.example.android_dome/databases/androiddomeDBnew.db",null,SQLiteDatabase.OPEN_READWRITE);
    }
    public QuestionDBService(Context context){
        this.context = context;
        db = SQLiteDatabase.openDatabase("/data/data/com.example.android_dome/databases/androiddomeDBnew.db",null,SQLiteDatabase.OPEN_READWRITE);

    }
    public List<Question> getQuestion(String questiontype){
        List<Question> listQuestion = new ArrayList<Question>();


        Cursor cursor = db.rawQuery("select * from Question where type="+"'"+questiontype+"'",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int count = cursor.getCount();

            for (int i =0;i<count;i++){
                cursor.moveToPosition(i);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("ID"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.quesA = cursor.getString(cursor.getColumnIndex("quesA"));
                question.quesB = cursor.getString(cursor.getColumnIndex("quesB"));
                question.quesC = cursor.getString(cursor.getColumnIndex("quesC"));
                question.quesD = cursor.getString(cursor.getColumnIndex("quesD"));
                question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                question.explaination = cursor.getString(cursor.getColumnIndex("expl"));
                question.userselectanswer = cursor.getInt(cursor.getColumnIndex("useranserw"));
                listQuestion.add(question);
            }
        }
        cursor.close();
        return listQuestion;
    }

    public boolean write_userselect(int select, String ID){
        int is_suc = -1;
        ContentValues values = new ContentValues();
        values.put("useranserw",select);
        db.update("Question",values,"ID=?", new String[]{ID});
        Cursor cursor = db.rawQuery("select useranserw from Question where ID = "+ID,null);
        cursor.moveToPosition(0);
        is_suc = cursor.getInt(cursor.getColumnIndex("useranserw"));
        if(is_suc == -1){
            return false;
        }
        else {
            cursor.close();
            //updateDB();
            return true;
        }
    }

    public boolean inserterror(int select, String ID){
        int id = Integer.parseInt(ID);
        Cursor query = db.rawQuery("select ID from  QuestionError",null);
        query.moveToPosition(0);
        for(int i =0;i<query.getCount();i++){
            if(id == query.getInt(query.getColumnIndex("ID")))
            {
                return false;
            }
        }
        Cursor cursor = db.rawQuery("select * from Question where ID = "+ id,null);
        cursor.moveToPosition(0);
        String question = cursor.getString(cursor.getColumnIndex("question"));
        String quesA = cursor.getString(cursor.getColumnIndex("quesA"));
        String quesB = cursor.getString(cursor.getColumnIndex("quesB"));
        String quesC = cursor.getString(cursor.getColumnIndex("quesC"));
        String quesD = cursor.getString(cursor.getColumnIndex("quesD"));
        int answer = cursor.getInt(cursor.getColumnIndex("answer"));
        String expl = cursor.getString(cursor.getColumnIndex("expl"));
        String type = cursor.getString(cursor.getColumnIndex("type"));


        try {
            ContentValues values = new ContentValues();
            values.put("ID",id);
            values.put("question",question);
            values.put("quesA",quesA);
            values.put("quesB",quesB);
            values.put("quesC",quesC);
            values.put("quesD",quesD);
            values.put("answer",answer);
            values.put("expl",expl);
            values.put("useranserw",select);
            db.insertOrThrow("QuestionError",null,values);
            cursor.close();
            //updateDB();
            return true;

        }catch (SQLiteConstraintException e){
            e.printStackTrace();
            return false;
        }

    }
//    public void updateDB(){
//        try {
//            InputStream in = context.getAssets().open(DB_NAME);
//            OutputStream out = new FileOutputStream(DB_PATH + DB_NAME);
//            byte[]  buffer = new byte[in.available()];
//            int length = in.read(buffer);
//            if (length > 0){
//                out.write(buffer, 0 ,length);
//            }
//
//            out.flush();
//            out.close();
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }

    public List<Question>getErrorQuestion(){
        List<Question> listErrorQuestion = new ArrayList<Question>();


        Cursor cursor = db.rawQuery("select * from QuestionError",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int count = cursor.getCount();

            for (int i =0;i<count;i++){
                cursor.moveToPosition(i);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("ID"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.quesA = cursor.getString(cursor.getColumnIndex("quesA"));
                question.quesB = cursor.getString(cursor.getColumnIndex("quesB"));
                question.quesC = cursor.getString(cursor.getColumnIndex("quesC"));
                question.quesD = cursor.getString(cursor.getColumnIndex("quesD"));
                question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                question.explaination = cursor.getString(cursor.getColumnIndex("expl"));
                question.userselectanswer = cursor.getInt(cursor.getColumnIndex("useranserw"));
                listErrorQuestion.add(question);
            }
        }
        cursor.close();
        return listErrorQuestion;

    }

    public Question getErrorquetion(String ID){
        Question question = new Question();
        Cursor cursor = db.rawQuery("select * from QuestionError where ID = "+ID,null);
        cursor.moveToPosition(0);
        question.ID = cursor.getInt(cursor.getColumnIndex("ID"));
        question.question = cursor.getString(cursor.getColumnIndex("question"));
        question.quesA = cursor.getString(cursor.getColumnIndex("quesA"));
        question.quesB = cursor.getString(cursor.getColumnIndex("quesB"));
        question.quesC = cursor.getString(cursor.getColumnIndex("quesC"));
        question.quesD = cursor.getString(cursor.getColumnIndex("quesD"));
        question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
        question.explaination = cursor.getString(cursor.getColumnIndex("expl"));
        question.userselectanswer = cursor.getInt(cursor.getColumnIndex("useranserw"));

        return question;

    }

    public boolean deteleErrorQuestion(String ID){

        Cursor cursorbefore = db.rawQuery("select * from QuestionError",null);
        int countbefore = cursorbefore.getCount();

        db.delete("QuestionError","ID=?",new String[]{ID});
        Cursor cursorafter = db.rawQuery("select * from QuestionError",null);
        int countafter = cursorafter.getCount();
        if(countbefore == countafter +1){
            return true;

        }
        else
        {
            return false;
        }

    }

    public List<Question> getTestQursiton(int id[]){
        List<Question> listTestQuestion = new ArrayList<Question>();

            for(int index =0;index<4;index++){
                Cursor cursor = db.rawQuery("select * from Question where ID="+id[index],null);
                cursor.moveToPosition(0);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("ID"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.quesA = cursor.getString(cursor.getColumnIndex("quesA"));
                question.quesB = cursor.getString(cursor.getColumnIndex("quesB"));
                question.quesC = cursor.getString(cursor.getColumnIndex("quesC"));
                question.quesD = cursor.getString(cursor.getColumnIndex("quesD"));
                question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                question.explaination = cursor.getString(cursor.getColumnIndex("expl"));
                question.userselectanswer = cursor.getInt(cursor.getColumnIndex("useranserw"));
                listTestQuestion.add(question);

            }

            return listTestQuestion;

    }

}

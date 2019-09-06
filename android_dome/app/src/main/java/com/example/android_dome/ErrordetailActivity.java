package com.example.android_dome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ErrordetailActivity extends AppCompatActivity {
    ImageView lvteturn;
    TextView errortv;
    TextView errorexpl;
    Button errordelete;
    String ID;
    ImageView errorimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errordetail);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        intiBD(ID);
    }

    public void intiBD(final String ID){
        lvteturn = findViewById(R.id.errortitle_tv);
        errortv = findViewById(R.id.errorquetion);
        errorexpl = findViewById(R.id.errordetail);
        errordelete = findViewById(R.id.errordelete);
        errorimg = findViewById(R.id.errorimg);

        final QuestionDBService dbService = new QuestionDBService();
         Question q = dbService.getErrorquetion(ID);

         errortv.setText(q.ID + "、" + q.question);
         if(q.ID!=4 && q.ID!=5){
             errorimg.setImageDrawable(null);
         }
         else if(q.ID == 4){
             errorimg.setImageResource(R.drawable.jq);
         }
         else if(q.ID ==5){
             errorimg.setImageResource(R.drawable.zd);
         }

         errorexpl.setText("-------------详细解答-------------\n"+q.explaination);

         lvteturn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });

         errordelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                if(dbService.deteleErrorQuestion(ID)){
                    Toast.makeText(ErrordetailActivity.this,"已去除",Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("is",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("is",1);
                    editor.commit();

                    finish();

                }
                else {
                    Toast.makeText(ErrordetailActivity.this,"去除失败",Toast.LENGTH_SHORT).show();
                }

             }
         });





    }
}

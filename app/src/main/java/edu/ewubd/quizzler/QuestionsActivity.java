package edu.ewubd.quizzler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    private ListView listQuestion;
    private ArrayList<QuestionItem> questions;
    private QuestionsAdapter questionAdapter;
    Button submitB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        submitB = findViewById(R.id.submitB);

        listQuestion = findViewById(R.id.questionView);
        questions = new ArrayList<>();

        questionAdapter = new QuestionsAdapter(this, questions);
        listQuestion.setAdapter(questionAdapter);

        for (int i=0; i<10; i++) {
            QuestionItem e = new QuestionItem("What is C++", "A", "B", "C", "D");
            questions.add(e);
        }

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsActivity.this, ScoreActivity.class);
                startActivity(i);
            }
        });
    }
}

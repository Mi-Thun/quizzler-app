package edu.ewubd.quizzler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QuestionsActivity extends AppCompatActivity {

    private ListView listQuestion;
    private ArrayList<QuestionItem> questions;
    private QuestionsAdapter questionAdapter;
    private TextView tvTimer;
    private Handler handler;

    private static final int TIMER_INTERVAL = 1000;
    private static final int TOTAL_TIME_IN_MILLIS = 1500000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        String categoryName = getIntent().getStringExtra("categoryName");

        tvTimer = findViewById(R.id.tvTimer);

        listQuestion = findViewById(R.id.questionView);
        questions = new ArrayList<>();

//        for (int i=0; i<10; i++) {
//            QuestionItem e = new QuestionItem("What is C++", "Programming Language", "Food Name", "", "DDDDDDDDDDDDDDDDDDDD");
//            questions.add(e);
//        }

        questions.add(new QuestionItem("Python", "What is Python?", "Programming Language", "Reptile", "Fruit", "Animal"));
        questions.add(new QuestionItem("java", "Which of the following is NOT a Python data type?", "Class", "Integer", "List", "String"));
        questions.add(new QuestionItem("Python", "What is the file extension for Python source code files?", ".py", ".pt", ".pg", ".pn"));
        questions.add(new QuestionItem("Python", "Which keyword is used to define a function in Python?", "def", "function", "define", "func"));
        questions.add(new QuestionItem("ruby", "Which Python library is commonly used for data manipulation and analysis?", "Pandas", "Matplotlib", "Numpy", "Scikit-learn"));
        questions.add(new QuestionItem("Python", "Which Python data type is used to store an ordered collection of items?", "List", "Tuple", "Set", "Dictionary"));
        questions.add(new QuestionItem("Python", "What does the 'len()' function do in Python?", "Return the length of a sequence", "Convert a value to lowercase", "Generate a random number", "Check if a value is even"));
        questions.add(new QuestionItem("Python", "In Python, how do you open and read from a file?", "open('file.txt', 'r')", "read_file('file.txt')", "file.open('file.txt')", "load('file.txt')"));
        questions.add(new QuestionItem("Python", "Which loop is used for iterating over a sequence (such as a list or tuple) in Python?", "for loop", "while loop", "repeat loop", "do-while loop"));
        questions.add(new QuestionItem("c++", "What is the result of the expression '3 + 5 * 2' in Python?", "13", "16", "11", "10"));

        List<QuestionItem> filteredQuestions = new ArrayList<>();
        for (QuestionItem question : questions) {
            if (question.getCategory().equalsIgnoreCase(categoryName)) {
                filteredQuestions.add(question);
            }
        }

        questionAdapter = new QuestionsAdapter(this, filteredQuestions);
        listQuestion.setAdapter(questionAdapter);

        startTimer();

        findViewById(R.id.submitB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsActivity.this, ResultActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void startTimer() {
        Executor executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            private long remainingTime = TOTAL_TIME_IN_MILLIS;
            @Override
            public void run() {
                if (remainingTime <= 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(QuestionsActivity.this, ResultActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    long minutes = remainingTime / 60000;
                    long seconds = (remainingTime % 60000) / 1000;
                    final String timerText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvTimer.setText(timerText);
                        }
                    });

                    remainingTime -= TIMER_INTERVAL;
                    handler.postDelayed(this, TIMER_INTERVAL);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

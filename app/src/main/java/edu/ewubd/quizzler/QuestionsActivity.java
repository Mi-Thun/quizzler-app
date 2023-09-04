package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class QuestionsActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private ListView listQuestion;
    private ArrayList<QuestionItem> questions;
    private QuestionsAdapter questionAdapter;
    private TextView tvTimer;
    private Handler handler;
    private static final int TIMER_INTERVAL = 1000;
    private static final int TOTAL_TIME_IN_MILLIS = 1500000;
    private long elapsedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        AdRequest adRequest = new AdRequest.Builder().build();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }
        });

        String categoryName = getIntent().getStringExtra("categoryName");

        tvTimer = findViewById(R.id.tvTimer);

        listQuestion = findViewById(R.id.questionView);
        questions = new ArrayList<>();

        questions.add(new QuestionItem("Python", "What is Python?", "Programming Language", "Reptile", "Fruit", "Animal", "A"));
        questions.add(new QuestionItem("java", "Which of the following is NOT a Python data type?", "Class", "Integer", "List", "String", "B"));
        questions.add(new QuestionItem("Python", "What is the file extension for Python source code files?", ".py", ".pt", ".pg", ".pn", "C"));
        questions.add(new QuestionItem("Python", "Which keyword is used to define a function in Python?", "def", "function", "define", "func", "D"));
        questions.add(new QuestionItem("ruby", "Which Python library is commonly used for data manipulation and analysis?", "Pandas", "Matplotlib", "Numpy", "Scikit-learn", "A"));
        questions.add(new QuestionItem("Python", "Which Python data type is used to store an ordered collection of items?", "List", "Tuple", "Set", "Dictionary", "B"));
        questions.add(new QuestionItem("Python", "What does the 'len()' function do in Python?", "Return the length of a sequence", "Convert a value to lowercase", "Generate a random number", "Check if a value is even","C"));
        questions.add(new QuestionItem("Python", "In Python, how do you open and read from a file?", "open('file.txt', 'r')", "read_file('file.txt')", "file.open('file.txt')", "load('file.txt')", "D"));
        questions.add(new QuestionItem("Python", "Which loop is used for iterating over a sequence (such as a list or tuple) in Python?", "for loop", "while loop", "repeat loop", "do-while loop", "A"));
        questions.add(new QuestionItem("c++", "What is the result of the expression '3 + 5 * 2' in Python?", "13", "16", "11", "10", "B"));

        QuestionDB questionDB = new QuestionDB(this);
        Cursor cursor = questionDB.selectQuestionsByCategory(categoryName);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex("Question"));
                @SuppressLint("Range") String optionA = cursor.getString(cursor.getColumnIndex("OptionA"));
                @SuppressLint("Range") String optionB = cursor.getString(cursor.getColumnIndex("OptionB"));
                @SuppressLint("Range") String optionC = cursor.getString(cursor.getColumnIndex("OptionC"));
                @SuppressLint("Range") String optionD = cursor.getString(cursor.getColumnIndex("OptionD"));
                @SuppressLint("Range") String answer = cursor.getString(cursor.getColumnIndex("Answer"));

                questions.add(new QuestionItem(categoryName, question, optionA, optionB, optionC, optionD, answer));

            } while (cursor.moveToNext());
            cursor.close();
        }

        Collections.shuffle(questions);

        List<QuestionItem> filteredQuestions = new ArrayList<>();
        for (QuestionItem question : questions) {
            if (question.getCategory().equalsIgnoreCase(categoryName) && filteredQuestions.size() < 25) {
                filteredQuestions.add(question);
                Collections.shuffle(filteredQuestions);            }
        }

        if (filteredQuestions.size() == 0) {
            Toast.makeText(this, "No questions found for this category.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
            finish();
        } else {
            questionAdapter = new QuestionsAdapter(this, filteredQuestions);
            listQuestion.setAdapter(questionAdapter);
        }

        startTimer();

        findViewById(R.id.submitB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsActivity.this, ResultActivity.class);
                i.putExtra("SCORE", questionAdapter.calculateScore());
                i.putExtra("correct", questionAdapter.getCorrect());
                i.putExtra("wrong", questionAdapter.getWromg());
                i.putExtra("not_answer", questionAdapter.getNotAnswe());
                i.putExtra("time", elapsedTime);
                startActivity(i);
                finish();
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(QuestionsActivity.this);
                }
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
                            Intent i = new Intent(QuestionsActivity.this, ResultActivity.class);
                            i.putExtra("SCORE", questionAdapter.calculateScore());
                            i.putExtra("correct", questionAdapter.getCorrect());
                            i.putExtra("wrong", questionAdapter.getWromg());
                            i.putExtra("not_answer", questionAdapter.getNotAnswe());
                            i.putExtra("time", elapsedTime = TOTAL_TIME_IN_MILLIS);
                            startActivity(i);
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
                    elapsedTime = TOTAL_TIME_IN_MILLIS - remainingTime;
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

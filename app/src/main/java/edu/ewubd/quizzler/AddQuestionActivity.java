package edu.ewubd.quizzler;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddQuestionActivity extends AppCompatActivity {
    EditText questionEditText, optionAEditText, optionBEditText, optionCEditText, optionDEditText, answerEditText, categoryEditText;
    Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        questionEditText = findViewById(R.id.Question);
        optionAEditText = findViewById(R.id.OptionA);
        optionBEditText = findViewById(R.id.OptionB);
        optionCEditText = findViewById(R.id.OptionC);
        optionDEditText = findViewById(R.id.OptionD);
        answerEditText = findViewById(R.id.Answer);
        categoryEditText = findViewById(R.id.Category);
        saveButton = findViewById(R.id.btnSave);
        cancelButton = findViewById(R.id.btnCancel);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestion();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveQuestion() {
        String question = questionEditText.getText().toString();
        String optionA = optionAEditText.getText().toString();
        String optionB = optionBEditText.getText().toString();
        String optionC = optionCEditText.getText().toString();
        String optionD = optionDEditText.getText().toString();
        String answer = answerEditText.getText().toString();
        String category = categoryEditText.getText().toString();

        QuestionDB questionDB = new QuestionDB(this);
        questionDB.insertQuestion(question, optionA, optionB, optionC, optionD, answer, category);

        Toast.makeText(this, "Question saved successfully", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void clearFields() {
        questionEditText.setText("");
        optionAEditText.setText("");
        optionBEditText.setText("");
        optionCEditText.setText("");
        optionDEditText.setText("");
        answerEditText.setText("");
        categoryEditText.setText("");
    }
}

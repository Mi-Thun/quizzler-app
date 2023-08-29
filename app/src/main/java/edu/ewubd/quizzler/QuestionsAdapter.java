package edu.ewubd.quizzler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class QuestionsAdapter extends BaseAdapter {
    private Context context;
    private List<QuestionItem> questions;

    public QuestionsAdapter(Context context, List<QuestionItem> questions) {
        this.context = context;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.question_item_layout, parent, false);
        }

        TextView questionText = convertView.findViewById(R.id.ques);
        Button option1 = convertView.findViewById(R.id.option1);
        Button option2 = convertView.findViewById(R.id.option2);
        Button option3 = convertView.findViewById(R.id.option3);
        Button option4 = convertView.findViewById(R.id.option4);

        QuestionItem question = questions.get(position);
        questionText.setText(question.getQuestionText());
        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        option4.setText(question.getOption4());

        // Set click listeners for options here if needed

        return convertView;
    }
}

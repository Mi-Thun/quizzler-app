package edu.ewubd.quizzler;

public class QuestionItem {
    private int selectedOption = -1;
    private String category;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionItem(String category, String questionText, String option1, String option2, String option3, String option4) {
        this.category = category;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }
}


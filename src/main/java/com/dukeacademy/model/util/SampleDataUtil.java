package com.dukeacademy.model.util;

import com.dukeacademy.model.StandardQuestionBank;
import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.question.Question;

/**
 * Contains utility methods for populating {@code QuestionBank} with sample data.
 */
public class SampleDataUtil {
    public static Question[] getSampleQuestions() {
        return new Question[10];
    }

    public static QuestionBank getSampleQuestionBank() {
        StandardQuestionBank sampleAb = new StandardQuestionBank();
        for (Question sampleQuestion : getSampleQuestions()) {
            sampleAb.addQuestion(sampleQuestion);
        }
        return sampleAb;
    }
}

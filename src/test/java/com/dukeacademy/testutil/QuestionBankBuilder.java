package com.dukeacademy.testutil;

import com.dukeacademy.model.question.StandardQuestionBank;
import com.dukeacademy.model.question.Question;

/**
 * A utility class to help with building QuestionBank objects.
 * Example usage: <br>
 *     {@code QuestionBank ab = new QuestionBankBuilder().withPerson("John", "Doe").build();}
 */
public class QuestionBankBuilder {

    private StandardQuestionBank standardQuestionBank;

    public QuestionBankBuilder() {
        standardQuestionBank = new StandardQuestionBank();
    }

    public QuestionBankBuilder(StandardQuestionBank standardQuestionBank) {
        this.standardQuestionBank = standardQuestionBank;
    }

    /**
     * Adds a new {@code Question} to the {@code QuestionBank} that we are building.
     */
    public QuestionBankBuilder withQuestion(Question question) {
        standardQuestionBank.addQuestion(question);
        return this;
    }

    public StandardQuestionBank build() {
        return standardQuestionBank;
    }
}

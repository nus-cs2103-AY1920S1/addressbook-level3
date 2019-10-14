package com.dukeacademy.testutil;

import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.question.Question;

/**
 * A utility class to help with building QuestionBank objects.
 * Example usage: <br>
 *     {@code QuestionBank ab = new QuestionBankBuilder().withPerson("John", "Doe").build();}
 */
public class QuestionBankBuilder {

    private QuestionBank questionBank;

    public QuestionBankBuilder() {
        questionBank = new QuestionBank();
    }

    public QuestionBankBuilder(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    /**
     * Adds a new {@code Question} to the {@code QuestionBank} that we are building.
     */
    public QuestionBankBuilder withQuestion(Question question) {
        questionBank.addQuestion(question);
        return this;
    }

    public QuestionBank build() {
        return questionBank;
    }
}

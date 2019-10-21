package com.dukeacademy.model.question;

import java.util.Collection;

import javafx.collections.ObservableList;

/**
 * Question bank to encapsulate the storage of all the questions registered in the program. A requirement for the
 * question bank is that each question is tagged with unique integer id from 0 to n - 1, where n is the number of
 * questions in the question bank. All crud operations are performed using this unique integer id.
 */
public interface QuestionBank {

    /**
     * Returns an unmodifiable view of the questions in question bank.. The order of the questions in the list must
     * always follow the ordering of their unique ids at any point.
     */
    ObservableList<Question> getReadOnlyQuestionListObservable();

    /**
     * Adds a new question to the question bank.
     * @param question the question to be added.
     */
    void addQuestion(Question question);

    /**
     * Adds the questions from a different question bank to this instance.
     * @param questionBank the question bank from which the questions are to be added.
     */
    void addQuestionBank(QuestionBank questionBank);

    /**
     * Replaces all the questions in the question bank with the new collection.
     * @param questions new collection of questions.
     */
    void setQuestions(Collection<Question> questions);

    /**
     * Replaces the question corresponding to the id provided with a new question.
     * @param id the id of the question to be replaced
     * @param question the new question.
     */
    void replaceQuestion(int id, Question question);

    /**
     * Removes the question corresponding to the id provided.
     * @param id the id of the question to be deleted.
     */
    void removeQuestion(int id);

    /**
     * Deletes all the questions in the question bank.
     */
    void resetQuestions();
}

package com.dukeacademy.logic.question;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Predicate;

import com.dukeacademy.model.question.Question;

import javafx.collections.ObservableList;

/**
 * Interface to handle all CRUD operations related to questions.
 */
public interface QuestionsLogic {
    /**
     * Returns an observable list that represents all the questions in the application. The default list contains
     * all the questions unless a filter was previously set. Note that this index of this list is 0-based.
     * @return an observable list of questions
     */
    ObservableList<Question> getFilteredQuestionsList();

    /**
     * Filters the visible questions in the application according to the predicate given.
     * @param predicate the predicate to be applied as a filter
     */
    void filterQuestionsList(Predicate<Question> predicate);

    /**
     * Adds a question to the model.
     * @param question the new question to be added
     */
    void addQuestion(Question question);

    /**
     * Adds a collection of questions to the model.
     * @param questions the collection of questions to be added
     */
    void addQuestions(Collection<Question> questions);

    /**
     * Loads questions from a specified file type, depending on the implementation.
     * @param questionsFilePath the path of the file to be loaded
     */
    void addQuestionsFromPath(Path questionsFilePath);

    /**
     * Returns the question corresponding to the index in the list returned by getFilteredQuestionsList.
     * @param index the index of the question to be returned
     * @return the corresponding question
     */
    Question getQuestion(int index);

    /**
     * Sets the question corresponding to the index in the list returned by getFilteredQuestionsList to a new question.
     * @param index the index of the question to be replaced
     * @param newQuestion the new question
     */
    void setQuestion(int index, Question newQuestion);

    /**
     * Replaces the old question with a new question.
     * @param oldQuestion the old question to be replaced
     * @param newQuestion the new question
     */
    void replaceQuestion(Question oldQuestion, Question newQuestion);

    /**
     * Deletes the question corresponding to the index in the list returned by getFilteredQuestionsList.
     * @param index the index of the question to be deleted
     */
    void deleteQuestion(int index);

    /**
     * Deletes all the stored questions in the application.
     */
    void deleteAllQuestions();
}

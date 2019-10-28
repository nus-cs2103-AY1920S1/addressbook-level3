package com.dukeacademy.logic.question;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.StandardQuestionBank;
import com.dukeacademy.model.util.SampleDataUtil;
import com.dukeacademy.storage.question.QuestionBankStorage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Logic class to handle all CRUD operations regarding questions in the application.
 */
public class QuestionsLogicManager implements QuestionsLogic {
    private final Logger logger;

    private final QuestionBankStorage storage;
    private final QuestionBank questionBank;
    private final FilteredList<Question> filteredList;
    private String problemStatement = "";

    /**
     * Instantiates a new Questions logic manager.
     *
     * @param storage the storage
     */
    public QuestionsLogicManager(QuestionBankStorage storage) {
        this.logger = LogsCenter.getLogger(QuestionsLogicManager.class);
        this.storage = storage;
        this.questionBank = this.loadQuestionsFromStorage();
        this.filteredList = new FilteredList<>(questionBank.getReadOnlyQuestionListObservable());
    }

    /**
     * Gets question bank.
     *
     * @return the question bank
     */
    public QuestionBank getQuestionBank() {
        return new StandardQuestionBank(this.questionBank);
    }

    @Override
    public ObservableList<Question> getFilteredQuestionsList() {
        return FXCollections.unmodifiableObservableList(this.filteredList);
    }

    @Override
    public void filterQuestionsList(Predicate<Question> predicate) {
        this.filteredList.setPredicate(predicate);
    }

    @Override
    public void addQuestion(Question question) {
        this.questionBank.addQuestion(question);
        this.saveQuestionBankToStorage(this.questionBank);
    }

    @Override
    public void addQuestions(Collection<Question> questions) {
        for (Question question: questions) {
            this.questionBank.addQuestion(question);
        }

        this.saveQuestionBankToStorage(this.questionBank);
    }

    @Override
    public void addQuestionsFromPath(Path questionsFilePath) {
        try {
            QuestionBank newQuestionBank = this.storage.readQuestionBank(questionsFilePath).orElseGet(() -> {
                logger.info("Unable to find json file at: " + questionsFilePath);
                return new StandardQuestionBank();
            });

            this.addQuestions(newQuestionBank.getReadOnlyQuestionListObservable());
        } catch (IOException e) {
            logger.info("Error encountered while reading data file. Questions will not be added.");
        } catch (DataConversionException e) {
            logger.info("Error encountered while parsing data file, please check the format.");
        }
    }

    @Override
    public Question getQuestion(int index) {
        return this.filteredList.get(index);
    }

    @Override
    public void setQuestion(int index, Question newQuestion) {
        Question oldQuestion = filteredList.get(index);
        this.replaceQuestion(oldQuestion, newQuestion);
    }

    @Override
    public void replaceQuestion(Question oldQuestion, Question newQuestion) {
        this.questionBank.replaceQuestion(oldQuestion, newQuestion);
        this.saveQuestionBankToStorage(this.questionBank);
    }

    @Override
    public void deleteQuestion(int index) {
        Question questionToDelete = filteredList.get(index);
        this.questionBank.removeQuestion(questionToDelete);
        this.saveQuestionBankToStorage(this.questionBank);
    }

    @Override
    public void deleteAllQuestions() {
        this.questionBank.resetQuestions();
        this.saveQuestionBankToStorage(this.questionBank);
    }

    /**
     * Loads a new question bank from the given storage instance.
     * @return the loaded question bank.
     */
    private QuestionBank loadQuestionsFromStorage() {
        logger.info("storage instance built. trying to load questions / "
            + "samples");
        try {
            return this.storage.readQuestionBank().get();
        } catch (IOException | DataConversionException e) {
            logger.info("Unable to load question bank from: " + storage.getQuestionBankFilePath()
                + ".\n Loading sample data instead...");
            return SampleDataUtil.getSampleQuestionBank();
        } catch (NullPointerException e) {
            logger.info("Unable to find json file: " + storage.getQuestionBankFilePath()
                + ".\n Loading sample data instead...");
            return SampleDataUtil.getSampleQuestionBank();
        }
    }

    /**
     * Saves a new question bank to the given storage instance.
     * @param questionBank the question bank to be saved.
     */
    private void saveQuestionBankToStorage(QuestionBank questionBank) {
        try {
            storage.saveQuestionBank(questionBank);
        } catch (IOException e) {
            logger.info("Unable to save question data to: " + storage.getQuestionBankFilePath());
        }
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }
}

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
import com.dukeacademy.model.question.exceptions.QuestionNotFoundRuntimeException;
import com.dukeacademy.model.util.SampleDataUtil;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;
import com.dukeacademy.storage.question.QuestionBankStorage;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Logic class to handle all CRUD operations regarding questions in the application.
 */
public class QuestionsLogicManager implements QuestionsLogic {
    private static final String NO_QUESTION_FOUND_MESSAGE = "No question found with id  : ";

    private final Logger logger;
    private final QuestionBankStorage storage;
    private final QuestionBank questionBank;
    private final FilteredList<Question> filteredList;
    private final StandardObservable<Question> selectedQuestion;

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
        this.selectedQuestion = new StandardObservable<>();
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
    public ObservableList<Question> getAllQuestionsList() {
        return questionBank.getReadOnlyQuestionListObservable();
    }

    @Override
    public ObservableList<Question> getFilteredQuestionsList() {
        return this.filteredList;
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
    public Question getQuestion(int id) {
        return questionBank.getReadOnlyQuestionListObservable()
                .stream()
                .filter(question -> question.getId() == id)
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundRuntimeException(NO_QUESTION_FOUND_MESSAGE + id));
    }

    @Override
    public void setQuestion(int id, Question newQuestion) {
        Question oldQuestion = questionBank.getReadOnlyQuestionListObservable()
                .stream()
                .filter(question -> question.getId() == id)
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundRuntimeException(NO_QUESTION_FOUND_MESSAGE + id));
        this.replaceQuestion(oldQuestion, newQuestion);
    }

    @Override
    public void replaceQuestion(Question oldQuestion, Question newQuestion) {
        try {
            this.questionBank.replaceQuestion(oldQuestion, newQuestion);
        } catch (QuestionNotFoundRuntimeException e) {
            throw new QuestionNotFoundRuntimeException(NO_QUESTION_FOUND_MESSAGE + oldQuestion.getId());
        }
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

    @Override
    public Observable<Question> getSelectedQuestion() {
        return this.selectedQuestion;
    }

    @Override
    public void selectQuestion(int id) {
        Question selectedQuestion = questionBank.getReadOnlyQuestionListObservable()
                .stream()
                .filter(question -> question.getId() == id)
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundRuntimeException(NO_QUESTION_FOUND_MESSAGE + id));
        this.selectedQuestion.setValue(selectedQuestion);
    }
}

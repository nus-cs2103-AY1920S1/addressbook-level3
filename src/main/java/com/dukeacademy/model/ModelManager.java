package com.dukeacademy.model;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.GuiSettings;
import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.model.prefs.ReadOnlyUserPrefs;
import com.dukeacademy.model.prefs.UserPrefs;
import com.dukeacademy.model.question.Question;

import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.StandardQuestionBank;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the question bank data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StandardQuestionBank standardQuestionBank;
    private final UserPrefs userPrefs;
    private final FilteredList<Question> filteredQuestions;

    /**
     * Initializes a ModelManager with the given questionBank and userPrefs.
     */
    public ModelManager(QuestionBank questionBank, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(questionBank, userPrefs);

        logger.fine("Initializing with question bank: " + questionBank + " and user prefs " + userPrefs);

        this.standardQuestionBank = new StandardQuestionBank(questionBank.getReadOnlyQuestionListObservable());
        this.userPrefs = new UserPrefs(userPrefs);
        filteredQuestions = new FilteredList<>(this.standardQuestionBank.getReadOnlyQuestionListObservable());
    }

    public ModelManager() {
        this(new StandardQuestionBank(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getQuestionBankFilePath() {
        return userPrefs.getQuestionBankFilePath();
    }

    @Override
    public void setQuestionBankFilePath(Path questionBankFilePath) {
        requireNonNull(questionBankFilePath);
        userPrefs.setQuestionBankFilePath(questionBankFilePath);
    }

    //=========== QuestionBank ================================================================================

    @Override
    public void setStandardQuestionBank(QuestionBank standardQuestionBank) {
        this.standardQuestionBank.setQuestions(standardQuestionBank.getReadOnlyQuestionListObservable());
    }

    @Override
    public QuestionBank getStandardQuestionBank() {
        return standardQuestionBank;
    }

    @Override
    public void deleteQuestion(Question target) {
        // standardQuestionBank.removeQuestion(target);
    }

    @Override
    public void addQuestion(Question question) {
        standardQuestionBank.addQuestion(question);
        updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
    }

    @Override
    public void setQuestion(Question target, Question editedQuestion) {
        requireAllNonNull(target, editedQuestion);

        // standardQuestionBank.setQuestion(target, editedQuestion);
    }

    //=========== Filtered Question List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Question} backed by the internal list of
     * {@code versionedQuestionBank}
     */
    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        return filteredQuestions;
    }

    @Override
    public void updateFilteredQuestionList(Predicate<Question> predicate) {
        requireNonNull(predicate);
        filteredQuestions.setPredicate(predicate);
    }
}

package seedu.revision.stubs;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.revision.commons.core.GuiSettings;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.ReadOnlyUserPrefs;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.quiz.Statistics;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getRevisionToolFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getHistoryFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRevisionToolFilePath(Path revisionToolFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setHistoryFilePath(Path historyFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAnswerable(Answerable answerable) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStatistics(Statistics statistics) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRevisionTool(ReadOnlyRevisionTool newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setHistory(ReadOnlyHistory newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyRevisionTool getRevisionTool() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyHistory getHistory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAnswerable(Answerable answerable) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAnswerable(Answerable target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAnswerable(Answerable target, Answerable editedAnswerable) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Answerable> getFilteredAnswerableList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Statistics> getStatisticsList() {
        throw new AssertionError("This method should not be called.");
    }

    public void updateFilteredAnswerableList(Predicate<Answerable> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeFiltersFromAnswerableList() {
        throw new AssertionError("This method should not be called.");
    }
}

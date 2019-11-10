package com.typee.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import com.itextpdf.text.DocumentException;
import com.typee.commons.core.GuiSettings;
import com.typee.logic.commands.exceptions.DeleteDocumentException;
import com.typee.logic.commands.exceptions.GenerateExistingReportException;
import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.engagement.Engagement;
import com.typee.model.report.Report;

import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */

    Predicate<Engagement> PREDICATE_SHOW_ALL_ENGAGEMENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' engagement list file path.
     */
    Path getEngagementListFilePath();

    /**
     * Sets the user prefs' engagement list file path.
     */
    void setEngagementListFilePath(Path engagementListFilePath);

    /**
     * Replaces engagement list data with the data in {@code typee}.
     */
    void setHistoryManager(ReadOnlyEngagementList historyManager);

    /** Returns the engagement list */
    ReadOnlyEngagementList getEngagementList();

    /**
     * REDUNDANT.
     * Returns true if the engagement is in the engagement list.
     */
    boolean hasEngagement(Engagement engagement);

    /**
     * Deletes the given engagement.
     * The engagement must exist in the engagement list.
     */
    void deleteEngagement(Engagement target);

    /**
     * Adds the given engagement.
     * {@code engagement} must not already exist in the engagement list.
     */
    void addEngagement(Engagement engagement);

    /**
     * Replaces the given engagement {@code target} with {@code editedEngagement}.
     * {@code target} must exist in the engagement list.
     * The engagement identity of {@code editedEngagement} must not be the same as
     * another existing engagement in the engagement list.
     */
    void setEngagement(Engagement target, Engagement editedEngagement);

    /** Returns an unmodifiable view of the filtered engagement list */
    ObservableList<Engagement> getFilteredEngagementList();

    /**
     * Updates the filter of the filtered engagement list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */

    void updateFilteredEngagementList(Predicate<Engagement> predicate);

    void updateSortedEngagementList();

    ObservableList<Engagement> getSortedEngagementList();

    boolean hasNoUndoableCommand();

    void undoEngagementList() throws NullUndoableActionException;

    boolean hasNoRedoableCommand();

    void saveEngagementList();

    void redoEngagementList() throws NullRedoableActionException;

    void setComparator(Comparator<Engagement> comparator);

    Path saveReport(Path fileDir, Report report) throws DocumentException, IOException, GenerateExistingReportException;

    boolean deleteReport(Path fileDir, Report report) throws DeleteDocumentException;
}

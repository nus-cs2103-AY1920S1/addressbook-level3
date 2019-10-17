package com.typee.logic;

import java.nio.file.Path;

import com.typee.commons.core.GuiSettings;
import com.typee.commons.exceptions.DataConversionException;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.Model;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.engagement.Engagement;
import com.typee.ui.Tab;

import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the EngagementList.
     *
     * @see Model#getHistoryManager()
     */
    ReadOnlyEngagementList getAddressBook();

    /** Returns an unmodifiable view of the filtered list of engagements */
    ObservableList<Engagement> getFilteredEngagementList();

    /** Returns an unmodifiable view of tabs in Typee */
    ObservableList<Tab> getTabList() throws DataConversionException;

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}

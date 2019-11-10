package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.ui.Tab;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean isShowHelp;

    /** The application should exit. */
    private final boolean isExit;

    /** The application should switch tab menu. */
    private final boolean isTabCommand;

    /* The application should interact with the calendar window. */
    private final boolean isCalendarCommand;

    private final boolean isPdfCommand;

    private final Tab tab;

    private final LocalDate calendarDate;

    private final String calendarCommandType;

    private final Path docPath;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isShowHelp, boolean isExit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = isShowHelp;
        this.isExit = isExit;
        this.isTabCommand = false;
        this.isPdfCommand = false;
        this.tab = new Tab("main");
        this.isCalendarCommand = false;
        this.calendarDate = null;
        this.calendarCommandType = "";
        this.docPath = null;
    }

    /**
     * Constructs a {@code CommandResult} specified for tab command.
     */
    public CommandResult(String feedbackToUser, boolean isTabCommand, Tab tab) {
        this.feedbackToUser = feedbackToUser;
        this.isShowHelp = false;
        this.isExit = false;
        this.isTabCommand = isTabCommand;
        this.tab = tab;
        this.isCalendarCommand = false;
        this.isPdfCommand = false;
        this.calendarDate = null;
        this.calendarCommandType = "";
        this.docPath = null;
    }

    /**
     * Constructs a {@code CommandResult} for a calendar command with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isCalendarCommand, LocalDate calendarDate,
                         String calendarCommandType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = false;
        this.isExit = false;
        this.isTabCommand = false;
        this.isPdfCommand = false;
        this.tab = new Tab("calendar");
        this.isCalendarCommand = isCalendarCommand;
        this.calendarDate = calendarDate;
        this.calendarCommandType = calendarCommandType;
        this.docPath = null;
    }

    /**
     * Constructs a {@code CommandResult} for a calendar command with the specified fields,
     * excluding the date field.
     */
    public CommandResult(String feedbackToUser, boolean isCalendarCommand, String calendarCommandType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = false;
        this.isExit = false;
        this.isTabCommand = false;
        this.isPdfCommand = false;
        this.tab = new Tab("calendar");
        this.isCalendarCommand = isCalendarCommand;
        this.calendarDate = null;
        this.calendarCommandType = calendarCommandType;
        this.docPath = null;
    }

    public CommandResult(String feedbackToUser, boolean isPdfCommand, Path docPath) {
        this.feedbackToUser = feedbackToUser;
        this.isShowHelp = false;
        this.isExit = false;
        this.isTabCommand = false;
        this.tab = new Tab("report");
        this.isCalendarCommand = false;
        this.calendarDate = null;
        this.calendarCommandType = "";
        this.isPdfCommand = isPdfCommand;
        this.docPath = docPath;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return isShowHelp;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isTabCommand() {
        return isTabCommand;
    }

    public Tab getTab() {
        return tab;
    }

    public boolean isCalendarCommand() {
        return isCalendarCommand;
    }

    public boolean isPdfCommand() {
        return isPdfCommand;
    }

    public LocalDate getCalendarDate() throws CommandException {
        if (!isCalendarCommand) {
            throw new CommandException("Cannot get calendar date from a non-calendar command result");
        }
        return calendarDate;
    }

    public String getCalendarCommandType() throws CommandException {
        if (!isCalendarCommand) {
            throw new CommandException("Cannot get calendar command type from a non-calendar command result");
        }
        return calendarCommandType;
    }

    public Path getDocPath() {
        return docPath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && isShowHelp == otherCommandResult.isShowHelp
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowHelp, isExit);
    }

}

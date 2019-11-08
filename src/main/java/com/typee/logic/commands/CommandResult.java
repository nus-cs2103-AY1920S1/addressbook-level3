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
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should switch tab menu. */
    private final boolean tabCommand;

    /* The application should interact with the calendar window. */
    private final boolean calendarCommand;

    private final boolean pdfCommand;

    private final Tab tab;

    private final LocalDate calendarDate;

    private final String calendarCommandType;

    private final Path docPath;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.tabCommand = false;
        this.pdfCommand = false;
        this.tab = new Tab("main");
        this.calendarCommand = false;
        this.calendarDate = null;
        this.calendarCommandType = "";
        this.docPath = null;
    }

    /**
     * Constructs a {@code CommandResult} specified for tab command.
     */
    public CommandResult(String feedbackToUser, boolean tabCommand, Tab tab) {
        this.feedbackToUser = feedbackToUser;
        this.showHelp = false;
        this.exit = false;
        this.tabCommand = tabCommand;
        this.tab = tab;
        this.calendarCommand = false;
        this.pdfCommand = false;
        this.calendarDate = null;
        this.calendarCommandType = "";
        this.docPath = null;
    }

    /**
     * Constructs a {@code CommandResult} for a calendar command with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean calendarCommand, LocalDate calendarDate,
                         String calendarCommandType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.tabCommand = false;
        this.pdfCommand = false;
        this.tab = new Tab("calendar");
        this.calendarCommand = calendarCommand;
        this.calendarDate = calendarDate;
        this.calendarCommandType = calendarCommandType;
        this.docPath = null;
    }

    /**
     * Constructs a {@code CommandResult} for a calendar command with the specified fields,
     * excluding the date field.
     */
    public CommandResult(String feedbackToUser, boolean calendarCommand, String calendarCommandType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.tabCommand = false;
        this.pdfCommand = false;
        this.tab = new Tab("calendar");
        this.calendarCommand = calendarCommand;
        this.calendarDate = null;
        this.calendarCommandType = calendarCommandType;
        this.docPath = null;
    }

    public CommandResult(String feedbackToUser, boolean pdfCommand, Path docPath) {
        this.feedbackToUser = feedbackToUser;
        this.showHelp = false;
        this.exit = false;
        this.tabCommand = false;
        this.tab = new Tab("report");
        this.calendarCommand = false;
        this.calendarDate = null;
        this.calendarCommandType = "";
        this.pdfCommand = pdfCommand;
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
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isTabCommand() {
        return tabCommand;
    }

    public Tab getTab() {
        return tab;
    }

    public boolean isCalendarCommand() {
        return calendarCommand;
    }

    public boolean isPdfCommand() {
        return pdfCommand;
    }

    public LocalDate getCalendarDate() throws CommandException {
        if (!calendarCommand) {
            throw new CommandException("Cannot get calendar date from a non-calendar command result");
        }
        return calendarDate;
    }

    public String getCalendarCommandType() throws CommandException {
        if (!calendarCommand) {
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
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}

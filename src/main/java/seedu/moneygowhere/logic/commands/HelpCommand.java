package seedu.moneygowhere.logic.commands;

import seedu.moneygowhere.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "List of commands\n\n"
        + "add \t\t\t\t- to add a spending\n"
        + "edit \t\t\t\t- to edit a spending\n"
        + "delete \t\t\t- to delete a spending\n"
        + "list \t\t\t\t- to display a list of spending\n"
        + "find \t\t\t\t- to search for spending based on keyword\n"
        + "budget \t\t\t- to set a monthly budget\n"
        + "showbudget \t\t- shows the current monthly budget\n"
        + "stats \t\t\t\t- to generate statistics of spending\n"
        + "graph \t\t\t- to show the spending in the form of a graph\n"
        + "import \t\t\t- to import data from a CSV file\n"
        + "export \t\t\t- to export data to a CSV file\n"
        + "undo \t\t\t- to restores the list of spending to the state before the previous undoable command\n"
        + "redo \t\t\t- to reverse the most recent undo command\n"
        + "clear \t\t\t- to clear all entries from the application\n"
        + "reminder add \t\t- to add a reminder\n"
        + "reminder delete \t- to delete a reminder\n"
        + "exchangerate \t\t- to view exchange rates\n"
        + "currency \t\t\t- to view or set the currency used to list spending\n"
        + "exit \t\t\t\t- to exit the application\n\n"
        + "To find out more about a specific command, simply type 'COMMAND' e.g. 'add', 'reminder', etc";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false, false);
    }
}

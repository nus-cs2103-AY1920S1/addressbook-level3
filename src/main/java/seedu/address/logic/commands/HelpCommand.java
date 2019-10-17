package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "List of commands\n\n"
            + "add \t\t- to add a spending\n"
            + "update \t- to update a spending\n"
            + "delete \t- to delete a spending\n"
            + "list \t\t- to display a list of spending\n"
            + "find \t\t- to search for spending based on keyword\n"
            + "budget \t- to set a monthly budget\n"
            + "showbudget \t- shows the current monthly budget\n"
            + "stats \t\t- to generate statistics of spending\n"
            + "ltags \t- to list all available tags\n"
            + "import \t- to import data from a CSV file\n"
            + "export \t- to export data to a CSV file\n"
            + "undo \t- to restores the list of spending to the state before the previous undoable command\n"
            + "redo \t- to reverse the most recent undo command\n"
            + "graph \t- to show all spending in the form of a graph\n"
            + "clear \t- to clear all entries from the application\n"
            + "exit \t\t- to exit the application\n\n"
            + "To find out more about a specific command, simply type 'COMMAND' e.g. 'add', 'list', etc";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false);
    }
}

package seedu.address.logic.commands.gui;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Type help <command> to find out more. List of commands:\n"
            + "\nRegarding Storage:\n"
            + "newplan - Creating a new study plan\n"
            + "removeplan - Deleting a study plan\n"
            + "commit - Commiting edits to a study plan\n"
            + "history - Checking commit history\n"
            + "viewcommit - Viewing a commit\n"
            + "revert - Reverting to a commit\n"
            + "viewplan - Viewing another study plan\n"
            + "activate - Setting another study plan as active\n"
            + "move - Moving a semester to a study plan\n"
            + "remove - Deleting a semester from a study plan\n"
            + "description - Viewing description of a module\n"
            + "title - Editing the title of current study plan\n"
            + "default - Setting default study plan\n"
            + "\nRegarding the GUI:\n"
            + "help - Gives help\n"
            + "expand - Expanding a semester\n"
            + "collapse - Collapsing a semester\n"
            + "\nRegarding the Command Line Interface\n"
            + "addmodule - Assigning a module to a given semester\n"
            + "remove - Removing a module from a given semester\n"
            + "nameue - Naming a UE from a semester\n"
            + "move - Moving a module from one semester to another\n"
            + "setcurrent - Setting the current semester\n"
            + "block - Blocking off the given semester\n"
            + "undo - Undo-ing the previous command\n"
            + "redo - Redo-ing the previous undone command\n"
            + "declarefocusarea - Declaring a focus area\n"
            + "\nRegarding data classification and management:\n"
            + "viewdefaulttags - Viewing default tags\n"
            + "viewalltags - Viewing all tags\n"
            + "newtag - Creating new tags\n"
            + "renametag - Renaming an existing tag\n"
            + "addtag - Tagging modules\n"
            + "removetag - Removing a tag from a module\n"
            + "removeall - Removing a tag from all modules\n"
            + "deletetag - Deleting tags\n"
            + "viewtagged - Viewing modules with specific tags\n"
            + "viewtags - Viewing tags for a specific module\n"
            + "findmod - Finding modules using the module code\n"
            + "getmodcode - Finding module code using keywords\n"
            + "mcs - Viewing total completed MCs\n"
            + "validmods - Viewing modules that can be taken in a given semester\n"
            + "\nRegarding verification:\n"
            + "check - Checking a study plan's feasibility";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}

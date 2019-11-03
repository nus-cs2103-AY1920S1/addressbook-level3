package seedu.tarence.logic.commands;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_TAB;

import java.util.Arrays;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.storage.Storage;

/**
 * Displays tab information to the user.
 */
public class ChangeTabCommand extends Command {
    public static final String COMMAND_WORD = "cd";

    public static final String MESSAGE_SUCCESS = "%1$s has been displayed";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "changetab"};

    private static final String[] modNames = {"m", "mod", "mods", "module", "modules"};
    private static final String[] tutNames = {"t", "tut", "tuts", "tutorial", "tutorials"};
    private static final String[] studentNames = {"s", "stu", "students", "student"};

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays tab information to the user.\n"
            + "Parameters: TAB TO DISPLAY\n"
            + "Synonyms for module tab:\n"
            + String.join(" ", modNames) + "\n"
            + "Synonyms for tutorial tab:\n"
            + String.join(" ", tutNames) + "\n"
            + "Synonyms for student tab:\n"
            + String.join(" ", studentNames) + "\n"
            + "Example:\n"
            + COMMAND_WORD + " m\n"
            + COMMAND_WORD + " t\n"
            + COMMAND_WORD + " s\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private String tabName;
    private TabNames tabToDisplay;

    public ChangeTabCommand(String tabToDisplay) {
        this.tabName = tabToDisplay.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (tabName.equals("")) {
            throw new CommandException(MESSAGE_USAGE);
        }
        this.tabToDisplay = getTabvalue();
        return new CommandResult(String.format(MESSAGE_SUCCESS, tabToDisplay.toString().toLowerCase()), tabToDisplay);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    /**
     * Returns the Tabname that matches user input
     * TODO: pretty sure there's a better way to do this. do let me know how i can improve iy
     */
    public TabNames getTabvalue() throws CommandException {

        if (Arrays.stream(modNames).anyMatch(name -> name.equals(tabName))) {
            return TabNames.MODULES;
        }

        if (Arrays.stream(tutNames).anyMatch(name -> name.equals(tabName))) {
            return TabNames.TUTORIALS;
        }

        if (Arrays.stream(studentNames).anyMatch(name -> name.equals(tabName))) {
            return TabNames.STUDENTS;
        }

        throw new CommandException(MESSAGE_INVALID_TAB);
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeTabCommand)) {
            return false;
        }

        return tabName.equals(((ChangeTabCommand) other).tabName);
    }


}

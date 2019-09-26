package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.tutorial.Tutorial;

public class AddTutorialCommand extends Command {

    public static final String COMMAND_WORD = "addTutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Tutorial to the Application. "
            + "Parameters: " ;

    public static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
            "addtut"};

    private Tutorial tutorial;

    public AddTutorialCommand(Tutorial newTutorial) {
        requireNonNull(newTutorial);
        tutorial = newTutorial;
    }

    @Override
    public CommandResult execute (Model model) throws CommandException {
        requireNonNull(model);
        throw new CommandException("Hello from Add Tutorial Command");
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



}


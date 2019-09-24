package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.tarence.model.Model;

/**
 * Lists all persons in the application (or class) to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "listStudent";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "liststu", "liststud"};

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
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

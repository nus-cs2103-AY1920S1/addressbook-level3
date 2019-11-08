package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.util.SampleDataUtil;
import seedu.tarence.storage.Storage;

/**
 * Clears the application memory state to a free clean slate.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clear the Application Memory State.\n";

    public static final String MESSAGE_SUCCESS = "Memory state has been successfully cleared";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase()};

    public ClearCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ReadOnlyApplication emptyApplication = SampleDataUtil.getSampleApplication();

        model.setModel(emptyApplication);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
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


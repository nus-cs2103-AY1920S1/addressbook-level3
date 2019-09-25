package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.Module;

/**
 * Adds a module into T.A.rence.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "addModule";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
            "addMod", "addmod"};

    private final Module newModule;

    /**
     * Creates an AddModuleCommand to add the specified {@code Module}
     */
    public AddModuleCommand(Module newModule) {
        requireNonNull(newModule);
        this.newModule = newModule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        throw new CommandException("Hello from execute AddModuleCommand");
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



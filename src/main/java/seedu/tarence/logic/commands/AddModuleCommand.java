package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.Module;
import seedu.tarence.storage.Storage;

/**
 * Adds a module into T.A.rence.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "addModule";

    public static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "addMod", "addmod", "addmodule"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Module to the Application.\n"
            + "Parameters:\n"
            + PREFIX_MODULE + "MODULE\n"
            + "Example:\n" + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS1010S\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists!";
    public static final String MESSAGE_SUCCESS = "New module added: %1$s";

    private Module module;

    /**
     * Creates an AddModuleCommand to add the specified {@code Module}
     */
    public AddModuleCommand(Module newModule) {
        requireNonNull(newModule);
        this.module = newModule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(module)) {
            throw new CommandException((MESSAGE_DUPLICATE_MODULE));
        }

        model.addModule(module);

        return new CommandResult(String.format(MESSAGE_SUCCESS, module));

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



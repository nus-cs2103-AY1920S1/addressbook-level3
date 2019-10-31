package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.alias.AliasList;

/**
 * Allows the user to put aliases for the commands of $aveNUS.
 */
public class AliasCommand extends Command {
    public static final String COMMAND_WORD = "alias";

    public static final String NO_ARGUMENTS_USAGE = "You have entered in zero arguments.";
    public static final String TOO_MANY_ARGUMENTS_USAGE = "You have entered in too many arguments.";

    public static final String EXAMPLE_USAGE = "Example: alias filter cut";
    public static final String PROPER_USAGE = "%s\n"
            + "You will need one command word and one keyword only.\n" + EXAMPLE_USAGE;

    public static final String WORD_ALREADY_EXISTS_USAGE = "%s is already an alias for %s.\n"
            + "Please either clear the alias for %s for use another word.";
    public static final String COMMAND_WORD_USAGE = "%s is already a command word.\n"
            + "Please use another alias word.";
    public static final String INVALID_COMMAND_WORD_USAGE = "You have entered an invalid command word: %s.\n"
            + "Please enter a proper command word.";

    public static final String ERASE_ALIAS_COMMAND_SUCCESS = "You have successfully erased the alias for %s.";
    public static final String ALIAS_COMMAND_SUCCESS = "You have successfully set the alias of %s to be %s.";

    private final String commandWord;
    private final String keyword;

    /**
     * Creates a new AliasCommand based on the command word and keyword given.
     * @param commandWord the command to have the alias assigned to.
     * @param keyword the word representing the alias.
     */
    public AliasCommand(String commandWord, String keyword) {
        this.commandWord = commandWord;
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AliasList aliasList = model.getAliasList();
        if (aliasList.isCommandWord(keyword)) {
            throw new CommandException(COMMAND_WORD_USAGE);
        } else if (aliasList.hasAliasWord(commandWord, keyword)) {
            throw new CommandException(String.format(WORD_ALREADY_EXISTS_USAGE, keyword,
                    aliasList.getCommandWord(keyword),
                    aliasList.getCommandWord(keyword)));
        } else if (!aliasList.doesCommandWordExist(commandWord)) {
            throw new CommandException(String.format(INVALID_COMMAND_WORD_USAGE, commandWord));
        }

        aliasList.changeAliasWord(commandWord, keyword);
        model.setAliasList(aliasList);
        System.out.println(model.getAliasList());

        if (keyword.isEmpty()) {
            return new CommandResult(String.format(ERASE_ALIAS_COMMAND_SUCCESS, commandWord));
        } else {
            return new CommandResult(String.format(ALIAS_COMMAND_SUCCESS,
                    commandWord, keyword));
        }
    }
}

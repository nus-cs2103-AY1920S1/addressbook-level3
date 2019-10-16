package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_VALUE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Defines an alias.
 */
public class AliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_SUCCESS = "Alias added: %1$s -> %2$s";
    public static final String MESSAGE_ALIAS_FAILED = "Alias cannot include a defined command.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": defines an alias. "
            + "Parameters: "
            + PREFIX_ALIAS_NAME + "ALIAS_NAME "
            + PREFIX_ALIAS_VALUE + "ALIAS_VALUE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ALIAS_NAME + "h "
            + PREFIX_ALIAS_VALUE + "help";

    private final String alias;
    private final String aliasTo;

    /**
     * Creates an AliasCommand to define an alias.
     */
    public AliasCommand(String alias, String aliasTo) {
        requireNonNull(alias);
        requireNonNull(aliasTo);
        this.alias = alias.trim();
        this.aliasTo = aliasTo.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            Command a = new AddressBookParser().parseCommand(alias);
        } catch (ParseException e) {
            if (e.getMessage().equals(MESSAGE_UNKNOWN_COMMAND)) {
                model.getUserPrefs().getAliasTable().addAlias(alias, aliasTo);
                return new CommandResult(String.format(MESSAGE_SUCCESS, alias, aliasTo));
            }
        }
        throw new CommandException(MESSAGE_ALIAS_FAILED);
    }
}

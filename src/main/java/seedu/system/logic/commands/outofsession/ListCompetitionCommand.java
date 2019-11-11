package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;
import static seedu.system.model.Model.PREDICATE_SHOW_ALL_COMPETITIONS;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;

/**
 * Lists all competitions in the system to the user.
 */
public class ListCompetitionCommand extends Command {

    public static final String COMMAND_WORD = "listCompetition";

    public static final CommandType COMMAND_TYPE = CommandType.COMPETITION;

    public static final String MESSAGE_SUCCESS = "Listed all competitions";

    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        model.updateFilteredCompetitionList(PREDICATE_SHOW_ALL_COMPETITIONS);
        return new CommandResult(MESSAGE_SUCCESS, COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof ListCompetitionCommand; // instanceof handles nulls
    }
}

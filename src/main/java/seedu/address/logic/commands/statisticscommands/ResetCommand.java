package seedu.address.logic.commands.statisticscommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * A command to reset the {@code WordBankStatistics} of the opened {@code WordBank}.
 */
public class ResetCommand extends WordBankStatisticsCommand {

    public static final String COMMAND_WORD = "reset";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.clearActiveWordBankStatistics();
        return new ResetCommandResult(model.getWordBankStatistics());
    }
}

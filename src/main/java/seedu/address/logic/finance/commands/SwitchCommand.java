package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_SWITCH_INVALID;

import seedu.address.commons.core.Messages;
import seedu.address.logic.SwitchOperation;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;


/**
 * Switches to a new application component.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": need to pass the argument "
            + "you want to switch";

    private final String args;

    public SwitchCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!args.equals("quiz") && !args.equals("calendar") && !args.equals("cap") && !args.equals("finance")) {
            throw new CommandException(String.format(MESSAGE_SWITCH_INVALID));
        }

        SwitchOperation swOperation = new SwitchOperation(args);
        swOperation.execute();

        return new CommandResult(
                String.format(Messages.MESSAGE_STATE_CHANGE, model.getFilteredLogEntryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand // instanceof handles nulls
                && args.equals(((SwitchCommand) other).args)); // state check
    }
}

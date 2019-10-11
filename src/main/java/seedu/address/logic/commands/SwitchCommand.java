package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.SwitchOperation;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": need to pass the argument " +
            "you want to switch";

    private final String args;

    public SwitchCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        SwitchOperation swOperation = new SwitchOperation(args);
        swOperation.execute();

        return new CommandResult(
                String.format(Messages.MESSAGE_STATE_CHANGE, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand // instanceof handles nulls
                && args.equals(((SwitchCommand) other).args)); // state check
    }
}

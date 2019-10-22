package seedu.address.testutil;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.model.Model;

/**
 * Dummy {@link MutatorCommand} for testing history functionality
 */
public class DummyMutatorCommand extends Command implements MutatorCommand {
    public static final String RESULT_PREAMBLE = "This is a dummy command with data ";

    private String dummyData;

    public DummyMutatorCommand(String dummyData) {
        this.dummyData = dummyData;
    }

    public CommandResult execute(Model model) {
        return new CommandResult(RESULT_PREAMBLE + dummyData);
    }
}

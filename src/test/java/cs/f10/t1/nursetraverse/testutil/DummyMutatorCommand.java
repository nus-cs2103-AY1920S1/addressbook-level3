package cs.f10.t1.nursetraverse.testutil;

import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.model.Model;

/**
 * Dummy {@link MutatorCommand} for testing history functionality.
 */
public class DummyMutatorCommand extends MutatorCommand {
    public static final String RESULT_PREAMBLE = "This is a dummy command with data %s";

    private String dummyData;

    public DummyMutatorCommand(String dummyData) {
        this.dummyData = dummyData;
    }

    public CommandResult execute(Model model) {
        return new CommandResult(String.format(RESULT_PREAMBLE, dummyData));
    }
}

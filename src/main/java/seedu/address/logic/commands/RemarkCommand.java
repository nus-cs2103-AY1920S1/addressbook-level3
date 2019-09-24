package seedu.address.logic.commands;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

//
//    private final Index targetIndex;
//    public RemarkCommand(Index index) {
//        this.targetIndex = index;
//
//    }
    public RemarkCommand() {}

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from remark");
    }
}

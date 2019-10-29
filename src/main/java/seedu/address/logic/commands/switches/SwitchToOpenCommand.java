package seedu.address.logic.commands.switches;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class SwitchToOpenCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Opening bank";

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        return new CommandResult(MESSAGE_HOME_ACKNOWLEDGEMENT, false, false);
    }

    public ModeEnum getNewMode(ModeEnum old) {
        return ModeEnum.OPEN;
    }
}

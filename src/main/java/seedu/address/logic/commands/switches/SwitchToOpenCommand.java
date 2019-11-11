package seedu.address.logic.commands.switches;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;

/**
 * Switches application to open mode.
 */
public class SwitchToOpenCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Opened bank: %1$s\n"
            + "Here you can edit the bank's content.\n"
            + "Try adding cards.\n"
            + "Eg. add w/My first word m/My first meaning";

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        return new CommandResult(
                String.format(MESSAGE_HOME_ACKNOWLEDGEMENT, model.getCurrentWordBank()), false, false);
    }

    public ModeEnum getNewMode(ModeEnum old) {
        return ModeEnum.OPEN;
    }
}

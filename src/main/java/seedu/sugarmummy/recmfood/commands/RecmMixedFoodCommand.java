package seedu.sugarmummy.recmfood.commands;

import static java.util.Objects.requireNonNull;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Recommends a combination of foods one from each type.
 */
public class RecmMixedFoodCommand extends Command {

    public static final String COMMAND_WORD = "recmfmix";
    public static final String MESSAGE_SUCCESS = "Hope you like this combination~\n"
            + "If not, type again to see other possible combinations =)";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.RECM_MIXED_FOOD;
    }

    @Override
    public boolean isToCreateNewPane() {
        return true;
    }
}

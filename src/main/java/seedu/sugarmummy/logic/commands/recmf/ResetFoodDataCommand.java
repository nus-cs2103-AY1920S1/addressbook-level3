package seedu.sugarmummy.logic.commands.recmf;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.model.util.SampleFoodDataUtil.getSampleFoodList;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Cleans all modifications from the user and resets the food data to the pre-loaded recommendations.
 */
public class ResetFoodDataCommand extends Command {

    public static final String COMMAND_WORD = "resetf";
    public static final String MESSAGE_SUCCESS = "Food data has been reset!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setFoodList(getSampleFoodList());
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.RESET_FOOD;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof ResetFoodDataCommand);
    }
}

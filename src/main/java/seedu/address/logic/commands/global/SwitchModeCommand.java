package seedu.address.logic.commands.global;

import java.util.Optional;

import seedu.address.logic.FunctionMode;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.model.Model;

/**
 * Toggles between the different functions of the StudyBuddy app.
 */
public class SwitchModeCommand extends Command {
    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches between the respective functions and"
            + " commands available.\n"
            + "Parameters: MODE ABBREVIATION\n"
            + "Options:\n"
            + COMMAND_WORD + " fc\n"
            + COMMAND_WORD + " notes\n"
            + COMMAND_WORD + " cs";

    private static final String SWITCH_TO_CHEATSHEET_FUNCTION_FEEDBACK = "You are currently using the "
            + "cheatsheet function!";
    private static final String SWITCH_TO_FLASHCARD_FUNCTION_FEEDBACK = "You are currently using the "
            + "flashcard function!";
    private static final String SWITCH_TO_NOTES_FUNCTION_FEEDBACK = "You are currently using the notes function!";
    private final FunctionMode targetMode;

    public SwitchModeCommand(FunctionMode targetMode) {
        this.targetMode = targetMode;
    }

    @Override
    public CommandResult execute(Model model) {
        String feedBackString;
        switch (targetMode) {
        case NOTE:
            feedBackString = SWITCH_TO_NOTES_FUNCTION_FEEDBACK;
            LogicManager.setMode(FunctionMode.NOTE);
            break;
        case FLASHCARD:
            feedBackString = SWITCH_TO_FLASHCARD_FUNCTION_FEEDBACK;
            LogicManager.setMode(FunctionMode.FLASHCARD);
            break;
        case CHEATSHEET:
            feedBackString = SWITCH_TO_CHEATSHEET_FUNCTION_FEEDBACK;
            LogicManager.setMode(FunctionMode.CHEATSHEET);
            break;
        default:
            feedBackString = null; // To be re-implemented
            LogicManager.setMode(FunctionMode.UNDEFINED);
        }
        return new GlobalCommandResult(feedBackString, false, false, true,
                Optional.of(targetMode));
    }

    public FunctionMode getTargetMode() {
        // Used in MainWindow
        return targetMode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchModeCommand // instanceof handles nulls
                && targetMode.equals(((SwitchModeCommand) other).targetMode)); // state check
    }
}

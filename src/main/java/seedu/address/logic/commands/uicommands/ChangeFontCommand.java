package seedu.address.logic.commands.uicommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.ui.util.FontName;

/**
 * Changes the font of the application.
 */
public class ChangeFontCommand extends Command {

    public static final String COMMAND_WORD = "changeFont";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the application font to specified font. "
            + "PARAMETERS: font name (case sensitive; type listFont to see available fonts) "
            + "EXAMPLE: " + COMMAND_WORD + " verdana";

    public static final String MESSAGE_SUCCESS = "Changed font to %1$s";

    private final FontName fontName;

    public ChangeFontCommand(FontName fontName) {
        this.fontName = fontName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, fontName), fontName, false, true);
    }

}

package seedu.guilttrip.logic.commands.uicommands;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.ui.util.FontName;

/**
 * Changes the font of the application.
 */
public class ChangeFontCommand extends Command {

    public static final String COMMAND_WORD = "changeFont";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Changes the application font to specified font. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "PARAMETERS: font name:\n" + FontName.getAllFontNameStrings().toString() + "\n"
            + "EXAMPLE: " + COMMAND_WORD + " verdana";

    public static final String MESSAGE_SUCCESS = "Changed font to %1$s";

    private final FontName fontName;

    public ChangeFontCommand(FontName fontName) {
        this.fontName = fontName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, fontName), fontName, true);
    }

}

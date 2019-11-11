package seedu.weme.logic.commands.createcommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_COLOR;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_SIZE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_STYLE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.template.MemeText;

/**
 * Adds a piece of text to the current template.
 */
public class TextAddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": adds text to the current template at the specified coordinates.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + "\nParameters: "
            + "TEXT "
            + PREFIX_X_COORDINATE + "X_COORDINATE "
            + PREFIX_Y_COORDINATE + "Y_COORDINATE "
            + "[" + PREFIX_COLOR + "COLOR] "
            + "[" + PREFIX_SIZE + "SIZE] "
            + "[" + PREFIX_STYLE + "STYLE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS students be like "
            + PREFIX_X_COORDINATE + "0.2 "
            + PREFIX_Y_COORDINATE + "0.3 "
            + PREFIX_COLOR + "cyan "
            + PREFIX_STYLE + "bold "
            + PREFIX_SIZE + "3";

    public static final String MESSAGE_SUCCESS = "New text added: %s";

    private final MemeText text;

    /**
     * Creates an TextAddCommand to add {@code MemeText} to the meme being created..
     */
    public TextAddCommand(MemeText text) {
        requireNonNull(text);
        this.text = text;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addMemeText(text);
        model.addMemeTextToRecords(text);

        CommandResult result = new CommandResult(
                String.format(MESSAGE_SUCCESS, text.toString()));
        model.commitWeme(result.getFeedbackToUser());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TextAddCommand // instanceof handles nulls
                && text.equals(((TextAddCommand) other).text));
    }
}


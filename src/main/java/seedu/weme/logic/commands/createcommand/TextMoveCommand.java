package seedu.weme.logic.commands.createcommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;

import java.util.List;
import java.util.Optional;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.template.Coordinates;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.exceptions.InvalidCoordinatesException;

/**
 * Moves a piece of text added for meme creation. Serves as shorthand for `edit INDEX x/X y/X`.
 */
public class TextMoveCommand extends Command {

    public static final String COMMAND_WORD = "move";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": Moves a piece of added text. Serves as "
        + "shorthand for \"edit INDEX x/X y/X\"";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
        + "\nParameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_X_COORDINATE + "CHANGE_IN_X] "
        + "[" + PREFIX_Y_COORDINATE + "CHANGE_IN_Y]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + "x/-0.1";

    public static final float DEFAULT_MOVE_DISTANCE = 0.025f;

    public static final String MESSAGE_MOVE_TEXT_SUCCESS = "Moved text \"%s\" from %s to %s.";
    public static final String MESSAGE_NO_COORDINATES_PROVIDED = "At least one coordinate must be provided.";
    public static final String MESSAGE_TEXT_MOVED_OUT_OF_IMAGE_BOUNDARY = "Text would be moved out of image boundary";

    private final Index index;
    private final Optional<Float> changeInX;
    private final Optional<Float> changeInY;

    /**
     * @param index     of the text in the list to edit
     * @param changeInX change in the x coordinate, empty of no change
     * @param changeInY change in the y coordinate, empty of no change
     */
    public TextMoveCommand(Index index, Optional<Float> changeInX, Optional<Float> changeInY) {
        requireAllNonNull(index, changeInX, changeInY);

        this.index = index;
        this.changeInX = changeInX;
        this.changeInY = changeInY;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (changeInX.isEmpty() && changeInY.isEmpty()) {
            throw new CommandException(MESSAGE_NO_COORDINATES_PROVIDED);
        }

        List<MemeText> textList = model.getMemeTextList();

        if (index.getZeroBased() >= textList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_TEXT_DISPLAYED_INDEX);
        }

        MemeText textToMove = textList.get(index.getZeroBased());
        float x = textToMove.getX();
        float y = textToMove.getY();
        if (changeInX.isPresent()) {
            x += changeInX.get();
        }
        if (changeInY.isPresent()) {
            y += changeInY.get();
        }
        Coordinates coordinates;
        try {
            coordinates = new Coordinates(x, y);
        } catch (InvalidCoordinatesException ice) {
            throw new CommandException(MESSAGE_TEXT_MOVED_OUT_OF_IMAGE_BOUNDARY, ice);
        }
        MemeText replacement = new MemeText(
            textToMove.getText(), coordinates, textToMove.getMemeTextColor(),
            textToMove.getMemeTextStyle(), textToMove.getMemeTextSize());

        model.setMemeText(textToMove, replacement);
        model.addMemeTextToRecords(replacement);

        CommandResult result = new CommandResult(
            String.format(MESSAGE_MOVE_TEXT_SUCCESS,
                replacement.getText(),
                textToMove.getCoordinates().toString(),
                replacement.getCoordinates().toString()));
        model.commitWeme(result.getFeedbackToUser());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TextMoveCommand)) {
            return false;
        }

        // state check
        TextMoveCommand e = (TextMoveCommand) other;
        return index.equals(e.index)
            && changeInX.equals(e.changeInX)
            && changeInY.equals(e.changeInY);
    }

}

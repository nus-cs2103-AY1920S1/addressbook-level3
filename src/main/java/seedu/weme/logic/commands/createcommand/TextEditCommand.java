package seedu.weme.logic.commands.createcommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_COLOR;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_SIZE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_STYLE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TEXT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;

import java.util.List;
import java.util.Optional;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.commons.util.CollectionUtil;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.template.Coordinates;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.MemeTextColor;
import seedu.weme.model.template.MemeTextSize;
import seedu.weme.model.template.MemeTextStyle;

/**
 * Edits the details of an existing meme in Weme.
 */
public class TextEditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meme text identified "
            + "by the index number. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TEXT + "TEXT] "
            + "[" + PREFIX_X_COORDINATE + "X_COORDINATE] "
            + "[" + PREFIX_Y_COORDINATE + "Y_COORDINATE] "
            + "[" + PREFIX_COLOR + "COLOR] "
            + "[" + PREFIX_SIZE + "SIZE] "
            + "[" + PREFIX_STYLE + "STYLE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "t/When you wake up at 3am "
            + "x/0.3";

    public static final String MESSAGE_EDIT_MEME_TEXT_SUCCESS = "Edited text %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditMemeTextDescriptor editMemeTextDescriptor;

    /**
     * @param index                  of the text in the list to edit
     * @param editMemeTextDescriptor details to edit the text with
     */
    public TextEditCommand(Index index, EditMemeTextDescriptor editMemeTextDescriptor) {
        requireAllNonNull(index, editMemeTextDescriptor);

        this.index = index;
        this.editMemeTextDescriptor = new EditMemeTextDescriptor(editMemeTextDescriptor);
    }

    /**
     * Creates and returns a {@code Memetext} with the details of {@code textToEdit}
     * edited with {@code editTextDescriptor}.
     */
    private static MemeText createEditedMemeText(MemeText textToEdit, EditMemeTextDescriptor editTextDescriptor) {
        requireAllNonNull(textToEdit, editTextDescriptor);

        String text = editTextDescriptor.getText().orElse(textToEdit.getText());
        float x = editTextDescriptor.getX().orElse(textToEdit.getX());
        float y = editTextDescriptor.getY().orElse(textToEdit.getY());
        Coordinates coordinates = new Coordinates(x, y);
        MemeTextColor color = editTextDescriptor.getColor().orElse(textToEdit.getMemeTextColor());
        MemeTextStyle style = editTextDescriptor.getStyle().orElse(textToEdit.getMemeTextStyle());
        MemeTextSize size = editTextDescriptor.getSize().orElse(textToEdit.getMemeTextSize());

        return new MemeText(text, coordinates, color, style, size);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        MemeCreation session = model.getMemeCreation();
        List<MemeText> textList = session.getMemeTextList();

        if (index.getZeroBased() >= textList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_TEXT_DISPLAYED_INDEX);
        }

        MemeText textToEdit = textList.get(index.getZeroBased());
        MemeText editedText = createEditedMemeText(textToEdit, editMemeTextDescriptor);

        try {
            session.setText(textToEdit, editedText);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage(), ive);
        }

        CommandResult result = new CommandResult(
                String.format(MESSAGE_EDIT_MEME_TEXT_SUCCESS, editedText.toString()));
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
        if (!(other instanceof TextEditCommand)) {
            return false;
        }

        // state check
        TextEditCommand e = (TextEditCommand) other;
        return index.equals(e.index)
                && editMemeTextDescriptor.equals(e.editMemeTextDescriptor);
    }

    /**
     * Stores the details to edit the meme text with. Each non-empty field value will replace the
     * corresponding field value of the meme text.
     */
    public static class EditMemeTextDescriptor {
        private String text;
        private Float x;
        private Float y;
        private MemeTextColor color;
        private MemeTextStyle style;
        private MemeTextSize size;

        public EditMemeTextDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMemeTextDescriptor(EditMemeTextDescriptor toCopy) {
            setText(toCopy.text);
            setX(toCopy.x);
            setY(toCopy.y);
            setColor(toCopy.color);
            setStyle(toCopy.style);
            setSize(toCopy.size);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(text, x, y, color, style, size);
        }

        public Optional<String> getText() {
            return Optional.ofNullable(text);
        }

        public void setText(String text) {
            this.text = text;
        }

        public Optional<Float> getX() {
            return Optional.ofNullable(x);
        }

        public void setX(Float x) {
            this.x = x;
        }

        public Optional<Float> getY() {
            return Optional.ofNullable(y);
        }

        public void setY(Float y) {
            this.y = y;
        }

        public Optional<MemeTextColor> getColor() {
            return Optional.ofNullable(color);
        }

        public void setColor(MemeTextColor color) {
            this.color = color;
        }

        public Optional<MemeTextStyle> getStyle() {
            return Optional.ofNullable(style);
        }

        public void setStyle(MemeTextStyle style) {
            this.style = style;
        }

        public Optional<MemeTextSize> getSize() {
            return Optional.ofNullable(size);
        }

        public void setSize(MemeTextSize size) {
            this.size = size;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMemeTextDescriptor)) {
                return false;
            }

            // state check
            EditMemeTextDescriptor e = (EditMemeTextDescriptor) other;

            return text.equals(e.text)
                    && x.equals(e.x)
                    && y.equals(e.y)
                    && color.equals(e.color)
                    && style.equals(e.style)
                    && size.equals(e.size);
        }
    }
}

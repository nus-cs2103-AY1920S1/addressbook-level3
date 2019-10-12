package seedu.address.logic.commands.preferences;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_GUI_LOCK;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_HEIGHT;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_WIDTH;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_XPOS;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_YPOS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Constructs a command that attempts to modify the current values in the edit trip page.
 * It overwrites each of the values of the current pageStatus editTripDescriptor with
 * the provided editTripDescriptor's values if they are specified.
 */
public class EditPrefsFieldCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits your preferences fields "
            + "by the index of the form field as displayed, or by the various prefixes of the fields. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_WINDOW_WIDTH + "WINDOW WIDTH (must be a positive decimal integer)] "
            + "[" + PREFIX_WINDOW_HEIGHT + "WINDOW HEIGHT (must be a positive decimal integer)] "
            + "[" + PREFIX_WINDOW_XPOS + "WINDOW X POSITION (must be an integer)] "
            + "[" + PREFIX_WINDOW_YPOS + "WINDOW Y POSITION (must be an integer)] "
            + "[" + PREFIX_GUI_LOCK + "GUI LOCK ('t' or 'f', case insensitive)] "
            + "[" + PREFIX_DATA_FILE_PATH + "DATA FILE PATH (must be a valid path to an existing file)]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_WINDOW_WIDTH + "800 "
            + PREFIX_WINDOW_HEIGHT + "600";

    public static final String MESSAGE_NOT_EDITED = "At least one field to must be provided!";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited the current form:%1$s";

    private final EditPrefsDescriptor editPrefsDescriptor;

    /**
     * @param editPrefsDescriptor {@code EditPrefsDescriptor} to modify the current descriptor with.
     */
    public EditPrefsFieldCommand(EditPrefsDescriptor editPrefsDescriptor) {
        requireNonNull(editPrefsDescriptor);

        this.editPrefsDescriptor = editPrefsDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        EditPrefsDescriptor currentDescriptor = model.getPageStatus().getEditPrefsDescriptor();
        requireNonNull(currentDescriptor);

        EditPrefsDescriptor newEditPrefsDescriptor =
                new EditPrefsDescriptor(currentDescriptor, editPrefsDescriptor);

        model.setPageStatus(
                model.getPageStatus().withNewEditPrefsDescriptor(newEditPrefsDescriptor));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editPrefsDescriptor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPrefsFieldCommand)) {
            return false;
        }

        // state check
        EditPrefsFieldCommand e = (EditPrefsFieldCommand) other;
        return editPrefsDescriptor.equals(e.editPrefsDescriptor);
    }

    /**
     * Stores the details to edit the preferences with.
     * Each non-empty field value will replace the
     * corresponding field value of the preferences field.
     */
    public static class EditPrefsDescriptor {

        private OptionalDouble windowWidth;
        private OptionalDouble windowHeight;
        private OptionalInt windowXPosition;
        private OptionalInt windowYPosition;
        private Optional<String> dataFilePath;
        private Optional<Boolean> isGuiLocked;

        public EditPrefsDescriptor() {
            windowWidth = OptionalDouble.empty();
            windowHeight = OptionalDouble.empty();
            windowXPosition = OptionalInt.empty();
            windowYPosition = OptionalInt.empty();
            dataFilePath = Optional.empty();
            isGuiLocked = Optional.empty();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPrefsDescriptor(EditPrefsDescriptor toCopy) {
            windowHeight = toCopy.getWindowHeight();
            windowWidth = toCopy.getWindowWidth();
            windowXPosition = toCopy.getWindowXPos();
            windowYPosition = toCopy.getWindowYPos();
            dataFilePath = toCopy.getDataFilePath();
            isGuiLocked = toCopy.isGuiLocked();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPrefsDescriptor(ReadOnlyUserPrefs toCopy) {
            GuiSettings currentGuiSettings = toCopy.getGuiSettings();
            Path currentDataFilePath = toCopy.getTravelPalFilePath();

            windowHeight = OptionalDouble.of(currentGuiSettings.getWindowHeight());
            windowWidth = OptionalDouble.of(currentGuiSettings.getWindowWidth());
            windowXPosition = OptionalInt.of(currentGuiSettings.getWindowCoordinates().x);
            windowYPosition = OptionalInt.of(currentGuiSettings.getWindowCoordinates().y);
            dataFilePath = Optional.of(currentDataFilePath.toString());
            isGuiLocked = Optional.of(toCopy.isGuiPrefsLocked());
        }

        /**
         * Overwrite constructor.
         * Constructs a new {@code EditTripDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditTripDescriptor} to use.
         * @param newDescriptor New {@code EditTripDescriptor} to use.
         */
        public EditPrefsDescriptor(EditPrefsDescriptor oldDescriptor, EditPrefsDescriptor newDescriptor) {
            this();
            newDescriptor.windowWidth.ifPresentOrElse(
                    this::setWindowWidth, () ->
                            oldDescriptor.windowWidth.ifPresent(this::setWindowWidth));
            newDescriptor.windowHeight.ifPresentOrElse(
                    this::setWindowHeight, () ->
                            oldDescriptor.windowHeight.ifPresent(this::setWindowHeight));

            newDescriptor.windowXPosition.ifPresentOrElse(
                    this::setWindowXPos, () ->
                            oldDescriptor.windowXPosition.ifPresent(this::setWindowXPos));
            newDescriptor.windowYPosition.ifPresentOrElse(
                    this::setWindowYPos, () ->
                            oldDescriptor.windowYPosition.ifPresent(this::setWindowYPos));

            newDescriptor.dataFilePath.ifPresentOrElse(
                    this::setDataFilePath, () ->
                            oldDescriptor.dataFilePath.ifPresent(this::setDataFilePath));
            newDescriptor.isGuiLocked.ifPresentOrElse(
                    this::setGuiLocked, () ->
                            oldDescriptor.isGuiLocked.ifPresent(this::setGuiLocked));
        }

        /**
         * Builds a new {@code UserPrefs} instance.
         * Requires all fields to have been set.
         *
         * @return New {@code UserPrefs} created.
         * @throws NullPointerException If any of the specified fields are empty.
         */
        public UserPrefs buildUserPrefs() throws NullPointerException {
            if (isAllPresent(dataFilePath, isGuiLocked) && windowWidth.isPresent() && windowHeight.isPresent()
                    && windowXPosition.isPresent() && windowYPosition.isPresent()) {

                UserPrefs userPrefs = new UserPrefs();
                GuiSettings guiSettings = new GuiSettings(windowWidth.getAsDouble(), windowHeight.getAsDouble(),
                        windowXPosition.getAsInt(), windowYPosition.getAsInt());
                userPrefs.setGuiSettings(guiSettings);

                Path dataFilePath = Paths.get(this.dataFilePath.get());
                userPrefs.setTravelPalFilePath(dataFilePath);

                userPrefs.setGuiPrefsLocked(isGuiLocked.get());

                return userPrefs;
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(dataFilePath, isGuiLocked)
                    || windowWidth.isPresent() || windowHeight.isPresent()
                    || windowXPosition.isPresent() || windowYPosition.isPresent();
        }

        public void setWindowHeight(double windowHeight) {
            this.windowHeight = OptionalDouble.of(windowHeight);
        }

        public OptionalDouble getWindowHeight() {
            return this.windowHeight;
        }

        public void setWindowWidth(double windowWidth) {
            this.windowWidth = OptionalDouble.of(windowWidth);
        }

        public OptionalDouble getWindowWidth() {
            return this.windowWidth;
        }

        public void setWindowXPos(int windowXPosition) {
            this.windowXPosition = OptionalInt.of(windowXPosition);
        }

        public OptionalInt getWindowXPos() {
            return this.windowXPosition;
        }

        public void setWindowYPos(int windowYPosition) {
            this.windowYPosition = OptionalInt.of(windowYPosition);
        }

        public OptionalInt getWindowYPos() {
            return this.windowYPosition;
        }

        public void setDataFilePath(String dataFilePath) {
            this.dataFilePath = Optional.of(dataFilePath);
        }

        public Optional<String> getDataFilePath() {
            return this.dataFilePath;
        }

        public Optional<Boolean> isGuiLocked() {
            return isGuiLocked;
        }

        public void setGuiLocked(boolean isGuiLocked) {
            this.isGuiLocked = Optional.of(isGuiLocked);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPrefsDescriptor e = (EditPrefsDescriptor) other;

            return getWindowWidth().equals(e.getWindowWidth())
                    && getWindowHeight().equals(e.getWindowHeight())
                    && getWindowXPos().equals(e.getWindowXPos())
                    && getWindowYPos().equals(e.getWindowYPos())
                    && getDataFilePath().equals(e.getDataFilePath())
                    && isGuiLocked().equals(e.isGuiLocked());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            windowHeight.ifPresent(height -> builder.append(" Window Height: ").append(height));
            windowWidth.ifPresent(width -> builder.append(" Window Width: ").append(width));
            windowXPosition.ifPresent(xPos -> builder.append(" Window X Position: ").append(xPos));
            windowYPosition.ifPresent(yPos -> builder.append(" Window Y Position: ").append(yPos));
            isGuiLocked.ifPresent(guiLocked -> builder.append(" Is gui locked: ").append(guiLocked));
            dataFilePath.ifPresent(dataFilePath -> builder.append(" Data file path: ").append(dataFilePath));

            return builder.toString();
        }
    }
}

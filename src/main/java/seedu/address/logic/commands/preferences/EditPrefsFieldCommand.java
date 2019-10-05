package seedu.address.logic.commands.preferences;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.Trip;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_HEIGHT;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_WIDTH;

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
            + "[" + PREFIX_WINDOW_HEIGHT + "WINDOW HEIGHT] "
            + "[" + PREFIX_WINDOW_WIDTH + "WINDOW WIDTH] "
            + "[" + PREFIX_DATA_FILE_PATH + "DATA FILE PATH]...\n"
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
        private Optional<Double> windowWidth;
        private Optional<Double> windowHeight;
        private Optional<Integer> windowXPosition;
        private Optional<Integer> windowYPosition;
        private Optional<String> dataFilePath;

        public EditPrefsDescriptor() {
            windowWidth = Optional.empty();
            windowHeight = Optional.empty();
            windowXPosition = Optional.empty();
            windowYPosition = Optional.empty();
            dataFilePath = Optional.empty();
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
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPrefsDescriptor(ReadOnlyUserPrefs toCopy) {
            GuiSettings currentGuiSettings = toCopy.getGuiSettings();
            Path currentDataFilePath = toCopy.getTravelPalFilePath();

            windowHeight = Optional.of(currentGuiSettings.getWindowHeight());
            windowWidth = Optional.of(currentGuiSettings.getWindowWidth());
            windowXPosition = Optional.of(currentGuiSettings.getWindowCoordinates().x);
            windowYPosition = Optional.of(currentGuiSettings.getWindowCoordinates().y);
            dataFilePath = Optional.of(currentDataFilePath.toString());
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
            newDescriptor.windowHeight.ifPresentOrElse(
                    newHeight -> setWindowHeight(newHeight),
                    () -> setWindowHeight(oldDescriptor.windowHeight.get()));

            newDescriptor.windowWidth.ifPresentOrElse(
                    newWidth -> setWindowWidth(newWidth),
                    () -> setWindowWidth(oldDescriptor.windowWidth.get()));

            newDescriptor.windowXPosition.ifPresentOrElse(
                    newXPos -> setWindowXPos(newXPos),
                    () -> setWindowXPos(oldDescriptor.windowXPosition.get()));

            newDescriptor.windowYPosition.ifPresentOrElse(
                    newYPos -> setWindowYPos(newYPos),
                    () -> setWindowYPos(oldDescriptor.windowYPosition.get()));

            newDescriptor.dataFilePath.ifPresentOrElse(
                    newFilePath -> setDataFilePath(newFilePath),
                    () -> setDataFilePath(oldDescriptor.dataFilePath.get()));
        }

        /**
         * Builds a new {@code UserPrefs} instance.
         * Requires all fields to have been set.
         *
         * @return New {@code UserPrefs} created.
         * @throws NullPointerException If any of the specified fields are empty.
         */
        public UserPrefs buildUserPrefs() throws NullPointerException {
            if (isAllPresent(windowHeight, windowWidth, windowXPosition, windowYPosition, dataFilePath)) {
                UserPrefs userPrefs = new UserPrefs();

                GuiSettings guiSettings = new GuiSettings(windowWidth.get(), windowHeight.get(),
                        windowXPosition.get(), windowYPosition.get());
                userPrefs.setGuiSettings(guiSettings);

                Path dataFilePath = Paths.get(this.dataFilePath.get());
                userPrefs.setTravelPalFilePath(dataFilePath);

                return userPrefs;
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(
                    windowHeight, windowWidth, windowXPosition, windowYPosition, dataFilePath);
        }

        public void setWindowHeight(double windowHeight) {
            this.windowHeight = Optional.of(windowHeight);
        }

        public Optional<Double> getWindowHeight() {
            return this.windowHeight;
        }

        public void setWindowWidth(double windowWidth) {
            this.windowWidth = Optional.of(windowWidth);
        }

        public Optional<Double> getWindowWidth() {
            return this.windowWidth;
        }

        public void setWindowXPos(int windowXPosition) {
            this.windowXPosition = Optional.of(windowXPosition);
        }

        public Optional<Integer> getWindowXPos() {
            return this.windowXPosition;
        }

        public void setWindowYPos(int windowYPosition) {
            this.windowYPosition = Optional.of(windowYPosition);
        }

        public Optional<Integer> getWindowYPos() {
            return this.windowYPosition;
        }

        public void setDataFilePath(String dataFilePath) {
            this.dataFilePath = Optional.of(dataFilePath);
        }

        public Optional<String> getDataFilePath() {
            return this.dataFilePath;
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
                    && getDataFilePath().equals(e.getDataFilePath());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            windowHeight.ifPresent(height -> builder.append(" Window Height: ").append(windowHeight));
            windowWidth.ifPresent(width -> builder.append(" Window Width: ").append(windowWidth));
            windowXPosition.ifPresent(width -> builder.append(" Window X Position: ").append(windowXPosition));
            windowYPosition.ifPresent(width -> builder.append(" Window Y Position: ").append(windowYPosition));
            dataFilePath.ifPresent(width -> builder.append(" Data file path: ").append(dataFilePath));

            return builder.toString();
        }
    }
}

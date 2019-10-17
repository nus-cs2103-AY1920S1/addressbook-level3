package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CHEATSHEETS;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.cheatsheet.Title;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing cheatsheet in the StudyBuddy Application.
 */
public class EditCheatSheetCommand extends Command {

    public static final String COMMAND_WORD = EDIT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the cheatsheet identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_CONTENT + "CONTENT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_CHEATSHEET_SUCCESS = "Edited Cheatsheet: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CHEATSHEET =
            "This cheatsheet already exists in the StudyBuddy application.";

    private final Index index;
    private final EditCheatSheetDescriptor editCheatSheetDescriptor;

    /**
     * @param index of the cheatsheet in the filtered cheatsheet list to edit
     * @param editCheatSheetDescriptor details to edit the cheatsheet with
     */
    public EditCheatSheetCommand(Index index, EditCheatSheetDescriptor editCheatSheetDescriptor) {
        requireNonNull(index);
        requireNonNull(editCheatSheetDescriptor);

        this.index = index;
        this.editCheatSheetDescriptor = new EditCheatSheetDescriptor(editCheatSheetDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CheatSheet> lastShownList = model.getFilteredCheatSheetList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
        }

        CheatSheet cheatSheetToEdit = lastShownList.get(index.getZeroBased());
        CheatSheet editedCheatSheet = createEditedCheatSheet(cheatSheetToEdit, editCheatSheetDescriptor);

        if (!cheatSheetToEdit.isSameCheatSheet(editedCheatSheet) && model.hasCheatSheet(editedCheatSheet)) {
            throw new CommandException(MESSAGE_DUPLICATE_CHEATSHEET);
        }

        model.setCheatSheet(cheatSheetToEdit, editedCheatSheet);
        model.updateFilteredCheatSheetList(PREDICATE_SHOW_ALL_CHEATSHEETS);
        return new CommandResult(String.format(MESSAGE_EDIT_CHEATSHEET_SUCCESS, editedCheatSheet));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public static CheatSheet createEditedCheatSheet(CheatSheet cheatSheetToEdit,
                                                 EditCheatSheetDescriptor editCheatSheetDescriptor) {
        assert cheatSheetToEdit != null;

        Title updatedTitle = editCheatSheetDescriptor.getTitle().orElse(cheatSheetToEdit.getTitle());
        Set<Content> updatedContents = editCheatSheetDescriptor.getContents().orElse(cheatSheetToEdit.getContents());
        Set<Tag> updatedTags = editCheatSheetDescriptor.getTags().orElse(cheatSheetToEdit.getTags());

        return new CheatSheet(updatedTitle, updatedContents, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCheatSheetCommand)) {
            return false;
        }

        // state check
        EditCheatSheetCommand e = (EditCheatSheetCommand) other;
        return index.equals(e.index)
                && editCheatSheetDescriptor.equals(e.editCheatSheetDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditCheatSheetDescriptor {
        private Title title;
        private Set<Content> contents;
        private Set<Tag> tags;

        public EditCheatSheetDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCheatSheetDescriptor(EditCheatSheetDescriptor toCopy) {
            setTitle(toCopy.title);
            setContents(toCopy.contents);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, contents, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        /**
         * Sets {@code contents} to this object's {@code contents}.
         * A defensive copy of {@code contents} is used internally.
         */
        public void setContents(Set<Content> contents) {
            this.contents = (contents != null) ? new HashSet<>(contents) : null;
        }

        /**
         * Returns an unmodifiable content set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code content} is null.
         */
        public Optional<Set<Content>> getContents() {
            return (contents != null) ? Optional.of(Collections.unmodifiableSet(contents)) : Optional.empty();
        }


        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCheatSheetDescriptor)) {
                return false;
            }

            // state check
            EditCheatSheetDescriptor e = (EditCheatSheetDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getContents().equals(e.getContents())
                    && getTags().equals(e.getTags());
        }
    }
}

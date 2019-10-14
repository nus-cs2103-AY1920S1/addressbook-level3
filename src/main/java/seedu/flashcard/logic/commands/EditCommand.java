package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_WORD;
import static seedu.flashcard.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import seedu.flashcard.commons.util.CollectionUtil;
import seedu.flashcard.commons.core.Messages;
import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.Word;
import seedu.flashcard.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Command to edit a flashcard or tag
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the flashcard identified "
            + "by the index number used in the displayed flashcard list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_WORD + "WORD] "
            + "[" + PREFIX_DEFINITION + "DEFINITION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_WORD + "A* Search "
            + PREFIX_TAG + "Artificial Intelligence";

    public static final String MESSAGE_EDIT_FLASHCARD_SUCCESS = "Edited Flashcard: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the flashcard list.";

    private final Index index;
    private final EditFlashcardDescriptor editFlashcardDescriptor;

    /**
     * @param index of the flashcard in the filtered flashcard list to edit
     * @param editFlashCardDescriptor details to edit the flashcard with
     */
    public EditCommand(Index index, EditFlashcardDescriptor editFlashCardDescriptor) {
        requireNonNull(index);
        requireNonNull(editFlashCardDescriptor);
        this.index = index;
        this.editFlashcardDescriptor = new EditFlashcardDescriptor(editFlashCardDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException{
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_ID_NUMBER);
        }
        Flashcard flashcardToEdit = lastShownList.get(index.getZeroBased());
        Flashcard editedFlashcard = createEditedFlashcard(flashcardToEdit, editFlashcardDescriptor);
        if (!flashcardToEdit.isSameFlashcard(editedFlashcard) && model.hasFlashcard(editedFlashcard)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }
        model.setFlashcard(flashcardToEdit, editedFlashcard);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        return new CommandResult(String.format(MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard));
    }

    /**
     * Creates and returns a {@code Flashcard} with the details of {@code flashcardToEdit}
     * edited with {@code editFlashcardDescriptor}.
     */
    private static Flashcard createEditedFlashcard(Flashcard flashcardToEdit, EditFlashcardDescriptor editFlashcardDescriptor) {
        assert flashcardToEdit != null;
        Word updatedWord = editFlashcardDescriptor.getWord().orElse(flashcardToEdit.getWord());
        Definition updatedDefinition = editFlashcardDescriptor.getDefinition().orElse(flashcardToEdit.getDefinition());
        Set<Tag> updatedTags = editFlashcardDescriptor.getTags().orElse(flashcardToEdit.getTags());
        return new Flashcard(updatedWord, updatedDefinition, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editFlashcardDescriptor.equals(e.editFlashcardDescriptor);
    }

    /**
     * Stores the details to edit the flashcard with.
     * corresponding field value of the person.
     */
    public static class EditFlashcardDescriptor {

        private Word word;
        private Definition definition;
        private Set<Tag> tags;

        public EditFlashcardDescriptor() {}

        public EditFlashcardDescriptor(EditFlashcardDescriptor toCopy) {
            setWord(toCopy.word);
            setDefinition(toCopy.definition);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(word, definition, tags);
        }

        public void setWord(Word word) {
            this.word = word;
        }

        public Optional<Word> getWord() {
            return Optional.ofNullable(word);
        }

        public void setDefinition(Definition definition) {
            this.definition = definition;
        }

        public Optional<Definition> getDefinition() {
            return Optional.ofNullable(definition);
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
            if (!(other instanceof EditFlashcardDescriptor)) {
                return false;
            }

            // state check
            EditFlashcardDescriptor e = (EditFlashcardDescriptor) other;

            return getWord().equals(e.getWord())
                    && getDefinition().equals(e.getDefinition())
                    && getTags().equals(e.getTags());
        }
    }
}

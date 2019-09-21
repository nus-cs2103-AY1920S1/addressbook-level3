package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.algobase.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;

/**
 * Edits the details of an existing Problem in the algobase.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Problem identified "
            + "by the index number used in the displayed Problem list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Problem: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This Problem already exists in the algobase.";

    private final Index index;
    private final EditProblemDescriptor editProblemDescriptor;

    /**
     * @param index of the Problem in the filtered Problem list to edit
     * @param editProblemDescriptor details to edit the Problem with
     */
    public EditCommand(Index index, EditProblemDescriptor editProblemDescriptor) {
        requireNonNull(index);
        requireNonNull(editProblemDescriptor);

        this.index = index;
        this.editProblemDescriptor = new EditProblemDescriptor(editProblemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Problem> lastShownList = model.getFilteredProblemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Problem problemToEdit = lastShownList.get(index.getZeroBased());
        Problem editedProblem = createEditedProblem(problemToEdit, editProblemDescriptor);

        if (!problemToEdit.isSameProblem(editedProblem) && model.hasProblem(editedProblem)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setProblem(problemToEdit, editedProblem);
        model.updateFilteredProblemList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedProblem));
    }

    /**
     * Creates and returns a {@code Problem} with the details of {@code problemToEdit}
     * edited with {@code editProblemDescriptor}.
     */
    private static Problem createEditedProblem(Problem problemToEdit, EditProblemDescriptor editProblemDescriptor) {
        assert problemToEdit != null;

        Name updatedName = editProblemDescriptor.getName().orElse(problemToEdit.getName());
        Author updatedAuthor = editProblemDescriptor.getAuthor().orElse(problemToEdit.getAuthor());
        WebLink updatedWebLink = editProblemDescriptor.getWebLink().orElse(problemToEdit.getWebLink());
        Description updatedDescription = editProblemDescriptor.getDescription().orElse(problemToEdit.getDescription());
        Set<Tag> updatedTags = editProblemDescriptor.getTags().orElse(problemToEdit.getTags());

        return new Problem(updatedName, updatedAuthor, updatedWebLink, updatedDescription, updatedTags);
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
                && editProblemDescriptor.equals(e.editProblemDescriptor);
    }

    /**
     * Stores the details to edit the Problem with. Each non-empty field value will replace the
     * corresponding field value of the Problem.
     */
    public static class EditProblemDescriptor {
        private Name name;
        private Author author;
        private WebLink webLink;
        private Description description;
        private Set<Tag> tags;

        public EditProblemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditProblemDescriptor(EditProblemDescriptor toCopy) {
            setName(toCopy.name);
            setAuthor(toCopy.author);
            setWebLink(toCopy.webLink);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, author, webLink, description, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public Optional<Author> getAuthor() {
            return Optional.ofNullable(author);
        }

        public void setWebLink(WebLink webLink) {
            this.webLink = webLink;
        }

        public Optional<WebLink> getWebLink() {
            return Optional.ofNullable(webLink);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
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
            if (!(other instanceof EditProblemDescriptor)) {
                return false;
            }

            // state check
            EditProblemDescriptor e = (EditProblemDescriptor) other;

            return getName().equals(e.getName())
                    && getAuthor().equals(e.getAuthor())
                    && getWebLink().equals(e.getWebLink())
                    && getDescription().equals(e.getDescription())
                    && getTags().equals(e.getTags());
        }
    }
}

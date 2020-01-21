package seedu.algobase.ui.action.actions;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PROBLEMS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionResult;

/**
 * Edits the details of an existing Problem in the algobase.
 */
public class EditProblemUiAction extends UiAction {

    public static final String MESSAGE_EDIT_PROBLEM_SUCCESS = "Problem [%1$s] edited.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROBLEM = "Problem [%1$s] already exists in AlgoBase.";

    private final Id id;
    private final EditProblemDescriptor editProblemDescriptor;
    /**
     * @param id of the Problem in the filtered Problem list to edit
     * @param editProblemDescriptor details to edit the Problem with
     */
    public EditProblemUiAction(Id id, EditProblemDescriptor editProblemDescriptor) {
        requireNonNull(id);
        requireNonNull(editProblemDescriptor);

        this.id = id;
        this.editProblemDescriptor = new EditProblemDescriptor(editProblemDescriptor);
    }


    /**
     * Retrieves the problem to be edited from the problem list.
     */
    private Problem retrieveProblemToEdit(List<Problem> problemList) throws NoSuchElementException {
        for (Problem problem : problemList) {
            if (problem.getId().equals(id)) {
                return problem;
            }
        }
        throw new NoSuchElementException("No Problem Found");
    }

    @Override
    public UiActionResult execute(Model model) throws UiActionException {
        requireNonNull(model);
        List<Problem> lastShownList = model.getFilteredProblemList();

        Problem problemToEdit = retrieveProblemToEdit(lastShownList);
        Problem editedProblem = createEditedProblem(problemToEdit, editProblemDescriptor);

        if (!problemToEdit.isSameProblem(editedProblem) && model.hasProblem(editedProblem)) {
            throw new UiActionException(String.format(MESSAGE_DUPLICATE_PROBLEM, problemToEdit.getName()));
        }

        model.setProblem(problemToEdit, editedProblem);

        if (editProblemDescriptor.tags != null) {
            for (Tag tag : editProblemDescriptor.tags) {
                if (!model.hasTag(tag)) {
                    model.addTag(tag);
                }
            }
        }

        model.updateFilteredProblemList(PREDICATE_SHOW_ALL_PROBLEMS);
        return new UiActionResult(
            true,
            Optional.of(String.format(MESSAGE_EDIT_PROBLEM_SUCCESS, editedProblem.getName()))
        );
    }

    /**
     * Creates and returns a {@code Problem} with the details of {@code problemToEdit}
     * edited with {@code editProblemDescriptor}.
     */
    private static Problem createEditedProblem(Problem problemToEdit, EditProblemDescriptor editProblemDescriptor) {
        assert problemToEdit != null;

        Id id = problemToEdit.getId();
        Name updatedName = editProblemDescriptor.getName().orElse(problemToEdit.getName());
        Author updatedAuthor = editProblemDescriptor.getAuthor().orElse(problemToEdit.getAuthor());
        WebLink updatedWebLink = editProblemDescriptor.getWebLink().orElse(problemToEdit.getWebLink());
        Description updatedDescription = editProblemDescriptor.getDescription().orElse(problemToEdit.getDescription());
        Set<Tag> updatedTags = editProblemDescriptor.getTags().orElse(problemToEdit.getTags());
        Difficulty updatedDifficulty = editProblemDescriptor.getDifficulty().orElse(problemToEdit.getDifficulty());
        Remark updatedRemark = editProblemDescriptor.getRemark().orElse(problemToEdit.getRemark());
        Source updatedSource = editProblemDescriptor.getSource().orElse(problemToEdit.getSource());

        return new Problem(id, updatedName, updatedAuthor, updatedWebLink, updatedDescription, updatedTags,
            updatedDifficulty, updatedRemark, updatedSource);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.algobase.logic.commands.problem.EditCommand)) {
            return false;
        }

        // state check
        EditProblemUiAction e = (EditProblemUiAction) other;
        return id.equals(e.id)
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
        private Difficulty difficulty;
        private Remark remark;
        private Source source;

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
            setDifficulty(toCopy.difficulty);
            setRemark(toCopy.remark);
            setSource(toCopy.source);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, author, webLink, description, tags, difficulty, remark, source);
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

        public void setDifficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
        }

        public Optional<Difficulty> getDifficulty() {
            return Optional.ofNullable(difficulty);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public Optional<Source> getSource() {
            return Optional.ofNullable(source);
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
            if (!(other instanceof seedu.algobase.logic.commands.problem.EditCommand.EditProblemDescriptor)) {
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

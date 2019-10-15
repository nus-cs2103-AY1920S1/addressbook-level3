package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.model.problem.AuthorMatchesKeywordPredicate.DEFAULT_AUTHOR_PREDICATE;
import static seedu.algobase.model.problem.DescriptionContainsKeywordsPredicate.DEFAULT_DESCRIPTION_PREDICATE;
import static seedu.algobase.model.problem.DifficultyIsInRangePredicate.DEFAULT_DIFFICULTY_PREDICATE;
import static seedu.algobase.model.problem.NameContainsKeywordsPredicate.DEFAULT_NAME_PREDICATE;
import static seedu.algobase.model.problem.SourceMatchesKeywordPredicate.DEFAULT_SOURCE_PREDICATE;
import static seedu.algobase.model.problem.TagIncludesKeywordsPredicate.DEFAULT_TAG_PREDICATE;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.problem.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.problem.DifficultyIsInRangePredicate;
import seedu.algobase.model.problem.NameContainsKeywordsPredicate;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.SourceMatchesKeywordPredicate;
import seedu.algobase.model.problem.TagIncludesKeywordsPredicate;

/**
 * Finds and lists all problems in algobase fulfilling all the given constraints.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds a problem by name, author, and/or "
            + "description and displays them as a list with index numbers.\n"
            + "Parameters:\n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_SOURCE + "SOURCE] "
            + "[" + PREFIX_DIFFICULTY + "LOWER_BOUND-UPPER_BOUND] "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example:\n"
            + COMMAND_WORD
            + PREFIX_AUTHOR + "Tung Kam Chuen";
    public static final String MESSAGE_NO_CONSTRAINTS = "At least one search constraint should be provided.";

    /**
     * {@code ALWAYS_TRUE_PROBLEM_PREDICATE} is a non-restrictive predicate that always returns true, which
     * is used as a placeholder for predicates not provided by the user.
     */
    private static final Predicate<Problem> ALWAYS_TRUE_PROBLEM_PREDICATE = problem -> true;
    private final Predicate<Problem> predicate;

    public FindCommand(FindProblemDescriptor findProblemDescriptor) {
        requireNonNull(findProblemDescriptor);
        this.predicate = createFindProblemPredicate(findProblemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProblemList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, model.getFilteredProblemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }

    /**
     * Creates and returns a {@code findProblemPredicate} with the details of {@code findProblemDescriptor}.
     */
    private static Predicate<Problem> createFindProblemPredicate(FindProblemDescriptor findProblemDescriptor) {
        requireNonNull(findProblemDescriptor);
        NameContainsKeywordsPredicate namePredicate =
            findProblemDescriptor.getNamePredicate().orElse(DEFAULT_NAME_PREDICATE);
        AuthorMatchesKeywordPredicate authorPredicate =
            findProblemDescriptor.getAuthorPredicate().orElse(DEFAULT_AUTHOR_PREDICATE);
        DescriptionContainsKeywordsPredicate descriptionPredicate =
            findProblemDescriptor.getDescriptionPredicate().orElse(DEFAULT_DESCRIPTION_PREDICATE);
        SourceMatchesKeywordPredicate sourcePredicate =
            findProblemDescriptor.getSourcePredicate().orElse(DEFAULT_SOURCE_PREDICATE);
        DifficultyIsInRangePredicate difficultyPredicate =
            findProblemDescriptor.getDifficultyPredicate().orElse(DEFAULT_DIFFICULTY_PREDICATE);
        TagIncludesKeywordsPredicate tagPredicate =
            findProblemDescriptor.getTagPredicate().orElse(DEFAULT_TAG_PREDICATE);
        return namePredicate
            .and(authorPredicate)
            .and(descriptionPredicate)
            .and(sourcePredicate)
            .and(difficultyPredicate)
            .and(tagPredicate);
    }

    /**
     * Stores the details to find the {@code Problem}. Each non-empty field value will replace the
     * corresponding field value of the {@code findProblemPredicate}.
     */
    public static class FindProblemDescriptor {
        private NameContainsKeywordsPredicate namePredicate;
        private AuthorMatchesKeywordPredicate authorPredicate;
        private DescriptionContainsKeywordsPredicate descriptionPredicate;
        private SourceMatchesKeywordPredicate sourcePredicate;
        private DifficultyIsInRangePredicate difficultyPredicate;
        private TagIncludesKeywordsPredicate tagPredicate;

        public FindProblemDescriptor() {
            // Default constructor as empty
        }

        /**
         * Copy constructor.
         */
        public FindProblemDescriptor(FindProblemDescriptor toCopy) {
            setNamePredicate(toCopy.namePredicate);
            setAuthorPredicate(toCopy.authorPredicate);
            setDescriptionPredicate(toCopy.descriptionPredicate);
            setSourcePredicate(toCopy.sourcePredicate);
            setDifficultyPredicate(toCopy.difficultyPredicate);
            setTagPredicate(toCopy.tagPredicate);
        }

        public boolean isAnyFieldProvided() {
            return CollectionUtil.isAnyNonNull(namePredicate, authorPredicate, descriptionPredicate, sourcePredicate,
                difficultyPredicate, tagPredicate);
        }

        public void setNamePredicate(NameContainsKeywordsPredicate nameContainsKeywordsPredicate) {
            this.namePredicate = nameContainsKeywordsPredicate;
        }

        public Optional<NameContainsKeywordsPredicate> getNamePredicate() {
            return Optional.ofNullable(namePredicate);
        }

        public void setAuthorPredicate(AuthorMatchesKeywordPredicate authorPredicate) {
            this.authorPredicate = authorPredicate;
        }

        public Optional<AuthorMatchesKeywordPredicate> getAuthorPredicate() {
            return Optional.ofNullable(authorPredicate);
        }

        public void setDescriptionPredicate(DescriptionContainsKeywordsPredicate descriptionPredicate) {
            this.descriptionPredicate = descriptionPredicate;
        }

        public Optional<DescriptionContainsKeywordsPredicate> getDescriptionPredicate() {
            return Optional.ofNullable(descriptionPredicate);
        }

        public void setSourcePredicate(SourceMatchesKeywordPredicate sourcePredicate) {
            this.sourcePredicate = sourcePredicate;
        }

        public Optional<SourceMatchesKeywordPredicate> getSourcePredicate() {
            return Optional.ofNullable(sourcePredicate);
        }

        public void setDifficultyPredicate(DifficultyIsInRangePredicate difficultyPredicate) {
            this.difficultyPredicate = difficultyPredicate;
        }

        public Optional<DifficultyIsInRangePredicate> getDifficultyPredicate() {
            return Optional.ofNullable(difficultyPredicate);
        }

        public void setTagPredicate(TagIncludesKeywordsPredicate tagPredicate) {
            this.tagPredicate = tagPredicate;
        }

        public Optional<TagIncludesKeywordsPredicate> getTagPredicate() {
            return Optional.ofNullable(tagPredicate);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindProblemDescriptor)) {
                return false;
            }

            // state check
            FindProblemDescriptor e = (FindProblemDescriptor) other;

            return getNamePredicate().equals(e.getNamePredicate())
                && getAuthorPredicate().equals(e.getAuthorPredicate())
                && getDescriptionPredicate().equals(e.getDescriptionPredicate())
                && getSourcePredicate().equals(e.getSourcePredicate())
                && getDifficultyPredicate().equals(e.getDifficultyPredicate())
                && getTagPredicate().equals(e.getTagPredicate());
        }
    }
}

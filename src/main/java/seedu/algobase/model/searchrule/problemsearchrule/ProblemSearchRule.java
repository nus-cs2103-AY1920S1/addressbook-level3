package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.algobase.model.problem.Problem;

/**
 * Represents a search rule on finding {@code Problem} in AlgoBase.
 * Guarantees: details are present and not null, field values are immutable.
 *
 * Note that although this class's implementation is quite similar to
 * {@link FindProblemDescriptor}, {@code ProblemSearchRule} is a model that
 * needs to be stored in the storage, thus it's better we make it immutable.
 *
 * This seemingly duplicated implementation is to keep
 */
public class ProblemSearchRule {

    // Identity field(s)
    private final Name name;

    // Data field(s)
    private final NameContainsKeywordsPredicate namePredicate;
    private final AuthorMatchesKeywordPredicate authorPredicate;
    private final DescriptionContainsKeywordsPredicate descriptionPredicate;
    private final SourceMatchesKeywordPredicate sourcePredicate;
    private final DifficultyIsInRangePredicate difficultyPredicate;
    private final TagIncludesKeywordsPredicate tagPredicate;
    private final boolean hasDefaultNamePredicate;
    private final boolean hasDefaultAuthorPredicate;
    private final boolean hasDefaultDescriptionPredicate;
    private final boolean hasDefaultSourcePredicate;
    private final boolean hasDefaultDifficultyPredicate;
    private final boolean hasDefaultTagPredicate;

    public ProblemSearchRule(Name name, NameContainsKeywordsPredicate namePredicate,
                             AuthorMatchesKeywordPredicate authorPredicate,
                             DescriptionContainsKeywordsPredicate descriptionPredicate,
                             SourceMatchesKeywordPredicate sourcePredicate,
                             DifficultyIsInRangePredicate difficultyPredicate,
                             TagIncludesKeywordsPredicate tagPredicate) {
        requireNonNull(name);
        this.name = name;
        this.namePredicate = namePredicate;
        this.authorPredicate = authorPredicate;
        this.descriptionPredicate = descriptionPredicate;
        this.sourcePredicate = sourcePredicate;
        this.difficultyPredicate = difficultyPredicate;
        this.tagPredicate = tagPredicate;
        this.hasDefaultNamePredicate = isNull(namePredicate);
        this.hasDefaultAuthorPredicate = isNull(authorPredicate);
        this.hasDefaultDescriptionPredicate = isNull(descriptionPredicate);
        this.hasDefaultSourcePredicate = isNull(sourcePredicate);
        this.hasDefaultDifficultyPredicate = isNull(difficultyPredicate);
        this.hasDefaultTagPredicate = isNull(tagPredicate);
    }

    public Name getName() {
        return name;
    }

    public NameContainsKeywordsPredicate getNamePredicate() {
        return namePredicate;
    }

    public AuthorMatchesKeywordPredicate getAuthorPredicate() {
        return authorPredicate;
    }

    public DescriptionContainsKeywordsPredicate getDescriptionPredicate() {
        return descriptionPredicate;
    }

    public SourceMatchesKeywordPredicate getSourcePredicate() {
        return sourcePredicate;
    }

    public DifficultyIsInRangePredicate getDifficultyPredicate() {
        return difficultyPredicate;
    }

    public TagIncludesKeywordsPredicate getTagPredicate() {
        return tagPredicate;
    }

    public boolean hasDefaultNamePredicate() {
        return hasDefaultNamePredicate;
    }

    public boolean hasDefaultAuthorPredicate() {
        return hasDefaultAuthorPredicate;
    }

    public boolean hasDefaultDescriptionPredicate() {
        return hasDefaultDescriptionPredicate;
    }

    public boolean hasDefaultSourcePredicate() {
        return hasDefaultSourcePredicate;
    }

    public boolean hasDefaultDifficultyPredicate() {
        return hasDefaultDifficultyPredicate;
    }

    public boolean hasDefaultTagPredicate() {
        return hasDefaultTagPredicate;
    }

    public Predicate<Problem> getFindProblemPredicate() {
        FindProblemDescriptor descriptor = new FindProblemDescriptor();
        if (!hasDefaultNamePredicate) {
            descriptor.setNamePredicate(namePredicate);
        }
        if (!hasDefaultAuthorPredicate) {
            descriptor.setAuthorPredicate(authorPredicate);
        }
        if (!hasDefaultDescriptionPredicate) {
            descriptor.setDescriptionPredicate(descriptionPredicate);
        }
        if (!hasDefaultSourcePredicate) {
            descriptor.setSourcePredicate(sourcePredicate);
        }
        if (!hasDefaultDifficultyPredicate) {
            descriptor.setDifficultyPredicate(difficultyPredicate);
        }
        if (!hasDefaultTagPredicate) {
            descriptor.setTagPredicate(tagPredicate);
        }
        return descriptor.getFindProblemPredicate();
    }

    /**
     * Returns true when {@code other} has the same identity field(s) as {@code this}.
     *
     * @param other
     */
    public boolean isSameProblemSearchRule(ProblemSearchRule other) {
        if (other == this) {
            return true;
        }

        return other != null
            && other.getName().equals(getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ProblemSearchRule // instanceof handles nulls
            && name.equals(((ProblemSearchRule) other).getName())); // state check
    }
}

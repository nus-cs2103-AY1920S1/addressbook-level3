package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * Represents a search rule on finding {@code Problem} in AlgoBase.
 */
public class ProblemSearchRule extends FindProblemDescriptor {

    // Identity field(s)
    private final Name name;

    public ProblemSearchRule(Name name, NameContainsKeywordsPredicate namePredicate,
                             AuthorMatchesKeywordPredicate authorPredicate,
                             DescriptionContainsKeywordsPredicate descriptionPredicate,
                             SourceMatchesKeywordPredicate sourcePredicate,
                             DifficultyIsInRangePredicate difficultyPredicate,
                             TagIncludesKeywordsPredicate tagPredicate) {
        super(namePredicate, authorPredicate, descriptionPredicate, sourcePredicate, difficultyPredicate, tagPredicate);
        requireNonNull(name);
        this.name = name;
    }

    public Name getName() {
        return name;
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

    /**
     * Returns true if two {@code Optional} are both present and have the same content or are both empty.
     * @param first optional object
     * @param second optional object
     */
    private boolean optionalEquals(Optional first, Optional second) {
        if (first.isPresent()) {
            return second.isPresent() && first.get().equals(second.get());
        } else {
            return second.isEmpty();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ProblemSearchRule // instanceof handles nulls
            && name.equals(((ProblemSearchRule) other).getName()))
            && optionalEquals(getNamePredicate(), ((ProblemSearchRule) other).getNamePredicate())
            && optionalEquals(getAuthorPredicate(), ((ProblemSearchRule) other).getAuthorPredicate())
            && optionalEquals(getDescriptionPredicate(), ((ProblemSearchRule) other).getDescriptionPredicate())
            && optionalEquals(getDifficultyPredicate(), ((ProblemSearchRule) other).getDifficultyPredicate())
            && optionalEquals(getSourcePredicate(), ((ProblemSearchRule) other).getSourcePredicate())
            && optionalEquals(getTagPredicate(), ((ProblemSearchRule) other).getTagPredicate()); // state check
    }
}

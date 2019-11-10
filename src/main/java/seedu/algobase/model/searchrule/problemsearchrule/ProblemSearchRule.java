package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.optionalEquals;

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
     * @param other the other problem search rule to be compared
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
            && name.equals(((ProblemSearchRule) other).getName()))
            && optionalEquals(getNamePredicate(), ((ProblemSearchRule) other).getNamePredicate())
            && optionalEquals(getAuthorPredicate(), ((ProblemSearchRule) other).getAuthorPredicate())
            && optionalEquals(getDescriptionPredicate(), ((ProblemSearchRule) other).getDescriptionPredicate())
            && optionalEquals(getDifficultyPredicate(), ((ProblemSearchRule) other).getDifficultyPredicate())
            && optionalEquals(getSourcePredicate(), ((ProblemSearchRule) other).getSourcePredicate())
            && optionalEquals(getTagPredicate(), ((ProblemSearchRule) other).getTagPredicate()); // state check
    }
}

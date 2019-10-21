package seedu.algobase.model.searchrule.problemsearchrule;

/**
 * Represents a search rule on finding {@code Problem} in AlgoBase.
 * Guarantees: details are present and not null, field values are immutable.
 */
public class ProblemSearchRule {
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

    public ProblemSearchRule (NameContainsKeywordsPredicate namePredicate,
                             AuthorMatchesKeywordPredicate authorPredicate,
                             DescriptionContainsKeywordsPredicate descriptionPredicate,
                             SourceMatchesKeywordPredicate sourcePredicate,
                             DifficultyIsInRangePredicate difficultyPredicate,
                             TagIncludesKeywordsPredicate tagPredicate,
                             boolean hasDefaultNamePredicate,
                             boolean hasDefaultAuthorPredicate,
                            boolean hasDefaultDescriptionPredicate,
                            boolean hasDefaultSourcePredicate,
                            boolean hasDefaultDifficultyPredicate,
                            boolean hasDefaultTagPredicate) {
        this.namePredicate = namePredicate;
        this.authorPredicate = authorPredicate;
        this.descriptionPredicate = descriptionPredicate;
        this.sourcePredicate = sourcePredicate;
        this.difficultyPredicate = difficultyPredicate;
        this.tagPredicate = tagPredicate;
        this.hasDefaultNamePredicate = hasDefaultNamePredicate;
        this.hasDefaultAuthorPredicate = hasDefaultAuthorPredicate;
        this.hasDefaultDescriptionPredicate = hasDefaultDescriptionPredicate;
        this.hasDefaultSourcePredicate = hasDefaultSourcePredicate;
        this.hasDefaultDifficultyPredicate = hasDefaultDifficultyPredicate;
        this.hasDefaultTagPredicate = hasDefaultTagPredicate;
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
}

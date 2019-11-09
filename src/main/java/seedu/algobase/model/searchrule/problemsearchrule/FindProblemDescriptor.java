package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate.DEFAULT_AUTHOR_PREDICATE;
import static seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate.DEFAULT_DESCRIPTION_PREDICATE;
import static seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate.DEFAULT_DIFFICULTY_PREDICATE;
import static seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate.DEFAULT_NAME_PREDICATE;
import static seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate.DEFAULT_SOURCE_PREDICATE;
import static seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate.DEFAULT_TAG_PREDICATE;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.model.problem.Problem;

/**
 * Stores the details to find the {@code Problem}. Each non-empty field value will replace the
 * corresponding field value of the {@code findProblemPredicate}.
 */
public class FindProblemDescriptor {
    private NameContainsKeywordsPredicate namePredicate;
    private AuthorMatchesKeywordPredicate authorPredicate;
    private DescriptionContainsKeywordsPredicate descriptionPredicate;
    private SourceMatchesKeywordPredicate sourcePredicate;
    private DifficultyIsInRangePredicate difficultyPredicate;
    private TagIncludesKeywordsPredicate tagPredicate;

    public FindProblemDescriptor() {
        namePredicate = null;
        authorPredicate = null;
        descriptionPredicate = null;
        sourcePredicate = null;
        difficultyPredicate = null;
        tagPredicate = null;
    }

    public FindProblemDescriptor(NameContainsKeywordsPredicate namePredicate,
                                 AuthorMatchesKeywordPredicate authorPredicate,
                                 DescriptionContainsKeywordsPredicate descriptionPredicate,
                                 SourceMatchesKeywordPredicate sourcePredicate,
                                 DifficultyIsInRangePredicate difficultyPredicate,
                                 TagIncludesKeywordsPredicate tagPredicate) {
        this.namePredicate = namePredicate;
        this.authorPredicate = authorPredicate;
        this.descriptionPredicate = descriptionPredicate;
        this.sourcePredicate = sourcePredicate;
        this.difficultyPredicate = difficultyPredicate;
        this.tagPredicate = tagPredicate;
    }

    /**
     * A defensive copy constructor.
     */
    public FindProblemDescriptor(FindProblemDescriptor toCopy) {
        requireNonNull(toCopy);
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

    /**
     * Creates and returns a {@code findProblemPredicate} with the details of {@code findProblemDescriptor}.
     */
    public Predicate<Problem> getFindProblemPredicate() {
        NameContainsKeywordsPredicate namePredicate = getNamePredicate().orElse(DEFAULT_NAME_PREDICATE);
        AuthorMatchesKeywordPredicate authorPredicate = getAuthorPredicate().orElse(DEFAULT_AUTHOR_PREDICATE);
        DescriptionContainsKeywordsPredicate descriptionPredicate =
                getDescriptionPredicate().orElse(DEFAULT_DESCRIPTION_PREDICATE);
        SourceMatchesKeywordPredicate sourcePredicate = getSourcePredicate().orElse(DEFAULT_SOURCE_PREDICATE);
        DifficultyIsInRangePredicate difficultyPredicate =
                getDifficultyPredicate().orElse(DEFAULT_DIFFICULTY_PREDICATE);
        TagIncludesKeywordsPredicate tagPredicate = getTagPredicate().orElse(DEFAULT_TAG_PREDICATE);
        return namePredicate
            .and(authorPredicate)
            .and(descriptionPredicate)
            .and(sourcePredicate)
            .and(difficultyPredicate)
            .and(tagPredicate);
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

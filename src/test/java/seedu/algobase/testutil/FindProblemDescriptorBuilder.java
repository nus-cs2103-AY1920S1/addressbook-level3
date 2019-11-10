package seedu.algobase.testutil;

import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.FindProblemDescriptor;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;

/**
 * A utility class to help with building FindProblemDescriptor objects.
 */
public class FindProblemDescriptorBuilder {

    private FindProblemDescriptor descriptor;

    public FindProblemDescriptorBuilder() {
        descriptor = new FindProblemDescriptor();
    }

    /**
     * Sets the {@code NameContainsKeywordsPredicate} of the {@code FindProblemDescriptor} that we are building.
     */
    public FindProblemDescriptorBuilder withNamePredicate(NameContainsKeywordsPredicate predicate) {
        descriptor.setNamePredicate(predicate);
        return this;
    }

    /**
     * Sets the {@code AuthorMatchesKeywordPredicate} of the {@code FindProblemDescriptor} that we are building.
     */
    public FindProblemDescriptorBuilder withAuthorPredicate(AuthorMatchesKeywordPredicate predicate) {
        descriptor.setAuthorPredicate(predicate);
        return this;
    }

    /**
     * Sets the {@code DescriptionContainsKeywordsPredicate} of the {@code FindProblemDescriptor} that we are building.
     */
    public FindProblemDescriptorBuilder withDescriptionPredicate(DescriptionContainsKeywordsPredicate predicate) {
        descriptor.setDescriptionPredicate(predicate);
        return this;
    }

    /**
     * Sets the {@code DifficultyIsInRangePredicate} of the {@code FindProblemDescriptor} that we are building.
     */
    public FindProblemDescriptorBuilder withDifficultyPredicate(DifficultyIsInRangePredicate predicate) {
        descriptor.setDifficultyPredicate(predicate);
        return this;
    }

    /**
     * Sets the {@code SourceMatchesKeywordPredicate} of the {@code FindProblemDescriptor} that we are building.
     */
    public FindProblemDescriptorBuilder withSourcePredicate(SourceMatchesKeywordPredicate predicate) {
        descriptor.setSourcePredicate(predicate);
        return this;
    }

    /**
     * Sets the {@code TagIncludesKeywordsPredicate} of the {@code FindProblemDescriptor} that we are building.
     */
    public FindProblemDescriptorBuilder withTagPredicate(TagIncludesKeywordsPredicate predicate) {
        descriptor.setTagPredicate(predicate);
        return this;
    }

    /**
     * Returns the built {@code FindProblemDescriptor}.
     */
    public FindProblemDescriptor build() {
        return descriptor;
    }
}

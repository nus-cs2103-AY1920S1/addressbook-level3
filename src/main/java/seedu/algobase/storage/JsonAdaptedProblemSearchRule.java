package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Name;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;

/**
 * Jackson-friendly version of {@link ProblemSearchRule}.
 */
public class JsonAdaptedProblemSearchRule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Find rule's %s field is missing!";

    private final String name;
    private final JsonAdaptedNameContainsKeywordsPredicate nameContainsKeywordsPredicate;
    private final JsonAdaptedAuthorMatchesKeywordPredicate authorMatchesKeywordPredicate;
    private final JsonAdaptedDescriptionContainsKeywordsPredicate descriptionContainsKeywordsPredicate;
    private final JsonAdaptedSourceMatchesKeywordPredicate sourceMatchesKeywordPredicate;
    private final JsonAdaptedDifficultyIsInRangePredicate difficultyIsInRangePredicate;
    private final JsonAdaptedTagIncludesKeywordsPredicate tagIncludesKeywordsPredicate;

    /**
     * Constructs a {@code JsonAdaptedProblemSearchRule} with the given
     * ProblemSearchRule details.
     */
    @JsonCreator
    public JsonAdaptedProblemSearchRule(@JsonProperty("name") String name,
        @JsonProperty("namePredicate") JsonAdaptedNameContainsKeywordsPredicate namePredicate,
        @JsonProperty("authorPredicate") JsonAdaptedAuthorMatchesKeywordPredicate authorPredicate,
        @JsonProperty("descriptionPredicate") JsonAdaptedDescriptionContainsKeywordsPredicate descriptionPredicate,
        @JsonProperty("sourcePredicate") JsonAdaptedSourceMatchesKeywordPredicate sourcePredicate,
        @JsonProperty("difficultyPredicate") JsonAdaptedDifficultyIsInRangePredicate difficultyPredicate,
        @JsonProperty("tagPredicate") JsonAdaptedTagIncludesKeywordsPredicate tagPredicate) {
        this.name = name;
        this.nameContainsKeywordsPredicate = namePredicate;
        this.authorMatchesKeywordPredicate = authorPredicate;
        this.descriptionContainsKeywordsPredicate = descriptionPredicate;
        this.sourceMatchesKeywordPredicate = sourcePredicate;
        this.difficultyIsInRangePredicate = difficultyPredicate;
        this.tagIncludesKeywordsPredicate = tagPredicate;
    }

    /**
     * Converts a given {@code ProblemSearchRule} into this class for Jackson use.
     */
    public JsonAdaptedProblemSearchRule(ProblemSearchRule rule) {
        name = rule.getName().name;
        if (!rule.hasDefaultNamePredicate()) {
            this.nameContainsKeywordsPredicate = new JsonAdaptedNameContainsKeywordsPredicate(rule.getNamePredicate());
        } else {
            this.nameContainsKeywordsPredicate = null;
        }
        if (!rule.hasDefaultAuthorPredicate()) {
            this.authorMatchesKeywordPredicate =
                new JsonAdaptedAuthorMatchesKeywordPredicate(rule.getAuthorPredicate());
        } else {
            this.authorMatchesKeywordPredicate = null;
        }
        if (!rule.hasDefaultDescriptionPredicate()) {
            this.descriptionContainsKeywordsPredicate =
                new JsonAdaptedDescriptionContainsKeywordsPredicate(rule.getDescriptionPredicate());
        } else {
            this.descriptionContainsKeywordsPredicate = null;
        }
        if (!rule.hasDefaultSourcePredicate()) {
            this.sourceMatchesKeywordPredicate =
                new JsonAdaptedSourceMatchesKeywordPredicate(rule.getSourcePredicate());
        } else {
            this.sourceMatchesKeywordPredicate = null;
        }
        if (!rule.hasDefaultDifficultyPredicate()) {
            this.difficultyIsInRangePredicate =
                new JsonAdaptedDifficultyIsInRangePredicate(rule.getDifficultyPredicate());
        } else {
            this.difficultyIsInRangePredicate = null;
        }
        if (!rule.hasDefaultTagPredicate()) {
            this.tagIncludesKeywordsPredicate =
                new JsonAdaptedTagIncludesKeywordsPredicate(rule.getTagPredicate());
        } else {
            this.tagIncludesKeywordsPredicate = null;
        }
    }


    /**
     * Converts a name in string format to a Name Object.
     *
     * @param name name in string format.
     * @return the corresponding Name Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public Name retrieveName(String name) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(String.format(Name.MESSAGE_CONSTRAINTS));
        }
        return new Name(name);
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's
     * {@code ProblemSearchRule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * ProblemSearchRule.
     */
    public ProblemSearchRule toModelType() throws IllegalValueException {
        final Name name = retrieveName(this.name);
        final NameContainsKeywordsPredicate namePredicate = this.nameContainsKeywordsPredicate == null ? null
            : this.nameContainsKeywordsPredicate.toModelType();
        final AuthorMatchesKeywordPredicate authorPredicate = this.authorMatchesKeywordPredicate == null ? null
            : this.authorMatchesKeywordPredicate.toModelType();
        final DescriptionContainsKeywordsPredicate descriptionPredicate =
            this.descriptionContainsKeywordsPredicate == null ? null
                : this.descriptionContainsKeywordsPredicate.toModelType();
        final SourceMatchesKeywordPredicate sourcePredicate = this.sourceMatchesKeywordPredicate == null ? null
            : this.sourceMatchesKeywordPredicate.toModelType();
        final DifficultyIsInRangePredicate difficultyPredicate = this.difficultyIsInRangePredicate == null ? null
            : this.difficultyIsInRangePredicate.toModelType();
        final TagIncludesKeywordsPredicate tagPredicate = this.tagIncludesKeywordsPredicate == null ? null
            : this.tagIncludesKeywordsPredicate.toModelType();
        return new ProblemSearchRule(name, namePredicate, authorPredicate,
            descriptionPredicate, sourcePredicate, difficultyPredicate, tagPredicate);
    }

}

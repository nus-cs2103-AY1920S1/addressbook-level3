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
        this.nameContainsKeywordsPredicate = getAdaptedNamePredicate(rule);
        this.authorMatchesKeywordPredicate = getAdaptedAuthorPredicate(rule);
        this.descriptionContainsKeywordsPredicate = getAdaptedDescriptionPredicate(rule);
        this.sourceMatchesKeywordPredicate = getAdaptedSourcePredicate(rule);
        this.difficultyIsInRangePredicate = getAdaptedDifficultyPredicate(rule);
        this.tagIncludesKeywordsPredicate = getAdaptedTagPredicate(rule);
    }

    /**
     * Returns a {@link JsonAdaptedNameContainsKeywordsPredicate} from a {@link ProblemSearchRule}.
     * @param rule the find rule to extract JSON adapted predicate from
     * @return JSON adapted predicate if it is present in {@code rule}, null otherwise.
     */
    private JsonAdaptedNameContainsKeywordsPredicate getAdaptedNamePredicate(ProblemSearchRule rule) {
        if (rule.getNamePredicate().isPresent()) {
            return new JsonAdaptedNameContainsKeywordsPredicate(rule.getNamePredicate().get());
        } else {
            return null;
        }
    }

    /**
     * Returns a {@link JsonAdaptedAuthorMatchesKeywordPredicate} from a {@link ProblemSearchRule}.
     * @param rule the find rule to extract JSON adapted predicate from
     * @return JSON adapted predicate if it is present in {@code rule}, null otherwise.
     */
    private JsonAdaptedAuthorMatchesKeywordPredicate getAdaptedAuthorPredicate(ProblemSearchRule rule) {
        if (rule.getAuthorPredicate().isPresent()) {
            return new JsonAdaptedAuthorMatchesKeywordPredicate(rule.getAuthorPredicate().get());
        } else {
            return null;
        }
    }

    /**
     * Returns a {@link JsonAdaptedDescriptionContainsKeywordsPredicate} from a {@link ProblemSearchRule}.
     * @param rule the find rule to extract JSON adapted predicate from
     * @return JSON adapted predicate if it is present in {@code rule}, null otherwise.
     */
    private JsonAdaptedDescriptionContainsKeywordsPredicate getAdaptedDescriptionPredicate(ProblemSearchRule rule) {
        if (rule.getDescriptionPredicate().isPresent()) {
            return new JsonAdaptedDescriptionContainsKeywordsPredicate(rule.getDescriptionPredicate().get());
        } else {
            return null;
        }
    }

    /**
     * Returns a {@link JsonAdaptedSourceMatchesKeywordPredicate} from a {@link ProblemSearchRule}.
     * @param rule the find rule to extract JSON adapted predicate from
     * @return JSON adapted predicate if it is present in {@code rule}, null otherwise.
     */
    private JsonAdaptedSourceMatchesKeywordPredicate getAdaptedSourcePredicate(ProblemSearchRule rule) {
        if (rule.getSourcePredicate().isPresent()) {
            return new JsonAdaptedSourceMatchesKeywordPredicate(rule.getSourcePredicate().get());
        } else {
            return null;
        }
    }

    /**
     * Returns a {@link JsonAdaptedDifficultyIsInRangePredicate} from a {@link ProblemSearchRule}.
     * @param rule the find rule to extract JSON adapted predicate from
     * @return JSON adapted predicate if it is present in {@code rule}, null otherwise.
     */
    private JsonAdaptedDifficultyIsInRangePredicate getAdaptedDifficultyPredicate(ProblemSearchRule rule) {
        if (rule.getDifficultyPredicate().isPresent()) {
            return new JsonAdaptedDifficultyIsInRangePredicate(rule.getDifficultyPredicate().get());
        } else {
            return null;
        }
    }

    /**
     * Returns a {@link JsonAdaptedTagIncludesKeywordsPredicate} from a {@link ProblemSearchRule}.
     * @param rule the find rule to extract JSON adapted predicate from
     * @return JSON adapted predicate if it is present in {@code rule}, null otherwise.
     */
    private JsonAdaptedTagIncludesKeywordsPredicate getAdaptedTagPredicate(ProblemSearchRule rule) {
        if (rule.getTagPredicate().isPresent()) {
            return new JsonAdaptedTagIncludesKeywordsPredicate(rule.getTagPredicate().get());
        } else {
            return null;
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

        final NameContainsKeywordsPredicate namePredicate;
        if (this.nameContainsKeywordsPredicate == null) {
            namePredicate = null;
        } else {
            namePredicate = this.nameContainsKeywordsPredicate.toModelType();
        }

        final AuthorMatchesKeywordPredicate authorPredicate;
        if (this.authorMatchesKeywordPredicate == null) {
            authorPredicate = null;
        } else {
            authorPredicate = this.authorMatchesKeywordPredicate.toModelType();
        }

        final DescriptionContainsKeywordsPredicate descriptionPredicate;
        if (this.descriptionContainsKeywordsPredicate == null) {
            descriptionPredicate = null;
        } else {
            descriptionPredicate = this.descriptionContainsKeywordsPredicate.toModelType();
        }

        final SourceMatchesKeywordPredicate sourcePredicate;
        if (this.sourceMatchesKeywordPredicate == null) {
            sourcePredicate = null;
        } else {
            sourcePredicate = this.sourceMatchesKeywordPredicate.toModelType();
        }

        final DifficultyIsInRangePredicate difficultyPredicate;
        if (this.difficultyIsInRangePredicate == null) {
            difficultyPredicate = null;
        } else {
            difficultyPredicate = this.difficultyIsInRangePredicate.toModelType();
        }

        final TagIncludesKeywordsPredicate tagPredicate;
        if (this.tagIncludesKeywordsPredicate == null) {
            tagPredicate = null;
        } else {
            tagPredicate = this.tagIncludesKeywordsPredicate.toModelType();
        }

        return new ProblemSearchRule(name, namePredicate, authorPredicate,
            descriptionPredicate, sourcePredicate, difficultyPredicate, tagPredicate);
    }

}

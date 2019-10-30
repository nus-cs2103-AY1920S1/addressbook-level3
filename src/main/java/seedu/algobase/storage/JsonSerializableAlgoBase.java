package seedu.algobase.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.tag.Tag;

/**
 * An Immutable AlgoBase that is serializable to JSON format.
 */
@JsonRootName(value = "algobase")
public class JsonSerializableAlgoBase {

    public static final String MESSAGE_DUPLICATE_PROBLEM = "Problems list contains duplicate Problem(s).";
    public static final String MESSAGE_DUPLICATE_TAG = "Tags list contains duplicate Tag(s).";
    public static final String MESSAGE_DUPLICATE_FIND_RULE = "Find rules list contains duplicate rules.";

    private final List<JsonAdaptedProblem> problems = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedProblemSearchRule> findRules = new ArrayList<>();
    private final List<JsonAdaptedPlan> plans = new ArrayList<>();
    private final JsonAdaptedGuiState guiState;

    /**
     * Constructs a {@code JsonSerializableAlgoBase} with the given problems.
     */
    @JsonCreator
    public JsonSerializableAlgoBase(@JsonProperty("problems") List<JsonAdaptedProblem> problems,
                                    @JsonProperty("tags") List<JsonAdaptedTag> tags,
                                    @JsonProperty("plans") List<JsonAdaptedPlan> plans,
                                    @JsonProperty("guiState") JsonAdaptedGuiState guiState,
                                    @JsonProperty("findRules") List<JsonAdaptedProblemSearchRule> findRules) {
        this.problems.addAll(problems);
        this.tags.addAll(tags);
        this.plans.addAll(plans);
        this.guiState = guiState;
        this.findRules.addAll(findRules);
    }

    /**
     * Converts a given {@code ReadOnlyAlgoBase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAlgoBase}.
     */
    public JsonSerializableAlgoBase(ReadOnlyAlgoBase source) {
        problems.addAll(source.getProblemList().stream().map(JsonAdaptedProblem::new).collect(Collectors.toList()));
        tags.addAll(source.getTagList().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        findRules.addAll(
            source.getFindRules().stream().map(JsonAdaptedProblemSearchRule::new).collect(Collectors.toList()));
        plans.addAll(source.getPlanList().stream().map(JsonAdaptedPlan::new).collect(Collectors.toList()));
        guiState = new JsonAdaptedGuiState(source.getGuiState());
    }

    /**
     * Converts this algobase into the model's {@code AlgoBase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AlgoBase toModelType() throws IllegalValueException {
        AlgoBase algoBase = new AlgoBase();
        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (algoBase.hasTag(tag)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            algoBase.addTag(tag);
        }
        for (JsonAdaptedProblem jsonAdaptedProblem : problems) {
            Problem problem = jsonAdaptedProblem.toModelType();
            if (algoBase.hasProblem(problem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROBLEM);
            }
            algoBase.addProblem(problem);
        }
        for (JsonAdaptedPlan jsonAdaptedPlan : plans) {
            Plan plan = jsonAdaptedPlan.toModelType(algoBase);
            algoBase.addPlan(plan);
        }

        algoBase.setGuiState(guiState.toModelType(algoBase));

        for (JsonAdaptedProblemSearchRule jsonAdaptedProblemSearchRule: findRules) {
            ProblemSearchRule rule = jsonAdaptedProblemSearchRule.toModelType();
            if (algoBase.hasFindRule(rule)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FIND_RULE);
            }
            algoBase.addFindRule(rule);
        }

        return algoBase;
    }

}

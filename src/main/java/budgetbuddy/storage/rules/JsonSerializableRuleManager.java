package budgetbuddy.storage.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.rule.Rule;

/**
 * An immutable RuleManager that is serializable to JSON format.
 */
@JsonRootName(value = "rulemanager")
public class JsonSerializableRuleManager {

    private final List<JsonAdaptedRule> rules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRuleManager} with the given rules.
     */
    @JsonCreator
    public JsonSerializableRuleManager(@JsonProperty("rules") List<JsonAdaptedRule> rules) {
        this.rules.addAll(rules);
    }

    /**
     * Converts a given {@code RuleManager} into this class for Jackson use.
     * @param source Future changes to the source will not affect the created {@code JsonSerializableRuleManager}.
     */
    public JsonSerializableRuleManager(RuleManager source) {
        rules.addAll(source.getRules().stream().map(JsonAdaptedRule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this rule manager into the model's {@code RuleManager} object.
     * @throws IllegalValueException If any data constraints are violated.
     */
    public RuleManager toModelType() throws IllegalValueException {
        List<Rule> ruleList = new ArrayList<>();
        for (JsonAdaptedRule jsonAdaptedRule : rules) {
            ruleList.add(jsonAdaptedRule.toModelType());
        }
        return new RuleManager(ruleList);
    }
}

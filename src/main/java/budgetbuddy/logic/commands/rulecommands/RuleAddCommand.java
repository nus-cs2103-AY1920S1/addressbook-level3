package budgetbuddy.logic.commands.rulecommands;

import static budgetbuddy.commons.core.Messages.MESSAGE_NO_SUCH_SCRIPT;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PREDICATE;
import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.script.ActionScript;
import budgetbuddy.model.rule.script.PredicateScript;
import budgetbuddy.model.script.ScriptName;

/**
 * Adds a rule.
 */
public class RuleAddCommand extends Command {

    public static final String COMMAND_WORD = "rule add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new rule.\n"
            + "Parameters: "
            + PREFIX_PREDICATE + "PREDICATE "
            + PREFIX_ACTION + "ACTION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PREDICATE + "desc contains food "
            + PREFIX_ACTION + "set_cat Food";

    public static final String MESSAGE_SUCCESS = "New rule added: %1$s";
    public static final String MESSAGE_DUPLICATE_RULE = "This rule already exists in the Rule Engine.";

    private final Rule rule;

    /**
     * Creates a RuleAddCommand to add the specified {@code Rule}
     */
    public RuleAddCommand(Rule rule) {
        requireNonNull(rule);
        this.rule = rule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getRuleManager(), model.getScriptLibrary());
        RuleManager ruleManager = model.getRuleManager();
        ScriptLibrary scriptLibrary = model.getScriptLibrary();

        if (ruleManager.hasRule(rule)) {
            throw new CommandException(MESSAGE_DUPLICATE_RULE);
        }

        RulePredicate pred = rule.getPredicate();
        if (pred.getType().equals(Rule.TYPE_SCRIPT)) {
            ScriptName scriptName = ((PredicateScript) pred).getScriptName();
            if (scriptLibrary.getScript(scriptName) == null) {
                throw new CommandException(String.format(MESSAGE_NO_SUCH_SCRIPT, scriptName));
            }
        }

        RuleAction act = rule.getAction();
        if (act.getType().equals(Rule.TYPE_SCRIPT)) {
            ScriptName scriptName = ((ActionScript) act).getScriptName();
            if (scriptLibrary.getScript(scriptName) == null) {
                throw new CommandException(String.format(MESSAGE_NO_SUCH_SCRIPT, scriptName));
            }
        }

        ruleManager.addRule(rule);
        return new CommandResult(String.format(MESSAGE_SUCCESS, rule), CommandCategory.RULE);
    }
}

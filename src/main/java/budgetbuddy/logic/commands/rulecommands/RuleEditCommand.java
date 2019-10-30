package budgetbuddy.logic.commands.rulecommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PREDICATE;

import java.util.Optional;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.commons.util.CollectionUtil;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.exceptions.RuleNotFoundException;

/**
 * Edits a rule.
 */
public class RuleEditCommand extends Command {

    public static final String COMMAND_WORD = "rule edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a rule.\n"
            + "Parameters: "
            + "<rule index> "
            + "[" + PREFIX_PREDICATE + "PREDICATE] "
            + "[" + PREFIX_ACTION + "ACTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_PREDICATE + "description contains daily";

    public static final String MESSAGE_SUCCESS = "Rule #%1$d edited.";
    public static final String MESSAGE_UNEDITED = "At least one field must be provided for editing.";
    public static final String MESSAGE_FAILURE = "The rule targeted for editing could not be found.";

    private final Index targetIndex;
    private final RuleEditDescriptor ruleEditDescriptor;

    public RuleEditCommand(Index targetIndex, RuleEditDescriptor ruleEditDescriptor) {
        requireAllNonNull(targetIndex, ruleEditDescriptor);
        this.targetIndex = targetIndex;
        this.ruleEditDescriptor = new RuleEditDescriptor(ruleEditDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getRuleManager());
        RuleManager ruleManager = model.getRuleManager();

        Rule editedRule;
        try {
            Rule targetRule = ruleManager.getRule(targetIndex);
            editedRule = createEditedRule(targetRule, ruleEditDescriptor);
            ruleManager.editRule(targetIndex, editedRule);
        } catch (RuleNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()), CommandCategory.RULE);
    }

    /**
     * Creates and returns a {@code Rule} with the details of {@code ruleToEdit},
     * edited with {@code ruleEditDescriptor}.
     */
    private static Rule createEditedRule(Rule ruleToEdit, RuleEditDescriptor ruleEditDescriptor) {
        assert ruleToEdit != null;

        RulePredicate updatedPredicate = ruleEditDescriptor.getPredicate().orElse(ruleToEdit.getPredicate());
        RuleAction updatedAction = ruleEditDescriptor.getAction().orElse(ruleToEdit.getAction());

        return new Rule(updatedPredicate, updatedAction);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RuleEditCommand)) {
            return false;
        }

        RuleEditCommand otherCommand = (RuleEditCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && ruleEditDescriptor.equals(otherCommand.ruleEditDescriptor);
    }

    /**
     * Stores the details to edit the rule with. Each non-empty field value will replace the
     * corresponding field value of the rule.
     */
    public static class RuleEditDescriptor {
        private RulePredicate predicate;
        private RuleAction action;

        public RuleEditDescriptor() {}

        public RuleEditDescriptor(RuleEditDescriptor toCopy) {
            setPredicate(toCopy.predicate);
            setAction(toCopy.action);
        }

        /**
         * Returns true if any field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(predicate, action);
        }

        public void setPredicate(RulePredicate predicate) {
            this.predicate = predicate;
        }

        public Optional<RulePredicate> getPredicate() {
            return Optional.ofNullable(predicate);
        }

        public void setAction(RuleAction action) {
            this.action = action;
        }

        public Optional<RuleAction> getAction() {
            return Optional.ofNullable(action);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof RuleEditDescriptor)) {
                return false;
            }

            RuleEditDescriptor e = (RuleEditDescriptor) other;
            return getPredicate().equals(e.getPredicate())
                    && getAction().equals(e.getAction());
        }
    }
}

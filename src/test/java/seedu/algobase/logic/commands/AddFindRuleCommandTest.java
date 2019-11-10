package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblemSearchRules.ALL_PREDICATE;
import static seedu.algobase.testutil.TypicalProblemSearchRules.NAME_SEQUENCES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.commands.findrule.AddFindRuleCommand;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

class AddFindRuleCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullProblemSearchRule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFindRuleCommand(null));
    }

    @Test
    public void execute_findRuleAcceptedByModel_addSuccessful() throws CommandException {
        ModelStubAcceptFindRuleAdded modelStub = new ModelStubAcceptFindRuleAdded();
        ProblemSearchRule validSearchRule = ALL_PREDICATE;

        CommandResult commandResult = new AddFindRuleCommand(validSearchRule).execute(modelStub, commandHistory);

        assertEquals(String.format(AddFindRuleCommand.MESSAGE_SUCCESS, validSearchRule.getName()),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSearchRule), modelStub.findRulesAdded);
    }

    @Test
    public void execute_duplicateFindRules_throwsCommandException() {
        ProblemSearchRule validRule = ALL_PREDICATE;
        AddFindRuleCommand command = new AddFindRuleCommand(validRule);
        ModelStubWithFindRule modelStubWithFindRule = new ModelStubWithFindRule(validRule);

        String expectedMessage = String.format(AddFindRuleCommand.MESSAGE_DUPLICATE_FIND_RULE, validRule.getName());

        assertThrows(
            CommandException.class, expectedMessage, ()
                -> command.execute(modelStubWithFindRule, commandHistory)
        );
    }

    @Test
    public void equals() {
        ProblemSearchRule validRuleOne = ALL_PREDICATE;
        ProblemSearchRule validRuleTwo = NAME_SEQUENCES;
        AddFindRuleCommand commandOne = new AddFindRuleCommand(validRuleOne);
        AddFindRuleCommand commandTwo = new AddFindRuleCommand(validRuleTwo);

        // same object -> returns true
        assertEquals(commandOne, commandOne);

        // same values -> returns true
        AddFindRuleCommand commandOneCopy = new AddFindRuleCommand(validRuleOne);
        assertEquals(commandOneCopy, commandOne);

        // different types -> returns false
        assertNotEquals(1, commandOne);

        // null -> returns false
        assertNotEquals(null, commandOne);

        // different person -> returns false
        assertNotEquals(commandOne, commandTwo);
    }

    private class ModelStubWithFindRule extends DefaultModelStub {
        private final ProblemSearchRule findRule;

        ModelStubWithFindRule(ProblemSearchRule findRule) {
            requireNonNull(findRule);
            this.findRule = findRule;
        }

        @Override
        public boolean hasFindRule(ProblemSearchRule rule) {
            requireNonNull(rule);
            return this.findRule.isSameProblemSearchRule(rule);
        }
    }


    private class ModelStubAcceptFindRuleAdded extends DefaultModelStub {
        final List<ProblemSearchRule> findRulesAdded = new ArrayList<>();

        @Override
        public boolean hasFindRule(ProblemSearchRule rule) {
            requireNonNull(rule);
            return findRulesAdded.stream().anyMatch(rule::isSameProblemSearchRule);
        }

        @Override
        public void addFindRule(ProblemSearchRule rule) {
            requireNonNull(rule);
            findRulesAdded.add(rule);
        }

        @Override
        public ReadOnlyAlgoBase getAlgoBase() {
            return new AlgoBase();
        }
    }

}

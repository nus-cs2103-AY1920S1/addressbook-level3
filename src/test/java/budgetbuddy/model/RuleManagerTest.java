package budgetbuddy.model;

import static budgetbuddy.testutil.Assert.assertThrows;
import static budgetbuddy.testutil.ruleutil.TypicalRules.EQUAL4090_INAMT_SCRIPT;
import static budgetbuddy.testutil.ruleutil.TypicalRules.FOOD_DESC_FOOD;
import static budgetbuddy.testutil.ruleutil.TypicalRules.LESS10_INAMT_SWITCH;
import static budgetbuddy.testutil.ruleutil.TypicalRules.RULE_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.exceptions.RuleNotFoundException;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.ruleutil.RuleBuilder;
import budgetbuddy.testutil.ruleutil.TypicalActions;

public class RuleManagerTest {

    private RuleManager ruleManager = new RuleManager();

    @BeforeEach
    public void cleanUp_setSampleData() {
        ruleManager = new RuleManager();
        ruleManager.addRule(FOOD_DESC_FOOD);
    }

    @Test
    public void constructor_noArguments_isEmptyList() {
        ruleManager = new RuleManager();
        assertEquals(Collections.emptyList(), ruleManager.getRules());
    }

    @Test
    public void constructor_nullRules_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RuleManager(null));
    }

    @Test
    public void getRules_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> ruleManager.getRules().remove(0));
    }

    @Test
    public void getRule_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ruleManager.getRule(null));
    }

    @Test
    public void getRule_indexBeyondListSize_throwsRuleNotFoundException() {
        assertThrows(RuleNotFoundException.class, () -> ruleManager.getRule(
                Index.fromZeroBased(ruleManager.getRuleCount())));
    }

    @Test
    public void hasRule_ruleNotInRuleList_returnsFalse() {
        assertFalse(ruleManager.hasRule(LESS10_INAMT_SWITCH));
    }

    @Test
    public void hasRule_nullRule_returnsFalse() {
        assertFalse(ruleManager.hasRule(null));
    }

    @Test
    public void editRule_validIndexValidRule_editedRuleReplacesTargetRule() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        Rule targetRule = ruleManager.getRule(targetIndex);
        Rule editedRule = new RuleBuilder(targetRule).withAction(TypicalActions.SWITCH_DIRECT).build();

        ruleManager.editRule(targetIndex, editedRule);
        assertEquals(editedRule, ruleManager.getRule(targetIndex));
    }

    @Test
    public void deleteLoan_validIndex_loanDeletedFromList() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        Rule targetRule = ruleManager.getRule(targetIndex);

        ruleManager.deleteRule(targetIndex);
        assertTrue(ruleManager.getRules().stream().noneMatch(loan -> loan.equals(targetRule)));
    }

    @Test
    public void equals() {
        // same values -> returns true
        RuleManager ruleManagerCopy = new RuleManager(ruleManager.getRules());
        assertEquals(ruleManagerCopy, ruleManager);

        // same object -> returns true
        assertEquals(ruleManager, ruleManager);

        // null -> returns false
        assertNotEquals(null, ruleManager);

        // different types -> returns false
        assertNotEquals(0, ruleManager);

        // different rules -> returns false
        ruleManager = new RuleManager(RULE_LIST);
        RuleManager ruleManagerAlt = new RuleManager();
        ruleManagerAlt.addRule(EQUAL4090_INAMT_SCRIPT);
        assertNotEquals(ruleManagerAlt, ruleManager);
    }
}

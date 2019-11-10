package seedu.module.model.module.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class PreclusionContainsKeywordsPredicateTest {

    private ArchivedModule archivedModule = new ArchivedModuleBuilder().withPreclusions("CS2030").build();

    @Test
    public void test_samePreclusionModuleCode_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2030");
            }
        };
        PreclusionContainsKeywordsPredicate preclusionCodeContainsKeywordsPredicate =
                new PreclusionContainsKeywordsPredicate(list);
        assertTrue(preclusionCodeContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_samePreclusionModuleCodeLowerCase_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("cs2030");
            }
        };
        PreclusionContainsKeywordsPredicate preclusionCodeContainsKeywordsPredicate =
                new PreclusionContainsKeywordsPredicate(list);
        assertTrue(preclusionCodeContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_differentPreclusionModuleCode_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2101");
            }
        };
        PreclusionContainsKeywordsPredicate preclusionCodeContainsKeywordsPredicate =
                new PreclusionContainsKeywordsPredicate(list);
        assertFalse(preclusionCodeContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_partialPreclusionModuleCode_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2");
            }
        };
        PreclusionContainsKeywordsPredicate preclusionCodeContainsKeywordsPredicate =
                new PreclusionContainsKeywordsPredicate(list);
        assertTrue(preclusionCodeContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void equals_sameList_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2030");
            }
        };
        PreclusionContainsKeywordsPredicate preclusionContainsKeywordsPredicate =
                new PreclusionContainsKeywordsPredicate(list);
        PreclusionContainsKeywordsPredicate otherPreclusionContainsKeywordsPredicate =
                new PreclusionContainsKeywordsPredicate(list);
        assertTrue(preclusionContainsKeywordsPredicate.equals(otherPreclusionContainsKeywordsPredicate));
    }

    @Test
    public void equals_differentList_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2030");
            }
        };
        ArrayList<String> diffList = new ArrayList<>() {
            {
                add("CS2101");
            }
        };
        PreclusionContainsKeywordsPredicate preclusionContainsKeywordsPredicate =
                new PreclusionContainsKeywordsPredicate(list);
        PreclusionContainsKeywordsPredicate otherPreclusionContainsKeywordsPredicate =
                new PreclusionContainsKeywordsPredicate(diffList);
        assertFalse(preclusionContainsKeywordsPredicate.equals(otherPreclusionContainsKeywordsPredicate));
    }

}

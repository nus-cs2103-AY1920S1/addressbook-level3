package seedu.module.model.module.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class ModuleCodeContainsKeywordsPredicateTest {

    private ArchivedModule archivedModule = new ArchivedModuleBuilder().build();

    @Test
    public void test_sameModuleCode_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2103T");
            }
        };
        ModuleCodeContainsKeywordsPredicate moduleCodeContainsKeywordsPredicate =
                new ModuleCodeContainsKeywordsPredicate(list);
        assertTrue(moduleCodeContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_sameModuleCodeLowerCase_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("cs2103t");
            }
        };
        ModuleCodeContainsKeywordsPredicate moduleCodeContainsKeywordsPredicate =
                new ModuleCodeContainsKeywordsPredicate(list);
        assertTrue(moduleCodeContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_differentModuleCode_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2101");
            }
        };
        ModuleCodeContainsKeywordsPredicate moduleCodeContainsKeywordsPredicate =
                new ModuleCodeContainsKeywordsPredicate(list);
        assertFalse(moduleCodeContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_partialModuleCode_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2");
            }
        };
        ModuleCodeContainsKeywordsPredicate moduleCodeContainsKeywordsPredicate =
                new ModuleCodeContainsKeywordsPredicate(list);
        assertTrue(moduleCodeContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void equals_sameList_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2103T");
            }
        };
        ModuleCodeContainsKeywordsPredicate moduleCodeContainsKeywordsPredicate =
                new ModuleCodeContainsKeywordsPredicate(list);
        ModuleCodeContainsKeywordsPredicate otherModuleCodeContainsKeywordsPredicate =
                new ModuleCodeContainsKeywordsPredicate(list);
        assertTrue(moduleCodeContainsKeywordsPredicate.equals(otherModuleCodeContainsKeywordsPredicate));
    }

    @Test
    public void equals_differentList_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2103T");
            }
        };
        ArrayList<String> diffList = new ArrayList<>() {
            {
                add("CS2101");
            }
        };
        ModuleCodeContainsKeywordsPredicate moduleCodeContainsKeywordsPredicate =
                new ModuleCodeContainsKeywordsPredicate(list);
        ModuleCodeContainsKeywordsPredicate otherModuleCodeContainsKeywordsPredicate =
                new ModuleCodeContainsKeywordsPredicate(diffList);
        assertFalse(moduleCodeContainsKeywordsPredicate.equals(otherModuleCodeContainsKeywordsPredicate));
    }

}

package seedu.module.model.module.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class NameContainsKeywordsPredicateTest {

    private ArchivedModule archivedModule = new ArchivedModuleBuilder().build();


    @Test
    public void test_sameModuleCode_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2103T");
            }
        };
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(list);
        assertTrue(nameContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_sameModuleCodeLowerCase_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("cs2103t");
            }
        };
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(list);
        assertTrue(nameContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_differentModuleCode_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("cs2101");
            }
        };
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(list);
        assertFalse(nameContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_partialModuleCode_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2103");
            }
        };
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(list);
        assertFalse(nameContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_emptyList_returnsFalse() {
        ArrayList<String> list = new ArrayList<>();
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(list);
        assertFalse(nameContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void equals_sameList_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("CS2103T");
            }
        };
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(list);
        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(list);
        assertTrue(nameContainsKeywordsPredicate.equals(otherNameContainsKeywordsPredicate));
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
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(list);
        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(diffList);
        assertFalse(nameContainsKeywordsPredicate.equals(otherNameContainsKeywordsPredicate));
    }

}

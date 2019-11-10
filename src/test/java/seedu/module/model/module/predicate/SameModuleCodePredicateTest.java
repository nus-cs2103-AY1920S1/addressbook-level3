package seedu.module.model.module.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class SameModuleCodePredicateTest {

    private ArchivedModule archivedModule = new ArchivedModuleBuilder().build();


    @Test
    public void test_sameModuleCode_returnsTrue() {
        SameModuleCodePredicate sameModuleCodePredicate = new SameModuleCodePredicate("CS2103T");
        assertTrue(sameModuleCodePredicate.test(archivedModule));
    }

    @Test
    public void test_sameModuleCodeLowerCase_returnsTrue() {
        SameModuleCodePredicate sameModuleCodePredicate = new SameModuleCodePredicate("cs2103t");
        assertTrue(sameModuleCodePredicate.test(archivedModule));
    }

    @Test
    public void test_differentModuleCode_returnsFalse() {
        SameModuleCodePredicate sameModuleCodePredicate = new SameModuleCodePredicate("CS2101");
        assertFalse(sameModuleCodePredicate.test(archivedModule));
    }

    @Test
    public void test_partialModuleCode_returnsFalse() {
        SameModuleCodePredicate sameModuleCodePredicate = new SameModuleCodePredicate("CS2103");
        assertFalse(sameModuleCodePredicate.test(archivedModule));
    }

    @Test
    public void equals_sameString_returnsTrue() {
        SameModuleCodePredicate moduleCodeContainsKeywordsPredicate =
                new SameModuleCodePredicate("CS2103T");
        SameModuleCodePredicate otherModuleCodeContainsKeywordsPredicate =
                new SameModuleCodePredicate("CS2103T");
        assertTrue(moduleCodeContainsKeywordsPredicate.equals(otherModuleCodeContainsKeywordsPredicate));
    }

    @Test
    public void equals_differentList_returnsFalse() {
        SameModuleCodePredicate moduleCodeContainsKeywordsPredicate =
                new SameModuleCodePredicate("CS2103T");
        SameModuleCodePredicate otherModuleCodeContainsKeywordsPredicate =
                new SameModuleCodePredicate("CS2101");
        assertFalse(moduleCodeContainsKeywordsPredicate.equals(otherModuleCodeContainsKeywordsPredicate));
    }

}

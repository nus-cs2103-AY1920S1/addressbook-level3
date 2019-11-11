package seedu.module.model.module.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class TitleContainsKeywordsPredicateTest {

    private ArchivedModule archivedModule = new ArchivedModuleBuilder().build();

    @Test
    public void test_sameDescription_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("Software");
                add("Engineering");
            }
        };
        TitleContainsKeywordsPredicate titleContainsKeywordsPredicate = new TitleContainsKeywordsPredicate(list);
        assertTrue(titleContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_sameDescriptionLowerCase_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("software");
                add("engineering");
            }
        };
        TitleContainsKeywordsPredicate titleContainsKeywordsPredicate = new TitleContainsKeywordsPredicate(list);
        assertTrue(titleContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_differentDescription_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("hello");
            }
        };
        TitleContainsKeywordsPredicate titleContainsKeywordsPredicate = new TitleContainsKeywordsPredicate(list);
        assertFalse(titleContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_partialDescription_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("Software");
            }
        };
        TitleContainsKeywordsPredicate titleContainsKeywordsPredicate = new TitleContainsKeywordsPredicate(list);
        assertTrue(titleContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_notAllMatches_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("Software");
                add("invalid");
            }
        };
        TitleContainsKeywordsPredicate titleContainsKeywordsPredicate = new TitleContainsKeywordsPredicate(list);
        assertFalse(titleContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void test_fuzzySearchSpellingMistakes_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("Softwae");
            }
        };
        TitleContainsKeywordsPredicate titleContainsKeywordsPredicate = new TitleContainsKeywordsPredicate(list);
        assertTrue(titleContainsKeywordsPredicate.test(archivedModule));
    }

    @Test
    public void equals_sameList_returnsTrue() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("Software");
            }
        };
        TitleContainsKeywordsPredicate titleContainsKeywordsPredicate =
                new TitleContainsKeywordsPredicate(list);
        TitleContainsKeywordsPredicate otherTitleContainsKeywordsPredicate =
                new TitleContainsKeywordsPredicate(list);
        assertTrue(titleContainsKeywordsPredicate.equals(otherTitleContainsKeywordsPredicate));
    }

    @Test
    public void equals_differentList_returnsFalse() {
        ArrayList<String> list = new ArrayList<>() {
            {
                add("Software");
            }
        };
        ArrayList<String> diffList = new ArrayList<>() {
            {
                add("engineering");
            }
        };
        TitleContainsKeywordsPredicate titleContainsKeywordsPredicate =
                new TitleContainsKeywordsPredicate(list);
        TitleContainsKeywordsPredicate otherTitleContainsKeywordsPredicate =
                new TitleContainsKeywordsPredicate(diffList);
        assertFalse(titleContainsKeywordsPredicate.equals(otherTitleContainsKeywordsPredicate));
    }

}

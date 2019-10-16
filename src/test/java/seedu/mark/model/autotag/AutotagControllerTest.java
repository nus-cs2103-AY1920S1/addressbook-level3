package seedu.mark.model.autotag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.mark.model.predicates.NameContainsKeywordsPredicate;
import seedu.mark.model.tag.Tag;

class AutotagControllerTest { // TODO: write tests

    private static final SelectiveBookmarkTagger TAGGER_1 = new SelectiveBookmarkTagger(
            new Tag("hello"), new NameContainsKeywordsPredicate(Arrays.asList("hello")));
    private static final SelectiveBookmarkTagger TAGGER_2 = new SelectiveBookmarkTagger(
            new Tag("myTag"), new NameContainsKeywordsPredicate(Arrays.asList("tag")));

    /*
    @Test
    public void addTagger() {
    }

    @Test
    public void applyTaggers() {
    }

    @Test
    public void applyTaggersToList() {
    }
    */

    @Test
    public void equals() {
        // same object -> returns true
        AutotagController controller = new AutotagController(Arrays.asList(TAGGER_1));
        assertTrue(controller.equals(controller));

        // same values -> returns true
        assertTrue(controller.equals(new AutotagController(Arrays.asList(TAGGER_1))));

        // null -> returns false
        assertFalse(controller.equals(null));

        // different taggers -> returns false
        assertFalse(controller.equals(new AutotagController(Arrays.asList(TAGGER_2))));
    }
}

package seedu.guilttrip.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.guilttrip.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.testutil.WishBuilder;
import seedu.guilttrip.ui.gui.guihandles.WishListCardHandle;
import seedu.guilttrip.ui.wishlist.WishListCard;

public class WishListCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Wish wishWithNoTags = new WishBuilder().withTags().build();
        WishListCard wishCard = new WishListCard(wishWithNoTags, 1);
        uiPartExtension.setUiPart(wishCard);
        assertCardDisplay(wishCard, wishWithNoTags, 1);

        // with tags
        Wish wishWithTags = new WishBuilder().build();
        wishCard = new WishListCard(wishWithTags, 2);
        uiPartExtension.setUiPart(wishCard);
        assertCardDisplay(wishCard, wishWithTags, 2);
    }

    @Test
    public void equals() {
        Wish wish = new WishBuilder().build();
        WishListCard wishCard = new WishListCard(wish, 0);

        // same wish, same index -> returns true
        WishListCard copy = new WishListCard(wish, 0);
        //assertEquals(wishCard, copy);

        // same object -> returns true
        assertEquals(wishCard, wishCard);

        // null -> not equals return true
        assertNotEquals(null, wishCard);

        // different types -> not equals returns true
        assertNotEquals(0, wishCard);

        // different wish, same index -> not equals returns true
        Wish differentWish = new WishBuilder().withDesc("differentDesc").build();
        assertNotEquals(wishCard, new WishListCard(differentWish, 0));

        // same wish, different index -> not equals returns true
        assertNotEquals(wishCard, new WishListCard(wish, 1));
    }

    /**
     * Asserts that {@code wishCard} displays the details of {@code expectedWish} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(WishListCard wishCard, Wish expectedWish, int expectedId) {
        guiRobot.pauseForHuman();

        WishListCardHandle wishCardHandle = new WishListCardHandle(wishCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", wishCardHandle.getId());

        // verify wish details are displayed correctly
        assertCardDisplaysEntry(expectedWish, wishCardHandle);
    }
}

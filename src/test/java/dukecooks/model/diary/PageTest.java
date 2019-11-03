package dukecooks.model.diary;

import static dukecooks.testutil.diary.TypicalPages.PHO_PAGE;
import static dukecooks.testutil.diary.TypicalPages.SUSHI_PAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Image;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.PageType;
import dukecooks.model.diary.components.Title;
import dukecooks.testutil.diary.PageBuilder;

public class PageTest {

    @Test
    public void isSamePage() {
        // same object -> returns true
        assertTrue(SUSHI_PAGE.isSamePage(SUSHI_PAGE));

        // null -> returns false
        assertFalse(SUSHI_PAGE.isSamePage(null));

        // different title -> returns false
        Page differentTitlePage = new PageBuilder(SUSHI_PAGE).withTitle(CommandTestUtil.VALID_PHO_TITLE).build();
        assertFalse(SUSHI_PAGE.isSamePage(differentTitlePage));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Page sushiCopy = new PageBuilder(SUSHI_PAGE).build();
        assertTrue(SUSHI_PAGE.equals(sushiCopy));

        // same object -> returns true
        assertTrue(SUSHI_PAGE.equals(SUSHI_PAGE));

        // null -> returns false
        assertFalse(SUSHI_PAGE.equals(null));

        // different type -> returns false
        assertFalse(SUSHI_PAGE.equals(5));

        // different page -> returns false
        assertFalse(SUSHI_PAGE.equals(PHO_PAGE));

        // different title -> returns false
        Page differentTitlePage = new PageBuilder(SUSHI_PAGE).withTitle(CommandTestUtil.VALID_PHO_TITLE).build();
        assertFalse(SUSHI_PAGE.equals(differentTitlePage));
    }

    @Test
    public void testPageHashCode() {
        Title firstTitle = new Title("hello");
        PageType firstType = new PageType("health");
        PageDescription firstDesc = new PageDescription("description");
        Image firstImage = new Image("/images/pho.jpg");
        Page firstPage = new Page(firstTitle, firstType, firstDesc, firstImage);

        Title secondTitle = new Title("hello");
        PageType secondType = new PageType("health");
        PageDescription secondDesc = new PageDescription("description");
        Image secondImage = new Image("/images/pho.jpg");
        Page secondPage = new Page(secondTitle, secondType, secondDesc, secondImage);

        assertEquals(firstPage.hashCode(), secondPage.hashCode());
    }

    @Test
    public void testPageToString() {
        Title firstTitle = new Title("hello");
        PageType firstType = new PageType("health");
        PageDescription firstDesc = new PageDescription("description");
        Image firstImage = new Image("/images/pho.jpg");
        Page firstPage = new Page(firstTitle, firstType, firstDesc, firstImage);

        final StringBuilder builder = new StringBuilder();
        builder.append("hello")
                .append(" Page Type: ")
                .append("health")
                .append(" Description: ")
                .append("description")
                .append(" Image: ")
                .append("Image can be found in: /images/pho.jpg and /images/pho.jpg");

        String expected = builder.toString();
        assertEquals(firstPage.toString(), expected);
    }

}

package dukecooks.storage.diary;

import static dukecooks.testutil.diary.TypicalPages.PHO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.Image;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.PageType;
import dukecooks.model.diary.components.Title;
import dukecooks.testutil.Assert;

public class JsonAdaptedPageTest {
    private static final String INVALID_PAGE_TITLE = "@Hello123";
    private static final String INVALID_PAGE_TYPE = "whatever";
    private static final String INVALID_PAGE_DESCRIPTION = "";
    private static final String INVALID_PAGE_IMAGE = "hello.txt";

    private static final String VALID_PAGE_TITLE = PHO.getTitle().toString();
    private static final String VALID_PAGE_TYPE = PHO.getPageType().toString();
    private static final String VALID_PAGE_DESCRIPTION = PHO.getDescription().toString();
    private static final String VALID_PAGE_IMAGE = PHO.getImage().toString();

    @Test
    public void toModelType_validPageDetails_returnsPage() throws Exception {
        JsonAdaptedPage page = new JsonAdaptedPage(PHO);
        assertEquals(PHO, page.toModelType());
    }

    @Test
    public void toModelType_invalidPageTitle_throwsIllegalValueException() {
        JsonAdaptedPage page = new JsonAdaptedPage(INVALID_PAGE_TITLE, VALID_PAGE_TYPE,
                        VALID_PAGE_DESCRIPTION, VALID_PAGE_IMAGE);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, page::toModelType);
    }

    @Test
    public void toModelType_invalidPageType_throwsIllegalValueException() {
        JsonAdaptedPage page = new JsonAdaptedPage(VALID_PAGE_TITLE, INVALID_PAGE_TYPE,
                VALID_PAGE_DESCRIPTION, VALID_PAGE_IMAGE);
        String expectedMessage = PageType.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, page::toModelType);
    }

    @Test
    public void toModelType_invalidPageDescription_throwsIllegalValueException() {
        JsonAdaptedPage page = new JsonAdaptedPage(VALID_PAGE_TITLE, VALID_PAGE_TYPE,
                INVALID_PAGE_DESCRIPTION, VALID_PAGE_IMAGE);
        String expectedMessage = PageDescription.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, page::toModelType);
    }

    @Test
    public void toModelType_invalidPageImage_throwsIllegalValueException() {
        JsonAdaptedPage page = new JsonAdaptedPage(VALID_PAGE_TITLE, VALID_PAGE_TYPE,
                VALID_PAGE_DESCRIPTION, INVALID_PAGE_IMAGE);
        String expectedMessage = Image.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, page::toModelType);
    }
}

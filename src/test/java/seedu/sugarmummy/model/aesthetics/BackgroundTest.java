package seedu.sugarmummy.model.aesthetics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BackgroundTest {

    @Test
    public void isValidBackgroundPicPath() {
        assertTrue(Background.isValidBackgroundPicPath("/Users/bg.png"));
    }

    @Test
    public void isValidBackgroundPicPath_emptyString() {
        assertTrue(Background.isValidBackgroundPicPath(""));
    }

    @Test
    public void isValidBackgroundSize_firstValue() {
        assertTrue(Background.isValidBackgroundSize(BackgroundImageArgs.BACKGROUND_SIZE_VALUES.get(0)));
    }

    @Test
    public void isValidBackgroundSize_secondValue() {
        assertTrue(Background.isValidBackgroundSize(BackgroundImageArgs.BACKGROUND_SIZE_VALUES.get(1)));
    }

    @Test
    public void isValidBackgroundSize_thirdValue() {
        assertTrue(Background.isValidBackgroundSize(BackgroundImageArgs.BACKGROUND_SIZE_VALUES.get(2)));
    }

    @Test
    public void isValidBackgroundSize_emptyStringInvalidValue() {
        assertTrue(Background.isValidBackgroundSize(""));
    }

    @Test
    public void isValidBackgroundSize_firstInvalidValue() {
        assertFalse(Background.isValidBackgroundSize("big"));
    }

    @Test
    public void isValidBackgroundSize_secondInvalidValue() {
        assertFalse(Background.isValidBackgroundSize("200"));
    }

    @Test
    public void isValidBackgroundSize_thirdInvalidValue() {
        assertFalse(Background.isValidBackgroundSize("tiny"));
    }

    @Test
    void isValidBackgroundRepeat_firstValue() {
        assertTrue(Background.isValidBackgroundRepeat(BackgroundImageArgs.BACKGROUND_REPEAT_VALUES.get(0)));
    }

    @Test
    public void isValidBackgroundRepeat_secondValue() {
        assertTrue(Background.isValidBackgroundRepeat(BackgroundImageArgs.BACKGROUND_REPEAT_VALUES.get(1)));
    }

    @Test
    public void isValidBackgroundRepeat_thirdValue() {
        assertTrue(Background.isValidBackgroundRepeat(BackgroundImageArgs.BACKGROUND_REPEAT_VALUES.get(2)));
    }

    @Test
    public void isValidBackgroundRepeat_fourthValue() {
        assertTrue(Background.isValidBackgroundRepeat(BackgroundImageArgs.BACKGROUND_REPEAT_VALUES.get(3)));
    }

    @Test
    public void isValidBackgroundRepeat_fifthValue() {
        assertTrue(Background.isValidBackgroundRepeat(BackgroundImageArgs.BACKGROUND_REPEAT_VALUES.get(4)));
    }

    @Test
    public void isValidBackgroundRepeat_sixthValidValue() {
        assertTrue(Background.isValidBackgroundRepeat(BackgroundImageArgs.BACKGROUND_REPEAT_VALUES.get(5)));
    }

    @Test
    public void isValidBackgroundRepeat_emptyStringInvalidValue() {
        assertTrue(Background.isValidBackgroundRepeat(""));
    }

    @Test
    public void isValidBackgroundRepeat_firstInvalidValue() {
        assertFalse(Background.isValidBackgroundRepeat("no repeat"));
    }

    @Test
    public void isValidBackgroundRepeat_secondInvalidValue() {
        assertFalse(Background.isValidBackgroundRepeat("repeat five times"));
    }

    @Test
    public void isValidBackgroundRepeat_thirdInvalidValue() {
        assertFalse(Background.isValidBackgroundRepeat("twice"));
    }

    @Test
    public void isBackgroundColour() {
        assertTrue((new Background("yellow")).isBackgroundColour());
    }

    @Test
    public void isNotBackgroundColour() {
        assertFalse((new Background("/Users/bg.png")).isBackgroundColour());
    }

    @Test
    public void merge_test() {
        Background firstBackground = new Background("/Users/bg.png");
        firstBackground.setBgSize("cover");
        Background secondBackground = new Background("/Users/bg.png");
        secondBackground.setBgRepeat("no-repeat");

        Background expectedBackground = new Background("/Users/bg.png");
        expectedBackground.setBgSize("cover");
        expectedBackground.setBgRepeat("no-repeat");

        firstBackground.merge(secondBackground);

        assertEquals(firstBackground, expectedBackground);
    }

    @Test
    public void getBgSize_null() {
        Background background = new Background("/Users/bg.png");
        assertNull(background.getBgSize());
    }

    @Test
    public void getBgSize_emptyString() {
        Background background = new Background("/Users/bg.png");
        background.setBgSize("");
        assertEquals("", background.getBgSize());
    }

    @Test
    public void getBgSize_testSize() {
        Background background = new Background("/Users/bg.png");
        background.setBgSize("cover");
        assertEquals("cover", background.getBgSize());
    }

    @Test
    public void setBgSize_emptyString() {
        Background background = new Background("/Users/bg.png");
        background.setBgSize("cover");
        background.setBgSize("");
        assertEquals("", background.getBgSize());
        assertNotEquals("cover", background.getBgSize());
    }

    @Test
    public void setBgSize_testSize() {
        Background background = new Background("/Users/bg.png");
        background.setBgSize("cover");
        assertEquals("cover", background.getBgSize());
        assertNotEquals("", background.getBgSize());
    }


    @Test
    public void getBgRepeat_null() {
        Background background = new Background("/Users/bg.png");
        assertNull(background.getBgRepeat());
    }

    @Test
    public void getBgRepeat_emptyString() {
        Background background = new Background("/Users/bg.png");
        background.setBgRepeat("");
        assertEquals("", background.getBgRepeat());
    }

    @Test
    public void getBgRepeat_testRepeat() {
        Background background = new Background("/Users/bg.png");
        background.setBgRepeat("no-repeat");
        assertEquals("no-repeat", background.getBgRepeat());
    }


    @Test
    public void setBgRepeat_emptyString() {
        Background background = new Background("/Users/bg.png");
        background.setBgRepeat("no-repeat");
        background.setBgRepeat("");
        assertEquals("", background.getBgRepeat());
        assertNotEquals("no-repeat", background.getBgRepeat());
    }

    @Test
    public void setBgRepeat_testRepeat() {
        Background background = new Background("/Users/bg.png");
        background.setBgRepeat("no-repeat");
        assertEquals("no-repeat", background.getBgRepeat());
        assertNotEquals("", background.getBgRepeat());
    }


    @Test
    public void getBgColour_emptyString() {
        Background background = new Background("");
        assertNull(background.getBackgroundColour());
    }

    @Test
    public void getBgColour_testColour() {
        Background background = new Background("yellow");
        assertEquals("yellow", background.getBackgroundColour());
    }


    @Test
    public void getBgPicPath_emptyString() {
        Background background = new Background("");
        assertEquals("", background.getBackgroundPicPath());
    }

    @Test
    public void getBgPicPath_testPicPath() {
        Background background = new Background("/Users/bg.png");
        assertEquals("/Users/bg.png", background.getBackgroundPicPath());
    }


    @Test
    public void setDominantColour_test() {
        Background background = new Background("yellow");
        background.setDominantColour();
        assertEquals(new Colour("yellow"), background.getDominantColour());
    }

    @Test
    public void getDominantColour_test() {
        Background background = new Background("yellow");
        background.setDominantColour();
        assertNotNull(background.getDominantColour());
        assertEquals(new Colour("yellow"), background.getDominantColour());
    }

    @Test
    public void getDominantColour_nullTest() {
        Background background = new Background("yellow");
        assertNotNull(background.getDominantColour());
        assertEquals(new Colour("yellow"), background.getDominantColour());
    }

    @Test
    public void isEmpty_test() {
        Background background = new Background("");
        assertTrue(background.isEmpty());
    }

    @Test
    public void showDefaultBackground_beforeSetting() {
        assertFalse((new Background("")).showDefaultBackground());
    }

    @Test
    public void setShowDefaultBackground() {
        Background background = new Background("");
        background.setShowDefaultBackground(true);
        assertTrue(background.showDefaultBackground());
    }

    @Test
    public void testToString_colour() {
        Background background = new Background("yellow");
        assertEquals("yellow", background.toString());
    }

    @Test
    public void testToString_pic() {
        Background background = new Background("/Users/bg.png");
        assertEquals("/Users/bg.png", background.toString());
    }

    @Test
    public void testEquals_emptyBackground() {
        Background firstBackground = new Background("");
        Background secondBackground = new Background("");
        assertEquals(firstBackground, secondBackground);
    }

    @Test
    public void testEquals_colour() {
        Background firstBackground = new Background("yellow");
        Background secondBackground = new Background("yellow");
        assertEquals(firstBackground, secondBackground);
    }

    @Test
    public void testEquals_pic() {
        Background firstBackground = new Background("/Users/bg.png");
        Background secondBackground = new Background("/Users/bg.png");
        assertEquals(firstBackground, secondBackground);
    }

    @Test
    public void testEquals_colourAndEmptyBackground() {
        Background firstBackground = new Background("yellow");
        Background secondBackground = new Background("");
        assertNotEquals(firstBackground, secondBackground);
    }

    @Test
    public void testEquals_picAndEmptyBackground() {
        Background firstBackground = new Background("/Users/bg.png");
        Background secondBackground = new Background("");
        assertNotEquals(firstBackground, secondBackground);
    }

    @Test
    public void testEquals_picAndColour() {
        Background firstBackground = new Background("/Users/bg.png");
        Background secondBackground = new Background("yellow");
        assertNotEquals(firstBackground, secondBackground);
    }

    @Test
    public void testEquals_differentColours() {
        Background firstBackground = new Background("red");
        Background secondBackground = new Background("yellow");
        assertNotEquals(firstBackground, secondBackground);
    }

    @Test
    public void testEquals_differentFilePaths() {
        Background firstBackground = new Background("/Users/bg.png");
        Background secondBackground = new Background("/Users/bg2.png");
        assertNotEquals(firstBackground, secondBackground);
    }

    @Test
    public void testHashCode_emptyBackground() {
        Background firstBackground = new Background("");
        Background secondBackground = new Background("");
        assertEquals(firstBackground.hashCode(), secondBackground.hashCode());
    }

    @Test
    public void testHashCode_colour() {
        Background firstBackground = new Background("yellow");
        Background secondBackground = new Background("yellow");
        assertEquals(firstBackground.hashCode(), secondBackground.hashCode());
    }

    @Test
    public void testHashCode_pic() {
        Background firstBackground = new Background("/Users/bg.png");
        Background secondBackground = new Background("/Users/bg.png");
        assertEquals(firstBackground.hashCode(), secondBackground.hashCode());
    }

    @Test
    public void testHashCode_colourAndEmptyBackground() {
        Background firstBackground = new Background("yellow");
        Background secondBackground = new Background("");
        assertNotEquals(firstBackground.hashCode(), secondBackground.hashCode());
    }

    @Test
    public void testHashCode_picAndEmptyBackground() {
        Background firstBackground = new Background("/Users/bg.png");
        Background secondBackground = new Background("");
        assertNotEquals(firstBackground.hashCode(), secondBackground.hashCode());
    }

    @Test
    public void testHashCode_picAndColour() {
        Background firstBackground = new Background("/Users/bg.png");
        Background secondBackground = new Background("yellow");
        assertNotEquals(firstBackground.hashCode(), secondBackground.hashCode());
    }

    @Test
    public void testHashCode_differentColours() {
        Background firstBackground = new Background("red");
        Background secondBackground = new Background("yellow");
        assertNotEquals(firstBackground.hashCode(), secondBackground.hashCode());
    }

    @Test
    public void testHashCode_differentFilePaths() {
        Background firstBackground = new Background("/Users/bg.png");
        Background secondBackground = new Background("/Users/bg2.png");
        assertNotEquals(firstBackground.hashCode(), secondBackground.hashCode());
    }

}

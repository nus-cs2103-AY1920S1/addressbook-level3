package dukecooks.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ImageTest {

    @Test
    void isValidImage() {

        // valid images from resources
        assertTrue(Image.isValidImage("/images/pho.jpg"));
        assertTrue(Image.isValidImage("/images/sushi.jpg"));

        // Invalid images
        assertFalse(Image.isValidImage("hello.txt")); // doesn't end with .jpg or .png
        assertFalse(Image.isValidImage(" dadada")); // invalid file name
    }

    @Test
    void generateTime() {
        Time toCompare = new Time(1, 1);
        assertTrue(toCompare.equals(Time.generateTime("01:01")));
        assertFalse(toCompare.equals(Time.generateTime("10:10")));
    }

    @Test
    void testEquals() {
        Image firstImage = new Image("/images/pho.jpg");
        Image secondImage = new Image("/images/pho.jpg");
        Image thirdImage = new Image("/images/sushi.jpg");

        // same objects
        assertTrue(firstImage.equals(secondImage));

        // different objects, same fields
        assertTrue(firstImage.equals(secondImage));

        // different objects and fields
        assertFalse(firstImage.equals(null));
        assertFalse(firstImage.equals(thirdImage));
    }

    @Test
    void testToString() {
        Image image = new Image("/images/pho.jpg");
        assertEquals("Image can be found in: /images/pho.jpg and /images/pho.jpg", image.toString());
    }
}

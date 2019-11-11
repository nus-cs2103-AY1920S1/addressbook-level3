package seedu.module.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LinkTest {

    @Test
    public void constructor_null_throwsNullException() {
        assertThrows(NullPointerException.class, () -> new Link(null, "http://example.com"));
        assertThrows(NullPointerException.class, () -> new Link("test", null));
    }
    @Test
    public void isMarked() {
        Link defaultTestLink = new Link ("test", "http://example.com");
        Link markedLink = new Link ("marked", "http://example.com", true);
        Link unmarkedLink = new Link ("unmarked", "http://example.com", false);
        //default value
        assertFalse(defaultTestLink.isMarked());
        //marked link
        assertTrue(markedLink.isMarked());
        //unmarked
        assertFalse(unmarkedLink.isMarked());
    }

    @Test
    public void setMarked() {
        Link markedLink = new Link ("marked", "http://example.com", true);
        Link unmarkedLink = new Link ("unmarked", "http://example.com", false);
        //marking already marked
        assertEquals(markedLink.setMarked(), false);
        //marking link
        assertEquals(unmarkedLink.setMarked(), true);
        //outcome
        assertTrue(unmarkedLink.isMarked());
    }

    @Test
    public void setUnmarked() {
        Link markedLink = new Link ("marked", "http://example.com", true);
        Link unmarkedLink = new Link ("unmarked", "http://example.com", false);
        //unmarking already unmarked
        assertEquals(unmarkedLink.setUnmarked(), false);
        //marking link
        assertEquals(markedLink.setUnmarked(), true);
        //outcome
        assertFalse(markedLink.isMarked());
    }

    @Test
    public void equals() {
        Link comparedLink = new Link ("test", "http://example.com");
        //same thing
        assertTrue(comparedLink.equals(comparedLink));
        //same name same url
        assertTrue(comparedLink.equals(new Link ("test", "http://example.com")));
        //same name different url
        assertFalse(comparedLink.equals(new Link("test", "http://google.com")));
        //different name same url
        assertFalse(comparedLink.equals(new Link("ntest", "http://example.com")));
    }
}

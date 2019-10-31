package cs.f10.t1.nursetraverse.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

class UserinputParserUtilTest {

    @Test
    void splitIntoSegments() {
        String testString = "pat-delete 1";
        String[] segments = UserinputParserUtil.splitIntoSegments(testString);
        assertEquals("pat-delete", segments[0]);
        assertEquals("1", segments[1]);
    }

    @Test
    void splitIntoSegments_emptyString_emptyArray() {
        String testString = "";
        String[] segments = UserinputParserUtil.splitIntoSegments(testString);
        assertEquals(1, segments.length);
        assertEquals("", segments[0]);
    }

    @Test
    void parseFirstSegment() {
        String testFirstSegment = "pat-delete";
        LinkedList<String> segments = UserinputParserUtil.parseFirstSegment(testFirstSegment);
        assertEquals("pat", segments.get(0));
        assertEquals("delete", segments.get(1));
    }

    @Test
    void parseFirstSegment_emptyString_listSizeOne() {
        String testFirstSegment = "";
        LinkedList<String> segments = UserinputParserUtil.parseFirstSegment(testFirstSegment);
        assertEquals(1, segments.size());
        assertEquals("", segments.get(0));
    }
}

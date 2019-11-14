package cs.f10.t1.nursetraverse.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

class UserinputParserUtilTest {

    @Test
    public void parse() {
        String testString = "pat-delete 1";
        LinkedList<String> parsedWords = UserinputParserUtil.parse(testString);
        assertEquals("pat-", parsedWords.get(0));
        assertEquals("delete", parsedWords.get(1));
        assertEquals("1", parsedWords.get(2));
    }

    @Test
    public void splitIntoSegments() {
        String testString = "pat-delete 1";
        String[] segments = UserinputParserUtil.splitIntoSegments(testString);
        assertEquals("pat-delete", segments[0]);
        assertEquals("1", segments[1]);
    }

    @Test
    public void splitIntoSegments_emptyString_emptyArray() {
        String testString = "";
        String[] segments = UserinputParserUtil.splitIntoSegments(testString);
        assertEquals(1, segments.length);
        assertEquals("", segments[0]);
    }

    @Test
    public void parseFirstSegment() {
        String testFirstSegment = "pat-delete";
        LinkedList<String> segments = UserinputParserUtil.parseFirstSegment(testFirstSegment);
        assertEquals("pat-", segments.get(0));
        assertEquals("delete", segments.get(1));
    }

    @Test
    public void parseFirstSegment_emptyString_listSizeOne() {
        String testFirstSegment = "";
        LinkedList<String> segments = UserinputParserUtil.parseFirstSegment(testFirstSegment);
        assertEquals(1, segments.size());
        assertEquals("", segments.get(0));
    }
}

package tagline.ui.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutoCompleteNodeTest {
    private static final AutoCompleteNode NODE_ALICE = new AutoCompleteNode("Alice");
    private static final AutoCompleteNode NODE_BOB = new AutoCompleteNode("Bob");
    private static final AutoCompleteNode NODE_ZACH = new AutoCompleteNode("Zach");

    private AutoCompleteNode rootNodeWithNoChildren;
    private AutoCompleteNode rootNodeWithChildren;
    private AutoCompleteNode nodeWithNoChildren;

    @BeforeEach
    private void setUp() {
        nodeWithNoChildren = new AutoCompleteNode("Charlie");

        rootNodeWithNoChildren = AutoCompleteNode.getRootNode();

        AutoCompleteNode nodeWithChildren = new AutoCompleteNode("Charlie");
        nodeWithChildren.addChildren(NODE_ALICE, NODE_BOB);

        AutoCompleteNode secondNodeWithChildren = new AutoCompleteNode("Delta");
        secondNodeWithChildren.addChildren(NODE_ZACH);

        rootNodeWithChildren = AutoCompleteNode.getRootNode();
        rootNodeWithChildren.addChildren(nodeWithChildren, secondNodeWithChildren);
    }

    @Test
    void addChildren_nullArgument_exceptionThrown() {
        assertThrows(Exception.class, () -> rootNodeWithNoChildren.addChildren(null, NODE_ALICE));
        assertThrows(Exception.class, () -> nodeWithNoChildren.addChildren(null, NODE_ALICE));
    }

    @Test
    void isMatch() {
        assertThrows(Exception.class, () -> rootNodeWithNoChildren.isMatch(null));
        assertThrows(Exception.class, () -> nodeWithNoChildren.isMatch(null));

        assertFalse(rootNodeWithNoChildren.isMatch(""));
        assertFalse(rootNodeWithNoChildren.isMatch("C"));

        assertTrue(nodeWithNoChildren.isMatch(""));
        assertTrue(nodeWithNoChildren.isMatch("C"));
        assertFalse(nodeWithNoChildren.isMatch("Charles"));
        assertFalse(nodeWithNoChildren.isMatch("Charlie")); //does not match full string
    }

    @Test
    void isTrimmable_nullArgument_exceptionThrown() {
        assertThrows(Exception.class, () -> rootNodeWithNoChildren.isTrimmable(null));
        assertThrows(Exception.class, () -> nodeWithNoChildren.isTrimmable(null));
    }

    @Test
    void isTrimmable_successful() {
        assertTrue(rootNodeWithNoChildren.isTrimmable(""));
        assertTrue(rootNodeWithNoChildren.isTrimmable("Charlie"));

        assertTrue(nodeWithNoChildren.isTrimmable("Charlie Bob"));
        assertFalse(nodeWithNoChildren.isTrimmable("Charlie"));
        assertFalse(nodeWithNoChildren.isTrimmable("Charles Bob"));
        assertFalse(nodeWithNoChildren.isTrimmable("Alice Charlie "));
    }

    @Test
    void trimMatcher_nullArgument_exceptionThrown() {
        assertThrows(Exception.class, () -> rootNodeWithNoChildren.trimMatcher(null));
        assertThrows(Exception.class, () -> nodeWithNoChildren.trimMatcher(null));
    }

    @Test
    void trimMatcher_successful() {
        assertEquals("", rootNodeWithNoChildren.trimMatcher(""));
        assertEquals("Charlie", rootNodeWithNoChildren.trimMatcher("Charlie"));

        assertEquals("Bob", nodeWithNoChildren.trimMatcher("Charlie Bob"));
        assertEquals("Bob  Eric", nodeWithNoChildren.trimMatcher("Charlie         Bob  Eric"));
    }

    @Test
    void trimMatcher_invalidTrim_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> nodeWithNoChildren.trimMatcher("Charlie"));
        assertThrows(IllegalArgumentException.class, () -> nodeWithNoChildren.trimMatcher("Alice Charlie"));
    }

    @Test
    void prependMatcher_nullArgument_exceptionThrown() {
        assertThrows(Exception.class, () -> rootNodeWithNoChildren.trimMatcher(null));
        assertThrows(Exception.class, () -> nodeWithNoChildren.trimMatcher(null));
    }

    @Test
    void prependMatcher_successful() {
        assertEquals("Bob", rootNodeWithNoChildren.prependMatcher("Bob"));
        assertEquals("Charlie Bob", nodeWithNoChildren.prependMatcher("Bob"));
    }

    @Test
    void findMatches_nullArgument_exceptionThrown() {
        assertThrows(Exception.class, () -> rootNodeWithNoChildren.findMatches(null));
        assertThrows(Exception.class, () -> nodeWithNoChildren.findMatches(null));
    }

    @Test
    void findMatches_successful() {
        assertEquals(Arrays.asList("Charlie", "Delta"), rootNodeWithChildren.findMatches(""));
        assertEquals(Arrays.asList("Charlie"), rootNodeWithChildren.findMatches("Char"));
        assertEquals(new ArrayList<>(), rootNodeWithChildren.findMatches("Charlie"));
        assertEquals(Arrays.asList("Charlie Alice", "Charlie Bob"),
                rootNodeWithChildren.findMatches("Charlie "));
        assertEquals(Arrays.asList("Charlie Alice"),
                rootNodeWithChildren.findMatches("Charlie Alic"));
        assertEquals(new ArrayList<>(),
                rootNodeWithChildren.findMatches("Charlie Alice"));
        assertEquals(new ArrayList<>(),
                rootNodeWithChildren.findMatches("CharlieAlice"));
    }
}

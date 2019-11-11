package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AndNodeTest {
    private static final String EXPECTED_REPRESENTATION = AndNode.STRING_REPRESENTATION;

    private static final String[] EXAMPLE_CHILDREN = {
        "aaa",
        "bbb",
        "ccc",
        "ddd"
    };

    private static final List<String> COLLECTION_PASSES_REQUIREMENTS = List.of(
        "aaa",
        "bbb",
        "ccc",
        "ddd"
    );

    private static final List<String> COLLECTION_FAILS_REQUIREMENTS_PARTIAL_MATCH = List.of(
        "aaa",
        "bbb",
        "ccc"
    );

    private static final List<String> COLLECTION_FAILS_REQUIREMENTS_NONE_MATCH = List.of(
        "yyy",
        "zzz"
    );

    private final AndNode<String> andNode = new AndNode<>();

    @BeforeEach
    public void initNode() {
        for (String s : EXAMPLE_CHILDREN) {
            andNode.insert(s);
        }
    }

    @Test
    public void fulfills_allMatch_returnsTrue() {
        assertTrue(andNode.fulfills(COLLECTION_PASSES_REQUIREMENTS));
    }

    @Test
    public void fulfills_partialMatch_returnsFalse() {
        assertFalse(andNode.fulfills(COLLECTION_FAILS_REQUIREMENTS_PARTIAL_MATCH));
    }

    @Test
    public void fulfills_noneMatch_returnsFalse() {
        assertFalse(andNode.fulfills(COLLECTION_FAILS_REQUIREMENTS_NONE_MATCH));
    }

    @Test
    public void type_returnsCorrectType() {
        assertEquals(andNode.type(), AndOrOperation.AND);
    }

    @Test
    public void toString_returnsRepresentation() {
        assertEquals(andNode.toString(), EXPECTED_REPRESENTATION);
    }
}

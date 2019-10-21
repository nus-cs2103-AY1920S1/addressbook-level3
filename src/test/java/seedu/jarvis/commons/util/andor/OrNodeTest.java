package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrNodeTest {
    private static final String EXPECTED_REPRESENTATION = OrNode.STRING_REPRESENTATION;

    private static final String[] EXAMPLE_CHILDREN = {
        "aaa",
        "bbb",
        "ccc",
        "ddd"
    };

    private static final List<String> COLLECTION_PASSES_REQUIREMENTS_SINGLE = List.of(
        "aaa"
    );

    private static final List<String> COLLECTION_PASSES_REQUIREMENTS_MULTIPLE = List.of(
        "aaa",
        "ccc",
        "ddd"
    );

    private static final List<String> COLLECTION_FAILS_REQUIREMENTS = List.of(
        "zzz",
        "yyy"
    );

    private final OrNode<String> orNode = new OrNode<>();

    @BeforeEach
    public void initNode() {
        for (String s : EXAMPLE_CHILDREN) {
            orNode.insert(s);
        }
    }

    @Test
    public void fulfills_oneMatch_returnsTrue() {
        assertTrue(orNode.fulfills(COLLECTION_PASSES_REQUIREMENTS_SINGLE));
    }

    @Test
    public void fulfills_multipleMatch_returnsTrue() {
        assertTrue(orNode.fulfills(COLLECTION_PASSES_REQUIREMENTS_MULTIPLE));
    }

    @Test
    public void fulfills_noneMatch_returnsFalse() {
        assertFalse(orNode.fulfills(COLLECTION_FAILS_REQUIREMENTS));
    }

    @Test
    public void type_returnsCorrectType() {
        assertEquals(orNode.type(), AndOrOperation.OR);
    }

    @Test
    public void toString_returnsRepresentation() {
        assertEquals(orNode.toString(), EXPECTED_REPRESENTATION);
    }
}

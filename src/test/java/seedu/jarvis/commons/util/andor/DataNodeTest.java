package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class DataNodeTest {
    private static final String EXAMPLE_DATA = "aaa";

    private static final List<String> COLLECTION_PASSES_REQUIREMENTS = List.of(
        "aaa"
    );

    private static final List<String> COLLECTION_PASSES_REQUIREMENTS_MANY = List.of(
        "aaa",
        "bbb",
        "ccc",
        "ddd"
    );

    private static final List<String> COLLECTION_FAILS_REQUIREMENTS = List.of(
        "yyy",
        "zzz"
    );

    private final DataNode<String> dataNode = new DataNode<>(EXAMPLE_DATA);

    @Test
    public void getData_returnsOptionalOfData() {
        assertEquals(dataNode.getData(), Optional.of(EXAMPLE_DATA));
    }

    @Test
    public void fulfills_oneMatch_returnsTrue() {
        assertTrue(dataNode.fulfills(COLLECTION_PASSES_REQUIREMENTS));
    }

    @Test
    public void fulfills_oneMatchManyCollection_returnsTrue() {
        assertTrue(dataNode.fulfills(COLLECTION_PASSES_REQUIREMENTS_MANY));
    }

    @Test
    public void fulfills_noneMatch_returnsFalse() {
        assertFalse(dataNode.fulfills(COLLECTION_FAILS_REQUIREMENTS));
    }

    @Test
    public void type_returnsCorrectType() {
        assertEquals(dataNode.type(), AndOrOperation.DATA);
    }

    @Test
    public void toString_returnsRepresentation() {
        assertEquals(dataNode.toString(), dataNode.getData().get().toString());
    }
}

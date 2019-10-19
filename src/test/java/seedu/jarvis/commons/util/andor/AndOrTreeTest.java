package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

class AndOrTreeTest {
    private static final String EXAMPLE_JSON_STRING =
        "{\"and\":[{\"or\":[\"CS2010\",\"CS2020\",\"CS2040\"]},{\"or\":[\"ESP1107\",\"ESP2107\","
        + "\"ST1232\",\"ST2131\",\"ST2132\",\"ST2334\"]},{\"or\":[\"MA1102R\",\"MA1505\",{\"and\":["
        + "\"MA1511\",\"MA1512\"]},\"MA1521\"]},{\"or\":[\"MA1101R\",\"MA1311\",\"MA1506\","
        + "\"MA1508E\"]}]}";

    private static final String EXAMPLE_JSON_STRING_ONE_DATA_ONLY = "CS1010";

    private static final String EMPTY_STRING = "";

    private static final String EXAMPLE_ROOT_DATA = "CS3244";

    private static final Function<? super String, ? extends String> STRING_FUNCTION = (t) -> t;

    private static final List<String> COLLECTION_PASSES_REQUIREMENTS_OF_EXAMPLE = List.of(
        "CS2040",
        "ST2334",
        "MA1511",
        "MA1512",
        "MA1101R"
    );

    private static final List<String> COLLECTION_BARELY_FAILS_REQUIREMENTS_OF_EXAMPLE = List.of(
        "CS2040",
        "ST2334",
        "MA1101R"
    );

    private static final List<String> COLLECTION_FAILS_REQUIREMENTS_OF_EXAMPLE = List.of(
        "ST2334",
        "MA1511",
        "MA1512",
        "MA1101R"
    );

    private static final List<String> COLLECTION_PASSES_REQUIREMENTS_OF_ONE_DATA = List.of(
        "CS1010",
        "CS2040",
        "CS2030"
    );

    private static final List<String> COLLECTION_FAILS_REQUIREMENTS_OF_ONE_DATA = List.of(
        "MA1511",
        "GET1011"
    );

    @Test
    void fulfills_exampleJsonPasses_returnsTrue() {
        AndOrTree<String> tree = AndOrTree.buildTree(
            EXAMPLE_ROOT_DATA, EXAMPLE_JSON_STRING, STRING_FUNCTION);
        assertTrue(tree.fulfills(COLLECTION_PASSES_REQUIREMENTS_OF_EXAMPLE));
    }

    @Test
    void fulfills_exampleJsonBarelyFails_returnsFalse() {
        AndOrTree<String> tree = AndOrTree.buildTree(
            EXAMPLE_ROOT_DATA, EXAMPLE_JSON_STRING, STRING_FUNCTION);
        assertFalse(tree.fulfills(COLLECTION_BARELY_FAILS_REQUIREMENTS_OF_EXAMPLE));
    }

    @Test
    void fulfills_exampleJsonFails_returnsFalse() {
        AndOrTree<String> tree = AndOrTree.buildTree(
            EXAMPLE_ROOT_DATA, EXAMPLE_JSON_STRING, STRING_FUNCTION);
        assertFalse(tree.fulfills(COLLECTION_FAILS_REQUIREMENTS_OF_EXAMPLE));
    }

    @Test
    void fulfills_oneDataJsonPasses_returnsTrue() {
        AndOrTree<String> tree = AndOrTree.buildTree(
            EXAMPLE_ROOT_DATA, EXAMPLE_JSON_STRING_ONE_DATA_ONLY, STRING_FUNCTION);
        assertTrue(tree.fulfills(COLLECTION_PASSES_REQUIREMENTS_OF_ONE_DATA));
    }

    @Test
    void fulfills_oneDataJsonFails_returnsFalse() {
        AndOrTree<String> tree = AndOrTree.buildTree(
            EXAMPLE_ROOT_DATA, EXAMPLE_JSON_STRING_ONE_DATA_ONLY, STRING_FUNCTION);
        assertFalse(tree.fulfills(COLLECTION_FAILS_REQUIREMENTS_OF_ONE_DATA));
    }

    @Test
    void fulfills_emptyJson_alwaysReturnsTrue() {
        AndOrTree<String> tree = AndOrTree.buildTree(
            EXAMPLE_ROOT_DATA, EMPTY_STRING, STRING_FUNCTION);
        assertTrue(tree.fulfills(List.of()));
        assertTrue(tree.fulfills(COLLECTION_PASSES_REQUIREMENTS_OF_ONE_DATA));
        assertTrue(tree.fulfills(COLLECTION_FAILS_REQUIREMENTS_OF_EXAMPLE));
    }
}

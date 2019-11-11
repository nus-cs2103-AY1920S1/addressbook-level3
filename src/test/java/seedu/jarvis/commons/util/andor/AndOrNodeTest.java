package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.util.andor.AndOrOperationMapper.KEYWORD_AND_NODE;
import static seedu.jarvis.commons.util.andor.AndOrOperationMapper.KEYWORD_OR_NODE;

import org.junit.jupiter.api.Test;

public class AndOrNodeTest {
    private static final String DUMMY_DATA = "";

    @Test
    public void createNode_andOrType_returnsCorrectNode() {
        assertTrue(AndOrNode.createNode(DUMMY_DATA, KEYWORD_AND_NODE) instanceof AndNode);
        assertTrue(AndOrNode.createNode(DUMMY_DATA, KEYWORD_OR_NODE) instanceof OrNode);
        assertTrue(AndOrNode.createNode(DUMMY_DATA, "") instanceof DataNode);
        assertTrue(AndOrNode.createNode(DUMMY_DATA, "other data") instanceof DataNode);
        assertTrue(AndOrNode.createNode(DUMMY_DATA, "no real type") instanceof DataNode);
    }
}

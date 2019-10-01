package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.util.andor.AndOrStubs.CourseStub;

import org.junit.jupiter.api.Test;

public class AndOrNodeTest {
    private static final String[] CREATE_NODE_INVALID_INPUTS = {
        "asdfsadf",
        "",
        "random string",
        "null"
    };

    @Test
    public void createAndOrNode_validInputs_returnsCorrectNode() {
        assertTrue(AndOrNode.createAndOrNode(null, "and") instanceof AndNode);
        assertTrue(AndOrNode.createAndOrNode(null, "or") instanceof OrNode);
        for (String invalid : CREATE_NODE_INVALID_INPUTS) {
            assertTrue(AndOrNode.createAndOrNode(null, invalid) == null);
        }
    }

    @Test
    public void createLeafNode_validInputs_returnsNotNull() {
        assertTrue(AndOrNode.createLeafNode(null, null) instanceof LeafNode);
        for (String any : CREATE_NODE_INVALID_INPUTS) {
            assertTrue(AndOrNode.createLeafNode(new CourseStub(any), null) instanceof LeafNode);
        }
    }
}

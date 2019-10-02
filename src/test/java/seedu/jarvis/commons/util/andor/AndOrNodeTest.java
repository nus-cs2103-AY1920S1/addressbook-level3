package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.util.andor.AndOrStubsUtil.CourseStub;

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

    @Test
    public void insert_validNode_success() {
        AndOrNode node = AndOrNode.createAndOrNode(null, "and");
        AndOrNode anotherNode = AndOrNode.createLeafNode(new CourseStub(""), null);
        node.insert(anotherNode);
        assertTrue(node.getChildren().contains(anotherNode));
    }

    @Test
    public void toTreeString_validTree_returnsCorrectString() {
        AndOrNode node = AndOrNode.createAndOrNode(null, "and");
        AndOrNode child1 = AndOrNode.createAndOrNode(null, "or");
        child1.insert(AndOrNode.createLeafNode(new CourseStub("t1"), null));
        child1.insert(AndOrNode.createLeafNode(new CourseStub("t2"), null));
        node.insert(child1);
        node.insert(AndOrNode.createLeafNode(new CourseStub("t3"), null));
        node.insert(AndOrNode.createLeafNode(new CourseStub("t4"), null));
        node.insert(AndOrNode.createLeafNode(new CourseStub("t5"), null));

        String correctString = "all of\n├── one of\n│   "
                + "├── t1\n│   └── t2\n├── t3\n├── t4\n└── t5\n";
        assertEquals(correctString, node.toTreeString());
    }
}

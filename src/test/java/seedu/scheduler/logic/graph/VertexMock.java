package seedu.scheduler.logic.graph;

/**
 * A mock used to unit test Vertex abstract class
 */
public class VertexMock extends Vertex<Integer, VertexMock> {
    public VertexMock(Integer item, int index) {
        super(item, index);
    }
}

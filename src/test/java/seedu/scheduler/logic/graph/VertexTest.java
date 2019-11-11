package seedu.scheduler.logic.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class VertexTest {
    // ========================================== Unit Testing (using mock) ===========================================
    @Test
    public void isMatchedTo_matchedVertex_returnsTrue() {
        Vertex subjectVertex = new VertexMock(1, 15);
        Vertex partner = new VertexMock(2, 75);

        subjectVertex.match(partner);
        assertTrue(subjectVertex.isMatchedTo(partner));
    }

    @Test
    public void isMatchedTo_notMatchedVertex_returnsFalse() {
        Vertex subjectVertex = new VertexMock(3, 20);
        Vertex vertex = new VertexMock(5, 97);

        assertFalse(subjectVertex.isMatchedTo(vertex));
    }

    @Test
    public void equals_sameVertex_returnsTrue() {
        Vertex subjectVertex = new VertexMock(5, 20);

        assertEquals(subjectVertex, subjectVertex);
    }

    @Test
    public void equals_equalVertex_returnsTrue() {
        Vertex subjectVertex = new VertexMock(5, 20);
        Vertex vertex = new VertexMock(5, 20);

        assertEquals(subjectVertex, vertex);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Vertex subjectVertex = new VertexMock(5, 20);

        assertNotEquals(subjectVertex, "1");
    }

    @Test
    public void equals_vertexDifferentItem_returnsFalse() {
        Vertex subjectVertex = new VertexMock(5, 20);
        Vertex vertex = new VertexMock(7, 20);

        assertNotEquals(subjectVertex, vertex);
    }

    @Test
    public void equals_vertexDifferentIndex_returnsFalse() {
        Vertex subjectVertex = new VertexMock(5, 17);
        Vertex partner = new VertexMock(5, 29);

        assertNotEquals(subjectVertex, partner);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void equals_vertexDifferentPartner_returnsFalse() {
        Vertex subjectVertex = new VertexMock(9, 28);
        Vertex partner1 = new VertexMock(8, 32);

        Vertex vertex = new VertexMock(9, 28);
        Vertex partner2 = new VertexMock(7, 97);

        subjectVertex.match(partner1);
        vertex.match(partner2);
        assertNotEquals(subjectVertex, vertex);
    }
}

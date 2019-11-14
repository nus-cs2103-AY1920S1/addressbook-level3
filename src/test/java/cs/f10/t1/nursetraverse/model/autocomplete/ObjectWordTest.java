package cs.f10.t1.nursetraverse.model.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class ObjectWordTest {
    private ObjectWord testObjectWord = new ObjectWord("test1", "test description 1");

    @Test
    public void getSuggestedWord() {
        assertEquals("test1", testObjectWord.getSuggestedWord());
    }

    @Test
    public void getConnectorChar() {
        assertEquals("", testObjectWord.getConnectorChar());
    }

    @Test
    public void getDescription() {
        assertEquals("test description 1", testObjectWord.getDescription());
    }
}

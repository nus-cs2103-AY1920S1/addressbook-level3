package cs.f10.t1.nursetraverse.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.autocomplete.AssociableWord;
import cs.f10.t1.nursetraverse.model.autocomplete.CommandWord;

class AssociableWordTest {
    private AssociableWord testAssociableWord =
            new CommandWord("abc", "hello",
                    "this commmand word is for testing", false, false);

    @Test
    public void getAssociatedWordList() {
        LinkedList<String> associableWordLinkedList = testAssociableWord.getAssociatedWordList();
        assertEquals(1, associableWordLinkedList.size());
        assertEquals("abc", associableWordLinkedList.get(0));
    }
}

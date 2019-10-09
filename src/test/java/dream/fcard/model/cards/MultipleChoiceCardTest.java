package dream.fcard.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class MultipleChoiceCardTest {

    @Test
    void evaluate_correctChoice_trueReturned() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("hello");
        choices.add("bye");

        MultipleChoiceCard card = new MultipleChoiceCard("What is hello?", "hello", choices);
        assertEquals(true, card.evaluate("hello"));
    }

    @Test
    void evaluate_wrongChoice_falseReturned() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("hello");
        choices.add("bye");

        MultipleChoiceCard card = new MultipleChoiceCard("What is hello?", "hello", choices);
        assertEquals(false, card.evaluate("helo"));
    }
}

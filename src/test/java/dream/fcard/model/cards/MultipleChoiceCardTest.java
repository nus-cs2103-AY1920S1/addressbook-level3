package dream.fcard.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dream.fcard.model.exceptions.ChoiceNotFoundException;

//import dream.fcard.model.exceptions.ChoiceNotFoundException;

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

    @Test
    void editChoice_changeChoiceSuccessfully() throws ChoiceNotFoundException {
        ArrayList<String> choices = new ArrayList<>();

        choices.add("bye");
        choices.add("yoyo");
        choices.add("yolo");
        choices.add("moin");

        MultipleChoiceCard card = new MultipleChoiceCard("What is hello?", "hello", choices);
        card.editChoice(3, "hello");

        String answer = card.getChoice(3);
        assertEquals(true, card.evaluate(answer));
    }

    @Test
    void editChoice_indexInvalid_exceptionThrown() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("bye");
        choices.add("yoyo");
        choices.add("yolo");
        choices.add("moin");

        MultipleChoiceCard card = new MultipleChoiceCard("What is hello?", "hello", choices);
        assertThrows(ChoiceNotFoundException.class, () -> {
            card.editChoice(-1, "hello");
        });
    }
}

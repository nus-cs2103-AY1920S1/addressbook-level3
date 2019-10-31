package dream.fcard.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.JavascriptCard;

class DeepCopyTest {

    @Test
    void duplicateCards() {
        Deck d = new Deck();
        JavascriptCard js = new JavascriptCard("front", "back");
        d.addNewCard(js);
        Deck duplicate = d.duplicateMyself();
        JavascriptCard dupJsCard = (JavascriptCard) duplicate.getCards().get(0);
        dupJsCard.editFront("duplicate");
        assertEquals(js.getFront(), "front");
    }
}

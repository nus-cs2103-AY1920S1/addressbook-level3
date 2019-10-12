package dream.fcard.logic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.util.FileReadWrite;

public class StorageManagerTest {
    @Test
    void readWriteDecksTest() {
        FlashCard d1c1 = new FrontBackCard("What year did NASA go to the moon?", "1969");
        FlashCard d2c1 = new MultipleChoiceCard(
                "A baby puffin is called a what?", "1",
                new ArrayList<>(Arrays.asList(
                        "Puffin",
                        "Puffy",
                        "Pofette",
                        "Poofeen"
                        )));

        Deck d1 = new Deck("deck1", new ArrayList<>(Arrays.asList(d1c1)));
        Deck d2 = new Deck("deck2", new ArrayList<>(Arrays.asList(d2c1)));
        // create stubs

        StorageManager.provideRoot("~");
        StorageManager.writeDeck(d1);
        StorageManager.writeDeck(d2);
        // write decks

        for (Deck d : StorageManager.loadDecks()) {
            if (d.getName() == "deck1") {
                assertEquals(d1.toJson().toString(), d.toJson().toString());
            } else if (d.getName() == "deck2") {
                assertEquals(d2.toJson().toString(), d.toJson().toString());
            }
        }
        // load decks and check

        FileReadWrite.delete("~/decks/deck1.json");
        FileReadWrite.delete("~/decks/deck2.json");
        FileReadWrite.delete("~/decks");
        // delete files
    }
}

package dream.fcard.logic.respond;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.util.FileReadWrite;

public class ResponsesTest {
    @Test
    void importTest() {
        String deckName = "test123";
        String path = FileReadWrite.normalizePath("~/" + deckName + ".json");
        String root = FileReadWrite.normalizePath("~/testDir");
        // test parameters

        StorageManager.provideRoot(root);

        State s = new State();
        Deck d = new Deck(deckName);
        d.addNewCard(new FrontBackCard("front", "back"));
        // create stubs

        FileReadWrite.write(path, d.toJson().toString());
        // create file

        Responses.IMPORT.call("import " + path, s);
        // test import

        try {
            assertEquals(deckName, s.getDeck(deckName).getName());
            assertEquals(d.toJson().toString(), s.getDeck(deckName).toJson().toString());
        } catch(DeckNotFoundException e) {
            fail();
        }
        // check deck valid

        FileReadWrite.delete(path);
        FileReadWrite.delete(FileReadWrite.resolve(root, "./decks/" + deckName + ".json"));
        FileReadWrite.delete(FileReadWrite.resolve(root, "./decks"));
        FileReadWrite.delete(root);
        // cleanup
    }
}

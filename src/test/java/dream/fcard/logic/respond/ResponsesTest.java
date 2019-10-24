package dream.fcard.logic.respond;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.util.FileReadWrite;

public class ResponsesTest {
    @Test
    void rootTest() {
        String path = FileReadWrite.resolve("./", "./testDir");
        new File(path).mkdirs();
        // make directory for testing

        Responses.ROOT.call("root " + path, new State());
        assertEquals(path, StorageManager.getRoot());
        // test

        FileReadWrite.delete(path);
        // cleanup
    }

    @Test
    void rootNoPathTest() {
        assertEquals(true, Responses.ROOT_NO_PATH.call("root", new State()));
    }

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
        } catch (DeckNotFoundException e) {
            fail();
        }
        // check deck valid

        FileReadWrite.delete(path);
        FileReadWrite.delete(FileReadWrite.resolve(root, "./decks/" + deckName + ".json"));
        FileReadWrite.delete(FileReadWrite.resolve(root, "./decks"));
        FileReadWrite.delete(root);
        // cleanup
    }

    @Test
    void importNoPathTest() {
        assertEquals(true, Responses.IMPORT_NO_PATH.call("import", new State()));
    }

    @Test
    void exportTest() {
        String deckName = "test123";
        String path = FileReadWrite.normalizePath("~");
        String exportPath = FileReadWrite.resolve(path, "./" + deckName + ".json");
        String root = FileReadWrite.normalizePath("~/testDir");
        // test parameters

        StorageManager.provideRoot(root);

        State s = new State();
        Deck d = new Deck(deckName);
        d.addNewCard(new FrontBackCard("front", "back"));
        s.addDeck(d);
        // create stubs

        StorageManager.writeDeck(d);
        // store in root

        Responses.EXPORT.call("export deck/ " + deckName + " path/ " + path, s);
        // test export

        try {
            assertEquals(d.toJson().toString(), FileReadWrite.read(exportPath));
        } catch (FileNotFoundException e) {
            fail();
        }
        // check export valid

        FileReadWrite.delete(exportPath);
        FileReadWrite.delete(FileReadWrite.resolve(root, "./decks/" + deckName + ".json"));
        FileReadWrite.delete(FileReadWrite.resolve(root, "./decks"));
        FileReadWrite.delete(root);
        // cleanup
    }

    @Test
    void exportNoPathTest() {
        assertEquals(true, Responses.EXPORT_NO_PATH.call("export deck/ test", new State()));
    }

    @Test
    void exportNoDeckTest() {
        assertEquals(true, Responses.EXPORT_NO_DECK.call("export", new State()));
    }
}

package dream.fcard.logic.respond;

public class ResponsesTest {
    //@Test
    //void rootTest() throws Exception {
    //    String path = FileReadWrite.resolve("./", "./testDir");
    //    new File(path).mkdirs();
    //    // make directory for testing
    //
    //    Responses.ROOT.call("root " + path, new State());
    //    assertEquals(path, StorageManager.getRoot());
    //    // test
    //
    //    FileReadWrite.delete(path);
    //    // cleanup
    //}
    //
    //@Test
    //void rootNoPathTest() throws Exception {
    //    assertEquals(true, Responses.ROOT_NO_PATH.call("root", new State()));
    //}
    //
    //@Test
    //void importTest() throws Exception {
    //    String deckName = "test123";
    //    String path = FileReadWrite.normalizePath("~/" + deckName + ".json");
    //    String root = FileReadWrite.normalizePath("~/testDir");
    //    // test parameters
    //
    //    StorageManager.provideRoot(root);
    //
    //    State s = new State();
    //    Deck d = new Deck(deckName);
    //    d.addNewCard(new FrontBackCard("front", "back"));
    //    // create stubs
    //
    //    FileReadWrite.write(path, d.toJson().toString());
    //    // create file
    //
    //    Responses.IMPORT.call("import " + path, s);
    //    // test import
    //
    //    try {
    //        assertEquals(deckName, s.getDeck(deckName).getName());
    //        assertEquals(d.toJson().toString(), s.getDeck(deckName).toJson().toString());
    //    } catch (DeckNotFoundException e) {
    //        fail();
    //    }
    //    // check deck valid
    //
    //    FileReadWrite.delete(path);
    //    FileReadWrite.delete(FileReadWrite.resolve(root, "./decks/" + deckName + ".json"));
    //    FileReadWrite.delete(FileReadWrite.resolve(root, "./decks"));
    //    FileReadWrite.delete(root);
    //    // cleanup
    //}
    //
    //@Test
    //void importNoPathTest() throws Exception {
    //    assertEquals(true, Responses.IMPORT_NO_PATH.call("import", new State()));
    //}
    //
    //@Test
    //void exportTest() throws Exception {
    //    String deckName = "test123";
    //    String path = FileReadWrite.normalizePath("~");
    //    String exportPath = FileReadWrite.resolve(path, "./" + deckName + ".json");
    //    String root = FileReadWrite.normalizePath("~/testDir");
    //    // test parameters
    //
    //    StorageManager.provideRoot(root);
    //
    //    State s = new State();
    //    Deck d = new Deck(deckName);
    //    d.addNewCard(new FrontBackCard("front", "back"));
    //    s.addDeck(d);
    //    // create stubs
    //
    //    StorageManager.writeDeck(d);
    //    // store in root
    //
    //    Responses.EXPORT.call("export deck/ " + deckName + " path/ " + path, s);
    //    // test export
    //
    //    try {
    //        assertEquals(d.toJson().toString(), FileReadWrite.read(exportPath));
    //    } catch (FileNotFoundException e) {
    //        fail();
    //    }
    //    // check export valid
    //
    //    FileReadWrite.delete(exportPath);
    //    FileReadWrite.delete(FileReadWrite.resolve(root, "./decks/" + deckName + ".json"));
    //    FileReadWrite.delete(FileReadWrite.resolve(root, "./decks"));
    //    FileReadWrite.delete(root);
    //    // cleanup
    //}
    //
    //@Test
    //void exportNoPathTest() throws Exception {
    //    assertEquals(true, Responses.EXPORT_NO_PATH.call("export deck/ test", new State()));
    //}
    //
    //@Test
    //void exportNoDeckTest() throws Exception {
    //    assertEquals(true, Responses.EXPORT_NO_DECK.call("export", new State()));
    //}
}

package cs.f10.t1.nursetraverse.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

import cs.f10.t1.nursetraverse.model.autocomplete.IndexWord;
import cs.f10.t1.nursetraverse.model.autocomplete.PrefixWord;
import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.appointment.AutoCompleteWord;
import cs.f10.t1.nursetraverse.model.autocomplete.CommandWord;
import cs.f10.t1.nursetraverse.model.autocomplete.ObjectWord;
import javafx.collections.ObservableList;

class AutoCompleteListHandlerTest {
    private AutoCompleteWordStorage typicalAutoCompleteStorage = new AutoCompleteStorageStub();
    private AutoCompleteListHandler autoCompleteListHandler = new AutoCompleteListHandler(typicalAutoCompleteStorage);

    @Test
    public void generateList_emptyString_ObjectListGenerated() {
        LinkedList<String> testParsedUserinputList = new LinkedList<>();
        testParsedUserinputList.add("");
        ObservableList<AutoCompleteWord> generatedList =
                autoCompleteListHandler.generateList(new LinkedList<>(), testParsedUserinputList);

        assertTrue(generatedList.get(0) instanceof ObjectWord);
    }

    @Test
    public void generateList_correctFirstWord_commandListGenerated() {
        LinkedList<String> testParsedUserinputList = new LinkedList<>();
        testParsedUserinputList.add("pat-");

        LinkedList<AutoCompleteWord> currentMatchedWords = new LinkedList<>();
        currentMatchedWords.add(new ObjectWord("pat-", null));

        ObservableList<AutoCompleteWord> generatedList =
                autoCompleteListHandler.generateList(currentMatchedWords, testParsedUserinputList);

        assertTrue(generatedList.get(0) instanceof CommandWord);
    }

    @Test
    public void generateList_commandWordNoIndexNoPrefix_noListGenerated() {
        LinkedList<String> testParsedUserinputList = new LinkedList<>();
        testParsedUserinputList.add("pat-");
        testParsedUserinputList.add("list");

        LinkedList<AutoCompleteWord> currentMatchedWords = new LinkedList<>();
        currentMatchedWords.add(new ObjectWord("pat-", null));
        currentMatchedWords.add(new CommandWord("pat-", "list",
                null, false, false));

        ObservableList<AutoCompleteWord> generatedList =
                autoCompleteListHandler.generateList(currentMatchedWords, testParsedUserinputList);

        assertEquals(0, generatedList.size());
    }

    @Test
    public void generateList_commandWordNoIndexHasPrefix_prefixListGenerated() {
        LinkedList<String> testParsedUserinputList = new LinkedList<>();
        testParsedUserinputList.add("pat-");
        testParsedUserinputList.add("add");

        LinkedList<AutoCompleteWord> currentMatchedWords = new LinkedList<>();
        currentMatchedWords.add(new ObjectWord("pat-", null));
        currentMatchedWords.add(new CommandWord("pat-", "add",
                null, false, true));

        ObservableList<AutoCompleteWord> generatedList =
                autoCompleteListHandler.generateList(currentMatchedWords, testParsedUserinputList);

        assertTrue(generatedList.get(0) instanceof PrefixWord);
    }

    @Test
    public void generateList_commandWordHasIndexHasPrefix_prefixListGenerated() {
        LinkedList<String> testParsedUserinputList = new LinkedList<>();
        testParsedUserinputList.add("pat-");
        testParsedUserinputList.add("edit");
        testParsedUserinputList.add("1");

        LinkedList<AutoCompleteWord> currentMatchedWords = new LinkedList<>();
        currentMatchedWords.add(new ObjectWord("pat-", null));
        currentMatchedWords.add(new CommandWord("pat-", "edit",
                null, false, true));
        currentMatchedWords.add(new IndexWord("1", null));

        ObservableList<AutoCompleteWord> generatedList =
                autoCompleteListHandler.generateList(currentMatchedWords, testParsedUserinputList);

        assertTrue(generatedList.get(0) instanceof PrefixWord);
    }
}

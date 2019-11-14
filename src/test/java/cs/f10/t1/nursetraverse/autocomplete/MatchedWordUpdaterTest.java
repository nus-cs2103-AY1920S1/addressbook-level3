package cs.f10.t1.nursetraverse.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.appointment.AutoCompleteWord;

class MatchedWordUpdaterTest {
    private AutoCompleteWordStorage typicalAutoCompleteStorage = new AutoCompleteStorageStub();
    private MatchedWordUpdater matchedWordUpdater =
            new MatchedWordUpdater(typicalAutoCompleteStorage, new AutoCompleteListHandler(typicalAutoCompleteStorage));

    @Test
    public void findMatchedWords_wrongCommandWord_onlyObjectWordMatches() {
        LinkedList<String> testParsedUserinputList = new LinkedList<>();
        testParsedUserinputList.add("pat-");
        testParsedUserinputList.add("wrong input");
        LinkedList<AutoCompleteWord> matchedWords = matchedWordUpdater.findMatchedWords(testParsedUserinputList);
        assertEquals("pat-", matchedWords.get(0).getSuggestedWord());
    }

    @Test
    public void findMatchedWords_emptyProvidedList_emptyMatchedList() {
        LinkedList<String> testParsedUserinputList = new LinkedList<>();
        LinkedList<AutoCompleteWord> matchedWords = matchedWordUpdater.findMatchedWords(testParsedUserinputList);
        assertEquals(0, matchedWords.size());
    }
}

package cs.f10.t1.nursetraverse.autocomplete;

import java.util.LinkedList;

import cs.f10.t1.nursetraverse.model.appointment.AutoCompleteWord;
import cs.f10.t1.nursetraverse.model.autocomplete.CommandWord;
import cs.f10.t1.nursetraverse.model.autocomplete.ObjectWord;
import javafx.collections.ObservableList;

/**
 * Update current matching words based on string in command box text field
 */
public class MatchedWordUpdater {
    private LinkedList<AutoCompleteWord> matchedAutoCompleteWords = new LinkedList<>();
    private AutoCompleteWordStorage autoCompleteWordStorage;
    private AutoCompleteListHandler autoCompleteListHandler;

    public MatchedWordUpdater(AutoCompleteWordStorage autoCompleteWordStorage,
                              AutoCompleteListHandler autoCompleteListHandler) {
        this.autoCompleteWordStorage = autoCompleteWordStorage;
        this.autoCompleteListHandler = autoCompleteListHandler;
    }

    /**
     * Match userinputs with sets of default list to check for correctness
     *
     * @param parsedUserInputList list of parsed userinput {objectword, commandword, ...}
     * @return A linked list of matched words
     */
    public LinkedList<AutoCompleteWord> findMatchedWords(LinkedList<String> parsedUserInputList) {
        matchedAutoCompleteWords.clear();

        // Check first word (object)
        boolean isObjectWordCorrect = false;
        if (parsedUserInputList.size() >= 1) {
            isObjectWordCorrect = matchWord(parsedUserInputList.get(0),
                    autoCompleteWordStorage.getOListAllObjectWord());
        }

        // Check second word (command)
        boolean isCommandWordCorrect = false;
        if (parsedUserInputList.size() >= 2) {
            if (isObjectWordCorrect) {
                isCommandWordCorrect = matchWord(parsedUserInputList.get(1), autoCompleteListHandler
                        .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllCommandWord()));
            }
        }

        // Check third word (index / prefix)
        boolean isThirdWordCorrect = false;
        if (parsedUserInputList.size() >= 3) {
            if (isCommandWordCorrect) {
                // Check with the command word whether index and prefix is needed
                if (((CommandWord) matchedAutoCompleteWords.get(1)).hasIndex()) {
                    isThirdWordCorrect = matchWord(parsedUserInputList.get(2), autoCompleteWordStorage
                            .generateOListAllIndexWord((ObjectWord) matchedAutoCompleteWords.get(0)));
                } else if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
                    isThirdWordCorrect = matchWord(parsedUserInputList.get(2), autoCompleteListHandler
                            .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord()));
                }
            }
        }

        // Check rest of the word (prefix)
        if (isThirdWordCorrect) {
            if (parsedUserInputList.size() >= 4) {
                for (int i = 3; i < parsedUserInputList.size(); i++) {
                    matchWord(parsedUserInputList.get(i), autoCompleteListHandler
                            .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord()));
                }
            }
        }

        return matchedAutoCompleteWords;
    }

    /**
     * Checks if a word is in an observable list
     *
     * @param wordToBeMatched word to be matched with list
     * @param list list containing words to be matched with wordToBeMatched
     * @return true if wordToBeMatched is in list
     */
    private boolean matchWord(String wordToBeMatched, ObservableList<AutoCompleteWord> list) {
        for (AutoCompleteWord autoCompleteWord : list) {
            if (wordToBeMatched.matches(autoCompleteWord.getSuggestedWord())) {
                matchedAutoCompleteWords.add(autoCompleteWord);
                return true;
            }
        }
        return false;
    }

    /**
     * @return string representation of all the matched words
     */
    public String getCombinedMatchedWords() {
        StringBuilder combinedMatchedWords = new StringBuilder();
        for (AutoCompleteWord autoCompleteWord : matchedAutoCompleteWords) {
            combinedMatchedWords
                    .append(autoCompleteWord.getSuggestedWord())
                    .append(autoCompleteWord.getConnectorChar());
        }
        return combinedMatchedWords.toString();
    }
}

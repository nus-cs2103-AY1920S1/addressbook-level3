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
        boolean isFirstWordCorrect = checkObjectWord(parsedUserInputList);

        // Check second word (command)
        boolean isSecondWordCorrect = false;
        if (isFirstWordCorrect) {
            isSecondWordCorrect = checkCommandWord(parsedUserInputList);
        }

        boolean isThirdWordCorrect = false;
        if (isSecondWordCorrect) {
            isThirdWordCorrect = checkThirdWord(parsedUserInputList);
        }

        // Check rest of the word (prefix)
        if (isThirdWordCorrect) {
            processRemainingPrefixWord(parsedUserInputList);
        }

        return matchedAutoCompleteWords;
    }

    /**
     * Checks if a word is in the specified observable list, if it is, add to matched word list
     *
     * @param wordToBeMatched word to be matched with list
     * @param list            list containing words to be matched with wordToBeMatched
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
     * Check if the first word provided by user is an object word, if it is, add to matched word list
     *
     * @param parsedUserInputList list of parsed userinput {objectword, commandword, ...}
     * @return true if first word provided by user is an object word
     */
    private boolean checkObjectWord(LinkedList<String> parsedUserInputList) {
        if (parsedUserInputList.size() >= 1) {
            return matchWord(parsedUserInputList.get(0), autoCompleteWordStorage.getOListAllObjectWord());
        }
        return false;
    }

    /**
     * Check if the second word provided by user is a command word, if it is, add to matched word list
     *
     * @param parsedUserInputList list of parsed userinput {objectword, commandword, ...}
     * @return true if second word provided by user is an command word
     */
    private boolean checkCommandWord(LinkedList<String> parsedUserInputList) {
        if (parsedUserInputList.size() >= 2) {
            return matchWord(parsedUserInputList.get(1), autoCompleteListHandler
                    .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllCommandWord()));
        }
        return false;
    }

    /**
     * Check if the third word provided by user is an index or prefix word, if it is, add to matched word list
     *
     * @param parsedUserInputList list of parsed userinput {objectword, commandword, ...}
     * @return true if second word provided by user is an index or prefix word
     */
    private boolean checkThirdWord(LinkedList<String> parsedUserInputList) {
        if (parsedUserInputList.size() >= 3) {
            // Check with the command word whether index and prefix is needed
            if (((CommandWord) matchedAutoCompleteWords.get(1)).hasIndex()) {
                return matchWord(parsedUserInputList.get(2), autoCompleteWordStorage
                        .generateOListAllIndexWord((ObjectWord) matchedAutoCompleteWords.get(0)));
            } else if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
                return matchWord(parsedUserInputList.get(2), autoCompleteListHandler
                        .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord()));
            }
        }
        return false;
    }

    /**
     * Check if the remaining words provided by user are prefix words, if it is, add to matched word list
     *
     * @param parsedUserInputList list of parsed userinput {objectword, commandword, ...}
     */
    private void processRemainingPrefixWord(LinkedList<String> parsedUserInputList) {
        if (parsedUserInputList.size() >= 4) {
            for (int i = 3; i < parsedUserInputList.size(); i++) {
                matchWord(parsedUserInputList.get(i), autoCompleteListHandler
                        .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord()));
            }
        }
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

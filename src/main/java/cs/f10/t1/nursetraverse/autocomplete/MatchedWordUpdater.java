package cs.f10.t1.nursetraverse.autocomplete;

import java.util.LinkedList;

/**
 * Update current matching words based on string in command box text field
 */
public class MatchedWordUpdater {
    private LinkedList<AutoCompleteWord> matchedAutoCompleteWords;
    private AutoCompleteWordStorage autoCompleteWordStorage;
    private AutoCompleteListHandler autoCompleteListHandler;

    public MatchedWordUpdater(AutoCompleteWordStorage autoCompleteWordStorage,
                              AutoCompleteListHandler autoCompleteListHandler) {
        this.autoCompleteWordStorage = autoCompleteWordStorage;
        this.autoCompleteListHandler = autoCompleteListHandler;
        this.matchedAutoCompleteWords = new LinkedList<>();
    }

    /**
     * Update matchedAutoCompleteWords list given userinput
     *
     * @param segments segments of the userinputs
     * @param firstSegmentParts parts of the first segment of userinput
     */
    public void updateMatchedWords(String[] segments, LinkedList<String> firstSegmentParts) {
        // segments[0] -> object-command format
        // segments[1] -> either prefix or index
        // segments[2] onwards -> prefix
        matchedAutoCompleteWords.clear();

        boolean isCorrectFirstSegment = matchFirstSegment(firstSegmentParts);

        if (segments.length >= 2) {
            boolean isCorrectSecondSegment = false;
            if (isCorrectFirstSegment) {
                isCorrectSecondSegment = matchSecondSegment(segments[1]);
            }
            if (segments.length >= 3 && isCorrectFirstSegment && isCorrectSecondSegment) {
                matchRestOfSegment(segments);
            }
        }
    }

    /**
     * Check if first segment of userinput matches with current matched words
     *
     * @param firstSegmentParts parts of the first segment of userinput
     * @return true if first segment parts matches with current matched words
     */
    public boolean matchFirstSegment(LinkedList<String> firstSegmentParts) {
        boolean isCorrectObjectWord = false;
        boolean isCorrectCommandWord = false;

        if (firstSegmentParts.size() >= 1) {
            // First object word
            for (AutoCompleteWord autoCompleteWord : autoCompleteWordStorage.getOListAllObjectWord()) {
                if (firstSegmentParts.get(0).matches(autoCompleteWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(autoCompleteWord);
                    isCorrectObjectWord = true;
                    break;
                }
            }
            // Second command word
            if (firstSegmentParts.size() == 2 && isCorrectObjectWord) {
                for (AutoCompleteWord autoCompleteWord : autoCompleteWordStorage.getOListAllCommandWord()) {
                    if (firstSegmentParts.get(1).matches(autoCompleteWord.getSuggestedWord())) {
                        matchedAutoCompleteWords.add(autoCompleteWord);
                        isCorrectCommandWord = true;
                        break;
                    }
                }
            }
        }
        return isCorrectObjectWord && isCorrectCommandWord;
    }

    /**
     * Check if second segment of userinput matches with current matched words
     *
     * @param secondSegment second segment of userinput
     * @return true if second segment matches with current matched words
     */
    public boolean matchSecondSegment(String secondSegment) {
        boolean isCorrect = false;

        if (((CommandWord) matchedAutoCompleteWords.get(1)).hasIndex()) {
            for (AutoCompleteWord autoCompleteWord : autoCompleteWordStorage
                    .generateOListAllIndexWord((ObjectWord) matchedAutoCompleteWords.get(0))) {
                if (secondSegment.equals(autoCompleteWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(autoCompleteWord);
                    isCorrect = true;
                    break;
                }
            }
        } else if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
            for (AutoCompleteWord autoCompleteWord : autoCompleteListHandler
                    .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord())) {
                if (secondSegment.equals(autoCompleteWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(autoCompleteWord);
                    isCorrect = true;
                    break;
                }
            }
        }
        return isCorrect;
    }

    /**
     * Check if all other segment of userinput matches with current matched words
     *
     * @param segments Array of segments to be checked
     */
    public void matchRestOfSegment(String[] segments) {
        for (int i = 3; i <= segments.length; i++) {
            if (matchedAutoCompleteWords.size() == i) {
                for (AutoCompleteWord autoCompleteWord : autoCompleteListHandler
                        .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord())) {
                    if (segments[i - 1].equals(autoCompleteWord.getSuggestedWord())) {
                        matchedAutoCompleteWords.add(autoCompleteWord);
                    }
                }
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

    public LinkedList<AutoCompleteWord> getMatchedAutoCompleteWords() {
        return matchedAutoCompleteWords;
    }
}

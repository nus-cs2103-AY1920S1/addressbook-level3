package seedu.address.autocomplete;

import java.util.LinkedList;

public class UserinputParserUtil {

    public static String[] splitIntoSegments(String currentPhraseInCommandBox) {
        return currentPhraseInCommandBox.split(" ");
    }

    public static LinkedList<String> parseFirstSegment(String firstSegment) {
        LinkedList<String> splitWords = new LinkedList<>();

        String[] parts = firstSegment.split("-");
        if (parts.length != 0) {
            String firstSplitWord = parts[0];
            splitWords.add(firstSplitWord);

            String secondSplitWord = null;
            if (parts.length >= 2) {
                secondSplitWord = parts[1];
                try {
                    for (int i = 2; i < parts.length; i++) {
                        secondSplitWord += "-";
                        secondSplitWord += parts[i];
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new IndexOutOfBoundsException(e.getMessage());
                }
            }
            if (secondSplitWord != null) {
                splitWords.add(secondSplitWord);
            }
        }
        return splitWords;
    }
}

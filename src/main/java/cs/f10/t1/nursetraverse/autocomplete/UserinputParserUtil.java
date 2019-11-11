package cs.f10.t1.nursetraverse.autocomplete;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Utility class for parsing userinput
 */
public class UserinputParserUtil {

    /**
     * Parse and return user input (eg: pat-edit 1 n/jo) in a linkedlist in the following format
     * {object word, command word, (if any) prefix/index word, (if any) prefix word, ...}
     *
     * @param currentPhraseInCommandBox string in command box text field
     * @return combinedList a list containing parsed userinputs in the specified format
     */
    public static LinkedList<String> parse(String currentPhraseInCommandBox) {
        String[] segments = splitIntoSegments(currentPhraseInCommandBox);
        LinkedList<String> parsedFirstSegment = parseFirstSegment(segments[0]);

        LinkedList<String> parsedList = new LinkedList<>(parsedFirstSegment);
        parsedList.addAll(Arrays.asList(segments).subList(1, segments.length));

        return parsedList;
    }

    /**
     * Parse userinput by spaces
     *
     * @param currentPhraseInCommandBox string in command box text field
     * @return array of split strings
     */
    public static String[] splitIntoSegments(String currentPhraseInCommandBox) {
        return currentPhraseInCommandBox.split(" ");
    }

    /**
     * Parse first segment of userinput into object and command word
     *
     * @param firstSegment a string from the start of userinput to the first space
     * @return linkedlist of object word and command word if they exist
     */
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

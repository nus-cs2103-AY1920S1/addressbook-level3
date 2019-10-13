package seedu.ezwatchlist.spellcheck;

import java.util.Comparator;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SpellCheck {
    public static final int KEYBOARD_DISTANCE_SORT = 0;
    private WordDictionary dict;

    private boolean isCaseSensitive;
    private int errorSensitivity = 1;

    public SpellCheck() throws IOException {
            dict = new WordDictionary();
            isCaseSensitive = true;
    }

    public void setErrorSensitivity(int errSensitivity) {
        if (errSensitivity < 0) {
            errorSensitivity = 0;
        } else {
            this.errorSensitivity = errorSensitivity;
        }
    }

    public void setIsCaseSensitive(boolean bool) {
        isCaseSensitive = bool;
    }

    public String suggest(String word) throws Exception {
        dict.updateWordList();
        Iterator<String> it = dict.getWordList().iterator();
        PriorityQueue<Pair<String, Integer>> suggestions =
                new PriorityQueue<>(Comparator.comparingInt(Pair::getValue));

        while (it.hasNext()) {
            String s = it.next();
            int led = computeLedDistance(word, s);
            if (led <= errorSensitivity) {
                Pair<String, Integer> pair = new Pair(s, led);
                suggestions.add(pair);
            }
        }
        Pair<String, Integer> head = suggestions.peek();
        if (head != null) {
            return head.getKey();
        } else {
            throw new Exception("Can't find any suggestions");
        }
    }

    /**
     * Returns the edit distance needed to convert string s1 to s2
     * If returns 0, the strings are same
     * If returns 1, that means either one character is added, removed or replaced
     */
    //solution below adapted from https://bakedcircuits.wordpress.com/2013/08/10/simple-spell-checker-in-java/
    private int computeLedDistance(String s1, String s2) {
        if (isCaseSensitive) {
            s1 = s1.toLowerCase();
            s2 = s2.toLowerCase();
        }

        int[] ledCosts = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;

            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    ledCosts[j] = j;
                } else if (j > 0) {
                        int newValue = ledCosts[j - 1];

                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), ledCosts[j]) + 1;
                        }

                        ledCosts[j - 1] = lastValue;
                        lastValue = newValue;
                }
            }

            if (i > 0) {
                ledCosts[s2.length()] = lastValue;
            }
        }
        return ledCosts[s2.length()];
    }

}

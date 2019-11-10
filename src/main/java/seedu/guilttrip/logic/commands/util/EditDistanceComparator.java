package seedu.guilttrip.logic.commands.util;

import java.util.Arrays;
import java.util.Comparator;

/**
 * EditDistanceComparator compares similarity of strings based on edit distance.
 */
public class EditDistanceComparator implements Comparator<String> {
    private final String reference;

    public EditDistanceComparator(String toCompare) {
        reference = toCompare;
    }

    @Override
    public int compare(String arg0, String arg1) {
        return editDistance(reference, arg0) - editDistance(reference, arg1);
    }

    /**
     * Calculates the edit distance between two strings.
     * Allowed operations are - deleting a character, adding a character and changing a character.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return the edit distance
     */
    private int editDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        // initialize memoization
        int[][] memo = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    memo[i][j] = j;
                } else if (j == 0) {
                    memo[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    memo[i][j] = memo[i - 1][j - 1];
                } else {
                    memo[i][j] = 1 + min(memo[i][j - 1], memo[i - 1][j], memo[i - 1][j - 1]);
                }
            }
        }

        return memo[m][n];
    }

    private static int min(int... args) {
        return Arrays.stream(args).min().getAsInt();
    }
}

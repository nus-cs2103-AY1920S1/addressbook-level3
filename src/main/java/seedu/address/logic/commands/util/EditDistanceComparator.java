package seedu.address.logic.commands.util;

import java.util.Arrays;
import java.util.Comparator;

/** */
public class EditDistanceComparator implements Comparator<String> {
    private final String REFERENCE_STRING;

    public EditDistanceComparator(String toCompare) {
        REFERENCE_STRING = toCompare;
    }

    @Override
    public int compare(String arg0, String arg1) {
        return editDistance(REFERENCE_STRING, arg0) - editDistance(REFERENCE_STRING, arg1);
    }

    private int editDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        // initialize memoization
        int[][] memo = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    memo[i][j] = j;
                } else if (j == 0) {
                    memo[i][j] = i;
                } else if (s1.charAt(i) == s2.charAt(j)) {
                    memo[i][j] = memo[i - 1][j - 1];
                } else {
                    memo[i][j] = 1 + min(memo[i][j - 1], // insert into i
                            memo[i - 1][j], // remove from i
                            memo[i - 1][j - 1]);// change
                }
            }
        }

        return memo[m][n];
    }

    private static int min(int... args) {
        return Arrays.stream(args).min().getAsInt();
    }
}

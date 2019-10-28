package seedu.address.ui.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A class to handle generation of colors.
 */
public class ColorGenerator {
    private static ArrayList<String> listOfColors = new ArrayList<String>(List.of("#FF8A80", "#FF80AB", "#F06292",
            "#EA80FC", "#B388FF", "#8C9EFF", "#80D8FF", "#18FFFF", "#64FFDA", "#00E676", "#69F0AE", "#FFFF00",
            "#FFC400", "#FFAB40", "#FF9E80", "#90A4AE", "#BCAAA4", "66FCF1"));

    /**
     * Method to generate a random list of colors to tag each group member with a particular colour.
     * @param groupSize The group size.
     * @return A list of unique colors for the group's schedule view.
     */
    public static ArrayList<String> generateColorList(int groupSize) {
        ArrayList<String> colors = new ArrayList<String>();
        HashSet<String> colorChecker = new HashSet<String>();
        int i = 0;
        while (i < groupSize) {
            int index = (int) (Math.random() * (listOfColors.size() - 1));
            if (colorChecker.contains(listOfColors.get(index))) {
                continue;
            }
            colors.add(listOfColors.get(index));
            colorChecker.add(listOfColors.get(index));
            i++;
        }
        return colors;
    }
}

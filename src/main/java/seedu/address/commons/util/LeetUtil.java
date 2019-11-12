package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class for converting a leet password into list of unleet possibilities.
 */
public class LeetUtil {
    private static Map<Character, Character[]> leetTable = new HashMap<>();
    static {
        leetTable.put('4', new Character[]{'a'});
        leetTable.put('@', new Character[]{'a'});
        leetTable.put('8', new Character[]{'b'});
        leetTable.put('(', new Character[]{'c'});
        leetTable.put('{', new Character[]{'c'});
        leetTable.put('[', new Character[]{'c'});
        leetTable.put('<', new Character[]{'c'});
        leetTable.put('3', new Character[]{'e'});
        leetTable.put('9', new Character[]{'g'});
        leetTable.put('6', new Character[]{'g'});
        leetTable.put('&', new Character[]{'g'});
        leetTable.put('#', new Character[]{'h'});
        leetTable.put('!', new Character[]{'i', 'l'});
        leetTable.put('1', new Character[]{'i', 'l'});
        leetTable.put('|', new Character[]{'i', 'l'});
        leetTable.put('0', new Character[]{'o'});
        leetTable.put('$', new Character[]{'s'});
        leetTable.put('5', new Character[]{'s'});
        leetTable.put('+', new Character[]{'t'});
        leetTable.put('7', new Character[]{'t', 'l'});
        leetTable.put('%', new Character[]{'x'});
        leetTable.put('2', new Character[]{'z'});
    }

    /**
     * Generates the possible list of possibilities given a password.
     *
     * @param password the password string to unleet
     * @return the list of possible unleet passwords
     */
    public static List<String> generateUnleetList(String password) {
        ArrayList<String> possibilities = new ArrayList<>();
        TreeMap<Integer, Character[]> indexToReplacements = new TreeMap<>();
        for (int i = 0; i < password.length(); i++) {
            Character[] replacement = leetTable.get(password.charAt(i));
            if (replacement != null) {
                indexToReplacements.put(i, replacement);
            }
        }

        // Don't bother for passwords that are all special characters.
        if (indexToReplacements.keySet().size() == password.length()) {
            return possibilities;
        }
        if (indexToReplacements.size() > 0) {
            char[] passwordCharArray = password.toCharArray();
            replaceAtIndex(indexToReplacements, indexToReplacements.firstKey(), passwordCharArray, possibilities);
        }
        return possibilities;
    }

    /**
     * Internal function to recursively build the list of un-leet possibilities.
     */
    private static void replaceAtIndex(TreeMap<Integer, Character[]> indexToReplacements,
                                       Integer currentIndex, char[] password, List<String> possibilities) {
        Character[] listOfReplacementsForSpecialCharacter = indexToReplacements.get(currentIndex);
        for (char replacement : listOfReplacementsForSpecialCharacter) {
            password[currentIndex] = replacement;
            if (currentIndex.equals(indexToReplacements.lastKey())) {
                //add into list of possibilities only after we've replaced till the last index
                possibilities.add(new String(password));
            } else if (possibilities.size() == 100) { //TODO: What is an appropriate length to stop at?
                // Cap at 100 possibilities for efficiency
                return;
            } else {
                int nextIndex = indexToReplacements.higherKey(currentIndex);
                replaceAtIndex(indexToReplacements, nextIndex,
                        password, possibilities);
            }
        }
    }



}

package seedu.address.model.password.analyser.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the adjacency matrix of each keyboard character to it's surrounding neighbours.
 */
public class AdjacencyGraph {
    /**
     * Adjacency graph for the keyboardAdjMatrix keyboard
     */
    private static final HashMap<Character, String[]> keyboardAdjMatrix;

    static {
        keyboardAdjMatrix = new HashMap<>();
        keyboardAdjMatrix.put('0', new String[]{"9(", null, null, "-_", "pP", "oO"});
        keyboardAdjMatrix.put('1', new String[]{"`~", null, null, "2@", "qQ", null});
        keyboardAdjMatrix.put('2', new String[]{"1!", null, null, "3#", "wW", "qQ"});
        keyboardAdjMatrix.put('3', new String[]{"2@", null, null, "4$", "eE", "wW"});
        keyboardAdjMatrix.put('4', new String[]{"3#", null, null, "5%", "rR", "eE"});
        keyboardAdjMatrix.put('5', new String[]{"4$", null, null, "6^", "tT", "rR"});
        keyboardAdjMatrix.put('6', new String[]{"5%", null, null, "7&", "yY", "tT"});
        keyboardAdjMatrix.put('7', new String[]{"6^", null, null, "8*", "uU", "yY"});
        keyboardAdjMatrix.put('8', new String[]{"7&", null, null, "9(", "iI", "uU"});
        keyboardAdjMatrix.put('9', new String[]{"8*", null, null, "0)", "oO", "iI"});
        keyboardAdjMatrix.put('!', new String[]{"`~", null, null, "2@", "qQ", null});
        keyboardAdjMatrix.put('"', new String[]{";:", "[{", "]}", null, null, "/?"});
        keyboardAdjMatrix.put('#', new String[]{"2@", null, null, "4$", "eE", "wW"});
        keyboardAdjMatrix.put('$', new String[]{"3#", null, null, "5%", "rR", "eE"});
        keyboardAdjMatrix.put('%', new String[]{"4$", null, null, "6^", "tT", "rR"});
        keyboardAdjMatrix.put('&', new String[]{"6^", null, null, "8*", "uU", "yY"});
        keyboardAdjMatrix.put('\'', new String[]{";:", "[{", "]}", null, null, "/?"});
        keyboardAdjMatrix.put('(', new String[]{"8*", null, null, "0)", "oO", "iI"});
        keyboardAdjMatrix.put(')', new String[]{"9(", null, null, "-_", "pP", "oO"});
        keyboardAdjMatrix.put('*', new String[]{"7&", null, null, "9(", "iI", "uU"});
        keyboardAdjMatrix.put('+', new String[]{"-_", null, null, null, "]}", "[{"});
        keyboardAdjMatrix.put(',', new String[]{"mM", "kK", "lL", ".>", null, null});
        keyboardAdjMatrix.put('-', new String[]{"0)", null, null, "=+", "[{", "pP"});
        keyboardAdjMatrix.put('.', new String[]{",<", "lL", ";:", "/?", null, null});
        keyboardAdjMatrix.put('/', new String[]{".>", ";:", "'\"", null, null, null});
        keyboardAdjMatrix.put(':', new String[]{"lL", "pP", "[{", "'\"", "/?", ".>"});
        keyboardAdjMatrix.put(';', new String[]{"lL", "pP", "[{", "'\"", "/?", ".>"});
        keyboardAdjMatrix.put('<', new String[]{"mM", "kK", "lL", ".>", null, null});
        keyboardAdjMatrix.put('=', new String[]{"-_", null, null, null, "]}", "[{"});
        keyboardAdjMatrix.put('>', new String[]{",<", "lL", ";:", "/?", null, null});
        keyboardAdjMatrix.put('?', new String[]{".>", ";:", "'\"", null, null, null});
        keyboardAdjMatrix.put('@', new String[]{"1!", null, null, "3#", "wW", "qQ"});
        keyboardAdjMatrix.put('A', new String[]{null, "qQ", "wW", "sS", "zZ", null});
        keyboardAdjMatrix.put('B', new String[]{"vV", "gG", "hH", "nN", null, null});
        keyboardAdjMatrix.put('C', new String[]{"xX", "dD", "fF", "vV", null, null});
        keyboardAdjMatrix.put('D', new String[]{"sS", "eE", "rR", "fF", "cC", "xX"});
        keyboardAdjMatrix.put('E', new String[]{"wW", "3#", "4$", "rR", "dD", "sS"});
        keyboardAdjMatrix.put('F', new String[]{"dD", "rR", "tT", "gG", "vV", "cC"});
        keyboardAdjMatrix.put('G', new String[]{"fF", "tT", "yY", "hH", "bB", "vV"});
        keyboardAdjMatrix.put('H', new String[]{"gG", "yY", "uU", "jJ", "nN", "bB"});
        keyboardAdjMatrix.put('I', new String[]{"uU", "8*", "9(", "oO", "kK", "jJ"});
        keyboardAdjMatrix.put('J', new String[]{"hH", "uU", "iI", "kK", "mM", "nN"});
        keyboardAdjMatrix.put('K', new String[]{"jJ", "iI", "oO", "lL", ",<", "mM"});
        keyboardAdjMatrix.put('L', new String[]{"kK", "oO", "pP", ";:", ".>", ",<"});
        keyboardAdjMatrix.put('M', new String[]{"nN", "jJ", "kK", ",<", null, null});
        keyboardAdjMatrix.put('N', new String[]{"bB", "hH", "jJ", "mM", null, null});
        keyboardAdjMatrix.put('O', new String[]{"iI", "9(", "0)", "pP", "lL", "kK"});
        keyboardAdjMatrix.put('P', new String[]{"oO", "0)", "-_", "[{", ";:", "lL"});
        keyboardAdjMatrix.put('Q', new String[]{null, "1!", "2@", "wW", "aA", null});
        keyboardAdjMatrix.put('R', new String[]{"eE", "4$", "5%", "tT", "fF", "dD"});
        keyboardAdjMatrix.put('S', new String[]{"aA", "wW", "eE", "dD", "xX", "zZ"});
        keyboardAdjMatrix.put('T', new String[]{"rR", "5%", "6^", "yY", "gG", "fF"});
        keyboardAdjMatrix.put('U', new String[]{"yY", "7&", "8*", "iI", "jJ", "hH"});
        keyboardAdjMatrix.put('V', new String[]{"cC", "fF", "gG", "bB", null, null});
        keyboardAdjMatrix.put('W', new String[]{"qQ", "2@", "3#", "eE", "sS", "aA"});
        keyboardAdjMatrix.put('X', new String[]{"zZ", "sS", "dD", "cC", null, null});
        keyboardAdjMatrix.put('Y', new String[]{"tT", "6^", "7&", "uU", "hH", "gG"});
        keyboardAdjMatrix.put('Z', new String[]{null, "aA", "sS", "xX", null, null});
        keyboardAdjMatrix.put('[', new String[]{"pP", "-_", "=+", "]}", "'\"", ";:"});
        keyboardAdjMatrix.put('\\', new String[]{"]}", null, null, null, null, null});
        keyboardAdjMatrix.put(']', new String[]{"[{", "=+", null, "\\|", null, "'\""});
        keyboardAdjMatrix.put('^', new String[]{"5%", null, null, "7&", "yY", "tT"});
        keyboardAdjMatrix.put('_', new String[]{"0)", null, null, "=+", "[{", "pP"});
        keyboardAdjMatrix.put('`', new String[]{null, null, null, "1!", null, null});
        keyboardAdjMatrix.put('a', new String[]{null, "qQ", "wW", "sS", "zZ", null});
        keyboardAdjMatrix.put('b', new String[]{"vV", "gG", "hH", "nN", null, null});
        keyboardAdjMatrix.put('c', new String[]{"xX", "dD", "fF", "vV", null, null});
        keyboardAdjMatrix.put('d', new String[]{"sS", "eE", "rR", "fF", "cC", "xX"});
        keyboardAdjMatrix.put('e', new String[]{"wW", "3#", "4$", "rR", "dD", "sS"});
        keyboardAdjMatrix.put('f', new String[]{"dD", "rR", "tT", "gG", "vV", "cC"});
        keyboardAdjMatrix.put('g', new String[]{"fF", "tT", "yY", "hH", "bB", "vV"});
        keyboardAdjMatrix.put('h', new String[]{"gG", "yY", "uU", "jJ", "nN", "bB"});
        keyboardAdjMatrix.put('i', new String[]{"uU", "8*", "9(", "oO", "kK", "jJ"});
        keyboardAdjMatrix.put('j', new String[]{"hH", "uU", "iI", "kK", "mM", "nN"});
        keyboardAdjMatrix.put('k', new String[]{"jJ", "iI", "oO", "lL", ",<", "mM"});
        keyboardAdjMatrix.put('l', new String[]{"kK", "oO", "pP", ";:", ".>", ",<"});
        keyboardAdjMatrix.put('m', new String[]{"nN", "jJ", "kK", ",<", null, null});
        keyboardAdjMatrix.put('n', new String[]{"bB", "hH", "jJ", "mM", null, null});
        keyboardAdjMatrix.put('o', new String[]{"iI", "9(", "0)", "pP", "lL", "kK"});
        keyboardAdjMatrix.put('p', new String[]{"oO", "0)", "-_", "[{", ";:", "lL"});
        keyboardAdjMatrix.put('q', new String[]{null, "1!", "2@", "wW", "aA", null});
        keyboardAdjMatrix.put('r', new String[]{"eE", "4$", "5%", "tT", "fF", "dD"});
        keyboardAdjMatrix.put('s', new String[]{"aA", "wW", "eE", "dD", "xX", "zZ"});
        keyboardAdjMatrix.put('t', new String[]{"rR", "5%", "6^", "yY", "gG", "fF"});
        keyboardAdjMatrix.put('u', new String[]{"yY", "7&", "8*", "iI", "jJ", "hH"});
        keyboardAdjMatrix.put('v', new String[]{"cC", "fF", "gG", "bB", null, null});
        keyboardAdjMatrix.put('w', new String[]{"qQ", "2@", "3#", "eE", "sS", "aA"});
        keyboardAdjMatrix.put('x', new String[]{"zZ", "sS", "dD", "cC", null, null});
        keyboardAdjMatrix.put('y', new String[]{"tT", "6^", "7&", "uU", "hH", "gG"});
        keyboardAdjMatrix.put('z', new String[]{null, "aA", "sS", "xX", null, null});
        keyboardAdjMatrix.put('{', new String[]{"pP", "-_", "=+", "]}", "'\"", ";:"});
        keyboardAdjMatrix.put('|', new String[]{"]}", null, null, null, null, null});
        keyboardAdjMatrix.put('}', new String[]{"[{", "=+", null, "\\|", null, "'\""});
        keyboardAdjMatrix.put('~', new String[]{null, null, null, "1!", null, null});
    }

    /**
     * Returns a set of neighbors for a specific character.
     *
     * @param key the specific input character.
     * @return A set of characters which are neighbors to the input character.
     */
    public static List<Character> getNeighbors(Character key) {
        ArrayList<Character> neighbors = new ArrayList<>();

        if (keyboardAdjMatrix.containsKey(key)) {
            String[] keyNeighbors = keyboardAdjMatrix.get(key);
            for (String keyNeighbor : keyNeighbors) {
                if (keyNeighbor == null) {
                    continue;
                }
                for (Character character : keyNeighbor.toCharArray()) {
                    neighbors.add(character);
                }
            }
        }
        return neighbors;
    }

    /**
     * Returns the number of turns in the password substring passed in based on the adjacency graph.
     * The substring with the least amount of turns is the easiest password.
     *
     * @param substring the substring of the password.
     * @return the number of turns in this substring using the {@code AdjacencyGraph}
     */
    public static int getDirections(String substring) {
        int direction = 1; // the direction of the curr -> next
        int turns = 0;
        char[] characters = substring.toCharArray();

        for (int i = 0; i < characters.length; i++) { //for each character
            Character curr = characters[i];
            if (i + 1 >= characters.length) { //while not at the last character
                continue;
            }

            Character next = characters[i + 1];
            if (keyboardAdjMatrix.containsKey(curr)) {
                String[] neighbors = keyboardAdjMatrix.get(curr);
                for (int j = 0; j < neighbors.length; j++) { //j will represent the direction of neighbour
                    if (neighbors[j] == null) { //skip if is null
                        continue;
                    }
                    for (Character neighbor : neighbors[j].toCharArray()) { // for both char and shift char
                        if (next.equals(neighbor)) { //
                            if (direction == 0) {
                                direction = j; //set the first direction
                            } else if (direction != j) { //else check if same as previous direction.
                                turns++;
                                direction = j;
                            }
                        }
                    }
                }
            }
        }
        return turns;
    }

}

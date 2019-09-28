package seedu.address.logic.autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * TrieNode of Trie.
 */
public class TrieNode {
    private HashMap<Character, TrieNode> children = new HashMap<>();

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    /**
     * Get all possible word fragments from current TrieNode.
     *
     * @return List of word fragments
     */
    public List<String> getPossibilities() {
        if (children.isEmpty()) {
            return new ArrayList<String>(Collections.singleton(""));
        }
        ArrayList<String> ls = new ArrayList<>();
        children.forEach((k, v) -> v.getPossibilities().forEach(str -> ls.add(k + str)));
        return ls;
    }
}

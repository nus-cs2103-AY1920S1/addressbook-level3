package seedu.address.logic.autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TrieNode {
    private HashMap<Character, TrieNode> children = new HashMap<>();

    public TrieNode() {
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public ArrayList<String> getPossibilities() {
        if (children.isEmpty()) {
            return new ArrayList<String>(Collections.singleton(""));
        }
        ArrayList<String> ls = new ArrayList<>();
        children.forEach((k, v) -> v.getPossibilities().forEach(str -> ls.add(k + str)));
        return ls;
    }
}

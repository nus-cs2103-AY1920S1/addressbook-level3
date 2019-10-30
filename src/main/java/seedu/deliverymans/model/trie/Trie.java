package seedu.deliverymans.model.trie;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Javadoc comment
 */
public class Trie {
    private final HashMap<Character, Trie> children;
    private String content;

    Trie(String content) {
        this.content = content;
        children = new HashMap<>();
    }

    /**
     * Inserts a {@code String} into the Trie.
     */
    void insert(String key) {
        Trie curr = this;
        for (char c : key.toLowerCase().toCharArray()) {
            if (!curr.children.containsKey(c)) {
                Trie temp = new Trie(curr.content + c);
                curr.children.put(c, temp);
                curr = temp;
            } else {
                curr = curr.children.get(c);
            }
        }
    }

    /**
     * Tofill.
     */
    private Trie search(String toFind) {
        Trie curr = this;
        for (char c : toFind.toLowerCase().toCharArray()) {
            if (!curr.children.containsKey(c)) {
                return null;
            }
            curr = curr.children.get(c);
        }
        return curr;
    }

    /**
     * Tofill.
     */
    public LinkedList<String> autoComplete(String prefix) {
        Trie result = search(prefix);
        if (result == null) {
            return new LinkedList<>();
        }
        return result.allPrefixes();
    }

    /**
     * Tofill
     */
    private LinkedList<String> allPrefixes() {
        LinkedList<String> contentList = new LinkedList<>();
        if (children.isEmpty()) {
            contentList.add(this.content);
        }
        for (Map.Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            LinkedList<String> childPrefixes = child.allPrefixes();
            contentList.addAll(childPrefixes);
        }
        return contentList;
    }
}

package seedu.deliverymans.model.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * Javadoc comment
 */
public class Trie {
    private final HashMap<Character, Trie> children;
    private final LinkedList<String> commandList = new LinkedList<>();

    private boolean containsCommandWord;

    Trie() {
        children = new HashMap<>();
    }

    void insert(String key) {
        insert(key, key);
    }

    /**
     * Inserts a {@code String} into the Trie.
     */
    private void insert(String key, String command) {
        if (key.length() == 1) {
            char c = key.charAt(0);
            if (!children.containsKey(c)) {
                children.put(c, new Trie());
            }
            Trie leaf = children.get(c);
            leaf.addContent(command);
            leaf.containsCommandWord = true;
        } else if (key.length() > 1) {
            char first = key.charAt(0);
            if (!children.containsKey(first)) {
                children.put(first, new Trie());
            }
            String keySubstring = key.substring(1);
            children.get(first).insert(keySubstring, command);
            insert(keySubstring, command);
        }
    }

    private void addContent(String content) {
        this.commandList.addLast(content);
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
    public LinkedList<String> autoComplete(String prefixSuffix) {
        Trie result = search(prefixSuffix);
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
        HashSet<String> uniqueList = new HashSet<>();
        if (containsCommandWord) {
            uniqueList.addAll(this.commandList);
        }
        for (Map.Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            LinkedList<String> childPrefixes = child.allPrefixes();
            uniqueList.addAll(childPrefixes);
        }
        contentList.addAll(uniqueList);
        contentList.sort(String::compareToIgnoreCase);
        return contentList;
    }
}

package seedu.deliverymans.model.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * Javadoc comment
 */
class Trie {
    private final HashMap<Character, Trie> children;
    private final LinkedList<String> contentList  = new LinkedList<>();

    private boolean containsCommandWord;

    Trie() {
        children = new HashMap<>();
    }

    void insertCommand(String key, LinkedList<String> prefixes) {
        insertCommand(key, key, prefixes);
    }

    /**
     * Inserts a {@code String} into the Trie.
     */
    private void insertCommand(String key, String command, LinkedList<String> prefixes) {
        if (key.length() == 1) {
            char c = key.charAt(0);
            if (!children.containsKey(c)) {
                children.put(c, new Trie());
            }
            Trie leaf = children.get(c);
            leaf.addContent(command);
            leaf.insertPrefixes(prefixes);
            leaf.containsCommandWord = true;
        } else if (key.length() > 1) {
            char first = key.charAt(0);
            if (!children.containsKey(first)) {
                children.put(first, new Trie());
            }
            String keySubstring = key.substring(1);
            children.get(first).insertCommand(keySubstring, command, prefixes);
            insertCommand(keySubstring, command, prefixes);
        }
    }

    private void insertPrefixes(LinkedList<String> prefixes) {
        for (String prefix : prefixes) {
            insertPrefix(prefix);
        }
    }

    private void insertPrefix(String prefix) {
        Trie curr = this;
        char[] cArray = prefix.toCharArray();
        for (int i = 0; i < cArray.length; ++i) {
            char c = cArray[i];
            if (i == cArray.length - 1) {
                if (!curr.children.containsKey(c)) {
                    curr.children.put(c, new Trie());
                }
                curr.children.get(c).addContent(prefix);
            } else {
                if (!curr.children.containsKey(c)) {
                    curr.children.put(c, new Trie());
                }
                curr = curr.children.get(c);
            }
        }
    }

    private void addContent(String content) {
        this.contentList.addLast(content);
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
            uniqueList.addAll(this.contentList);
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

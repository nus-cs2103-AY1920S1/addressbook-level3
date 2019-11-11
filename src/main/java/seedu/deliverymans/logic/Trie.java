package seedu.deliverymans.logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import seedu.deliverymans.logic.parser.Prefix;

/**
 * Trie ADT used for log(l) time complexity searching, where l is the length of input String.
 */
class Trie {
    private final HashMap<Character, Trie> children;
    private final LinkedList<String> contentList = new LinkedList<>();
    private final LinkedList<Prefix> prefixList = new LinkedList<>();
    private boolean containsCommandWord;

    Trie() {
        children = new HashMap<>();
    }

    /**
     * Inserts a {@code String} into the Trie.
     */
    void insertCommand(String key, LinkedList<Prefix> prefixes) {
        insertCommand(key, key, prefixes);
    }

    /**
     * Inserts a {@code String} into the Trie.
     */
    void insertCommand(String key) {
        insertCommand(key, key, new LinkedList<>());
    }

    /**
     * Inserts a {@code String} into the Trie.
     */
    private void insertCommand(String key, String command, LinkedList<Prefix> prefixes) {
        if (key.length() == 1) {
            char c = key.charAt(0);
            if (!children.containsKey(c)) {
                children.put(c, new Trie());
            }
            Trie leaf = children.get(c);
            leaf.addContent(command);
            leaf.addPrefixes(prefixes);
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

    /**
     * Returns the prefixesList of the current Trie.
     */
    private LinkedList<Prefix> getPrefixList() {
        return this.prefixList;
    }

    /**
     * Adds a {@code String} prefix to the prefixesList of the current Trie.
     */
    private void addPrefixes(LinkedList<Prefix> prefixes) {
        this.prefixList.addAll(prefixes);
    }

    LinkedList<Prefix> getPrefixes(String toFind) {
        Trie result = search(toFind);
        if (result == null) { // commandWord does not exist
            return new LinkedList<>();
        }
        return result.getPrefixList();
    }

    /**
     * Adds a {@code String} commandWord to the contestList of the Trie.
     */
    private void addContent(String content) {
        this.contentList.addLast(content);
    }

    /**
     * Returns a Trie containing the {@code String} to find.
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
     * Returns the list of commandWords containing the {@code String} to find.
     */
    LinkedList<String> autoCompleteCommandWord(String toFind) {
        Trie result = search(toFind);
        if (result == null) { // commandWord does not exist
            return new LinkedList<>();
        }
        return result.getAllCommandWords();
    }

    /**
     * Iterates through the Trie and returns all commandWords the Trie as well as its children contain.
     */
    private LinkedList<String> getAllCommandWords() {
        HashSet<String> uniqueList = new HashSet<>();
        if (containsCommandWord) {
            uniqueList.addAll(this.contentList);
        }
        for (Map.Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            LinkedList<String> childCommandWords = child.getAllCommandWords();
            uniqueList.addAll(childCommandWords);
        }
        LinkedList<String> contentList = new LinkedList<>(uniqueList);
        contentList.sort(String::compareToIgnoreCase);
        return contentList;
    }
}

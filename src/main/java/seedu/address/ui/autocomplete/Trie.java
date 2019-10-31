package seedu.address.ui.autocomplete;

/**
 * Trie for AutoComplete Searches.
 */
public class Trie {
    private final TrieNode root = new TrieNode();

    public Trie(String... commandsToSupport) {
        for (String command : commandsToSupport) {
            this.insert(command);
        }
    }

    /**
     * Inserts word into Trie.
     *
     * @param word
     */
    private void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            current = current.getChildren().computeIfAbsent(word.charAt(i), c -> new TrieNode());
        }
    }

    /**
     * Finds and returns the TrieNode of given prefix, returns null if does not exists.
     *
     * @param word
     * @return TrieNode
     */
    public TrieNode find(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length() && current != null; i++) {
            current = current.getChildren().get(word.charAt(i));
        }
        return current;
    }
}

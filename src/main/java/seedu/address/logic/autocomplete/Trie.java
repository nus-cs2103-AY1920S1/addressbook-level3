package seedu.address.logic.autocomplete;

/**
 * Trie for AutoComplete Searches.
 */
public class Trie {
    private TrieNode root = new TrieNode();

    public Trie(String[] commandsToSupport) {
        for (String command : commandsToSupport) {
            this.insert(command);
        }
    }

    /**
     * Inserts word into Trie.
     *
     * @param word
     */
    protected void insert(String word) {
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
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current;
    }
}

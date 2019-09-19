package seedu.address.logic.autocomplete;

public class Trie {
    private TrieNode root = new TrieNode();

    public Trie() {
        this.insert("queue");
        this.insert("add");
        this.insert("dequeue");
        this.insert("next");
        this.insert("patient");
        this.insert("register");
        this.insert("update");
        this.insert("doctors");
        this.insert("onduty");
        this.insert("offduty");
        this.insert("appointments");
        this.insert("cancel");
        this.insert("change");
        this.insert("slot");
        this.insert("missed");
        this.insert("ack");
        this.insert("settle");
        this.insert("help");
        this.insert("exit");
        this.insert("undo");
    }

    protected void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            current = current.getChildren().computeIfAbsent(word.charAt(i), c -> new TrieNode());
        }
    }

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

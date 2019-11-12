package seedu.ezwatchlist.logic.parser;

/**
 * Enumeration for the possible search key in a search command.
 */
public enum SearchKey {
    KEY_NAME("name"),
    KEY_TYPE("type"),
    KEY_ACTOR("actor"),
    KEY_GENRE("genre"),
    KEY_IS_WATCHED("is_watched"),
    KEY_FROM_ONLINE("from_online");

    public final String key;

    SearchKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

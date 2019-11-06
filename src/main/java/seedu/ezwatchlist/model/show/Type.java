package seedu.ezwatchlist.model.show;

public enum Type {
    MOVIE("movie"),
    TV_SHOW("tv");

    public final String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

package seedu.address.model.show;

/**
 * Represents an Episode of a TvShow in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Episode {
    private final Name name;
    private final int episodeNum;

    public Episode(Name name, int episodeNum) {
        this.name = name;
        this.episodeNum = episodeNum;
    }

    public Name getName() {
        return name;
    }

    public int getEpisodeNum() {
        return episodeNum;
    }
}

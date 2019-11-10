package seedu.ezwatchlist.model.show;

/**
 * Represents an Episode of a TvShow in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Episode {
    public static final String MESSAGE_CONSTRAINTS_EPISODE_NAME =
            "Episode names should only contain characters and spaces, and it should not be blank";
    public static final String MESSAGE_CONSTRAINTS_EPISODE_NUMBER =
            "Episode numbers should only be integers greater than 0, and it should not be blank";

    /*
     * The first character of the episode name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private final String episodeName;
    private final int episodeNum;

    public Episode(String episodeName, int episodeNum) {
        this.episodeName = episodeName;
        this.episodeNum = episodeNum;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public int getEpisodeNum() {
        return episodeNum;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidEpisodeName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given integer is a valid episode number.
     */
    public static boolean isValidEpisodeNum(int test) {
        return test > 0;
    }
}

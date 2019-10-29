package seedu.ezwatchlist.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.show.Episode;

/**
 * Jackson-friendly version of {@link Episode}.
 */
class JsonAdaptedEpisode {

    private final String episodeName;
    private final int episodeNum;

    /**
     * Constructs a {@code JsonAdaptedEpisode} with the given episode details.
     */
    @JsonCreator
    public JsonAdaptedEpisode(@JsonProperty("name") String episodeName,
                              @JsonProperty("episode number") int episodeNum) {
        this.episodeName = episodeName;
        this.episodeNum = episodeNum;
    }

    /**
     * Converts a given {@code Episode} into this class for Jackson use.
     */
    public JsonAdaptedEpisode(Episode source) {
        episodeName = source.getEpisodeName();
        episodeNum = source.getEpisodeNum();
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public int getEpisodeNum() {
        return episodeNum;
    }

    /**
     * Converts this Jackson-friendly adapted episode object into the model's {@code Episode} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted episode.
     */
    public Episode toModelType() throws IllegalValueException {
        if (!Episode.isValidEpisodeName(episodeName)) {
            throw new IllegalValueException(Episode.MESSAGE_CONSTRAINTS_EPISODE_NAME);
        }

        if (!Episode.isValidEpisodeNum(episodeNum)) {
            throw new IllegalValueException(Episode.MESSAGE_CONSTRAINTS_EPISODE_NUMBER);
        }

        return new Episode(episodeName, episodeNum);
    }

}

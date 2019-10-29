package seedu.ezwatchlist.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.show.Episode;
import seedu.ezwatchlist.model.show.TvSeason;

/**
 * Jackson-friendly version of {@link TvSeason}.
 */
class JsonAdaptedTvSeason {

    private final int seasonNumber;
    private final int totalNumOfEpisodes;
    private final List<JsonAdaptedEpisode> episodes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTvSeason} with the given {@code seasonNumber}.
     */
    @JsonCreator
    public JsonAdaptedTvSeason(@JsonProperty("seasonNumber") int seasonNumber,
                               @JsonProperty("totalNumOfEpisodes") int totalNumOfEpisodes,
                               @JsonProperty("episodes") List<JsonAdaptedEpisode> episodes) {
        this.seasonNumber = seasonNumber;
        this.totalNumOfEpisodes = totalNumOfEpisodes;
        if (episodes != null) {
            this.episodes.addAll(episodes);
        }
    }

    /**
     * Converts a given {@code TvSeason} into this class for Jackson use.
     */
    public JsonAdaptedTvSeason(TvSeason source) {
        seasonNumber = source.getSeasonNum();
        totalNumOfEpisodes = source.getTotalNumOfEpisodes();
        episodes.addAll(source.getEpisodes().stream()
                .map(JsonAdaptedEpisode::new)
                .collect(Collectors.toList()));
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getTotalNumOfEpisodes() {
        return totalNumOfEpisodes;
    }

    public List<JsonAdaptedEpisode> getEpisodes() {
        return episodes;
    }

    /**
     * Converts this Jackson-friendly adapted tv season object into the model's {@code TvSeason} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted TvSeason.
     */
    public TvSeason toModelType() throws IllegalValueException {
        final List<Episode> seasonEpisodes = new ArrayList<>();
        for (JsonAdaptedEpisode episode : episodes) {
            seasonEpisodes.add(episode.toModelType());
        }
        if (!TvSeason.isValidTvSeasonNumber(seasonNumber)) {
            throw new IllegalValueException(TvSeason.MESSAGE_CONSTRAINTS_SEASON_NUM);
        }
        if (!TvSeason.isValidTotalNumOfEpisodes(totalNumOfEpisodes)) {
            throw new IllegalValueException(TvSeason.MESSAGE_CONSTRAINTS_TOTAL_EPISODES);
        }
        final Set<Episode> modelActors = new HashSet<>(seasonEpisodes);
        return new TvSeason(seasonNumber, totalNumOfEpisodes, (ArrayList<Episode>) seasonEpisodes);
    }

}

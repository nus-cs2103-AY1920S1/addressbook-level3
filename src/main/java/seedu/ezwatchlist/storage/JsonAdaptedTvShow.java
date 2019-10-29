package seedu.ezwatchlist.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvSeason;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Jackson-friendly version of {@link TvShow}.
 */
public class JsonAdaptedTvShow {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Show's %s field is missing!";

    private final String name;
    private final String type;
    private final String dateOfRelease;
    private final boolean isWatched;
    private final String description;
    private final int runningTime;
    private final String poster;
    private final List<JsonAdaptedActor> actors = new ArrayList<>();
    private final int numOfEpisodesWatched;
    private final int totalNumOfEpisodes;
    private final List<JsonAdaptedTvSeason> tvSeasons = new ArrayList<>();
    private final JsonAdaptedGenres genres = new JsonAdaptedGenres(new ArrayList<>());
    /**
     * Constructs a {@code JsonAdaptedShows} with the given show details.
     */
    @JsonCreator
    public JsonAdaptedTvShow(@JsonProperty("name") String name,
                             @JsonProperty("type") String type,
                             @JsonProperty("dateOfRelease") String dateOfRelease,
                             @JsonProperty("watched") boolean isWatched,
                             @JsonProperty("description") String description,
                             @JsonProperty("runningTime") int runningTime,
                             @JsonProperty("actors") List<JsonAdaptedActor> actors,
                             @JsonProperty("poster") String poster,
                             @JsonProperty("numOfEpisodesWatched") int numOfEpisodesWatched,
                             @JsonProperty("totalNumOfEpisodes") int totalNumOfEpisodes,
                             @JsonProperty("tvSeasons") List<JsonAdaptedTvSeason> tvSeasons,
                             @JsonProperty("genres") List<String> genres) {
        this.name = name;
        this.type = type;
        this.dateOfRelease = dateOfRelease;
        this.isWatched = isWatched;
        this.description = description;
        this.runningTime = runningTime;
        if (actors != null) {
            this.actors.addAll(actors);
        }
        this.poster = poster;
        this.numOfEpisodesWatched = numOfEpisodesWatched;
        this.totalNumOfEpisodes = totalNumOfEpisodes;
        if (tvSeasons != null) {
            this.tvSeasons.addAll(tvSeasons);
        }
        this.genres.addAll(genres);
    }

    /**
     * Converts a given {@code TvShow} into this class for Jackson use.
     */
    public JsonAdaptedTvShow(Show source) {
        name = source.getName().showName;
        type = source.getType();
        dateOfRelease = source.getDateOfRelease().value;
        isWatched = source.isWatched().value;
        description = source.getDescription().fullDescription;
        runningTime = source.getRunningTime().value;
        if (actors != null) {
            this.actors.addAll(actors);
        }
        poster = source.getPoster().getImagePath();
        numOfEpisodesWatched = source.getNumOfEpisodesWatched();
        totalNumOfEpisodes = source.getTotalNumOfEpisodes();
        tvSeasons.addAll(source.getTvSeasons().stream()
                .map(JsonAdaptedTvSeason::new)
                .collect(Collectors.toList()));
        genres.addAll(source.getGenres().getGenres());
    }

    /**
     * Converts this Jackson-friendly adapted tv show object into the model's {@code TvShow} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tv show.
     */
    public Show toModelType() throws IllegalValueException {
        final List<Actor> showActors = new ArrayList<>();
        for (JsonAdaptedActor actor : actors) {
            showActors.add(actor.toModelType());
        }

        final List<TvSeason> showSeasons = new ArrayList<>();
        for (JsonAdaptedTvSeason season : tvSeasons) {
            showSeasons.add(season.toModelType());
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (dateOfRelease == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(dateOfRelease)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDateOfRelease = new Date(dateOfRelease);

        if (!IsWatched.isValidIsWatched(isWatched)) {
            throw new IllegalValueException(IsWatched.MESSAGE_CONSTRAINTS);
        }
        final IsWatched modelIsWatched = new IsWatched(isWatched);

        if (description == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);


        if (runningTime < 0) {
            throw new IllegalValueException(String.format(RunningTime.MESSAGE_CONSTRAINTS2));
        }
        if (runningTime == 0) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, RunningTime.class.getSimpleName()));
        }
        if (!RunningTime.isValidRunningTime(runningTime)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final RunningTime modelRunningTime = new RunningTime(runningTime);

        final Set<Actor> modelActors = new HashSet<>(showActors);

        Show show = new TvShow(modelName, modelDescription, modelIsWatched,
                modelDateOfRelease, modelRunningTime, modelActors,
                numOfEpisodesWatched, totalNumOfEpisodes, showSeasons);

        show.setType(type);
        show.setPoster(new Poster(poster));
        show.setGenres(genres.toModelType());
        return show;
    }
}

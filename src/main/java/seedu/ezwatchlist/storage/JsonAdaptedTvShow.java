package seedu.ezwatchlist.storage;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonAdaptedTvShow extends JsonAdaptedShow {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Show's %s field is missing!";

    private final int numOfEpisodesWatched;
    private final int totalNumOfEpisodes;
    private final List<JsonAdaptedTvSeason> tvSeasons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedShow} with the given show details.
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
                             @JsonProperty("tvSeasons") List<JsonAdaptedTvSeason> tvSeasons) {
        super(name, type, dateOfRelease, isWatched, description, runningTime, actors, poster);
        this.numOfEpisodesWatched = numOfEpisodesWatched;
        this.totalNumOfEpisodes = totalNumOfEpisodes;
        if (tvSeasons != null) {
            this.tvSeasons.addAll(tvSeasons);
        }
    }

    /**
     * Converts a given {@code TvShow} into this class for Jackson use.
     */
    public JsonAdaptedTvShow(Show source) {
        super(source);
        numOfEpisodesWatched = source.getNumOfEpisodesWatched();
        totalNumOfEpisodes = source.getTotalNumOfEpisodes();
        tvSeasons.addAll(source.getTvSeasons().stream()
                .map(JsonAdaptedTvSeason::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted tv show object into the model's {@code TvShow} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tv show.
     */
    public Show toModelType() throws IllegalValueException {
        final List<Actor> showActors = new ArrayList<>();
        for (JsonAdaptedActor actor : super.getActors()) {
            showActors.add(actor.toModelType());
        }

        final List<TvSeason> showSeasons = new ArrayList<>();
        for (JsonAdaptedTvSeason season : tvSeasons) {
            showSeasons.add(season.toModelType());
        }

        if (super.getRunningTime() < 0) {
            throw new IllegalValueException(String.format(RunningTime.MESSAGE_CONSTRAINTS2));
        }

        if (super.getName() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(super.getName())) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(super.getName());

        if (super.getDateOfRelease() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(super.getDateOfRelease())) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDateOfRelease = new Date(super.getDateOfRelease());

        if (!IsWatched.isValidIsWatched(super.isWatched())) {
            throw new IllegalValueException(IsWatched.MESSAGE_CONSTRAINTS);
        }
        final IsWatched modelIsWatched = new IsWatched(super.isWatched());

        if (super.getDescription() == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(super.getDescription())) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(super.getDescription());

        if (super.getRunningTime() == 0) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, RunningTime.class.getSimpleName()));
        }
        if (!RunningTime.isValidRunningTime(super.getRunningTime() )) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final RunningTime modelRunningTime = new RunningTime(super.getRunningTime() );

        final Set<Actor> modelActors = new HashSet<>(showActors);

        TvShow show = new TvShow(modelName, modelDescription, modelIsWatched,
                modelDateOfRelease, modelRunningTime, modelActors,
                numOfEpisodesWatched, totalNumOfEpisodes, showSeasons);

        show.setType(super.getType());
        show.setPoster(new Poster(super.getPoster()));
        return show;
    }
}

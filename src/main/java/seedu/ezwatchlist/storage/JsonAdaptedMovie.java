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
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;

/**
 * Jackson-friendly version of {@link Movie}.
 */
public class JsonAdaptedMovie {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Show's %s field is missing!";

    private final String name;
    private final String type;
    private final String dateOfRelease;
    private final String isWatched;
    private final String description;
    private final int runningTime;
    private final String poster;
    private final List<JsonAdaptedActor> actors = new ArrayList<>();
    private final List<JsonAdaptedGenre> genres = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMovie} with the given show details.
     */
    @JsonCreator
    public JsonAdaptedMovie(@JsonProperty("name") String name,
                             @JsonProperty("type") String type,
                             @JsonProperty("dateOfRelease") String dateOfRelease,
                             @JsonProperty("watched") String isWatched,
                             @JsonProperty("description") String description,
                             @JsonProperty("runningTime") int runningTime,
                             @JsonProperty("actors") List<JsonAdaptedActor> actors,
                             @JsonProperty("poster") String poster,
                             @JsonProperty("genres") List<JsonAdaptedGenre> genres) {
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
        if (genres != null) {
            this.genres.addAll(genres);
        }
    }

    /**
     * Converts a given {@code Movie} into this class for Jackson use.
     */
    public JsonAdaptedMovie(Show source) {
        name = source.getName().showName;
        type = source.getType();
        dateOfRelease = source.getDateOfRelease().value;
        isWatched = Boolean.toString(source.isWatched().value);
        description = source.getDescription().fullDescription;
        runningTime = source.getRunningTime().value;
        actors.addAll(source.getActors().stream()
                .map(JsonAdaptedActor::new)
                .collect(Collectors.toList()));
        poster = source.getPoster().getImagePath();
        genres.addAll(source.getGenres().stream()
                .map(JsonAdaptedGenre::new)
                .collect(Collectors.toList()));
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

        final List<Genre> showGenres = new ArrayList<>();
        for (JsonAdaptedGenre genre : genres) {
            showGenres.add(genre.toModelType());
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

        if (!RunningTime.isValidRunningTime(runningTime)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final RunningTime modelRunningTime = new RunningTime(runningTime);

        final Set<Actor> modelActors = new HashSet<>(showActors);

        final Set<Genre> modelGenres = new HashSet<>(showGenres);

        Show show = new Movie(modelName, modelDescription, modelIsWatched,
                modelDateOfRelease, modelRunningTime, modelActors);

        show.setType(type);
        show.setPoster(new Poster(poster));
        show.addGenres(modelGenres);

        return show;
    }

    public String getName() {
        return this.name;
    }
}

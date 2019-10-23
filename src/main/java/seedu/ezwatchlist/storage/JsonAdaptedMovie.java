package seedu.ezwatchlist.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;

/**
 * Jackson-friendly version of {@link Movie}.
 */
class JsonAdaptedMovie extends JsonAdaptedShow {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Movie's %s field is missing!";

    /**
     * Constructs a {@code JsonAdaptedMovie} with the given movie details.
     */
    @JsonCreator
    public JsonAdaptedMovie(@JsonProperty("name") String name,
                            @JsonProperty("type") String type,
                            @JsonProperty("dateOfRelease") String dateOfRelease,
                            @JsonProperty("watched") boolean isWatched,
                            @JsonProperty("description") String description,
                            @JsonProperty("runningTime") int runningTime,
                            @JsonProperty("actors") List<JsonAdaptedActor> actors,
                            @JsonProperty("poster") String poster) {
        super(name, type, dateOfRelease, isWatched, description, runningTime, actors, poster);
    }

    /**
     * Converts a given {@code Movie} into this class for Jackson use.
     */
    public JsonAdaptedMovie(Show source) {
        super(source);
    }

    /**
     * Converts this Jackson-friendly adapted movie object into the model's {@code Movie} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted movie.
     */
    public Movie toModelType() throws IllegalValueException {
        final List<Actor> movieActors = new ArrayList<>();
        for (JsonAdaptedActor actor : super.getActors()) {
            movieActors.add(actor.toModelType());
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
        if (!RunningTime.isValidRunningTime(super.getRunningTime())) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final RunningTime modelRunningTime = new RunningTime(super.getRunningTime());

        final Set<Actor> modelActors = new HashSet<>(movieActors);
        Movie movie = new Movie(modelName, modelDescription, modelIsWatched,
                            modelDateOfRelease, modelRunningTime, modelActors);
        movie.setType(super.getType());
        movie.setPoster(new Poster(super.getPoster()));
        return movie;
    }
}
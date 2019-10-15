package seedu.ezwatchlist.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.actor.Actor;

/**
 * Jackson-friendly version of {@link Show}.
 */
class JsonAdaptedShow {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Show's %s field is missing!";

    private final String name;
    private final String dateOfRelease;
    private final boolean isWatched;
    private final String description;
    private final int runningTime;
    private final List<JsonAdaptedActor> actors = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedShow} with the given show details.
     */
    @JsonCreator
    public JsonAdaptedShow(@JsonProperty("name") String name,
                           @JsonProperty("dateOfRelease") String dateOfRelease,
                           @JsonProperty("watched") boolean isWatched,
                           @JsonProperty("description") String description,
                           @JsonProperty("runningTime") int runningTime,
                           @JsonProperty("actors") List<JsonAdaptedActor> actors) {
        this.name = name;
        this.dateOfRelease = dateOfRelease;
        this.isWatched = isWatched;
        this.description = description;
        this.runningTime = runningTime;
        if (actors != null) {
            this.actors.addAll(actors);
        }
    }

    /**
     * Converts a given {@code Show} into this class for Jackson use.
     */
    public JsonAdaptedShow(Show source) {
        name = source.getName().showName;
        dateOfRelease = source.getDateOfRelease().value;
        isWatched = source.isWatched().value;
        description = source.getDescription().fullDescription;
        runningTime = source.getRunningTime().value;
        actors.addAll(source.getActors().stream()
                .map(JsonAdaptedActor::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted show object into the model's {@code Show} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted show.
     */
    public Show toModelType() throws IllegalValueException {
        final List<Actor> showActors = new ArrayList<>();
        for (JsonAdaptedActor actor : actors) {
            showActors.add(actor.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (runningTime == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, RunningTime.class.getSimpleName()));
        }
        if (!RunningTime.isValidRunningTime(runningTime)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final RunningTime modelRunningTime = new RunningTime(runningTime);

        final Set<Actor> modelActors = new HashSet<>(showActors);
        return new Show(modelName, modelDescription, modelIsWatched, modelDateOfRelease, modelRunningTime, modelActors);
    }

}

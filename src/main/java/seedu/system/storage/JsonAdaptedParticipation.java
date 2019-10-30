package seedu.system.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.attempt.Attempt;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Gender;
import seedu.system.model.person.Name;
import seedu.system.model.person.Person;

/**
 * Jackson-friendly version of {@link Participation}.
 */
public class JsonAdaptedParticipation implements JsonAdaptedData<Participation> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Participation's %s field is missing!";
    private static final String PERSON_NOT_FOUND = "Person was not found in list";
    private static final String COMPETITION_NOT_FOUND = "Competition was not found in list";
    private static final CustomDate SAMPLE_DATE = new CustomDate("01/01/2019");
    private static final Gender SAMPLE_GENDER = Gender.MALE;
    private static final int OUT_OF_BOUND_INDEX = -1;

    private final String personName;
    private final String competitionName;
    private final List<String> attempts;

    /**
     * Constructs a {@code JsonAdaptedParticipation} with the given participation details.
     */
    @JsonCreator
    public JsonAdaptedParticipation(@JsonProperty("person") String personName,
                                    @JsonProperty("competition") String competitionName,
                                    @JsonProperty("attempts") List<String> attempts) {
        this.personName = personName;
        this.competitionName = competitionName;
        this.attempts = attempts;
    }

    /**
     * Converts a given {@code Participation} into this class for Jackson use.
     */
    public JsonAdaptedParticipation(Participation source) {
        personName = source.getPerson().getName().toString();
        competitionName = source.getCompetition().toString();
        attempts = new ArrayList<>();
        for (Attempt attempt : source.getAttempts()) {
            attempts.add(Attempt.getStringStorageFormOfAttempt(attempt));
        }
    }

    @Override
    public Participation toModelType() throws IllegalValueException {
        return null;
    }

    /**
     * Converts the stored data into the model's {@code Participation} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Participation toModelType(
        ReadOnlyData<Person> personReadOnlyData,
        ReadOnlyData<Competition> competitionReadOnlyData
    ) throws IllegalValueException {
        if (personName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        ObservableList<Person> personObservableList = personReadOnlyData.getListOfElements();
        Person personToFind = new Person(new Name(personName), SAMPLE_DATE, SAMPLE_GENDER);
        int personIndex = personObservableList.indexOf(personToFind);
        if (personIndex == OUT_OF_BOUND_INDEX) {
            throw new IllegalValueException(PERSON_NOT_FOUND);
        }
        final Person modelPerson = personObservableList.get(personIndex);

        if (competitionName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        ObservableList<Competition> competitionObservableList = competitionReadOnlyData.getListOfElements();
        Competition competitionToFind = new Competition(new Name(competitionName), SAMPLE_DATE, SAMPLE_DATE);
        int competitionIndex = competitionObservableList.indexOf(competitionToFind);
        if (competitionIndex == OUT_OF_BOUND_INDEX) {
            throw new IllegalValueException(COMPETITION_NOT_FOUND);
        }
        final Competition modelCompetition = competitionObservableList.get(competitionIndex);

        if (attempts == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Attempt.class.getSimpleName() + " " + List.class.getSimpleName()));
        }
        final List<Attempt> modelAttempts = new ArrayList<>();
        for (String str : attempts) {
            try {
                Attempt attempt = Attempt.parseStringToAttempt(str);
                modelAttempts.add(attempt);
            } catch (IOException exception) {
                throw new IllegalValueException(Attempt.MESSAGE_CONSTRAINTS);
            }
        }

        return new Participation(modelPerson, modelCompetition, modelAttempts);
    }



}

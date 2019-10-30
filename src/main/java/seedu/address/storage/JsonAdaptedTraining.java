package seedu.address.storage;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.core.JsonProcessingException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.Training;

/**
 * Jackson-friendly version of {@link Training}.
 */
public class JsonAdaptedTraining {
    private final String date;
    private final HashMap<String, Boolean> trainingAttendance;

    /**
     * Constructs a {@code JsonAdaptedTraining} with the given training details.
     */
    @JsonCreator
    public JsonAdaptedTraining(@JsonProperty("date") String date, @JsonProperty("trainingAttendance") HashMap<String,
            Boolean> trainingAttendance) {
        this.date = date;
        this.trainingAttendance = trainingAttendance;
    }

    /**
     * Converts a given {@code Training} into this class for Jackson use.
     */
    public JsonAdaptedTraining(Training training) {
        this.date = training.getDate().toString();
        HashMap<String, Boolean> tempHash = new HashMap<>();
        HashMap<Person, Boolean> trainingAttendance = training.getTrainingAttendance();
        trainingAttendance.forEach((person, hasAttended) -> {
            JsonAdaptedPerson newPerson = new JsonAdaptedPerson(person);
            try {
                String personJsonString = JsonUtil.toJsonString(newPerson);
                tempHash.put(personJsonString, hasAttended);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        this.trainingAttendance = tempHash;
    }

    /**
     * Converts this Jackson-friendly adapted training object into the model's {@code Training} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the converting jackson adapted
     * person to Person.
     */
    public Training toModelType() throws IllegalValueException {
        AthletickDate date = ParserUtil.parseDate(this.date);

        HashMap<Person, Boolean> tempHash = new HashMap<>();
        this.trainingAttendance.forEach((jsonPerson, hasAttended) -> {
            try {
                Person person = JsonUtil.fromJsonString(jsonPerson, JsonAdaptedPerson.class).toModelType();
                tempHash.put(person, hasAttended);
            } catch (IllegalValueException | IOException e) {
                e.printStackTrace();
            }
        });
        return new Training(date, tempHash);
    }
}

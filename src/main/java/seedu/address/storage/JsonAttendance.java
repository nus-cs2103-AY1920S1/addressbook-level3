package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Attendance;
import seedu.address.model.training.Training;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
public class JsonAttendance {
    private final List<JsonAdaptedTraining> jsonTrainings;

    /**
     * Constructs a {@code JsonAttendance} with the given attendance details.
     */
    @JsonCreator
    public JsonAttendance(@JsonProperty("jsonTrainings") List<JsonAdaptedTraining> jsonTrainings) {
        this.jsonTrainings = jsonTrainings;
    }

    /**
     * Converts a given {@code Attendance} into this class for Jackson use.
     */
    public JsonAttendance(Attendance attendance) {
        List<JsonAdaptedTraining> jsonTrainings = new ArrayList<>();
        List<Training> normalTrainings = attendance.getTrainings();
        for (Training training: normalTrainings) {
            JsonAdaptedTraining toAdd = new JsonAdaptedTraining(training);
            jsonTrainings.add(toAdd);
        }
        this.jsonTrainings = jsonTrainings;
    }

    /**
     * Converts this Jackson-friendly adapted training object into the model's {@code Training} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the converting jackson adapted
     * person to Person.
     */
    public Attendance toModelType() throws IllegalValueException {
        List<Training> trainings = new ArrayList<>();
        for (JsonAdaptedTraining jsonTraining: jsonTrainings) {
            trainings.add(jsonTraining.toModelType());
        }
        return new Attendance(trainings);
    }
}

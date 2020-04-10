package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TrainingManager;
import seedu.address.model.training.Training;

/**
 * Jackson-friendly version of {@link TrainingManager}.
 */
public class JsonTrainingManager {
    private final List<JsonAdaptedTraining> jsonTrainings;

    /**
     * Constructs a {@code JsonTrainingManager} with the given training manager details.
     */
    @JsonCreator
    public JsonTrainingManager(@JsonProperty("jsonTrainings") List<JsonAdaptedTraining> jsonTrainings) {
        this.jsonTrainings = jsonTrainings;
    }

    /**
     * Converts a given {@code TrainingManger} into this class for Jackson use.
     */
    public JsonTrainingManager(TrainingManager trainingManager) {
        List<JsonAdaptedTraining> jsonTrainings = new ArrayList<>();
        List<Training> normalTrainings = trainingManager.getTrainings();
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
    public TrainingManager toModelType() throws IllegalValueException {
        List<Training> trainings = new ArrayList<>();
        for (JsonAdaptedTraining jsonTraining: jsonTrainings) {
            trainings.add(jsonTraining.toModelType());
        }
        return new TrainingManager(trainings);
    }
}

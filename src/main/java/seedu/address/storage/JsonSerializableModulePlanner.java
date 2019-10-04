package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ModulePlanner;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.studyplan.StudyPlan;

/**
 * An Immutable ModulePlanner that is serializable to JSON format.
 */
@JsonRootName(value = "moduleplanner")
class JsonSerializableModulePlanner {

    public static final String MESSAGE_DUPLICATE_STUDY_PLAN = "Study Plan list contains duplicate study plan(s).";

    private final List<JsonAdaptedStudyPlan> studyPlans = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableModulePlanner} with the given study plans.
     */
    @JsonCreator
    public JsonSerializableModulePlanner(@JsonProperty("studyPlans") List<JsonAdaptedStudyPlan> studyPlans) {
        this.studyPlans.addAll(studyPlans);
    }

    /**
     * Converts a given {@code ReadOnlyModulePlanner} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableModulePlanner}.
     */
    public JsonSerializableModulePlanner(ReadOnlyModulePlanner source) {

        // TODO: change this after implementing ReadOnlyModulePlanner
        //studyPlans.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this module planner into the model's {@code ModulePlanner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModulePlanner toModelType() throws IllegalValueException {
        ModulePlanner modulePlanner = new ModulePlanner();
        for (JsonAdaptedStudyPlan jsonAdaptedStudyPlan : studyPlans) {
            StudyPlan studyPlan = jsonAdaptedStudyPlan.toModelType();
            // if (modulePlanner.hasPerson(person)) {
            //    throw new IllegalValueException(MESSAGE_DUPLICATE_STUDYPLAN);
            // }

            // TODO: change this after implementing ModulePlanner model
            //modulePlanner.addStudyPlan(studyPlan);
        }
        return modulePlanner;
    }

}

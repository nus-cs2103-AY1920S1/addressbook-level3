package cs.f10.t1.nursetraverse.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import javafx.util.Pair;

/**
 * An Immutable PatientBook that is serializable to JSON format.
 */
@JsonRootName(value = "patientBook")
public class JsonSerializablePatientBook {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s).";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();
    private final int ongoingVisitPatientIndex;
    private final int ongoingVisitIndex;
    /**
     * Constructs a {@code JsonSerializablePatientBook} with the given patients.
     */
    @JsonCreator
    public JsonSerializablePatientBook(
            @JsonProperty("patients") List<JsonAdaptedPatient> patients,
            @JsonProperty("ongoingVisitPatientIndex") Integer ongoingVisitPatientIndex,
            @JsonProperty("ongoingVisitIndex") Integer ongoingVisitIndex) {
        this.patients.addAll(patients);
        Integer resultingPatientIndex = ongoingVisitPatientIndex;
        Integer resultingVisitIndex = ongoingVisitIndex;
        if (ongoingVisitPatientIndex == null) {
            resultingPatientIndex = -1;
        }
        if (ongoingVisitIndex == null) {
            resultingVisitIndex = -1;
        }
        this.ongoingVisitPatientIndex = resultingPatientIndex;
        this.ongoingVisitIndex = resultingVisitIndex;
    }

    /**
     * Converts a given {@code ReadOnlyPatientBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePatientBook}.
     */
    public JsonSerializablePatientBook(ReadOnlyPatientBook source) {
        patients.addAll(source.getPatientList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
        Pair<Integer, Integer> indexPairOfCurrentPatientAndVisit = source.getIndexPairOfOngoingPatientAndVisit();
        this.ongoingVisitPatientIndex = indexPairOfCurrentPatientAndVisit.getKey();
        this.ongoingVisitIndex = indexPairOfCurrentPatientAndVisit.getValue();
    }

    /**
     * Converts this patient book into the model's {@code PatientBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PatientBook toModelType() throws IllegalValueException {
        PatientBook patientBook = new PatientBook();
        for (int i = 0; i < patients.size(); i++) {
            JsonAdaptedPatient jsonAdaptedPatient = patients.get(i);
            Patient patient = jsonAdaptedPatient.toModelType();
            if (patientBook.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            patientBook.addPatient(patient);
            if (ongoingVisitPatientIndex == i) {
                Optional<Visit> optionalVisit = patient.getVisitByIndex(ongoingVisitIndex);
                optionalVisit.ifPresent(patientBook::setOngoingVisit);
            }
        }
        return patientBook;
    }

}

package cs.f10.t1.nursetraverse.testutil;

import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.patient.Patient;

/**
 * A utility class to help with building PatientBook objects.
 * Example usage: <br>
 *     {@code PatientBook ab = new PatientBookBuilder().withPatient("John", "Doe").build();}
 */
public class PatientBookBuilder {

    private PatientBook patientBook;

    public PatientBookBuilder() {
        patientBook = new PatientBook();
    }

    public PatientBookBuilder(PatientBook patientBook) {
        this.patientBook = patientBook;
    }

    /**
     * Adds a new {@code Patient} to the {@code PatientBook} that we are building.
     */
    public PatientBookBuilder withPatient(Patient patient) {
        patientBook.addPatient(patient);
        return this;
    }

    public PatientBook build() {
        return patientBook;
    }
}

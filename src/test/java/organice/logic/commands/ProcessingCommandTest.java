package organice.logic.commands;

import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import org.junit.jupiter.api.Test;

import organice.model.person.Donor;
import organice.model.person.Patient;
import organice.testutil.DonorBuilder;
import organice.testutil.PatientBuilder;

class ProcessingCommandTest {

    private Patient patient = new PatientBuilder(PATIENT_IRENE).withName("john").build();
    private Donor donor = new DonorBuilder().build();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProcessingCommand(null, null));
    }

    @Test
    void isValidDonorPatientPair() {
    }

    @Test
    void execute() {
    }

    @Test
    void testEquals() {
    }
}

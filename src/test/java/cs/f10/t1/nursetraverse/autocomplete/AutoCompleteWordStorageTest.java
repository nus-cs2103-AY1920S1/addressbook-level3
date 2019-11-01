package cs.f10.t1.nursetraverse.autocomplete;

import static cs.f10.t1.nursetraverse.model.util.SampleDataUtil.collateVisits;
import static cs.f10.t1.nursetraverse.model.util.SampleDataUtil.getTagSet;
import static cs.f10.t1.nursetraverse.model.util.SampleDataUtil.getVisitTodos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Address;
import cs.f10.t1.nursetraverse.model.patient.Email;
import cs.f10.t1.nursetraverse.model.patient.Name;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.Phone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

class AutoCompleteWordStorageTest {
    private ObservableList<Patient> testStagedPatients = FXCollections.observableArrayList(
            new Patient(new Name("Joh"), new Phone("12345678"), new Email("joh@johrox.com"),
                    new Address("address007 lol"), getTagSet("colleagues"),
                    getVisitTodos("Check wounds"), collateVisits()));
    private ObservableList<Appointment> testStagedAppointments = FXCollections.observableArrayList();

    private FilteredList<Patient> testFilteredPatients =
            new FilteredList<>(FXCollections.unmodifiableObservableList(testStagedPatients));
    private FilteredList<Appointment> testFilteredAppointments =
            new FilteredList<>(FXCollections.unmodifiableObservableList(testStagedAppointments));

    private AutoCompleteWordStorage autoCompleteWordStorage =
            new AutoCompleteWordStorage(testFilteredPatients, testFilteredAppointments);

    @Test
    public void getOListAllCommandWord() {
        assertTrue(autoCompleteWordStorage.getOListAllCommandWord().size() != 0);
    }

    @Test
    public void getOListAllPrefixWord() {
        assertTrue(autoCompleteWordStorage.getOListAllPrefixWord().size() != 0);
    }

    @Test
    public void getOListAllObjectWord() {
        assertEquals(4, autoCompleteWordStorage.getOListAllObjectWord().size());
        assertTrue(autoCompleteWordStorage.getOListAllObjectWord().size() != 0);
    }

    @Test
    public void generateOListAllIndexWord() {
        ObservableList<AutoCompleteWord> patIndexList = autoCompleteWordStorage
                .generateOListAllIndexWord(new ObjectWord("pat"));
        ObservableList<AutoCompleteWord> apptIndexList = autoCompleteWordStorage
                .generateOListAllIndexWord(new ObjectWord("appt"));

        assertEquals(patIndexList.size(), testFilteredPatients.size());
        assertEquals(apptIndexList.size(), testFilteredAppointments.size());
    }
}

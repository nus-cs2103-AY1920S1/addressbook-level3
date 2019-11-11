package cs.f10.t1.nursetraverse.autocomplete;

import static cs.f10.t1.nursetraverse.model.util.SampleDataUtil.collateVisits;
import static cs.f10.t1.nursetraverse.model.util.SampleDataUtil.getTagSet;
import static cs.f10.t1.nursetraverse.model.util.SampleDataUtil.getVisitTodos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

import cs.f10.t1.nursetraverse.model.appointment.AutoCompleteWord;
import cs.f10.t1.nursetraverse.model.autocomplete.CommandWord;
import cs.f10.t1.nursetraverse.model.autocomplete.IndexWord;
import cs.f10.t1.nursetraverse.model.autocomplete.ObjectWord;
import cs.f10.t1.nursetraverse.model.autocomplete.PrefixWord;
import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Address;
import cs.f10.t1.nursetraverse.model.patient.Email;
import cs.f10.t1.nursetraverse.model.patient.Name;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.Phone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

class AutoCompleteListHandlerTest {
    private ObservableList<Patient> testStagedPatients = FXCollections.observableArrayList(
            new Patient(new Name("Joh"), new Phone("12345678"), new Email("joh@johrox.com"),
                    new Address("address007 lol"), getTagSet("colleagues"),
                    getVisitTodos("Check wounds"), collateVisits()));
    private ObservableList<Appointment> testStagedAppointments = FXCollections.observableArrayList();

    private FilteredList<Patient> testFilteredPatients =
            new FilteredList<>(FXCollections.unmodifiableObservableList(testStagedPatients));
    private FilteredList<Appointment> testFilteredAppointments =
            new FilteredList<>(FXCollections.unmodifiableObservableList(testStagedAppointments));
    private ObservableList<HistoryRecord> testHistoryList = FXCollections.observableArrayList();

    private AutoCompleteWordStorage autoCompleteWordStorage =
            new AutoCompleteWordStorage(testFilteredPatients, testFilteredAppointments, testHistoryList);
    private AutoCompleteListHandler autoCompleteListHandler = new AutoCompleteListHandler(autoCompleteWordStorage);

    @Test
    public void chooseList() {
        LinkedList<AutoCompleteWord> matchedWordLinkedList = new LinkedList<>();
        ObservableList<AutoCompleteWord> chosenList = autoCompleteListHandler.chooseList(matchedWordLinkedList);
        assertEquals(0, matchedWordLinkedList.size());
        assertTrue(chosenList.get(0) instanceof ObjectWord);

        matchedWordLinkedList.add(new ObjectWord("pat", "test description 1"));
        chosenList = autoCompleteListHandler.chooseList(matchedWordLinkedList);
        assertEquals(1, matchedWordLinkedList.size());
        assertTrue(chosenList.get(0) instanceof CommandWord);

        matchedWordLinkedList.add(new CommandWord("pat", "edit",
                "test description 1", true, true));
        chosenList = autoCompleteListHandler.chooseList(matchedWordLinkedList);
        assertEquals(2, matchedWordLinkedList.size());
        assertTrue(chosenList.get(0) instanceof IndexWord);

        matchedWordLinkedList.add(new IndexWord("1", "test description 1"));
        chosenList = autoCompleteListHandler.chooseList(matchedWordLinkedList);
        assertEquals(3, matchedWordLinkedList.size());
        assertTrue(chosenList.get(0) instanceof PrefixWord);

        matchedWordLinkedList.add(new PrefixWord("pat", "edit",
                "test/", "test description 1"));
        chosenList = autoCompleteListHandler.chooseList(matchedWordLinkedList);
        assertEquals(4, matchedWordLinkedList.size());
        assertTrue(chosenList.get(0) instanceof PrefixWord);
    }

    /*@Test
    public void updateList() {
        LinkedList<AutoCompleteWord> matchedWordLinkedList = new LinkedList<>();
        matchedWordLinkedList.add(new ObjectWord("pat"));
        matchedWordLinkedList.add(new ObjectWord("edit"));
        autoCompleteListHandler.updateList();
    }

    @Test
    public void filterList() {
    }

    @Test
    public void addDashToObjectWordList() {
    }*/
}

package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalWorkers.getTypicalWorkers;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysWorker;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import guitests.guihandles.WorkerCardHandle;
import guitests.guihandles.WorkerListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Name;

//@@author shaoyi1997-reused
//Reused from SE-EDU Address Book Level 4 with modifications
public class WorkerListPanelTest extends GuiUnitTest {
    private static final ObservableList<Worker> TYPICAL_WORKERS =
            FXCollections.observableList(getTypicalWorkers());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Worker> selectedWorker = new SimpleObjectProperty<>();
    private WorkerListPanelHandle workerListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_WORKERS);

        for (int i = 0; i < TYPICAL_WORKERS.size(); i++) {
            workerListPanelHandle.navigateToCard(TYPICAL_WORKERS.get(i));
            Worker expectedWorker = TYPICAL_WORKERS.get(i);
            WorkerCardHandle actualCard = workerListPanelHandle.getWorkerCardHandle(i);
            assertCardDisplaysWorker(expectedWorker, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * Verifies that creating and deleting large number of workers in {@code WorkerListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Worker> backingList = createBackingList(1000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of workers cards exceeded time limit");
    }

    /**
     * Returns a list of workers containing {@code workerCount} workers that is used to populate the
     * {@code workerListPanel}.
     */
    private ObservableList<Worker> createBackingList(int workerCount) {
        ObservableList<Worker> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < workerCount; i++) {
            Name name = new Name(i + "a");
            PhoneNumber phone = new PhoneNumber("91234567");
            Date dateJoined = Calendar.getInstance().getTime();
            Date dateOfBirth = Calendar.getInstance().getTime();
            String designation = "coroner";
            String employmentStatus = "on B00000001";
            Worker worker = new Worker(name, phone, Sex.MALE, employmentStatus, dateOfBirth, dateJoined, designation,
                    null);
            backingList.add(worker);
        }
        return backingList;
    }

    /**
     * Initializes {@code workerListPanelHandle} with a {@code WorkerListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code WorkerListPanel}.
     */
    private void initUi(ObservableList<Worker> backingList) {
        WorkerListPanel workerListPanel =
                new WorkerListPanel(backingList);
        uiPartExtension.setUiPart(workerListPanel);

        workerListPanelHandle = new WorkerListPanelHandle(getChildNode(workerListPanel.getRoot(),
                WorkerListPanelHandle.WORKER_LIST_VIEW_ID));
    }
}
//@@author

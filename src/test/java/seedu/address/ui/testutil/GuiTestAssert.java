package seedu.address.ui.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import guitests.guihandles.FridgeCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.WorkerCardHandle;
import guitests.guihandles.WorkerListPanelHandle;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.ui.WorkerCard;

//@@author shaoyi1997-reused
//Reused from SE-EDU Address Book Level 4
/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(WorkerCardHandle expectedCard, WorkerCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getWorkerId(), actualCard.getWorkerId());
        assertEquals(expectedCard.getSex(), actualCard.getSex());
        assertEquals(expectedCard.getDateJoined(), actualCard.getDateJoined());
        assertEquals(expectedCard.getDateJoined(), actualCard.getDateOfBirth());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getEmploymentStatus(), actualCard.getEmploymentStatus());
        assertEquals(expectedCard.getDesignation(), actualCard.getDesignation());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedWorker}.
     */
    public static void assertCardDisplaysWorker(Worker expectedWorker, WorkerCardHandle actualCard) {
        assertEquals(expectedWorker.getName().toString(), actualCard.getName());
        assertEquals(expectedWorker.getSex().toString(), actualCard.getSex());
        assertEquals(WorkerCard.formatDate(expectedWorker.getDateJoined()), actualCard.getDateJoined());
        assertEquals(expectedWorker.getIdNum().toString(), actualCard.getWorkerId());
        assertEquals(expectedWorker.getPhone().isPresent() ? expectedWorker.getPhone().get().toString() : "-",
                actualCard.getPhone());
        assertEquals(expectedWorker.getDateOfBirth().isPresent()
                ? WorkerCard.formatDate(expectedWorker.getDateOfBirth().get()) : "-", actualCard.getDateOfBirth());
        assertEquals(expectedWorker.getDesignation().isPresent() ? expectedWorker.getDesignation().get()
                : "-", actualCard.getDesignation());
        assertEquals(expectedWorker.getEmploymentStatus().isPresent()
                ? expectedWorker.getEmploymentStatus().get() : "-", actualCard.getEmploymentStatus());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedFridge}.
     */
    public static void assertCardDisplaysFridge(Fridge expectedFridge, FridgeCardHandle actualCard) {
        assertEquals(expectedFridge.getIdNum().toString(), actualCard.getFridgeId());
        assertEquals(expectedFridge.getBody().isPresent() ? expectedFridge.getBody().get().getIdNum().toString()
                : "No body assigned", actualCard.getBodyId());
        assertEquals(expectedFridge.getFridgeStatus().toString(), actualCard.getStatus());
    }

    /**
     * Asserts that the list in {@code workerListPanelHandle} displays the details of {@code workers} correctly and
     * in the correct order.
     */
    public static void assertListMatching(WorkerListPanelHandle workerListPanelHandle, Worker... workers) {
        for (int i = 0; i < workers.length; i++) {
            workerListPanelHandle.navigateToCard(i);
            assertCardDisplaysWorker(workers[i], workerListPanelHandle.getWorkerCardHandle(i));
        }
        return;
    }

    /**
     * Asserts that the list in {@code workerListPanelHandle} displays the details of {@code workers} correctly and
     * in the correct order.
     */
    public static void assertListMatching(WorkerListPanelHandle workerListPanelHandle, List<Worker> workers) {
        assertListMatching(workerListPanelHandle, workers.toArray(new Worker[0]));
    }

    /**
     * Asserts the size of the list in {@code workerListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(WorkerListPanelHandle workerListPanelHandle, int size) {
        int numberOfWorkers = workerListPanelHandle.getListSize();
        assertEquals(size, numberOfWorkers);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
//@@author

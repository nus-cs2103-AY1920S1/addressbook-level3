package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysWorker;

import org.junit.jupiter.api.Test;

import guitests.guihandles.WorkerCardHandle;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;
import seedu.address.model.entity.worker.Worker;
import seedu.address.testutil.WorkerBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Worker workerWithNoTags = new WorkerBuilder().build();
        WorkerCard workerCard = new WorkerCard(workerWithNoTags, 1);
        uiPartExtension.setUiPart(workerCard);
        assertCardDisplay(workerCard, workerWithNoTags, 1);
    }

    @Test
    public void equals() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Worker worker = new WorkerBuilder().build();
        WorkerCard workerCard = new WorkerCard(worker, 0);

        // same person, same index -> returns true
        WorkerCard copy = new WorkerCard(worker, 0);
        assertTrue(workerCard.equals(copy));

        // same object -> returns true
        assertTrue(workerCard.equals(workerCard));

        // null -> returns false
        assertFalse(workerCard.equals(null));

        // different types -> returns false
        assertFalse(workerCard.equals(0));

        // different person, same index -> returns false
        Worker differentWorker = new WorkerBuilder().withName("differentName").build();
        assertFalse(workerCard.equals(new WorkerCard(differentWorker, 0)));

        // same person, different index -> returns false
        assertFalse(workerCard.equals(new WorkerCard(worker, 1)));
    }

    /**
     * Asserts that {@code workerCard} displays the details of {@code expectedWorker} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(WorkerCard workerCard, Worker expectedWorker, int expectedId) {
        guiRobot.pauseForHuman();

        WorkerCardHandle workerCardHandle = new WorkerCardHandle(workerCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", workerCardHandle.getId());

        // verify worker details are displayed correctly
        assertCardDisplaysWorker(expectedWorker, workerCardHandle);
    }
}

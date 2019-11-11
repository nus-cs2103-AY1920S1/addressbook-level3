package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.worker.Worker;
import seedu.address.testutil.WorkerBuilder;

class WorkerCardTest extends GuiUnitTest {

    @Test
    void equals_sameWorker_true() {
        Worker worker = new WorkerBuilder().build();
        WorkerCard workerCard = new WorkerCard(worker, 1);
        Worker otherWorker = worker;
        WorkerCard otherWorkerCard = new WorkerCard(otherWorker, 1);
        assertTrue(workerCard.equals(otherWorkerCard));
    }

    @Test
    void equals_differentFridge_false() {
        Worker worker = new WorkerBuilder().build();
        WorkerCard workerCard = new WorkerCard(worker, 1);
        Worker otherWorker = new WorkerBuilder().withName("Different name").build();
        WorkerCard otherWorkerCard = new WorkerCard(otherWorker, 1);
        assertFalse(workerCard.equals(otherWorkerCard));
    }
}

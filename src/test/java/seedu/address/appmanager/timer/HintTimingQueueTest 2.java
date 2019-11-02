package seedu.address.appmanager.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import seedu.address.model.appsettings.DifficultyEnum;

class HintTimingQueueTest {

    @Test
    public void pollNextTimeToUpdate_queueSizeZero_throwsNullPointerException() {
        HintTimingQueue testQueue = new HintTimingQueue(0, 1000);
        assertThrows(NullPointerException.class, () -> {
            testQueue.pollNextTimeToUpdate();
        });
    }

    @Test
    public void pollNextTimeToUpdate_queueSizeIsOne() {
        HintTimingQueue testQueue = new HintTimingQueue(1, 1000);
        assertTrue(testQueue.pollNextTimeToUpdate() == 50L);
    }

    @Test
    public void isEmpty() {
        HintTimingQueue testQueue = new HintTimingQueue(2, 5000);
        assertFalse(testQueue.isEmpty());

        testQueue.pollNextTimeToUpdate();
        assertFalse(testQueue.isEmpty());

        testQueue.pollNextTimeToUpdate();
        assertTrue(testQueue.isEmpty());
    }

    @Test
    void pollNextTimeToUpdate() {
        HintTimingQueue testQueue = new HintTimingQueue(7, 5000);
        long expectedDelta = (long) (DifficultyEnum.EASY.getTimeAllowedPerQuestion() * 0.75) / (7 - 1);
        Queue<Long> expectedTimings = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            long currentTiming = (long) (5000 * 0.75) - (i * expectedDelta);
            if (currentTiming <= 0) {
                expectedTimings.add(50L); // Last Hint Shown at 50ms
            } else {
                expectedTimings.add(currentTiming);
            }
        }

        for (int i = 0; i < 7; i++) {
            assertEquals(expectedTimings.poll(), testQueue.pollNextTimeToUpdate());
        }

    }
}

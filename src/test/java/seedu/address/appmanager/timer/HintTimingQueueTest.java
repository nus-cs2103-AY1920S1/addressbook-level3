package seedu.address.appmanager.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import seedu.address.model.appsettings.DifficultyEnum;

/**
 * Test class to check operations relating to HintTimingQueue.
 *
 * @@author kohyida1997
 */
class HintTimingQueueTest {

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
    public void pollNextTimeToUpdate_queueSizeMax() {
        assertTimingsUniqueAndCorrect(80, 15000);
    }

    @Test
    public void pollNextTimeToUpdate_queueSize79() {
        assertTimingsUniqueAndCorrect(79, 15000);
    }

    @Test
    public void pollNextTimeToUpdate_queueSizeMax_hardDifficulty() {
        assertTimingsUniqueAndCorrect(80, 10000);
    }

    @Test
    public void pollNextTimeToUpdate_queueSize79_hardDifficulty() {
        assertTimingsUniqueAndCorrect(70, 10000);
    }

    @Test
    public void pollNextTimeToUpdate_queueSizeZero_throwsNullPointerException() {
        HintTimingQueue testQueue = new HintTimingQueue(0, 1000);
        assertThrows(NullPointerException.class, () -> {
            testQueue.pollNextTimeToUpdate();
        });
    }

    @Test
    public void pollNextTimeToUpdate_queueSizeIsOne() {
        HintTimingQueue testQueue = new HintTimingQueue(1, 700);
        assertTrue(testQueue.pollNextTimeToUpdate() == 50L);
    }

    @Test
    public void pollNextTimeToUpdate_queueSizeIsTwo() {
        assertTimingsUniqueAndCorrect(2, 5000);
    }

    @Test
    public void pollNextTimeToUpdate_queueSize40() {
        assertTimingsUniqueAndCorrect(40, 5000);
    }

    /**
     * Utility method to check timestamps to be unique and matching based on {@code wordLen} and
     * {@code timeAllowedPerQuestion} specified.
     */
    private void assertTimingsUniqueAndCorrect(int wordLen, long timeAllowedPerQuestion) {
        HintTimingQueue testQueue = new HintTimingQueue(wordLen, timeAllowedPerQuestion);
        long expectedDelta = (long) (DifficultyEnum.EASY.getTimeAllowedPerQuestion() * 0.75) / (wordLen - 1);

        Queue<Long> expectedTimings = new LinkedList<>();

        for (int i = 0; i < wordLen; i++) {
            long currentTiming = (long) (timeAllowedPerQuestion * 0.75) - (i * expectedDelta);
            currentTiming = (currentTiming / 50) * 50;

            if (currentTiming <= 0) {
                expectedTimings.add(50L); // Last Hint Shown at 50ms
            } else {
                expectedTimings.add(currentTiming);
            }
        }

        ArrayList<Long> listOfTimeStamps = new ArrayList<>();
        for (int i = 0; i < wordLen; i++) {
            long currTiming = expectedTimings.poll();
            listOfTimeStamps.add(currTiming);
            assertEquals(currTiming, testQueue.pollNextTimeToUpdate());
        }

        /* Assert that all timings are unique except for Hints that cannot be displayed */
        assertEquals(listOfTimeStamps.stream().filter(x -> x != 50L).distinct().count()
                        + listOfTimeStamps.stream().filter(x -> x == 50L).count(),
                wordLen);
    }
}

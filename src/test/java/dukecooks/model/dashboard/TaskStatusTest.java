package dukecooks.model.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.TASK1;
import static dukecooks.testutil.dashboard.TypicalDashboard.TASK2;
import static dukecooks.testutil.dashboard.TypicalDashboard.TASK7;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskStatusTest {

    @Test
    public void isRecentlyDoneStatus_true() {
        assertTrue(TASK7.getTaskStatus().getRecentlyDoneStatus());
    }

    @Test
    public void isRecentlyDoneStatus_false() {
        assertFalse(TASK1.getTaskStatus().getRecentlyDoneStatus());
    }

    @Test
    public void isDoneStatus_trueForRecentlyCompleted() {
        assertTrue(TASK7.getTaskStatus().getDoneStatus());
    }

    @Test
    public void isDoneStatus_trueForCompleted() {
        assertTrue(TASK1.getTaskStatus().getDoneStatus());
    }

    @Test
    public void isDoneStatus_falseForNotComplete() {
        assertFalse(TASK2.getTaskStatus().getDoneStatus());
    }

    @Test
    public void isNotDoneStatus_trueForNotComplete() {
        assertTrue(TASK2.getTaskStatus().getNotDoneStatus());
    }

    @Test
    public void isNotDoneStatus_falseForRecentlyCompleted() {
        assertFalse(TASK7.getTaskStatus().getNotDoneStatus());
    }

    @Test
    public void isNotDoneStatus_falseForCompleted() {
        assertFalse(TASK1.getTaskStatus().getNotDoneStatus());
    }
}

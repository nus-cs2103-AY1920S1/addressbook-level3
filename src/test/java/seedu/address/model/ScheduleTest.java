package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ScheduleTest {

    @Test
    public void getFirstRow_skeleton_true() {
        assertNotNull(new Schedule().getFirstRow());
    }

    @Test
    public void getRow_indexSkeleton_true() {
        assertNotNull(new Schedule().getRow(0));
    }

    @Test
    public void getRow_timingSkeleton_true() {
        assertNotNull(new Schedule().getRow("26/10/2019 6:00pm-6:30pm"));
    }

    @Test
    public void getColumn_indexSkeleton_true() {
        assertNotNull(new Schedule().getColumn(0));
    }

    @Test
    public void getColumn_interviewerDescSkeleton_true() {
        assertNotNull(new Schedule().getColumn("26/10/2019 6:00pm-6:30pm"));
    }

    @Test
    public void deleteColumn_indexSkeleton_true() {
        assertNotNull(new Schedule().deleteColumn(0));
    }

    @Test
    public void deleteColumn_interviewerDescSkeleton_true() {
        assertNotNull(new Schedule().deleteColumn("26/10/2019 6:00pm-6:30pm"));
    }
}

package seedu.scheduler.logic.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.SampleGraph;
import seedu.scheduler.testutil.SampleInterviewSlotVertex;
import seedu.scheduler.testutil.TestUtil;

public class BfsHopcroftKarpTest {
    // ====================================== Integration Testing ===================================================
    @Test
    public void bfs_sampleGraphOneAfterOneIteration_success() {
        BipartiteGraph subjectGraph = SampleGraph.getSampleGraphOne();
        int numInterviewees = subjectGraph.getNumInterviewees();
        int numSlots = subjectGraph.getNumInterviewSlots();

        List<InterviewerSlotVertex> intervieweePredecessor = Arrays.asList(new InterviewerSlotVertex[numInterviewees]);
        List<List<IntervieweeVertex>> interviewSlotPredecessors = new ArrayList<>(numSlots);
        TestUtil.fillWithSubLists(interviewSlotPredecessors, numSlots);

        List<InterviewerSlotVertex> expectedVertices = SampleInterviewSlotVertex.getSampleInterviewSlotVerticesGraph1();
        List<InterviewerSlotVertex> resultVertices = new BfsHopcroftKarp(subjectGraph).execute(intervieweePredecessor,
            interviewSlotPredecessors);

        // resultVertices.forEach(System.out::println);
        assertEquals(resultVertices, expectedVertices);
    }
}

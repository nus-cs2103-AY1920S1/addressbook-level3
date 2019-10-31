package seedu.address.logic.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SampleGraph;
import seedu.address.testutil.SampleInterviewSlotVertex;
import seedu.address.testutil.TestUtil;

public class BfsHopCroftKarpTest {
    @Test
    public void bfs_sampleGraphOneAfterOneIteration_success() {
        BipartiteGraph subjectGraph = SampleGraph.getSampleGraphOne();
        int numInterviewees = subjectGraph.getNumInterviewees();
        int numSlots = subjectGraph.getNumInterviewSlots();

        List<InterviewSlotVertex> intervieweePredecessor = Arrays.asList(new InterviewSlotVertex[numInterviewees]);
        List<List<IntervieweeVertex>> interviewSlotPredecessors = new ArrayList<>(numSlots);
        TestUtil.fillWithSubLists(interviewSlotPredecessors, numSlots);

        List<InterviewSlotVertex> expectedVertices = SampleInterviewSlotVertex.getSampleInterviewSlotVerticesGraph1();
        List<InterviewSlotVertex> resultVertices = new BfsHopCroftKarp(subjectGraph).execute(intervieweePredecessor,
            interviewSlotPredecessors);

        // resultVertices.forEach(System.out::println);
        assertEquals(resultVertices, expectedVertices);
    }
}

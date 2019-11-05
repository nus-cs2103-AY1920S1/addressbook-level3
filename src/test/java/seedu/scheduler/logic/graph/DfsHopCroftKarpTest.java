package seedu.scheduler.logic.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.SampleGraph;
import seedu.scheduler.testutil.TestUtil;

class DfsHopCroftKarpTest {
    /**
     * Integration testing of bfs and dfs.
     */
    @Test
    public void execute_sampleGraph1After1Iteration_success() {
        BipartiteGraph subjectGraph = SampleGraph.getSampleGraphOne();
        int numInterviewees = subjectGraph.getNumInterviewees();
        int numSlots = subjectGraph.getNumInterviewSlots();

        List<InterviewerSlotVertex> intervieweePredecessor = Arrays.asList(new InterviewerSlotVertex[numInterviewees]);
        List<List<IntervieweeVertex>> interviewSlotPredecessors = new ArrayList<>(numSlots);
        boolean[] isUsedInterviewee = new boolean[numInterviewees];
        boolean[] isUsedSlot = new boolean[numSlots];
        TestUtil.fillWithSubLists(interviewSlotPredecessors, numSlots);

        List<InterviewerSlotVertex> lastLayer = new BfsHopCroftKarp(subjectGraph).execute(intervieweePredecessor,
                interviewSlotPredecessors);
        DfsHopCroftKarp dfs = new DfsHopCroftKarp(subjectGraph);
        dfs.execute(lastLayer, intervieweePredecessor, interviewSlotPredecessors, isUsedInterviewee, isUsedSlot);

        // Check the graph
        int numMatched = 0;
        for (int i = 0; i < numInterviewees; i++) {
            IntervieweeVertex intervieweeVertex = subjectGraph.getIntervieweePair(i).getHead();
            if (intervieweeVertex.isMatched()) {
                // System.out.printf("%s: allocated slot: %s\n", intervieweeVertex.getItem().getName(),
                //    intervieweeVertex.getPartner().getItem());
                assertTrue(isUsedInterviewee[intervieweeVertex.getIndex()]);
                assertTrue(isUsedSlot[intervieweeVertex.getPartner().getIndex()]);
                numMatched++;
            }
        }

        // For the case of sample graph one, there must be at least 3 interviewee vertices that are matched after
        // one iteration of bfs and dfs.
        assertTrue(numMatched > 2);
    }
}

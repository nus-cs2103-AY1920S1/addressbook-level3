package seedu.address.logic.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SampleGraph;

class HopCroftKarpTest {
    @Test
    public void execute_sampleGraph1_allMatched() {
        BipartiteGraph subjectGraph = SampleGraph.getSampleGraphOne();
        HopCroftKarp algorithm = new HopCroftKarp(subjectGraph);
        algorithm.execute();

        int numInterviewees = subjectGraph.getNumInterviewees();
        IntStream.range(0, numInterviewees).forEach(i -> {
            IntervieweeVertex intervieweeVertex = subjectGraph.getIntervieweePair(i).getHead();
            assertTrue(intervieweeVertex.isMatched());
            // System.out.println(intervieweeVertex.getPartner().getItem()); // TODO: comment this out!
        });
    }
}

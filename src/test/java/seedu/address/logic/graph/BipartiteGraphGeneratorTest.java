package seedu.address.logic.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.testutil.SampleGraph;
import seedu.address.testutil.SampleInterviewee;
import seedu.address.testutil.SampleInterviewer;

class BipartiteGraphGeneratorTest {
    @Test
    public void getGraph_sampleDataFromGraphOne_returnSampleGraphOne() {
        List<Interviewer> interviewers = SampleInterviewer.getSampleInterviewersForGraph1();
        List<Interviewee> interviewees = SampleInterviewee.getSampleIntervieweesForGraph1();
        BipartiteGraphGenerator generator = new BipartiteGraphGenerator(interviewers, interviewees);

        BipartiteGraph expectedGraph = SampleGraph.getSampleGraphOne();
        BipartiteGraph resultGraph = generator.generate();

        assertNotSame(expectedGraph, resultGraph);
        assertEquals(expectedGraph, resultGraph);
    }
}

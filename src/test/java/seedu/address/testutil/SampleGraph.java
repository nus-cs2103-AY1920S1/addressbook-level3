package seedu.address.testutil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.util.Pair;
import seedu.address.logic.graph.BipartiteGraph;
import seedu.address.logic.graph.InterviewSlotVertex;
import seedu.address.logic.graph.IntervieweeVertex;
import seedu.address.model.person.Interviewee;

/**
 * Provides sample bipartite graphs of interviewees and interview tine slots.
 */
public class SampleGraph {
    public static BipartiteGraph getSampleGraphOne() {
        List<Interviewee> interviewees = SampleInterviewee.getSampleIntervieweesForGraph1();
        List<InterviewSlotVertex> sampleSlotsVertices =
                SampleInterviewSlotVertex.getSampleInterviewSlotVerticesGraph1();

        LinkedList<InterviewSlotVertex> list0 = new LinkedList<>();
        list0.add(sampleSlotsVertices.get(0));
        IntervieweeVertex interviewee0 = new IntervieweeVertex(interviewees.get(0), 0);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex0 = new Pair<>(interviewee0, list0);

        LinkedList<InterviewSlotVertex> list1 = new LinkedList<>();
        list1.add(sampleSlotsVertices.get(0));
        list1.add(sampleSlotsVertices.get(1));
        list1.add(sampleSlotsVertices.get(2));
        IntervieweeVertex interviewee1 = new IntervieweeVertex(interviewees.get(1), 1);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex1 = new Pair<>(interviewee1, list1);

        LinkedList<InterviewSlotVertex> list2 = new LinkedList<>();
        list2.add(sampleSlotsVertices.get(0));
        list2.add(sampleSlotsVertices.get(3));
        IntervieweeVertex interviewee2 = new IntervieweeVertex(interviewees.get(2), 2);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex2 = new Pair<>(interviewee2, list2);

        LinkedList<InterviewSlotVertex> list3 = new LinkedList<>();
        list3.add(sampleSlotsVertices.get(1));
        list3.add(sampleSlotsVertices.get(4));
        IntervieweeVertex interviewee3 = new IntervieweeVertex(interviewees.get(3), 3);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex3 = new Pair<>(interviewee3, list3);

        LinkedList<InterviewSlotVertex> list4 = new LinkedList<>();
        list4.add(sampleSlotsVertices.get(2));
        list4.add(sampleSlotsVertices.get(3));
        IntervieweeVertex interviewee4 = new IntervieweeVertex(interviewees.get(4), 4);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex4 = new Pair<>(interviewee4, list4);

        List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> graph = new ArrayList<>();
        graph.add(vertex0);
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);

        return new BipartiteGraph(graph, interviewees.size(), 5);
    }
}

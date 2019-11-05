package seedu.scheduler.testutil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import seedu.scheduler.commons.util.Pair;
import seedu.scheduler.logic.graph.BipartiteGraph;
import seedu.scheduler.logic.graph.IntervieweeVertex;
import seedu.scheduler.logic.graph.InterviewerSlotVertex;
import seedu.scheduler.model.person.Interviewee;

/**
 * Provides sample bipartite graphs of interviewees and interview tine slots.
 */
public class SampleGraph {
    public static BipartiteGraph getSampleGraphOne() {
        List<Interviewee> interviewees = SampleInterviewee.getSampleIntervieweesForGraph1();
        List<InterviewerSlotVertex> sampleSlotsVertices =
                SampleInterviewSlotVertex.getSampleInterviewSlotVerticesGraph1();

        LinkedList<InterviewerSlotVertex> list0 = new LinkedList<>();
        list0.add(sampleSlotsVertices.get(0));
        IntervieweeVertex interviewee0 = new IntervieweeVertex(interviewees.get(0), 0);
        Pair<IntervieweeVertex, List<InterviewerSlotVertex>> vertex0 = new Pair<>(interviewee0, list0);

        LinkedList<InterviewerSlotVertex> list1 = new LinkedList<>();
        list1.add(sampleSlotsVertices.get(0));
        list1.add(sampleSlotsVertices.get(1));
        list1.add(sampleSlotsVertices.get(2));
        IntervieweeVertex interviewee1 = new IntervieweeVertex(interviewees.get(1), 1);
        Pair<IntervieweeVertex, List<InterviewerSlotVertex>> vertex1 = new Pair<>(interviewee1, list1);

        LinkedList<InterviewerSlotVertex> list2 = new LinkedList<>();
        list2.add(sampleSlotsVertices.get(0));
        list2.add(sampleSlotsVertices.get(3));
        IntervieweeVertex interviewee2 = new IntervieweeVertex(interviewees.get(2), 2);
        Pair<IntervieweeVertex, List<InterviewerSlotVertex>> vertex2 = new Pair<>(interviewee2, list2);

        LinkedList<InterviewerSlotVertex> list3 = new LinkedList<>();
        list3.add(sampleSlotsVertices.get(1));
        list3.add(sampleSlotsVertices.get(4));
        IntervieweeVertex interviewee3 = new IntervieweeVertex(interviewees.get(3), 3);
        Pair<IntervieweeVertex, List<InterviewerSlotVertex>> vertex3 = new Pair<>(interviewee3, list3);

        LinkedList<InterviewerSlotVertex> list4 = new LinkedList<>();
        list4.add(sampleSlotsVertices.get(2));
        list4.add(sampleSlotsVertices.get(3));
        IntervieweeVertex interviewee4 = new IntervieweeVertex(interviewees.get(4), 4);
        Pair<IntervieweeVertex, List<InterviewerSlotVertex>> vertex4 = new Pair<>(interviewee4, list4);

        List<Pair<IntervieweeVertex, List<InterviewerSlotVertex>>> graph = new ArrayList<>();
        graph.add(vertex0);
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);

        return new BipartiteGraph(graph, interviewees.size(), 5);
    }
}

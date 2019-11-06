package seedu.scheduler.logic.graph;

import java.util.List;

import seedu.scheduler.commons.util.Pair;

/**
 * Encapsulates a bipartite graph of interviewees vertices and interview slot vertices. Serves as a container
 * for the underlying list to store some additional information.
 */
public class BipartiteGraph {
    private List<Pair<IntervieweeVertex, List<InterviewerSlotVertex>>> graph;
    private int numInterviewees;
    private int numInterviewSlots;

    public BipartiteGraph(List<Pair<IntervieweeVertex, List<InterviewerSlotVertex>>> graph,
            int numInterviewees, int numInterviewSlots) {
        this.graph = graph;
        this.numInterviewees = numInterviewees;
        this.numInterviewSlots = numInterviewSlots;
    }

    public int getNumInterviewees() {
        return numInterviewees;
    }

    public int getNumInterviewSlots() {
        return numInterviewSlots;
    }

    /**
     * Returns the pair of intervieweeVertex and list of associated interview slots vertices based on the
     * given @code{index} of the intervieweeVertex.
     */
    public Pair<IntervieweeVertex, List<InterviewerSlotVertex>> getIntervieweePair(int index) {
        return graph.get(index);
    }

    /**
     * Returns the list of interview slots associated with the interviewee vertex of @code{index}.
     */
    public List<InterviewerSlotVertex> getInterviewSlotVertices(int index) {
        return graph.get(index).getTail();
    }

    /**
     * Returns true if the graph is empty.
     */
    public boolean isEmpty() {
        return graph.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || other instanceof BipartiteGraph && graph.equals(((BipartiteGraph) other).graph)
                && numInterviewees == ((BipartiteGraph) other).numInterviewees
                && numInterviewSlots == ((BipartiteGraph) other).numInterviewSlots;
    }
}

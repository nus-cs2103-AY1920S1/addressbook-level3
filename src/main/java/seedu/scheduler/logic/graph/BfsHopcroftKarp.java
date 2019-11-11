package seedu.scheduler.logic.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Performs breath-first search to find augmenting paths in the bipartite graph of interviewees and interview slots.
 */
public class BfsHopcroftKarp {
    private BipartiteGraph graph;

    public BfsHopcroftKarp(BipartiteGraph graph) {
        this.graph = graph;
    }

    /**
     * Returns a list consisting unmatched interview slot vertices in the last layer of the layered graph if augmenting
     * path(s) is found, otherwise an empty list is returned. The given predecessors array are used to record the
     * structure of the layered graph.
     *
     * @param intervieweePredecessor a list used to keep track of the predecessor of an interviewee, which is a
     *                               slot matched to it
     * @param interviewSlotPredecessors a list used to keep track of predecessor(s) of an interview slot, which is
     *                                  interviewee(s) that can match the interview slot
     */
    public List<InterviewerSlotVertex> execute(List<InterviewerSlotVertex> intervieweePredecessor,
            List<List<IntervieweeVertex>> interviewSlotPredecessors) {
        Queue<Vertex> currentLayer = initialiseBfs();
        Queue<Vertex> nextLayer = new LinkedList<>();
        List<InterviewerSlotVertex> unmatchedSlotVertices = new LinkedList<>();

        // Perform BFS and build the layered graph
        do {
            nextLayer.clear();
            // Constructs the next layer
            while (!currentLayer.isEmpty()) {
                Vertex u = currentLayer.poll();

                if (u instanceof IntervieweeVertex) {
                    nextLayer.addAll(findUnmatchedVertices(u, nextLayer, interviewSlotPredecessors));
                } else if (u instanceof InterviewerSlotVertex && u.isMatched()) {
                    Vertex v = findMatchedVertex(u, nextLayer, intervieweePredecessor);
                    if (v != null) {
                        nextLayer.add(v);
                    }
                }
            }

            if (containsUnmatchedSlotVertices(nextLayer)) {
                unmatchedSlotVertices = getUnmatchedSlotVertices(nextLayer);
                break;
            } else {
                currentLayer.addAll(nextLayer);
            }
        } while (!nextLayer.isEmpty());

        return unmatchedSlotVertices;
    }

    /**
     * Returns a queue of unmatched vertices in the first layer of the graph
     * (which is basically unmatched interviewee vertices).
     */
    private Queue<Vertex> initialiseBfs() {
        int numInterviewees = graph.getNumInterviewees();
        Queue<Vertex> firstLayer = new LinkedList<>();

        // Initialise the first layer
        for (int i = 0; i < numInterviewees; i++) {
            IntervieweeVertex intervieweeVertex = graph.getIntervieweePair(i).getHead();

            if (!intervieweeVertex.isMatched()) {
                firstLayer.offer(intervieweeVertex);
            }
        }

        return firstLayer;
    }

    /**
     * Returns a list of unmatched interview slot vertices in the next layer.
     */
    private List<Vertex> findUnmatchedVertices(Vertex u, Queue<Vertex> nextLayer,
            List<List<IntervieweeVertex>> interviewSlotPredecessors) {
        List<InterviewerSlotVertex> associatedSlotVertices = graph.getInterviewSlotVertices(u.getIndex());
        List<Vertex> unmatchedVertices = new LinkedList<>();

        for (InterviewerSlotVertex v : associatedSlotVertices) {
            if (v.isMatchedTo(u)) {
                continue;
            }
            List<IntervieweeVertex> vPredecessors = interviewSlotPredecessors.get(v.getIndex());
            vPredecessors.add((IntervieweeVertex) u);

            if (!nextLayer.contains(v)) {
                unmatchedVertices.add(v);
            }
        }

        return unmatchedVertices;
    }

    /**
     * Returns the matched interviewee vertex(in the next layer) of the interview slot vertex only if
     * the interviewee vertex does not already exist in the next layer given of the graph, otherwise
     * returns null.
     */
    private Vertex findMatchedVertex(Vertex u, Queue<Vertex> nextLayer,
            List<InterviewerSlotVertex> intervieweePredecessor) {
        IntervieweeVertex v = (IntervieweeVertex) u.getPartner();
        intervieweePredecessor.add(v.getIndex(), (InterviewerSlotVertex) u);

        if (!nextLayer.contains(v)) {
            return v;
        } else {
            return null;
        }
    }


    /**
     * Returns true if the next layer contains at least one unmatched slot vertex.
     */
    private boolean containsUnmatchedSlotVertices(Queue<Vertex> nextLayer) {
        boolean containsUnmatched = false;

        for (Vertex v : nextLayer) {
            if (v instanceof IntervieweeVertex) {
                break;
            }

            if (!v.isMatched()) {
                containsUnmatched = true;
                break;
            }
        }

        return containsUnmatched;
    }

    /**
     * Returns the list of unmatched interview slot vertices in the given queue of vertices.
     */
    private List<InterviewerSlotVertex> getUnmatchedSlotVertices(Queue<Vertex> vertices) {
        List<InterviewerSlotVertex> unmatchedSlotVertices = new LinkedList<>();

        for (Vertex v : vertices) {
            if (v instanceof InterviewerSlotVertex && !v.isMatched()) {
                unmatchedSlotVertices.add((InterviewerSlotVertex) v);
            }
        }

        return unmatchedSlotVertices;
    }
}

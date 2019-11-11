package seedu.scheduler.logic.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

/**
 * Performs depth-first search on the layered graph of interviewees and interview slots to try to augment the number of
 * matching between interviewees and interview slots. The layered graph is generated from the breath-first search on
 * the bipartite graph of interviewees and interview slots and it's recorded through predecessor arrays. Note that in
 * the layered graph an interview slot may have multiple predecessors, which is interviewees.
 */
public class DfsHopcroftKarp {
    private BipartiteGraph graph;
    private List<InterviewerSlotVertex> lastLayer;
    private List<InterviewerSlotVertex> intervieweePredecessor;
    private List<List<IntervieweeVertex>> interviewSlotPredecessors;
    private boolean[] isUsedInterviewee;
    private boolean[] isUsedSlot;

    public DfsHopcroftKarp(BipartiteGraph graph) {
        this.graph = graph;
    }

    /**
     * Executes depth-first search on the layered graph and tries to augment the number of matching between
     * interviewee vertex and interview slot vertex.
     *
     * @param lastLayer last layer of the layered graph.
     */
    public void execute(List<InterviewerSlotVertex> lastLayer, List<InterviewerSlotVertex> intervieweePredecessors,
            List<List<IntervieweeVertex>> interviewSlotPredecessors, boolean[] isUsedInterviewee,
                        boolean[] isUsedSlot) {
        this.lastLayer = lastLayer;
        this.intervieweePredecessor = intervieweePredecessors;
        this.interviewSlotPredecessors = interviewSlotPredecessors;
        this.isUsedInterviewee = isUsedInterviewee;
        this.isUsedSlot = isUsedSlot;

        for (Vertex v : lastLayer) {
            List<Vertex> path = new LinkedList<>();
            dfs(v, path);

            if (!path.isEmpty()) {
                flipMatchingStatus(path);
            }
        }
    }

    /**
     * Executes depth-first-search through the layered graph recursively to try to search for
     * a complete augmenting path, starting from the given free vertex in the last layer of the layered graph.
     */
    private void dfs(Vertex u, List<Vertex> path) {
        // If hits the first layer (i.e. don't have predecessor)
        if (!hasPredecessor(u)) {
            path.add(u);
            return;
        }

        if (u instanceof IntervieweeVertex) {
            InterviewerSlotVertex predecessor = intervieweePredecessor.get(u.getIndex());
            if (isUsedSlot[predecessor.getIndex()]) {
                return;
            }

            dfs(predecessor, path);
            if (!path.isEmpty()) {
                path.add(u);
            }
        } else {
            List<IntervieweeVertex> predecessors = interviewSlotPredecessors.get(u.getIndex());
            for (IntervieweeVertex predecessor : predecessors) {
                if (isUsedInterviewee[predecessor.getIndex()]) {
                    continue;
                }

                dfs(predecessor, path);
                if (!path.isEmpty()) {
                    path.add(u);
                    break;
                }
            }
        }
    }

    /**
     * Returns true if the vertex u has a predecessor.
     */
    private boolean hasPredecessor(Vertex u) {
        if (u instanceof IntervieweeVertex) {
            InterviewerSlotVertex predecessor = intervieweePredecessor.get(u.getIndex());
            return predecessor != null;
        } else {
            List<IntervieweeVertex> predecessors = interviewSlotPredecessors.get(u.getIndex());
            return !predecessors.isEmpty();
        }
    }

    /**
     * Flips the matched and unmatched status along the vertices in the augmented path.
     * When flipping matched status, attach the new matching partner of a vertex to itself through match(partner).
     */
    private void flipMatchingStatus(List<Vertex> path) {
        assert path.size() % 2 == 0;

        // System.out.printf("Printing augmenting path...\n");
        // path.forEach(System.out::println);
        ListIterator<Vertex> iterator = path.listIterator();
        IntStream.range(0, path.size() - 1).forEach(i -> {
            Vertex currVertex = iterator.next();
            Vertex nextVertex = iterator.next();

            if (i % 2 == 0) {
                // If now is to match (bidirectional matching)
                // TODO: Perform a safe matching instead
                currVertex.match(nextVertex);
                nextVertex.match(currVertex);
            } else {
                nextVertex.unmatch();
            }

            updateUsedStatus(currVertex);
            updateUsedStatus(nextVertex);
            iterator.previous(); // move the cursor backward by 1 position
        });
    }

    /**
     * Updates the used status of a vertex after it is used in one augmenting path so that it is not considered
     * in the subsequent search of a complete augmenting path in the layered graph during depth-first search.
     */
    private void updateUsedStatus(Vertex u) {
        if (u instanceof IntervieweeVertex) {
            isUsedInterviewee[u.getIndex()] = true;
        } else {
            isUsedSlot[u.getIndex()] = true;
        }
    }
}

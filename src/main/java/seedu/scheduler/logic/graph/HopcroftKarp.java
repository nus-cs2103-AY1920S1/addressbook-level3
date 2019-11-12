package seedu.scheduler.logic.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Represents the Hopcroft-Karp algorithm which can find the maximum number of matchings between interviewee and
 * interview slots.
* Crucial assumption: A bipartite graph is given to the algorithm.
 */
public class HopcroftKarp {
    /**
     * intervieweePredecessors -> predecessor of interviewee which is an interview slot matched to it.
     * interviewSlotPredecessors predecessor(s) of interview slots which is interviewee(s) that can match the slot.
     * isUsedInterviewee -> a boolean array to keep track of interviewee that were used in an augmenting path
     *                      to augment the number of matching.
     * isUsedSlot -> a boolean array to keep track of interview slot that were used in an augmenting path
     *               to augment the number of matching.
     */
    private BipartiteGraph graph;
    private List<InterviewerSlotVertex> intervieweePredecessor;
    private List<List<IntervieweeVertex>> interviewSlotPredecessors;
    private boolean[] usedInterviewees;
    private boolean[] usedSlots;

    public HopcroftKarp(BipartiteGraph graph) {
        this.graph = graph;
    }

    /**
     * Run the Hopcroft-Karp algorithm on the given bipartite graphs of interviewee and available interview slots
     * to find the maximum number of matching between interviewee and interview slots. The matching status between
     * interviewee and interview slots are tracked inside the given bipartite graph.
     */
    public void execute() {
        if (graph.isEmpty()) {
            return;
        }
        initialiseHopCroftKarp();
        List<InterviewerSlotVertex> lastLayer = new LinkedList<>();

        do {
            lastLayer = new BfsHopcroftKarp(graph).execute(intervieweePredecessor, interviewSlotPredecessors);
            // If augmenting path(s) is found
            if (!lastLayer.isEmpty()) {
                new DfsHopcroftKarp(graph).execute(lastLayer, intervieweePredecessor, interviewSlotPredecessors,
                        usedInterviewees, usedSlots);
            }
            cleanUp();
        } while (!lastLayer.isEmpty()); // while there exists an augmenting path(s)
    }

    /**
     * Initialises the necessary components of the HopCroft Karp algorithm.
     */
    private void initialiseHopCroftKarp() {
        int numInterviewees = graph.getNumInterviewees();
        int numSlots = graph.getNumInterviewSlots();

        intervieweePredecessor = new ArrayList<>(numInterviewees);
        interviewSlotPredecessors = new ArrayList<>(numSlots);
        usedInterviewees = new boolean[numInterviewees];
        usedSlots = new boolean[numSlots];

        IntStream.range(0, numSlots).forEach(i -> intervieweePredecessor.add(null));
        IntStream.range(0, numSlots).forEach(i -> interviewSlotPredecessors.add(new LinkedList<>()));
    }

    /**
     * Cleans up the components of the HopCroft Karp algorithm after one iteration of the algorithm.
     */
    private void cleanUp() {
        Collections.fill(intervieweePredecessor, null);
        interviewSlotPredecessors.forEach(list -> list.clear());
        Arrays.fill(usedInterviewees, false);
        Arrays.fill(usedSlots, false);
    }
}

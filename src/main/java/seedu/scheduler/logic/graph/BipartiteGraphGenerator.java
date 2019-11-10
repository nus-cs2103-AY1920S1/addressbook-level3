package seedu.scheduler.logic.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import seedu.scheduler.commons.util.Pair;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Slot;

/**
 * Generates a bipartite graph of interviewees linked to the the available interview slots.
 */
public class BipartiteGraphGenerator {
    private List<Interviewer> interviewers;
    private List<Interviewee> interviewees;

    public BipartiteGraphGenerator(List<Interviewer> interviewers, List<Interviewee> interviewees) {
        this.interviewers = interviewers;
        this.interviewees = interviewees;
    }

    /**
     * Returns a graph of interviewees matched to available interview slots. Note that there may be presence of
     * interviewees that are not linked to any interview slot at all as the availabilities of the interviewee does not
     * match any of the available interview slot.
     * The interviewees and interview slots are each wrapped in a vertex.
     */
    public BipartiteGraph generate() {
        Pair<List<Pair<Department, List<InterviewerSlotVertex>>>, Integer> result =
                generateInterviewSlotsVertices(interviewers);
        List<Pair<Department, List<InterviewerSlotVertex>>> list = result.getHead();
        int numSlots = result.getTail();

        List<Pair<IntervieweeVertex, List<InterviewerSlotVertex>>> graph = new ArrayList<>(interviewees.size());
        int currIntervieweeVertexIndex = 0;

        for (Interviewee interviewee : interviewees) {

            // Create a list to store the interview slots that the interviewee can match to
            List<InterviewerSlotVertex> interviewSlotVertices = new LinkedList<>();

            // Get the sorted available interview slots based on the department of choice of the interviewee
            // and also the sorted desired slots of the interviewee
            Department department = interviewee.getDepartmentChoices().get(0);
            List<InterviewerSlotVertex> availableSlots = getInterviewSlotVertices(list, department);
            List<Slot> desiredSlots = interviewee.getAvailableTimeslots();

            // Based on the desired slots, fill up the list with the matching available interview slots (wrapping
            // each of them in a vertex)
            fill(interviewSlotVertices, desiredSlots, availableSlots);

            IntervieweeVertex intervieweeVertex = new IntervieweeVertex(interviewee, currIntervieweeVertexIndex);
            Pair<IntervieweeVertex, List<InterviewerSlotVertex>> vertexListPair =
                    new Pair<>(intervieweeVertex, interviewSlotVertices);
            graph.add(vertexListPair);
            currIntervieweeVertexIndex++;
        }

        return new BipartiteGraph(graph, interviewees.size(), numSlots);
    }

    /**
     * Returns the sorted available interview slots based on the department of choice of the interviewee.
     */
    private List<InterviewerSlotVertex> getInterviewSlotVertices(
            List<Pair<Department, List<InterviewerSlotVertex>>> list, Department departmentOfChoice) {
        Pair<Department, List<InterviewerSlotVertex>> departmentListPair =
                getDepartmentListPair(list, departmentOfChoice);

        return departmentListPair.getTail();
    }

    /**
     * Fill up the given list with the matching available interview slots (wrapping each of them in a vertex)
     * based on the desired slots of the interviewee.
     */
    private void fill(List<InterviewerSlotVertex> interviewSlotVertices,
            List<Slot> desiredSlots, List<InterviewerSlotVertex> availableSlots) {
        ListIterator<InterviewerSlotVertex> availableSlotsIterator = availableSlots.listIterator();
        ListIterator<Slot> desiredSlotsIterator = desiredSlots.listIterator();

        if (!(availableSlotsIterator.hasNext() && desiredSlotsIterator.hasNext())) {
            return;
        }

        Slot desiredSlot = desiredSlotsIterator.next();
        InterviewerSlotVertex availableSlotVertex = availableSlotsIterator.next();

        while (true) {
            int comp = desiredSlot.compareTo(availableSlotVertex.getItem().getSlot());
            try {
                if (comp == 0) {
                    interviewSlotVertices.add(availableSlotVertex);
                    availableSlotVertex = availableSlotsIterator.next();
                } else if (comp > 0) {
                    availableSlotVertex = availableSlotsIterator.next();
                } else if (comp < 0) {
                    desiredSlot = desiredSlotsIterator.next();
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }

    /**
     * Returns a list of interview slots based on the availabilities of the interviewers.
     * The list is structured in such a way: the interview slots are categorised based on the department of the
     * interviewer. Thus, the actual structure of the list is a list of pairs, with the head being the department,
     * and the tail being the list of available interview slots of that department.
     */
    private Pair<List<Pair<Department, List<InterviewerSlotVertex>>>, Integer> generateInterviewSlotsVertices(
            List<Interviewer> interviewers) {
        List<Pair<Department, List<InterviewerSlotVertex>>> list = new LinkedList<>();
        int currNumSlots = 0;

        for (Interviewer interviewer : interviewers) {
            Department department = interviewer.getDepartment();
            Pair<Department, List<InterviewerSlotVertex>> pair = getDepartmentListPair(list, department);

            List<Slot> slots = interviewer.getAvailabilities();
            List<InterviewerSlotVertex> interviewSlotVertices = pair.getTail();
            for (Slot slot : slots) {
                InterviewSlot interviewSlot = new InterviewSlot(slot, interviewer);
                interviewSlotVertices.add(new InterviewerSlotVertex(interviewSlot, currNumSlots));
                currNumSlots++;
            }
        }

        // Sort the slots
        for (Pair<Department, List<InterviewerSlotVertex>> pair : list) {
            Collections.sort(pair.getTail());
        }

        return new Pair<>(list, currNumSlots);
    }

    /**
     * Returns the pair of department and the list of associated interview slots from the given list.
     * If the department does not already exist in the list, a new pair for the department will be created, with
     * the list of associated interview slots being empty.
     */
    private Pair<Department, List<InterviewerSlotVertex>> getDepartmentListPair(List<Pair<Department,
            List<InterviewerSlotVertex>>> list, Department department) {
        int size = list.size();
        Pair<Department, List<InterviewerSlotVertex>> pair = null;

        for (int i = 0; i < size; i++) {
            Pair<Department, List<InterviewerSlotVertex>> currPair = list.get(i);
            Department currDepartment = currPair.getHead();
            if (currDepartment.equals(department)) {
                pair = currPair;
                break;
            }
        }

        if (pair == null) {
            pair = new Pair<>(department, new LinkedList<>());
            list.add(pair);
        }

        return pair;
    }
}

package seedu.address.logic.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.Pair;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.graph.BipartiteGraph;
import seedu.address.logic.graph.BipartiteGraphGenerator;
import seedu.address.logic.graph.HopCroftKarp;
import seedu.address.logic.graph.InterviewSlotVertex;
import seedu.address.logic.graph.IntervieweeVertex;
import seedu.address.model.Model;
import seedu.address.model.person.InterviewSlot;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;

/**
 * Schedules the interviews using the interviewer's availability data and interviewee's selected slots.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule the interviews using the interviewer's "
        + "availability and the interviewee's selected time slot.\n"
        + "Parameters: none\n"
        + "Example: " + COMMAND_WORD + " (no other word should follow after it)";
    private static final Logger logger = LogsCenter.getLogger(ScheduleCommand.class);

    private List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> graph;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Starting to schedule interviews");
        model.clearAllAllocatedSlot();

        List<Interviewer> interviewers = model.getUnfilteredInterviewerList();
        List<Interviewee> interviewees = model.getUnfilteredIntervieweeList();

        BipartiteGraph graph = new BipartiteGraphGenerator(interviewers, interviewees).generate();
        HopCroftKarp algorithm = new HopCroftKarp(graph);
        algorithm.execute();

        List<Interviewee> intervieweesWithSlots = assignSlotToInterviewees(graph);
        String resultMessage = generateResultMessage(intervieweesWithSlots);
        logger.info("Finish scheduling interviews");

        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof ScheduleCommand; // instanceof handles nulls
    }

    /**
     * Attaches the interview slot that the interviewee is allocated to(after running the HopCroftKarp algorithm)
     * to it and returns a list of interviewee which are successfully allocated an interview slot.
     */
    private List<Interviewee> assignSlotToInterviewees(BipartiteGraph graph) {
        List<Interviewee> intervieweesWithSlot = new LinkedList<>();
        int numInterviewees = graph.getNumInterviewees();

        IntStream.range(0, numInterviewees).forEach(i -> {
            IntervieweeVertex vertex = graph.getIntervieweePair(i).getHead();

            if (vertex.isMatched()) {
                Interviewee interviewee = vertex.getItem();
                InterviewSlot slot = vertex.getPartner().getItem();
                interviewee.setAllocatedSlot(slot);
                intervieweesWithSlot.add(interviewee);
            }
        });

        return intervieweesWithSlot;
    }

    /**
     * Returns the result of the scheduling as a string message.
     */
    private String generateResultMessage(List<Interviewee> interviewees) {
        StringBuilder builder = new StringBuilder(300);
        builder.append("Successfully scheduled!\n");

        String resultMessage = "Name: %s, allocated slot: %s, interviewer: %s, department: %s\n";
        interviewees.stream().map(interviewee -> {
            InterviewSlot slot = interviewee.getAllocatedSlot();
            return String.format(resultMessage, interviewee.getName(), slot.getDateTime(), slot.getInterviewerName(),
                slot.getDepartment());
        }).forEach(builder::append);

        return builder.toString();
    }
}

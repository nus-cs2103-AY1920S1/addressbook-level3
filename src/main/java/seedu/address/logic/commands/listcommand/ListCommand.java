package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.ReadOnlyEntityList;

/**
 * Lists every {@link Entity} (i.e. mentor, participant, team) in Alfred.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all entities";
    public static final String MESSAGE_NO_MENTOR = "There are currently no mentors in Alfred.";
    public static final String MESSAGE_NO_PARTICIPANT = "There are currently no participants in Alfred.";
    public static final String MESSAGE_NO_PARTICIPANT_IN_TEAM = "There are currently no participants in this team.";
    public static final String MESSAGE_NO_TEAM = "There are currently no teams in Alfred.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all entities of the specified entity type "
            + "stored within Alfred.\n"
            + "Format: " + COMMAND_WORD + " [entity name] \n"
            + "For example: \"list mentors\" or \"list participants\" or \"list teams\"";

    private static final String MESSAGE_MENTOR_HEADER = "List of all mentors:";
    private static final String MESSAGE_PARTICIPANT_HEADER = "List of all participants:";
    private static final String MESSAGE_TEAM_HEADER = "List of all teams:";

    private boolean shouldShowConnection;
    // TODO: Message Usage

    public ListCommand() {
        this.shouldShowConnection = false;
    }

    /**
     * Constructs a ListCommand with given boolean value, {@code shouldShowConnection}.
     * If passed in value is true, Alfred will display the connections between {@code Entity} objects.
     *
     * @see #showConnection(Model)
     */
    public ListCommand(boolean shouldShowConnection) {
        this.shouldShowConnection = shouldShowConnection;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (shouldShowConnection) {
            this.showConnection(model);
        } else {
            this.displayTeams(model);
            this.displayMentors(model);
            this.displayParticipants(model);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Shows connection between {@code Entity} objects stored in Alfred.<p>
     * Outputs the connection in the following manner:<p>
     * <pre>
     *     Team: {Team Name} (Mentor: {Mentor Name})
     *     Participants:<p>
     *     <pre>
     *         {Participant 1 Name}
     *         {Participant 2 Name}
     *         ...
     *         {Participant n Name}
     *     </pre>
     * </pre>
     */
    private void showConnection(Model model) {
        model.getTeamList().list()
                .forEach(t -> {
                    Team team = (Team) t;
                    String teamName = team.getName().toString();
                    String mentorName = team.getMentor().isEmpty()
                            ? "NA"
                            : team.getMentor().get().toString();
                    System.out.println(String.format("Team: %s (Mentor: %s)\nParticipants:", teamName, mentorName));
                    if (team.getParticipants().isEmpty()) {
                        System.out.println("    " + MESSAGE_NO_PARTICIPANT_IN_TEAM);
                        return;
                    }
                    for (Participant p : team.getParticipants()) {
                        System.out.println("    " + p.getName());
                    }
                });
    }

    /**
     * Displays basic information of each {@code Team} in {@code Alfred}.
     */
    void displayTeams(Model model) {
        ReadOnlyEntityList teamList = model.getTeamList();
        if (teamList.isEmpty()) {
            System.out.println(MESSAGE_NO_TEAM);
            return;
        }
        System.out.println(MESSAGE_TEAM_HEADER);
        teamList.list().forEach(this::listEntity);
    }

    /**
     * Displays basic information of each {@code Mentor} in {@code Alfred}.
     */
    void displayMentors(Model model) {
        ReadOnlyEntityList mentorList = model.getMentorList();
        if (mentorList.isEmpty()) {
            System.out.println(MESSAGE_NO_MENTOR);
            return;
        }
        System.out.println(MESSAGE_MENTOR_HEADER);
        mentorList.list().forEach(this::listEntity);
    }

    /**
     * Displays basic information of each {@code Participant} in {@code Alfred}.
     */
    void displayParticipants(Model model) {
        ReadOnlyEntityList participantList = model.getParticipantList();
        if (participantList.isEmpty()) {
            System.out.println(MESSAGE_NO_PARTICIPANT);
            return;
        }
        System.out.println(MESSAGE_PARTICIPANT_HEADER);
        participantList.list().forEach(this::listEntity);
    }

    /**
     * Prints the basic information of given Entity.
     *
     * @param entity Entity to list.
     */
    private void listEntity(Entity entity) {
        Map<String, String> fieldMap = entity.viewMinimal();
        StringBuilder toPrint = new StringBuilder();
        for (String key : fieldMap.keySet()) {
            toPrint.append("\t")
                    .append(StringUtil.capitalize(key))
                    .append(": ")
                    .append(fieldMap.get(key))
                    .append("   ");
        }
        System.out.println(toPrint.toString().trim());
    }

}

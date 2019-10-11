package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * Lists every {@link Entity} (i.e. mentor, participant, team) in Alfred.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all entities";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all entities in Alfred.\n"
            + "Example: " + COMMAND_WORD;

    // TODO: Message Usage

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Team: <Team Name> (Mentor: <Mentor Name>)
        // Participants:
        //    <Participant 1> (in this team)
        //    ...
        //    <Participant n>
        model.getTeamList().list()
                .forEach(t -> {
                    Team team = (Team) t;
                    String teamName = team.getName().toString();
                    String mentorName = team.getMentor().isEmpty()
                                        ? "NA"
                                        : team.getMentor().get().toString();
                    System.out.println(String.format("Team: %s (Mentor: %s)\nParticipants:", teamName, mentorName));
                    for (Participant p : team.getParticipants()) {
                        System.out.println("    " + p.getName());
                    }
                });

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Prints the basic information of given Entity.
     *
     * @param entity Entity to list.
     */
    void listEntity(Entity entity) {
        Map<String, String> fieldMap = entity.viewMinimal();
        StringBuilder toPrint = new StringBuilder();
        for (String key : fieldMap.keySet()) {
            toPrint.append(StringUtil.capitalize(key))
                    .append(": ")
                    .append(fieldMap.get(key))
                    .append("   ");
        }
        System.out.println(toPrint.toString().trim());
    }

}

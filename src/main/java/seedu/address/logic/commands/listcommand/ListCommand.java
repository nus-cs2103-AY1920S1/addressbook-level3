package seedu.address.logic.commands.listcommand;

import java.util.Map;

import seedu.address.commons.util.PrefixUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entitylist.ReadOnlyEntityList;

/**
 * Lists every specified {@link Entity} (i.e. mentor, participant, team) in Alfred.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all entities";
    public static final String MESSAGE_NO_MENTOR = "There are currently no mentors in Alfred.";
    public static final String MESSAGE_NO_PARTICIPANT = "There are currently no participants in Alfred.";
    public static final String MESSAGE_NO_TEAM = "There are currently no teams in Alfred.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all entities of the specified entity type "
            + "stored within Alfred.\n"
            + "Format: " + COMMAND_WORD + " [entity name] \n"
            + "For example: \"list mentors\" or \"list participants\" or \"list teams\"";

    private static final String MESSAGE_MENTOR_HEADER = "List of all mentors:";
    private static final String MESSAGE_PARTICIPANT_HEADER = "List of all participants:";
    private static final String MESSAGE_TEAM_HEADER = "List of all teams:";

    /**
     * Displays basic information of entities of specified {@code PrefixType} in {@code Alfred}.
     */
    void displayEntities(Model model, PrefixType prefixType) {
        ReadOnlyEntityList entityList = PrefixUtil.getEntityListBasedOnPrefixType(prefixType, model);
        if (entityList.isEmpty()) {
            System.out.println(PrefixUtil.getStringBasedOnPrefixType(
                    prefixType,
                    MESSAGE_NO_MENTOR,
                    MESSAGE_NO_PARTICIPANT,
                    MESSAGE_NO_TEAM
            ));
            return;
        }
        System.out.println(PrefixUtil.getStringBasedOnPrefixType(
                prefixType,
                MESSAGE_MENTOR_HEADER,
                MESSAGE_PARTICIPANT_HEADER,
                MESSAGE_TEAM_HEADER
        ));
        entityList.list().forEach(this::listEntity);
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

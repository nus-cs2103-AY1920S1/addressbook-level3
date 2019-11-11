package seedu.address.logic.commands.suggestions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.GroupScheduleDisplay;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.timeslots.PersonSchedule;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.LookAtGroupMemberCommand}.
 */
public class LookAtGroupMemberCommandSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_NAME
    );

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        Prefix prefix = commandArgument.getPrefix();
        if (!prefix.equals(CliSyntax.PREFIX_NAME)) {
            return null;
        }

        ScheduleState status = model.getState();
        if (!status.equals(ScheduleState.GROUP)) {
            return null;
        }

        String enteredName = commandArgument.getValue();

        GroupScheduleDisplay groupScheduleDisplay = (GroupScheduleDisplay) model.getScheduleDisplay();

        ArrayList<PersonSchedule> personSchedules = groupScheduleDisplay.getPersonSchedules();
        List<String> presentNames = personSchedules.stream()
                .map(personSchedule -> personSchedule.getPersonDisplay().getName().fullName)
                .filter(name -> name.startsWith(enteredName))
                .collect(Collectors.toCollection(ArrayList::new));

        return presentNames;
    }
}

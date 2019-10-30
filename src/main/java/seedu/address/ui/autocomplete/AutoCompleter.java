package seedu.address.ui.autocomplete;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.appointments.AckAppCommand;
import seedu.address.logic.commands.appointments.AddAppCommand;
import seedu.address.logic.commands.appointments.AppointmentsCommand;
import seedu.address.logic.commands.appointments.CancelAppCommand;
import seedu.address.logic.commands.appointments.ChangeAppCommand;
import seedu.address.logic.commands.appointments.MissAppCommand;
import seedu.address.logic.commands.appointments.SettleAppCommand;
import seedu.address.logic.commands.duties.AddDutyShiftCommand;
import seedu.address.logic.commands.duties.CancelDutyShiftCommand;
import seedu.address.logic.commands.duties.ChangeDutyShiftCommand;
import seedu.address.logic.commands.duties.DutyShiftCommand;
import seedu.address.logic.commands.patients.EditPatientDetailsCommand;
import seedu.address.logic.commands.patients.ListPatientCommand;
import seedu.address.logic.commands.patients.RegisterPatientCommand;
import seedu.address.logic.commands.queue.AddConsultationRoomCommand;
import seedu.address.logic.commands.queue.BreakCommand;
import seedu.address.logic.commands.queue.DequeueCommand;
import seedu.address.logic.commands.queue.EnqueueCommand;
import seedu.address.logic.commands.queue.RemoveRoomCommand;
import seedu.address.logic.commands.queue.ResumeCommand;
import seedu.address.logic.commands.staff.EditStaffDetailsCommand;
import seedu.address.logic.commands.staff.ListStaffCommand;
import seedu.address.logic.commands.staff.RegisterStaffCommand;

/**
 * Component for AutoComplete
 */
public class AutoCompleter {
    private static final Map<String, Set<String>> SUPPORTED_ARGUMENTS = Map.ofEntries(
            Map.entry("ackappt", Set.of("i/")),
            Map.entry("addappt", Set.of("i/", "rec/", "num/", "str/")),
            Map.entry("addshift", Set.of("i/", "rec/", "num/", "str/")),
            Map.entry("appointments", Set.of("i/")),
            Map.entry("changeappt", Set.of("str/")),
            Map.entry("changeshift", Set.of("str/")),
            Map.entry("newdoctor", Set.of("i/", "n/", "p/", "a/", "e/")),
            Map.entry("register", Set.of("i/", "n/", "p/", "a/", "t/", "e/")),
            Map.entry("update", Set.of("i/", "n/", "p/", "a/", "t/", "e/")),
            Map.entry("updatedoctor", Set.of("i/", "n/", "p/", "a/", "e/"))
    );

    private static final String[] SUPPORTED_COMMANDS = new String[]{
            ListPatientCommand.COMMAND_WORD,
            RegisterPatientCommand.COMMAND_WORD,
            EditPatientDetailsCommand.COMMAND_WORD,

            ListStaffCommand.COMMAND_WORD,
            RegisterStaffCommand.COMMAND_WORD,
            EditStaffDetailsCommand.COMMAND_WORD,

            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,

            RedoCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD,

            EnqueueCommand.COMMAND_WORD,
            DequeueCommand.COMMAND_WORD,

            AckAppCommand.COMMAND_WORD,
            AddAppCommand.COMMAND_WORD,
            AppointmentsCommand.COMMAND_WORD,
            CancelAppCommand.COMMAND_WORD,
            ChangeAppCommand.COMMAND_WORD,
            MissAppCommand.COMMAND_WORD,
            SettleAppCommand.COMMAND_WORD,

            DutyShiftCommand.COMMAND_WORD,
            AddDutyShiftCommand.COMMAND_WORD,
            CancelDutyShiftCommand.COMMAND_WORD,
            ChangeDutyShiftCommand.COMMAND_WORD,
 
            AddConsultationRoomCommand.COMMAND_WORD,
            RemoveRoomCommand.COMMAND_WORD,

            NextCommand.COMMAND_WORD,
            BreakCommand.COMMAND_WORD,
            ResumeCommand.COMMAND_WORD
    };

    private Trie trie;
    private String currentQuery;

    public AutoCompleter() {
        trie = new Trie(SUPPORTED_COMMANDS);
    }

    public AutoCompleter(String... arr) {
        this.trie = new Trie(arr);
    }

    /**
     * Updates AutoComplete with current query.
     *
     * @param currentQuery
     * @return AutoComplete itself
     */
    public AutoCompleter update(String currentQuery) {
        if (currentQuery.matches("(.*)?\\s*\\w+\\s+$")) { //For Linux flag use "(.* )?(?<!-)\\w+\\s+$"
            try {
                Set<String> result = SUPPORTED_ARGUMENTS.get(currentQuery.substring(0, currentQuery.indexOf(' ')));
                HashSet<String> available = new HashSet<>(result);
                Arrays.asList(currentQuery.split("\\s+"))
                        .forEach(e -> available.remove(e.substring(0, e.indexOf('/') + 1).trim()));
                //available.removeAll(Arrays.asList(currentQuery.split("\\s+"))); //Linux flag uses "\\s+"
                if (result.contains("t/")) {
                    available.add("t/");
                }
                AutoCompleter autoCompleter = new AutoCompleter(available.toArray(String[]::new));
                autoCompleter.currentQuery = currentQuery.substring(currentQuery.lastIndexOf(' ') + 1);
                return autoCompleter;
            } catch (NullPointerException e) {
                return new AutoCompleter("");
            }
        } else {
            this.currentQuery = currentQuery;
            return this;
        }
    }

    public List<String> getSuggestions() {
        try {
            return trie.find(currentQuery).getPossibilities();
        } catch (NullPointerException e) {
            return Collections.emptyList();
        }
    }
}

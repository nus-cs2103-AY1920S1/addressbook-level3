package seedu.address.ui.autocomplete;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.AckAppCommand;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.AddConsultationRoomCommand;
import seedu.address.logic.commands.AppointmentsCommand;
import seedu.address.logic.commands.BreakCommand;
import seedu.address.logic.commands.CancelAppCommand;
import seedu.address.logic.commands.ChangeAppCommand;
import seedu.address.logic.commands.DequeueCommand;
import seedu.address.logic.commands.EnqueueCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.MissAppCommand;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveRoomCommand;
import seedu.address.logic.commands.ResumeCommand;
import seedu.address.logic.commands.SettleAppCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.patients.EditPatientDetailsCommand;
import seedu.address.logic.commands.patients.ListPatientCommand;
import seedu.address.logic.commands.patients.RegisterPatientCommand;
import seedu.address.logic.commands.patients.UnregisterPatientCommand;
import seedu.address.logic.commands.staff.EditStaffDetailsCommand;
import seedu.address.logic.commands.staff.ListStaffCommand;
import seedu.address.logic.commands.staff.RegisterStaffCommand;
import seedu.address.logic.commands.staff.UnregisterStaffCommand;

/**
 * Component for AutoComplete
 */
public class AutoCompleter {
    private static final Map<String, HashSet> SUPPORTED_ARGUMENTS = Map.ofEntries(
            Map.entry("add", new HashSet(Arrays.asList("-name", "-id", "-phone", "-address")))
    );

    private static final String[] SUPPORTED_COMMANDS = new String[] {
            ListPatientCommand.COMMAND_WORD,
            RegisterPatientCommand.COMMAND_WORD,
            EditPatientDetailsCommand.COMMAND_WORD,
            UnregisterPatientCommand.COMMAND_WORD,

            ListStaffCommand.COMMAND_WORD,
            RegisterStaffCommand.COMMAND_WORD,
            EditStaffDetailsCommand.COMMAND_WORD,
            UnregisterStaffCommand.COMMAND_WORD,

            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,

            UndoCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD,

            EnqueueCommand.COMMAND_WORD,
            DequeueCommand.COMMAND_WORD,

            AppointmentsCommand.COMMAND_WORD,
            AddAppCommand.COMMAND_WORD,
            CancelAppCommand.COMMAND_WORD,
            ChangeAppCommand.COMMAND_WORD,

            AckAppCommand.COMMAND_WORD,
            MissAppCommand.COMMAND_WORD,
            SettleAppCommand.COMMAND_WORD,

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
        if (currentQuery.matches("(.* )?(?<!-)\\w+\\s+$")) {
            try {
                HashSet<String> available = (HashSet) SUPPORTED_ARGUMENTS.get(currentQuery.substring(0,
                    currentQuery.indexOf(' ')))
                    .clone();
                available.removeAll(Arrays.asList(currentQuery.split("\\s+")));
                AutoCompleter autoCompleter = new AutoCompleter(available.toArray(new String[0]));
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

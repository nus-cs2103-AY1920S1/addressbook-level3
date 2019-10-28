package seedu.address.commons.core;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.exceptions.RecursiveAliasException;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.alias.AddAliasCommand;
import seedu.address.logic.commands.budget.AddBudgetCommand;
import seedu.address.logic.commands.budget.SwitchBudgetCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.ListEventsCommand;
import seedu.address.logic.commands.expense.AddExpenseCommand;
import seedu.address.logic.commands.expense.ClearExpenseCommand;
import seedu.address.logic.commands.expense.DeleteExpenseCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand;
import seedu.address.logic.commands.expense.FindExpenseCommand;
import seedu.address.logic.commands.expense.ListExpenseCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.ui.ViewPanelCommand;


/**
 * A Serializable class that represents the user's {@code Alias} settings.
 * Guarantees: fields are present, immutable.
 */
public class AliasMappings implements Serializable {

    private Map<String, Alias> aliasNameToAliasMap;

    private static final List<String> RESERVED_COMMAND_WORDS = List.of(
            // event
            AddEventCommand.COMMAND_WORD, ListEventsCommand.COMMAND_WORD,
            //expense
            AddExpenseCommand.COMMAND_WORD, DeleteExpenseCommand.COMMAND_WORD,
            ListExpenseCommand.COMMAND_WORD, FindExpenseCommand.COMMAND_WORD,
            EditExpenseCommand.COMMAND_WORD, ClearExpenseCommand.COMMAND_WORD,
            // budget
            AddBudgetCommand.COMMAND_WORD, SwitchBudgetCommand.COMMAND_WORD,
            // alias
            AddAliasCommand.COMMAND_WORD,
            // general
            HelpCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
            ViewPanelCommand.COMMAND_WORD
    );


    // Constructors
    public AliasMappings() {
        this.aliasNameToAliasMap = new HashMap<>();
    }

    private AliasMappings(AliasMappings aliasMappings) {
        requireNonNull(aliasMappings);
        this.aliasNameToAliasMap = new HashMap<>(aliasMappings.aliasNameToAliasMap);
    }

    public Alias getAlias(String aliasName) {
        return aliasNameToAliasMap.get(aliasName);
    }

    /**
     * Returns an {@code AliasMappings} with an added {@code Alias}.
     */
    public AliasMappings addAlias(Alias alias) throws RecursiveAliasException {
        if (aliasCommandWordIsAlias(alias)) {
            throw new RecursiveAliasException(alias);
        }
        AliasMappings aliasMappings = new AliasMappings(this);
        aliasMappings.aliasNameToAliasMap.put(alias.getAliasName(), alias);
        return aliasMappings;
    }

    /**
     * Returns true if an {@code Alias} is mapped to the given {@code String aliasName}, and false otherwise.
     * @param aliasName The alias name to check if it has a mapped {@code Alias}.
     * @return true if an {@code Alias} is mapped to the given {@code String aliasName}, and false otherwise.
     */
    public boolean aliasWithNameExists(String aliasName) {
        return aliasNameToAliasMap.containsKey(aliasName);
    }

    /**
     * Returns true if an alias' name is a reserved command word and false otherwise.
     * @param alias The alias which needs to be checked.
     * @return true if an alias name is a reserved command word and false otherwise.
     */
    public boolean aliasUsesReservedName(Alias alias) {
        String aliasName = alias.getAliasName();

        return RESERVED_COMMAND_WORDS.contains(aliasName);
    }

    /**
     * Returns true if the Alias' command word references another Alias' alias name.
     */
    public boolean aliasCommandWordIsAlias(Alias alias) {
        String commandWord = alias.getCommandWord();
        return aliasWithNameExists(commandWord);
    }

    @Override
    public int hashCode() {
        return aliasNameToAliasMap.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AliasMappings)) {
            return false;
        }

        AliasMappings other = (AliasMappings) obj;

        // contains the same keys, for the keys it contains, it maps to the same inputs
        if (!aliasNameToAliasMap.keySet().equals(other.aliasNameToAliasMap.keySet())) {
            return false;
        }
        return aliasNameToAliasMap
                .keySet()
                .stream()
                .allMatch(key -> aliasNameToAliasMap.get(key).equals(other.aliasNameToAliasMap.get(key)));
    }

}

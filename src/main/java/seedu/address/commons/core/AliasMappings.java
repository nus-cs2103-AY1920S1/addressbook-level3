package seedu.address.commons.core;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.exceptions.RecursiveAliasException;
import seedu.address.logic.commands.alias.AliasCommand;
import seedu.address.logic.commands.budget.AddBudgetCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.expense.AddCommand;
import seedu.address.logic.commands.expense.ClearCommand;
import seedu.address.logic.commands.expense.DeleteCommand;
import seedu.address.logic.commands.expense.EditCommand;
import seedu.address.logic.commands.expense.FindCommand;
import seedu.address.logic.commands.expense.ListCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;


/**
 * A Serializable class that represents the user's {@code Alias} settings.
 * Guarantees: fields are present, immutable.
 */
public class AliasMappings implements Serializable {

    private Map<String, Alias> aliasNameToAliasMap;

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
        switch (aliasName) {
        case AddCommand.COMMAND_WORD:
            // fallthrough
        case AliasCommand.COMMAND_WORD:
            // fallthrough
        case AddBudgetCommand.COMMAND_WORD:
            // fallthrough
        case AddEventCommand.COMMAND_WORD:
            // fallthrough
        case ClearCommand.COMMAND_WORD:
            // fallthrough
        case DeleteCommand.COMMAND_WORD:
            // fallthrough
        case EditCommand.COMMAND_WORD:
            // fallthrough
        case ExitCommand.COMMAND_WORD:
            // fallthrough
        case FindCommand.COMMAND_WORD:
            // fallthrough
        case HelpCommand.COMMAND_WORD:
            // fallthrough
        case ListCommand.COMMAND_WORD:
            return true;
        default:
            return false;
        }
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

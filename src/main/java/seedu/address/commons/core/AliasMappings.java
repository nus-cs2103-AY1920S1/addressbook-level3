package seedu.address.commons.core;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;


/**
 * A Serializable class that represents the user's {@code Alias} settings.
 * Guarantees: fields are present, immutable.
 */
public class AliasMappings implements Serializable {

    private Map<String, Alias> aliasesMappings;

    // Constructors
    public AliasMappings() {
        this.aliasesMappings = new HashMap<>();
    }

    private AliasMappings(AliasMappings aliasMappings) {
        requireNonNull(aliasMappings);
        this.aliasesMappings = new HashMap<>(aliasMappings.aliasesMappings);
    }

    public Alias getAlias(String aliasName) {
        return aliasesMappings.get(aliasName);
    }

    /**
     * Returns a {@code UserAliasSettings} with an added {@code Alias}.
     */
    public AliasMappings addAlias(Alias alias) {
        AliasMappings aliasMappings = new AliasMappings(this);
        aliasMappings.aliasesMappings.put(alias.getAliasName(), alias);
        return aliasMappings;
    }

    /**
     * Returns true if an {@code Alias} is mapped to the given {@code String aliasName}, and false otherwise.
     * @param aliasName The alias name to check if it has a mapped {@code Alias}.
     * @return true if an {@code Alias} is mapped to the given {@code String aliasName}, and false otherwise.
     */
    public boolean aliasExists(String aliasName) {
        return aliasesMappings.containsKey(aliasName);
    }

    /**
     * Returns true if an alias name is not a reserved command word and false otherwise.
     * @param aliasName The alias name which needs to be checked.
     * @return true if an alias name is not a reserved command word and false otherwise.
     */
    public boolean aliasNameIsReserved(String aliasName) {
        switch (aliasName) {
        case AddCommand.COMMAND_WORD:
            // fallthrough
        case AliasCommand.COMMAND_WORD:
            // fallthrough
        case EditCommand.COMMAND_WORD:
            // fallthrough
        case DeleteCommand.COMMAND_WORD:
            // fallthrough
        case ClearCommand.COMMAND_WORD:
            // fallthrough
        case FindCommand.COMMAND_WORD:
            // fallthrough
        case ListCommand.COMMAND_WORD:
            // fallthrough
        case ExitCommand.COMMAND_WORD:
            // fallthrough
        case HelpCommand.COMMAND_WORD:
            return true;
        default:
            return false;
        }
    }

    /**
     * Returns true if the Alias' command word references another Alias' alias name.
     */
    public boolean aliasCommandWordIsAlias(String commandWord) {
        return aliasesMappings.containsKey(commandWord);
    }

    @Override
    public int hashCode() {
        return aliasesMappings.hashCode();
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
        if (!aliasesMappings.keySet().equals(other.aliasesMappings.keySet())) {
            return false;
        }
        return aliasesMappings
                .keySet()
                .stream()
                .allMatch(key -> aliasesMappings.get(key).equals(other.aliasesMappings.get(key)));
    }

}

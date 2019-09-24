package seedu.address.commons.core;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A Serializable class that contains the user's created Aliases.
 * Guarantees: immutable.
 */
public class UserAliases implements Serializable {

    private Map<String, Alias> aliasesMappings;

    // Constructors
    public UserAliases() {
        this.aliasesMappings = new HashMap<>();
    }

    public UserAliases(UserAliases userAliases) {
        this.aliasesMappings = new HashMap<>(userAliases.aliasesMappings);
    }

    public Alias getAlias(String aliasName) {
        return aliasesMappings.get(aliasName);
    }

    public UserAliases addAlias(Alias alias) {
        UserAliases userAliases = new UserAliases(this);
        userAliases.aliasesMappings.put(alias.getAliasName(), alias);
        return userAliases;
    }

    public boolean aliasExists(String aliasName) {
        return aliasesMappings.containsKey(aliasName);
    }

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
        if (!(obj instanceof UserAliases)) {
            return false;
        }

        UserAliases other = (UserAliases) obj;

        // contains the same keys, for the keys it contains, it maps to the same inputs
        if (!aliasesMappings.keySet().equals(other.aliasesMappings.keySet())) {
            return false;
        }
        return aliasesMappings
                .keySet()
                .stream()
                .allMatch(key -> aliasesMappings.get(key).equals(other.aliasesMappings.get(key)));
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Aliases : ");
//        for (Alias alias : aliasesMappings.values(UserAliases)) {
//            sb.append("\n").append(alias.toString());
//        }
//        return sb.toString();
//    }
}

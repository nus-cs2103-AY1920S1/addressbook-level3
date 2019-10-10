package seedu.address.model;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A class that contains alias table settings.
 */
public class AliasTable {
    private HashMap<String, String> aliasTable;

    public AliasTable() {
        aliasTable = new HashMap<>();
    }

    public static AliasTable getDefaultAliasTable() {
        AliasTable rtv = new AliasTable();
        rtv.addAlias("h", "help")
                .addAlias("a", "add")
                .addAlias("e", "exit");
        return rtv;
    }

    /**
     * Apply a suitable alias to the input command text. A suitable alias is an alias that matches the regex
     * "{alias}($|\\s).*".
     * If multiple matches exists, this method chooses the longest matching alias.
     * @param commandText Command for alias to be applied to.
     * @return Command that alias has been applied to.
     */
    public String applyAlias(String commandText) {
        String aliasedCommand = commandText;
        int maxLength = 0;

        // match for the longest alias
        for (String key: aliasTable.keySet()) {
            if (key.length() > maxLength && commandText.matches(Pattern.quote(key) + "($|\\s).*")) {
                aliasedCommand = commandText.replace(key, aliasTable.get(key));
                maxLength = key.length();
            }
        }
        return aliasedCommand;
    }

    /**
     * Adds an alias into the alias table.
     * @param alias Alias name
     * @param aliasTo Alias value
     * @return This object.
     */
    public AliasTable addAlias(String alias, String aliasTo) {
        aliasTable.put(alias, aliasTo);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AliasTable)) {
            return false;
        }
        AliasTable other = (AliasTable) o;
        return other.aliasTable.equals(this.aliasTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasTable);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nAlias table : --hidden--");
        return sb.toString();
    }
}

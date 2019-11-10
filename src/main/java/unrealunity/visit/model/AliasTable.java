package unrealunity.visit.model;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
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
            Pattern pattern = Pattern.compile(Pattern.quote(key) + "($|\\s)(.*)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(commandText);
            if (key.length() > maxLength && matcher.matches()) {
                aliasedCommand = matcher.replaceFirst(aliasTable.get(key) + "$1$2");
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

    /**
     * Removes an alias from the alias table.
     * @param alias Alias name
     * @return Returns true if alias exists in alias table and removed successfully, returns false otherwise.
     */
    public boolean removeAlias(String alias) {
        return aliasTable.remove(alias) != null;
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
        sb.append("\nAlias table :\n");
        sb.append(getAlias(false));
        return sb.toString();
    }

    public String getAlias(boolean reusable) {
        StringBuilder sb = new StringBuilder();
        for (String key: aliasTable.keySet()) {
            if (reusable) {
                sb.append(String.format("alias l/%s v/%s\n", key, aliasTable.get(key)));
            } else {
                sb.append(String.format("%s ⟶ %s\n", key, aliasTable.get(key)));
            }
        }
        return sb.toString();
    }
}

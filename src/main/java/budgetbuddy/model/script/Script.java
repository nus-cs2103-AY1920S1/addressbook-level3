package budgetbuddy.model.script;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import budgetbuddy.model.attributes.Description;

/**
 * Represents a script.
 */
public class Script {
    private final ScriptName name;
    private final String code;
    private final Description description;

    /**
     * Creates a Script.
     *
     * @param name The name of the script
     * @param description The description of the script
     * @param code The script's code
     */
    public Script(ScriptName name, Description description, String code) {
        requireAllNonNull(name, description, code);
        this.name = name;
        this.description = description;
        this.code = code;
    }

    /**
     * Returns this script's name.
     *
     * @return the name
     */
    public ScriptName getName() {
        return name;
    }

    /**
     * Return this script's description.
     *
     * @return the description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Return this script's code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Script script = (Script) o;
        return name.equals(script.name) && code.equals(script.code) && description.equals(script.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, description);
    }
}

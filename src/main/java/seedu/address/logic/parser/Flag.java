package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;

/**
 * A flag that marks the beginning of a type that the command is applied to.
 * E.g. '-p' in 'delete -p 1'.
 */
public class Flag {

    public static final String MESSAGE_INVALID_FLAG = "The valid flags are "
        + FLAG_PERSON + " and " + FLAG_EVENT + ".";

    private final String flag;

    public Flag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public static boolean isValidFlag(String args) {
        return args.equals(FLAG_PERSON) || args.equals(FLAG_EVENT);
    }

    @Override
    public String toString() {
        return flag;
    }

    @Override
    public int hashCode() {
        return flag == null ? 0 : flag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flag)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Flag otherFlag = (Flag) obj;
        return otherFlag.getFlag().equals(getFlag());
    }
}

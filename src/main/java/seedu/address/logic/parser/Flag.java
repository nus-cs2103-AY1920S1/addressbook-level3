package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECORD;
import static seedu.address.logic.parser.CliSyntax.FLAG_TRAINING;

/**
 * A flag that marks the beginning of a type that the command is applied to.
 * E.g. '-p' in 'delete -p 1'.
 */
public class Flag {

    private final String flag;

    public Flag(String flag) {
        requireNonNull(flag);
        this.flag = flag;
    }

    /**
     * Checks if the flag is either for person, event or training.
     */
    public static boolean isValidFlag(String args) {
        return args.equals(FLAG_PERSON.toString())
            || args.equals(FLAG_EVENT.toString())
            || args.equals(FLAG_TRAINING.toString())
            || args.equals(FLAG_RECORD.toString());
    }

    public String getFlag() {
        return flag;
    }

    public String toString() {
        return getFlag();
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
        return otherFlag.getFlag().equals(flag);
    }
}

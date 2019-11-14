package seedu.address.model.entity;

/**
 * Represents a CommandType to indicate the CommandType of a command.
 * Guarantees: CommandType values are validated according to enum type,
 * immutable.
 */
public enum CommandType {
    P, // Participant
    M, // Mentor
    T, // Team
    HM, // History
    H, // History
    L; // for Leaderboard and TopTeams Command

    // Constants
    public static final String MESSAGE_CONSTRAINTS =
            "CommandType should be a string of either one of the following values:\n"
            + "P: to indicate that a command is of type Participant\n"
            + "M: to indicate that a command is of type Mentor\n" + "T: to indicate that a command is of type Team\n"
            + "H: to indicate that a command is of type History\n"
            + "HM: to indicate that a command is of type Home(Brings user to homepage).\n";

    /**
     * Returns if a given string is a valid CommandType.
     *
     * @param test String of type.
     * @return boolean whether test is in valid type format.
     */
    public static boolean isValidType(String test) {
        try {
            CommandType result = CommandType.valueOf(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

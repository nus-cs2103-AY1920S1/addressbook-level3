package seedu.billboard.model.history;

import java.util.Stack;

/**
 * Object to store all the executed command.
 */
public class CommandHistory {
    public static final String HAVE_HISTORY = "The following is the command history:%s";
    private static Stack<String> cmdList = new Stack<>();
    private static int statePointer = 0;

    /**
     * Add executed command into command history.
     *
     * @param cmd command to be added into the history.
     */
    public static void addCmdHistory(String cmd) {
        cmdList.push(cmd);
        statePointer = 0;
    }

    /**
     * Return the one next command.
     *
     * @return String return the one next command.
     */
    public static String peekNextCmd() {
        if (cmdList.empty()) {
            return "";
        }
        if (statePointer == cmdList.size()) {
            return cmdList.get(0);
        } else {
            statePointer++;
            return cmdList.get(cmdList.size() - statePointer);
        }
    }

    /**
     * Return the one previous command.
     *
     * @return String return the one previous command.
     */
    public static String peekPreviousCmd() {
        if (cmdList.empty()) {
            return "";
        }
        if (statePointer == 0) {
            return "";
        } else if (statePointer == 1) {
            statePointer--;
            return "";
        } else {
            statePointer--;
            return cmdList.get(cmdList.size() - statePointer);
        }
    }

    /**
     * Check whether there is any command executed before.
     *
     * @return boolean return true if the cmdList is not empty.
     */
    public static boolean hasCommand() {
        return cmdList.size() != 1;
    }

    /**
     * Return all the history commands as a string.
     *
     * @return String return the string containing all the history commands.
     */
    public static String getCmdHistory() {
        String result = "";
        for (int i = cmdList.size() - 1; i > 0; i--) {
            result = result.concat("\n\t");
            result = result.concat(cmdList.get(i - 1));
        }
        return String.format(HAVE_HISTORY, result);
    }

    /**
     * Clear command history.
     */
    public static void clearHistory() {
        cmdList.clear();
        statePointer = 0;
    }
}

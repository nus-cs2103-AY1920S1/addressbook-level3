package seedu.address.logic.commands;

/**
 * Contains Command Line Interface (CLI) Command Words common to multiple commands.
 */
public class GenericCommandWord {

    public static final String ADD = "add";
    public static final String LIST = "list";
    public static final String DELETE = "delete";
    public static final String EDIT = "edit";

    public static boolean isGeneric(String commandWord) {
        return commandWord.equals(ADD) || commandWord.equals(LIST) || commandWord.equals(DELETE)
                || commandWord.equals(EDIT);
    }

}

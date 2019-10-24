package seedu.savenus.model.info;

import seedu.savenus.logic.commands.MakeSortCommand;

public class MakeSortInfo {
    public static final String COMMAND_WORD = MakeSortCommand.COMMAND_WORD;

    public static final String INFORMATION =
            "Create your own custom comparator, which will "
                    + "be stored within the storage of the application, using specific FIELD and DIRECTION.";
    public static final String USAGE =
            "makesort PRICE ASC";

    public static final String OUTPUT =
            "You have successfully overridden your own custom comparator!";
}

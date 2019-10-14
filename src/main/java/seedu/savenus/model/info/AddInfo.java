package seedu.savenus.model.info;

import seedu.savenus.logic.commands.AddCommand;

public class AddInfo {

    public static final String COMMAND_WORD = AddCommand.COMMAND_WORD;

    public static final String INFORMATION =
            "Add command allows you to add a new Food item on the list.\n"
            + "The added food item information will depend on the following factors: \n"
            + "Name (Compulsory)\n"
            + "Price (Compulsory)\n"
            + "Category (Compulsory)\n"
            + "Location with\n"
            + "Multiple tags\n"
            + "Opening hours\n"
            + "Restrictions\n\n";

    public static final String USAGE =
            "add n/Mala Xiang Guo p/8.90 c/Chinese l/The deck t/Spicy t/Vegetables o/1000 1930 r/Not halal";

    public static final String OUTPUT =
            "Food with name Mala Xiang Guo and the specified description will be added to the food list.";
}

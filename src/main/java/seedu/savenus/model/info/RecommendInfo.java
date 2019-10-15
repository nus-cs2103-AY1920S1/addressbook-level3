package seedu.savenus.model.info;

import seedu.savenus.logic.commands.RecommendCommand;

public class RecommendInfo {

    public static final String COMMAND_WORD = RecommendCommand.COMMAND_WORD;

    public static final String INFORMATION = "Recommend command allows you obtain a new list of Food item.\n\n"
            + "The recommended Food item list will depend on the following factor:\n"
            + "The amount of money in your wallet\n"
            + "The price of the Food item\n\n";

    public static final String USAGE = "recommend";

    public static final String OUTPUT = "New Food item list will be returned based on your wallet and Food price.";
}

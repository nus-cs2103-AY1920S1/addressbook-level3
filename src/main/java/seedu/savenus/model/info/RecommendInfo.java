package seedu.savenus.model.info;

import seedu.savenus.logic.commands.RecommendCommand;

//@@author robytanama
/**
 * Contains information on Recommend command.
 */
public class RecommendInfo {

    public static final String COMMAND_WORD = RecommendCommand.COMMAND_WORD;

    public static final String INFORMATION =
            "The recommend command allows you to obtain a list of recommended food items.\n\n"
            + "The recommended Food item list will depend on the following factors:\n"
            + " - Liked and disliked tags, categories and locations\n"
            + " - Matched tags, categories and locations in purchase history\n"
            + " - Matched food item in purchase history\n"
            + " - Recency of food purchase\n"
            + " - Current amount of money in your wallet\n"
            + " - Price of the food item\n\n";

    public static final String USAGE = "recommend";

    public static final String OUTPUT =
            "The recommended item list will be returned based on the factors described above.";
}

package seedu.address.logic.commands;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.answerable.Answerable;


import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEFAULT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

public class StartQuizCommand extends Command{

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts quiz based on Category, "
            + "Difficulty or Default.\n"
            + "Parameters: "
            + PREFIX_CATEGORY
            + PREFIX_DIFFICULTY
            + PREFIX_DEFAULT
            + "\n"
            + "Example: " + COMMAND_WORD + PREFIX_DEFAULT;

    private static final String MESSAGE_SUCCESS = "Starting Quiz!";

    private static Object currentIteratedAnswerable;
    private final String startQuizkeyword;

    public StartQuizCommand(String startQuizKeywords) {
        this.startQuizkeyword = startQuizKeywords;
    }


    @Override
    public CommandResult execute(Model model) throws ParseException {
        requireNonNull(model);
        List<Answerable> currentList = model.getFilteredAnswerableList();

        AddressBookParser.parseCommand("clear");
        List<Answerable> showingList = currentList;

        /*
        * TODO: need to add showAnswerable method in model.
        *  This will allow us to show individual Answerables.
        *  Instead of the whole list.
         */
        model.getFilteredAnswerableList().sort(startQuizkeyword);
        model.showAnswerable();

        for (Answerable iteratedAnswerable : currentList) {
            System.out.println(iteratedAnswerable);
        }
        model.updateFilteredAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE);

        return new CommandResult(MESSAGE_SUCCESS);

    }
}

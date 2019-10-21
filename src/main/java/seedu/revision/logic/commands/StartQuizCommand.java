package seedu.revision.logic.commands;

import seedu.revision.logic.parser.AddressBookParser;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;


import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DEFAULT;
import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

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
//        model.getFilteredAnswerableList().sort(startQuizkeyword);
//        model.showAnswerable();

        for (Answerable iteratedAnswerable : currentList) {
            System.out.println(iteratedAnswerable);
        }
        model.updateFilteredAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE);

        return new CommandResult(MESSAGE_SUCCESS, false, false, true);

    }
}

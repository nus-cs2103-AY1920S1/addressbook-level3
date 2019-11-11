package seedu.guilttrip.logic.parser.addcommandparsers;

import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.guilttrip.logic.commands.addcommands.AddAutoExpenseCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.Frequency;

/**
 * Parses input arguments and creates a new AddAutoExpenseCommand object
 */
public class AddAutoExpenseCommandParser implements Parser<AddAutoExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddAutoExpenseCommand and returns an AddAutoExpenseCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAutoExpenseCommand parse(String args) throws ParseException {
        // must haves: desc, freq, amount, category
        // optional: tag, date
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_FREQ, PREFIX_AMOUNT,
                PREFIX_DATE, PREFIX_TAG, PREFIX_CATEGORY);

        ParserUtil.errorIfCompulsoryPrefixMissing(AddAutoExpenseCommand.MESSAGE_USAGE, argMultimap, false,
                PREFIX_DESC, PREFIX_AMOUNT, PREFIX_CATEGORY, PREFIX_FREQ);

        Description desc = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        Frequency freq = ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQ).get());
        Amount amt = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        String categoryName = argMultimap.getValue(PREFIX_CATEGORY).get();
        Category category = ParserUtil.parseCategory(categoryName, "Expense");
        Date date = ParserUtil.parseTime(argMultimap.getValue(PREFIX_DATE));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        AutoExpense autoExpense = new AutoExpense(category, desc, amt,
                tagList, freq, date);
        return new AddAutoExpenseCommand(autoExpense);
    }

}

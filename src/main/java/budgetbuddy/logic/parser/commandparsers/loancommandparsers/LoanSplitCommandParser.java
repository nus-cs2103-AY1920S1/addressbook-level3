package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.ArrayList;
import java.util.List;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanSplitCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Parses input arguments and creates a new LoanSplitCommand object.
 */
public class LoanSplitCommandParser implements CommandParser<LoanSplitCommand> {
    @Override
    public String name() {
        return LoanSplitCommand.COMMAND_WORD;
    }

    @Override
    public LoanSplitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_AMOUNT);

        List<Person> persons = new ArrayList<Person>();
        List<Amount> amounts = new ArrayList<Amount>();

        String[] personsArr = argMultiMap.getValue(PREFIX_PERSON).get().split("\\s+");
        for (String person : personsArr) {
            persons.add(new Person(CommandParserUtil.parseName(person)));
        }

        String[] amountsArr = argMultiMap.getValue(PREFIX_AMOUNT).get().split("\\s+");
        for (String amount : amountsArr) {
            amounts.add(CommandParserUtil.parseAmount(amount));
        }

        try {
            return new LoanSplitCommand(persons, amounts);
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }
}

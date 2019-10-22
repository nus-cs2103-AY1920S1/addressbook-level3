package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import java.util.ArrayList;
import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.loancommands.MultiLoanCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.person.Person;

/**
 * Represents a command parser that can handle multiple loan targets.
 */
public abstract class MultiLoanCommandParser implements CommandParser<MultiLoanCommand> {

    protected List<Index> loanIndices;
    protected List<Person> persons;

    public MultiLoanCommandParser() {
        loanIndices = new ArrayList<Index>();
        persons = new ArrayList<Person>();
    }

    /**
     * Parses multiple loan targets into two lists to be passed on to Command classes.
     * {@code loanIndices} is used for targeting individual loans.
     * {@code persons} is used for targeting all the loans of specific persons.
     * @param args The loan targets (as a String) to be parsed (e.g. 1 3 4 Mary).
     * @throws ParseException If parsing fails at any point.
     */
    protected void parseMultiLoanArgs(String args) throws ParseException {
        loanIndices.clear();
        persons.clear();
        String[] argsArray = args.trim().split("\\s+");
        for (String arg : argsArray) {
            try {
                loanIndices.add(CommandParserUtil.parseIndex(arg));
            } catch (ParseException e) {
                persons.add(new Person(CommandParserUtil.parseName(arg)));
            }
        }
    }
}

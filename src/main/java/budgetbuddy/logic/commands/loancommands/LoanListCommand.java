package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.DATE_EXAMPLE;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_IN;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_OUT;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_PAID;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_UNPAID;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ARG_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ARG_DATE;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ARG_PERSON;
import static budgetbuddy.model.loan.LoanFilters.FILTER_ALL;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Loan;

/**
 * Lists loans.
 */
public class LoanListCommand extends Command {

    public static final String COMMAND_WORD = "loan list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all loans.\n"
            + "Parameters: "
            + String.format("[%s|%s|", KEYWORD_LOAN_OUT, KEYWORD_LOAN_IN)
            + String.format("%s|%s ...] ", KEYWORD_LOAN_UNPAID, KEYWORD_LOAN_PAID)
            + "[" + PREFIX_PERSON + "PERSON ...] "
            + "[" + PREFIX_AMOUNT + "AMOUNT ...] "
            + "[" + PREFIX_DATE + "DATE ...] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION ...] "
            + String.format("[%s<%s|%s|%s>]\n", PREFIX_SORT, SORT_ARG_DATE, SORT_ARG_AMOUNT, SORT_ARG_PERSON)
            + "Example: "
            + String.format("%s %s %s ", COMMAND_WORD, KEYWORD_LOAN_OUT, KEYWORD_LOAN_UNPAID)
            + String.format("%sJohn %sMary %s%s ", PREFIX_PERSON, PREFIX_PERSON, PREFIX_DATE, DATE_EXAMPLE)
            + String.format("%s%s", PREFIX_SORT, SORT_ARG_AMOUNT);

    public static final String MESSAGE_SUCCESS = "Loans listed.";
    public static final String MESSAGE_SORTED = "Sorted.";
    public static final String MESSAGE_FILTERED = "Filtered.";

    public static final String MESSAGE_NO_LOANS =
            "No loans found in your list. Nobody owes anybody money... for now.";

    private Optional<Comparator<Loan>> optionalSorter;
    private List<Predicate<Loan>> filters;

    public LoanListCommand(Optional<Comparator<Loan>> optionalSorter, List<Predicate<Loan>> filters) {
        this.optionalSorter = optionalSorter;
        this.filters = new ArrayList<Predicate<Loan>>();
        this.filters.addAll(filters);
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getLoansManager());

        if (model.getLoansManager().getLoans().isEmpty()) {
            return new CommandResult(MESSAGE_NO_LOANS, CommandCategory.LOAN);
        }

        String resultMessage = MESSAGE_SUCCESS;

        if (optionalSorter.isPresent()) {
            model.getLoansManager().sortLoans(optionalSorter.get());
            resultMessage += " " + MESSAGE_SORTED;
        }

        model.getLoansManager().updateFilteredList(filters.stream().reduce(Predicate::and).orElse(FILTER_ALL));
        if (!filters.isEmpty()) {
            resultMessage += " " + MESSAGE_FILTERED;
        }

        return new CommandResult(resultMessage, CommandCategory.LOAN);
    }
}

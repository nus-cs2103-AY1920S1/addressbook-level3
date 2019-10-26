package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;
import static budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanListCommandParser.SORT_ARG_AMOUNT;
import static budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanListCommandParser.SORT_ARG_DATE;
import static budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanListCommandParser.SORT_ARG_PERSON;

import java.util.Comparator;
import java.util.Optional;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.LoansManager.SortBy;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Loan;

/**
 * Lists loans.
 */
public class LoanListCommand extends Command {

    public static final String COMMAND_WORD = "loan list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all loans.\n"
            + "Parameters: "
            + "[<person number ...>]"
            + "[o|i] "
            + String.format("[%s<%s|%s|%s>]\n", PREFIX_SORT, SORT_ARG_DATE, SORT_ARG_AMOUNT, SORT_ARG_PERSON)
            + "Example: "
            + String.format("%s 1 2 o %s%s", COMMAND_WORD, PREFIX_SORT, SORT_ARG_AMOUNT);

    public static final String MESSAGE_SUCCESS = "Loans listed.";
    public static final String MESSAGE_SUCCESS_PERSON = "Loans listed by person in alphabetical order.";
    public static final String MESSAGE_SUCCESS_AMOUNT = "Loans listed by amount.";
    public static final String MESSAGE_SUCESS_DATE = "Loans listed by date.";
    public static final String MESSAGE_NO_LOANS =
            "No loans found in your list. Nobody owes anybody money... for now.";

    private Optional<SortBy> optionalSortBy;

    public LoanListCommand(Optional<SortBy> optionalSortBy) {
        this.optionalSortBy = optionalSortBy;
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getLoansManager());

        String resultMessage = MESSAGE_SUCCESS;

        if (optionalSortBy.isPresent()) {
            Comparator<Loan> sorter;

            switch (optionalSortBy.get()) {
            case PERSON:
                sorter = LoansManager.PERSON_SORTER;
                resultMessage = MESSAGE_SUCCESS_PERSON;
                break;
            case AMOUNT:
                sorter = LoansManager.AMOUNT_SORTER;
                resultMessage = MESSAGE_SUCCESS_AMOUNT;
                break;
            default:
                sorter = LoansManager.DATE_SORTER;
                resultMessage = MESSAGE_SUCESS_DATE;
                break;
            }

            model.getLoansManager().sortLoans(sorter);
        }

        return new CommandResult(
                model.getLoansManager().getLoans().isEmpty() ? MESSAGE_NO_LOANS : resultMessage,
                CommandCategory.LOAN);
    }
}

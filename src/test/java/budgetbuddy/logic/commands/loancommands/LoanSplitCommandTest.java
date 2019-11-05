package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static budgetbuddy.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;
import budgetbuddy.testutil.loanutil.DebtorBuilder;
import budgetbuddy.testutil.loanutil.LoanBuilder;
import budgetbuddy.testutil.loanutil.TypicalPersons;

public class LoanSplitCommandTest {

    private static final Amount AMOUNT_ZERO = new Amount(0L);
    private static final Amount AMOUNT_FIVE = new Amount(500L);
    private static final Amount AMOUNT_TEN = new Amount(1000L);
    private static final Amount AMOUNT_TWENTY = new Amount(2000L);
    private static final Amount AMOUNT_THIRTY = new Amount(3000L);
    private static final Amount AMOUNT_FORTY = new Amount(4000L);
    private static final Amount AMOUNT_FIFTY = new Amount(5000L);
    private static final Amount AMOUNT_SIXTY = new Amount(6000L);

    private Model model;

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoanSplitCommand(
                null, null, null,
                null, null, null));
    }

    @Test
    public void constructor_personsAmountsNumberMismatch_throwsCommandException() {
        assertThrows(CommandException.class, () -> new LoanSplitCommand(
                getDefaultPersons(), new ArrayList<>(), new ArrayList<>(),
                Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    public void execute_alreadySplit_throwsCommandException() throws CommandException {
        List<Amount> amounts = List.of(AMOUNT_FIFTY, AMOUNT_FIFTY, AMOUNT_FIFTY);
        LoanSplitCommand loanSplitCommand = new LoanSplitCommand(
                getDefaultPersons(), amounts, new ArrayList<>(),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertThrows(CommandException.class,
                LoanSplitCommand.MESSAGE_ALREADY_SPLIT_EQUALLY, () -> loanSplitCommand.execute(model));
    }

    @Test
    public void execute_maxSharesExceedTotalAmount_throwsCommandException() throws CommandException {
        List<Long> maxShares = List.of(AMOUNT_SIXTY.toLong(), AMOUNT_SIXTY.toLong(), AMOUNT_SIXTY.toLong());
        LoanSplitCommand loanSplitCommand = new LoanSplitCommand(
                getDefaultPersons(), getDefaultAmounts(), maxShares,
                Optional.empty(), Optional.empty(), Optional.empty());

        assertThrows(CommandException.class,
                LoanSplitCommand.MESSAGE_MAX_SHARES_EXCEED_TOTAL_AMOUNT, () -> loanSplitCommand.execute(model));
    }

    @Test
    public void execute_validInput_splitSuccess() throws CommandException {
        LoanSplitCommand loanSplitCommand = new LoanSplitCommand(
                getDefaultPersons(), getDefaultAmounts(), new ArrayList<>(),
                Optional.empty(), Optional.empty(), Optional.empty());

        CommandResult expectedCommandResult =
                new CommandResult(LoanSplitCommand.MESSAGE_SUCCESS, CommandCategory.LOAN_SPLIT);
        Model expectedModel = new ModelManager();
        expectedModel.getLoansManager().setDebtors(getDefaultDebtors());

        assertCommandSuccess(loanSplitCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validInputWithMaxShare_splitSuccess() throws CommandException {
        List<Long> maxShares = List.of(AMOUNT_TEN.toLong());
        LoanSplitCommand loanSplitCommand = new LoanSplitCommand(
                getDefaultPersons(), getDefaultAmounts(), maxShares,
                Optional.empty(), Optional.empty(), Optional.empty());

        CommandResult expectedCommandResult =
                new CommandResult(LoanSplitCommand.MESSAGE_SUCCESS, CommandCategory.LOAN_SPLIT);
        Model expectedModel = new ModelManager();
        expectedModel.getLoansManager().setDebtors(getDefaultDebtorsWithMaxShare());

        assertCommandSuccess(loanSplitCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validInputWithOptionalUser_splitSuccessAndLoansAdded() throws CommandException {
        LoanSplitCommand loanSplitCommand = new LoanSplitCommand(
                getDefaultPersons(), getDefaultAmounts(), new ArrayList<>(),
                Optional.of(TypicalPersons.ELLE), Optional.of(new Description("Test")), Optional.of(LocalDate.now()));

        CommandResult expectedCommandResult =
                new CommandResult(LoanSplitCommand.MESSAGE_SUCCESS_LOANS_ADDED, CommandCategory.LOAN_SPLIT);
        Model expectedModel = new ModelManager();
        expectedModel.getLoansManager().setDebtors(getDefaultDebtorsWithOptionalUser());
        expectedModel.getLoansManager().addLoan(getDefaultOptionalLoan());

        assertCommandSuccess(loanSplitCommand, model, expectedCommandResult, expectedModel);
    }

    private List<Person> getDefaultPersons() {
        return List.of(TypicalPersons.ALICE, TypicalPersons.ELLE, TypicalPersons.BENSON);
    }

    private List<Amount> getDefaultAmounts() {
        return List.of(AMOUNT_ZERO, AMOUNT_FORTY, AMOUNT_SIXTY);
    }

    private List<Debtor> getDefaultDebtors() {
        return List.of(new DebtorBuilder()
                .withDebtor(TypicalPersons.ALICE.getName().toString())
                .withCreditors(
                        List.of(TypicalPersons.BENSON.getName().toString(),
                                TypicalPersons.ELLE.getName().toString()),
                        List.of(2667L,
                                666L))
                .build());
    }

    private List<Debtor> getDefaultDebtorsWithMaxShare() {
        Debtor firstExpectedDebtor = new DebtorBuilder()
                .withDebtor(TypicalPersons.ALICE.getName().toString())
                .withCreditors(List.of(TypicalPersons.BENSON.getName().toString()), List.of(AMOUNT_TEN.toLong()))
                .build();
        Debtor secondExpectedDebtor = new DebtorBuilder()
                .withDebtor(TypicalPersons.ELLE.getName().toString())
                .withCreditors(List.of(TypicalPersons.BENSON.getName().toString()), List.of(AMOUNT_FIVE.toLong()))
                .build();
        return List.of(firstExpectedDebtor, secondExpectedDebtor);
    }

    private List<Debtor> getDefaultDebtorsWithOptionalUser() {
        return List.of(new DebtorBuilder()
                .withDebtor(TypicalPersons.ALICE.getName().toString())
                .withCreditors(
                        List.of(TypicalPersons.BENSON.getName().toString(),
                               "You"),
                        List.of(2667L,
                                666L))
                .build());
    }

    private Loan getDefaultOptionalLoan() {
        // the default optional user is ELLE
        return new LoanBuilder()
                .withPerson(TypicalPersons.ALICE.getName().toString())
                .withDirection("OUT")
                .withAmount(666L)
                .withDescription("Test")
                .build();
    }
}

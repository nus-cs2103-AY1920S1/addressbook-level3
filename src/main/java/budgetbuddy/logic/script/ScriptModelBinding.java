package budgetbuddy.logic.script;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalLong;
import java.util.Set;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.commons.util.AppUtil;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.logic.script.exceptions.ScriptException;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;

/**
 * Provides model-related convenience functions to the script environment.
 * <p>
 * This class breaks a lot of abstraction barriers in order for the scripting experience to be ergonomic.
 * The abstraction-breaking is contained within this class.
 */
public class ScriptModelBinding implements ScriptEnvironmentInitialiser {
    private final Model model;

    public ScriptModelBinding(Model model) {
        this.model = model;
    }

    @Override
    public void initialise(ScriptEngine engine) {
        engine.setVariable("bb", model);

        engine.setVariable("refreshAccountView",
                (ScriptBindingInterfaces.Void) this::scriptRefreshAccountView);
        engine.setVariable("refreshTxnView", (ScriptBindingInterfaces.Void) this::scriptRefreshTxnView);

        engine.setVariable("getAccounts", (ScriptBindingInterfaces.Void) this::scriptGetAccounts);
        engine.setVariable("getAccount", (ScriptBindingInterfaces.ObjectOnly) this::scriptGetAccount);
        engine.setVariable("getActiveAccount", (ScriptBindingInterfaces.Void) this::scriptGetActiveAccount);
        engine.setVariable("setActiveAccount",
                (ScriptBindingInterfaces.ObjectOnly) this::scriptSetActiveAccount);
        engine.setVariable("addAccount", (ScriptBindingInterfaces.StringString) this::scriptAddAccount);
        engine.setVariable("morphAccount", (ScriptBindingInterfaces.AccountObjects) this::scriptMorphAccount);
        engine.setVariable("editAccount", (ScriptBindingInterfaces.ObjectObjects) this::scriptEditAccount);
        engine.setVariable("deleteAccount", (ScriptBindingInterfaces.ObjectOnly) this::scriptDeleteAccount);

        engine.setVariable("accountName", (ScriptBindingInterfaces.AccountOnly) this::scriptAccountName);
        engine.setVariable("accountBalance",
                (ScriptBindingInterfaces.AccountOnly) this::scriptAccountBalance);
        engine.setVariable("accountDescription",
                (ScriptBindingInterfaces.AccountOnly) this::scriptAccountDescription);
        engine.setVariable("accountTxns", (ScriptBindingInterfaces.AccountOnly) this::scriptAccountTxns);

        engine.setVariable("addTxn", (ScriptBindingInterfaces.LongStringStringObjects) this::scriptAddTxn);
        engine.setVariable("editTxn",
                (ScriptBindingInterfaces.AccountTransactionObjects) this::scriptEditTxn);
        engine.setVariable("morphTxn", (ScriptBindingInterfaces.TransactionObjects) this::scriptMorphTxn);
        engine.setVariable("deleteTxn", (ScriptBindingInterfaces.AccountTransaction) this::scriptDeleteTxn);
        engine.setVariable("getShownTxns", (ScriptBindingInterfaces.Void) this::scriptGetShownTxns);
        engine.setVariable("getShownTxn", (ScriptBindingInterfaces.IntOnly) this::scriptGetShownTxn);
        engine.setVariable("editShownTxn", (ScriptBindingInterfaces.IntObjects) this::scriptEditShownTxn);
        engine.setVariable("deleteShownTxn", (ScriptBindingInterfaces.IntOnly) this::scriptDeleteShownTxn);

        engine.setVariable("txnAmount", (ScriptBindingInterfaces.TransactionOnly) this::scriptTxnAmount);
        engine.setVariable("txnDescription",
                (ScriptBindingInterfaces.TransactionOnly) this::scriptTxnDescription);
        engine.setVariable("txnDate", (ScriptBindingInterfaces.TransactionOnly) this::scriptTxnDate);
        engine.setVariable("txnDirection",
                (ScriptBindingInterfaces.TransactionOnly) this::scriptTxnDirection);
        engine.setVariable("txnCategories",
                (ScriptBindingInterfaces.TransactionOnly) this::scriptTxnCategories);

        engine.setVariable("addLoan", (ScriptBindingInterfaces.LongStringStringObjects) this::scriptAddLoan);
        engine.setVariable("morphLoan", (ScriptBindingInterfaces.LoanObjects) this::scriptMorphLoan);
        engine.setVariable("editShownLoan", (ScriptBindingInterfaces.IntObjects) this::scriptEditShownLoan);
        engine.setVariable("deleteShownLoan", (ScriptBindingInterfaces.IntOnly) this::scriptDeleteShownLoan);
        engine.setVariable("getShownLoans", (ScriptBindingInterfaces.Void) this::scriptGetShownLoans);
        engine.setVariable("getLoans", (ScriptBindingInterfaces.Void) this::scriptGetLoans);

        engine.setVariable("loanAmount", (ScriptBindingInterfaces.LoanOnly) this::scriptLoanAmount);
        engine.setVariable("loanDirection", (ScriptBindingInterfaces.LoanOnly) this::scriptLoanDirection);
        engine.setVariable("loanPerson", (ScriptBindingInterfaces.LoanOnly) this::scriptLoanPerson);
        engine.setVariable("loanDate", (ScriptBindingInterfaces.LoanOnly) this::scriptLoanDate);
        engine.setVariable("loanDescription", (ScriptBindingInterfaces.LoanOnly) this::scriptLoanDescription);
        engine.setVariable("loanIsPaid", (ScriptBindingInterfaces.LoanOnly) this::scriptLoanIsPaid);

        engine.setVariable("parseDate", (ScriptBindingInterfaces.StringOnly) this::scriptParseDate);
        engine.setVariable("makeDate", (ScriptBindingInterfaces.IntIntInt) this::scriptMakeDate);
    }

    /**
     * Provides <code>refreshAccountView()</code>.
     */
    private Object scriptRefreshAccountView() {
        model.getAccountsManager().resetFilteredAccountList();

        return null;
    }

    /**
     * Provides <code>refreshTxnView()</code>.
     */
    private Object scriptRefreshTxnView() {
        model.getAccountsManager().transactionListUpdateSource();

        return null;
    }

    /**
     * Provides <code>getAccounts() -> List&lt;Account&gt;</code>.
     */
    private List<Account> scriptGetAccounts() throws Exception {
        return model.getAccountsManager().getAccounts();
    }

    /**
     * Provides <code>getActiveAccount() -> Account</code>.
     */
    private Account scriptGetActiveAccount() throws Exception {
        return model.getAccountsManager().getActiveAccount();
    }

    /**
     * Provides <code>setActiveAccount(nameOrIndex) -> Account</code>.
     */
    private Account scriptSetActiveAccount(Object nameOrIndex) throws Exception {
        requireAllNonNull(nameOrIndex);

        Account toSet = scriptGetAccount(nameOrIndex);
        int toSetIndex = model.getAccountsManager().getAccounts().indexOf(toSet);
        model.getAccountsManager().setActiveAccountByIndex(Index.fromZeroBased(toSetIndex));

        return toSet;
    }

    /**
     * Provides <code>getAccount(nameOrIndex) -> Account</code>.
     */
    private Account scriptGetAccount(Object nameOrIndex) throws Exception {
        requireAllNonNull(nameOrIndex);

        if (nameOrIndex instanceof Integer) {
            return model.getAccountsManager().getAccount(Index.fromZeroBased((int) nameOrIndex));
        } else if (nameOrIndex instanceof String) {
            return model.getAccountsManager().getAccount(CommandParserUtil.parseName((String) nameOrIndex));
        }

        throw new IllegalArgumentException(
                "Expected integer or string, got " + nameOrIndex.getClass().getSimpleName());
    }

    /**
     * Provides <code>addAccount(name, description) -> Account</code>.
     */
    private Account scriptAddAccount(String name, String description) throws Exception {
        Account account = new Account(CommandParserUtil.parseName(name),
                CommandParserUtil.parseDescription(description), new TransactionList());
        model.getAccountsManager().addAccount(account);

        return account;
    }

    /**
     * Provides <code>morphAccount(oldAccount, { name, description }) -> Account</code>.
     */
    private Account scriptMorphAccount(Account acc, Object... optional) throws Exception {
        requireAllNonNull(acc, optional);
        ScriptObjectWrapper opt = ScriptObjectWrapper.fromOptionalVarargs(optional);

        Name name = acc.getName();
        Description desc = acc.getDescription();

        // Can't use Optional properly here because our parser functions throw... sigh Java
        String newName = opt.get("name", String.class).orElse(null);
        if (newName != null) {
            name = CommandParserUtil.parseName(newName);
        }

        String newDesc = opt.get("description", String.class).orElse(null);
        if (newDesc != null) {
            desc = CommandParserUtil.parseDescription(newDesc);
        }

        return new Account(name, desc, acc.getTransactionList(), acc.getBalance());
    }

    /**
     * Provides <code>editAccount(nameOrIndex, { name, description }) -> Account</code>.
     */
    private Account scriptEditAccount(Object nameOrIndex, Object... optional) throws Exception {
        requireAllNonNull(nameOrIndex, optional);

        Account toEdit = scriptGetAccount(nameOrIndex);
        Account newAccount = scriptMorphAccount(toEdit, optional);

        int toEditIndex = model.getAccountsManager().getAccounts().indexOf(toEdit);

        model.getAccountsManager().editAccount(Index.fromZeroBased(toEditIndex), newAccount);

        return newAccount;
    }

    /**
     * Provides <code>deleteAccount(nameOrIndex)</code>.
     */
    private Object scriptDeleteAccount(Object nameOrIndex) throws Exception {
        requireAllNonNull(nameOrIndex);

        Account toDelete = scriptGetAccount(nameOrIndex);
        int toDeleteIndex = model.getAccountsManager().getAccounts().indexOf(toDelete);
        model.getAccountsManager().deleteAccount(Index.fromZeroBased(toDeleteIndex));

        return null;
    }

    /**
     * Provides <code>accountName(account) -> string</code>
     */
    private String scriptAccountName(Account account) throws Exception {
        return account.getName().toString();
    }

    /**
     * Provides <code>accountDescription(account) -> string</code>
     */
    private String scriptAccountDescription(Account account) throws Exception {
        return account.getDescription().getDescription();
    }

    /**
     * Provides <code>accountBalance(account) -> number</code>
     */
    private long scriptAccountBalance(Account account) throws Exception {
        return account.getBalance();
    }

    /**
     * Provides <code>accountTxns(account) -> List&lt;Transaction&gt;</code>
     */
    private List<Transaction> scriptAccountTxns(Account account) throws Exception {
        return account.getTransactionList().asUnmodifiableObservableList();
    }

    /**
     * Provides <code>addTxn(amount, direction, description, { account, date, categories })
     * -> Transaction</code>.
     */
    private Transaction scriptAddTxn(long inAmount, String direction, String description,
                                     Object... optional) throws Exception {
        ScriptObjectWrapper opt = ScriptObjectWrapper.fromOptionalVarargs(optional);
        requireAllNonNull(direction, description, opt);

        Account target = opt.get("account", Account.class)
                .orElseGet(() -> model.getAccountsManager().getActiveAccount());

        LocalDate date = opt.getDate("date").orElseGet(LocalDate::now);

        String[] catStrings = opt.getArray("categories", String.class).orElse(null);
        Set<Category> cats = parseScriptTxnCategories(catStrings);

        Transaction txn =
                new Transaction(date, new Amount(inAmount), CommandParserUtil.parseDirection(direction),
                        CommandParserUtil.parseDescription(description), cats);

        target.addTransaction(txn);

        return txn;
    }

    /**
     * Provides <code>morphTxn(oldTxn, { amount, direction, description, date, categories })
     * -> Transaction</code>.
     */
    private Transaction scriptMorphTxn(Transaction txn, Object... optional) throws Exception {
        requireAllNonNull(txn);
        ScriptObjectWrapper opt = ScriptObjectWrapper.fromOptionalVarargs(optional);

        Amount amt = txn.getAmount();
        Direction dir = txn.getDirection();
        Description desc = txn.getDescription();
        LocalDate date = opt.getDate("date").orElseGet(txn::getLocalDate);
        Set<Category> cats = txn.getCategories();

        OptionalLong newAmt = opt.getIntegral("amount");
        if (newAmt.isPresent()) {
            amt = new Amount(newAmt.getAsLong());
        }

        String newDir = opt.get("direction", String.class).orElse(null);
        if (newDir != null) {
            dir = CommandParserUtil.parseDirection(newDir);
        }

        String newDesc = opt.get("description", String.class).orElse(null);
        if (newDesc != null) {
            desc = CommandParserUtil.parseDescription(newDesc);
        }

        String[] newCats = opt.getArray("categories", String.class).orElse(null);
        if (newCats != null) {
            cats = parseScriptTxnCategories(newCats);
        }

        return new Transaction(date, amt, dir, desc, cats);
    }


    /**
     * Provides <code>editTxn(account, oldTxn, { amount, direction, description, date, categories })
     * -> Transaction</code>.
     */
    private Transaction scriptEditTxn(Account acc, Transaction txn, Object... optional) throws Exception {
        requireAllNonNull(acc, txn);
        Transaction newTxn = scriptMorphTxn(txn, optional);
        int index = acc.getTransactionList().asUnmodifiableObservableList().indexOf(txn);
        if (index == -1) {
            throw new ScriptException("Could not find transaction to edit in provided account");
        }

        Index wrappedIndex = Index.fromZeroBased(index);
        acc.updateTransaction(wrappedIndex, newTxn);
        return newTxn;
    }

    /**
     * Provides <code>deleteTxn(account, txn)</code>.
     */
    private Object scriptDeleteTxn(Account acc, Transaction txn) throws Exception {
        requireAllNonNull(acc, txn);
        acc.deleteTransaction(txn);

        return null;
    }

    /**
     * Provides <code>getShownTxn(index) -> Transaction</code>.
     */
    private Transaction scriptGetShownTxn(int index) throws Exception {
        return model.getFilteredTransactions().get(index);
    }

    /**
     * Provides <code>editShownTxn(index, { amount, direction, description, date, categories })
     * -> Transaction</code>.
     */
    private Transaction scriptEditShownTxn(int index, Object... optional) throws Exception {
        Transaction toEdit = model.getFilteredTransactions().get(index);
        return scriptEditTxn(model.getAccountsManager().getActiveAccount(), toEdit, optional);
    }

    /**
     * Provides <code>deleteShownTxn(index)</code>.
     */
    private Object scriptDeleteShownTxn(int index) throws Exception {
        Transaction toDelete = model.getFilteredTransactions().get(index);
        model.getAccountsManager().getActiveAccount().deleteTransaction(toDelete);

        return null;
    }

    /**
     * Provides <code>getShownTxns() -> List&lt;Transaction&gt;</code>.
     */
    private List<Transaction> scriptGetShownTxns() throws Exception {
        return model.getFilteredTransactions();
    }

    /**
     * Provides <code>txnAmount(txn) -> number</code>.
     */
    private long scriptTxnAmount(Transaction txn) throws Exception {
        requireAllNonNull(txn);
        return txn.getAmount().toLong();
    }

    /**
     * Provides <code>txnDescription(txn) -> string</code>.
     */
    private String scriptTxnDescription(Transaction txn) throws Exception {
        requireAllNonNull(txn);
        return txn.getDescription().getDescription();
    }

    /**
     * Provides <code>txnDate(txn) -> LocalDate</code>.
     */
    private LocalDate scriptTxnDate(Transaction txn) throws Exception {
        requireAllNonNull(txn);
        return txn.getLocalDate();
    }

    /**
     * Provides <code>txnDirection(txn) -> string ("IN" | "OUT")</code>.
     */
    private String scriptTxnDirection(Transaction txn) throws Exception {
        requireAllNonNull(txn);
        return txn.getDirection().toString();
    }

    /**
     * Provides <code>txnCategories(txn) -> [string]</code>.
     */
    private String[] scriptTxnCategories(Transaction txn) throws Exception {
        requireAllNonNull(txn);
        return txn.getCategories().stream().map(Category::getCategory).toArray(String[]::new);
    }

    /**
     * Provides <code>addLoan(amount, direction, person, { description, date, paid })
     * -> Loan</code>.
     */
    private Loan scriptAddLoan(long inAmount, String direction, String person, Object... optional) throws
            Exception {
        ScriptObjectWrapper opt = ScriptObjectWrapper.fromOptionalVarargs(optional);
        requireAllNonNull(direction, person, opt);

        Description description =
                CommandParserUtil.parseDescription(opt.get("description", String.class).orElse(""));
        LocalDate date = opt.getDate("date").orElseGet(LocalDate::now);
        Status status = opt.get("paid", Boolean.class).map(in -> in ? Status.PAID : Status.UNPAID)
                .orElse(Status.UNPAID);

        Loan loan = new Loan(new Person(CommandParserUtil.parseName(person)),
                CommandParserUtil.parseDirection(direction), new Amount(inAmount), date, description, status);

        model.getLoansManager().addLoan(loan);

        return loan;
    }

    /**
     * Provides <code>morphLoan(loan, { amount, direction, person, description, date, paid })
     * -> Loan</code>.
     */
    private Loan scriptMorphLoan(Loan loan, Object... optional) throws Exception {
        requireAllNonNull(loan);
        ScriptObjectWrapper opt = ScriptObjectWrapper.fromOptionalVarargs(optional);

        Amount amt = loan.getAmount();
        Direction dir = loan.getDirection();
        Person person = loan.getPerson();
        Description desc = loan.getDescription();
        LocalDate date = opt.getDate("date").orElseGet(loan::getDate);
        Status status = opt.get("paid", Boolean.class).map(in -> in ? Status.PAID : Status.UNPAID)
                .orElseGet(loan::getStatus);

        OptionalLong newAmt = opt.getIntegral("amount");
        if (newAmt.isPresent()) {
            amt = new Amount(newAmt.getAsLong());
        }

        String newDir = opt.get("direction", String.class).orElse(null);
        if (newDir != null) {
            dir = CommandParserUtil.parseDirection(newDir);
        }

        String newPerson = opt.get("person", String.class).orElse(null);
        if (newPerson != null) {
            person = new Person(CommandParserUtil.parseName(newPerson));
        }

        String newDesc = opt.get("description", String.class).orElse(null);
        if (newDesc != null) {
            desc = CommandParserUtil.parseDescription(newDesc);
        }

        return new Loan(person, dir, amt, date, desc, status);
    }

    /**
     * Provides <code>editShownLoan(index, { amount, direction, person, description, date, paid })
     * -> Loan</code>.
     */
    private Loan scriptEditShownLoan(int index, Object... optional) throws Exception {
        Index indexWrapped = Index.fromZeroBased(index);
        Loan toEdit = model.getLoansManager().getLoan(indexWrapped);
        Loan newLoan = scriptMorphLoan(toEdit, optional);
        model.getLoansManager().editLoan(indexWrapped, newLoan);
        return newLoan;
    }

    /**
     * Provides <code>deleteShownLoan(index)</code>.
     */
    private Object scriptDeleteShownLoan(int index) throws Exception {
        model.getLoansManager().deleteLoan(Index.fromZeroBased(index));
        return null;
    }

    /**
     * Provides <code>getShownLoans() -> List&lt;Loan&gt;</code>.
     */
    private List<Loan> scriptGetShownLoans() throws Exception {
        return model.getLoansManager().getFilteredLoans();
    }

    /**
     * Provides <code>getLoans() -> List&lt;Loan&gt;</code>.
     */
    private List<Loan> scriptGetLoans() throws Exception {
        return model.getLoansManager().getLoans();
    }

    /**
     * Provides <code>loanAmount(loan) -> number</code>
     */
    private long scriptLoanAmount(Loan loan) throws Exception {
        requireAllNonNull(loan);
        return loan.getAmount().toLong();
    }

    /**
     * Provides <code>loanDirection(loan) -> string ("IN" | "OUT")</code>.
     */
    private String scriptLoanDirection(Loan loan) throws Exception {
        requireAllNonNull(loan);
        return loan.getDirection().toString();
    }

    /**
     * Provides <code>loanPerson(loan) -> string</code>.
     */
    private String scriptLoanPerson(Loan loan) throws Exception {
        requireAllNonNull(loan);
        return loan.getPerson().getName().toString();
    }

    /**
     * Provides <code>loanDate(loan) -> LocalDate</code>.
     */
    private LocalDate scriptLoanDate(Loan loan) throws Exception {
        requireAllNonNull(loan);
        return loan.getDate();
    }

    /**
     * Provides <code>loanDescription(loan) -> string</code>.
     */
    private String scriptLoanDescription(Loan loan) throws Exception {
        requireAllNonNull(loan);
        return loan.getDescription().getDescription();
    }

    /**
     * Provides <code>loanIsPaid(loan) -> boolean</code>.
     */
    private boolean scriptLoanIsPaid(Loan loan) throws Exception {
        requireAllNonNull(loan);
        return loan.getStatus() == Status.PAID;
    }

    /**
     * Provides <code>parseDate(date) -> LocalDate</code>.
     */
    private LocalDate scriptParseDate(String date) throws Exception {
        return LocalDate.parse(date, AppUtil.getDateFormatter());
    }

    /**
     * Provides <code>makeDate(year, month, day) -> LocalDate</code>.
     */
    private LocalDate scriptMakeDate(int year, int month, int day) throws Exception {
        return LocalDate.of(year, month, day);
    }

    /**
     * Converts an array of strings to a set of categories.
     */
    private Set<Category> parseScriptTxnCategories(String[] catStrings) throws ParseException {
        Set<Category> cats;
        if (catStrings != null) {
            cats = new HashSet<>(catStrings.length);
            for (String catString : catStrings) {
                if (catString == null) {
                    continue;
                }
                cats.add(CommandParserUtil.parseCategory(catString));
            }
        } else {
            cats = Collections.emptySet();
        }
        return cats;
    }
}

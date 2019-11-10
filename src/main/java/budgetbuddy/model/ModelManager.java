package budgetbuddy.model;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import budgetbuddy.commons.core.GuiSettings;
import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.transaction.Transaction;

import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the budget buddy data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final LoansManager loansManager;
    private final AccountsManager accountsManager;
    private final RuleManager ruleManager;

    private final UserPrefs userPrefs;
    private final FilteredList<Transaction> filteredTransactions;
    private final ScriptLibrary scriptLibrary;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(LoansManager loansManager, RuleManager ruleManager, AccountsManager accountsManager,
                        ScriptLibrary scriptLibrary, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(loansManager, ruleManager, accountsManager, userPrefs, scriptLibrary);

        logger.fine("Initializing with accounts manager: " + accountsManager + " and user prefs " + userPrefs);

        this.loansManager = new LoansManager(loansManager.getLoans(), loansManager.getDebtors());
        this.ruleManager = new RuleManager(ruleManager.getRules());
        this.accountsManager = accountsManager;
        this.userPrefs = new UserPrefs(userPrefs);
        this.scriptLibrary = scriptLibrary;
        this.filteredTransactions = accountsManager.getFilteredTransactionList();
    }

    public ModelManager() {
        this(new LoansManager(), new RuleManager(), new AccountsManager(), new ScriptLibraryManager(), new UserPrefs());
    }

    //=========== Loan Manager ===============================================================================

    @Override
    public LoansManager getLoansManager() {
        return loansManager;
    }

    //=========== Account Book ===============================================================================

    @Override
    public void resetFilteredTransactionList() {
        filteredTransactions.setPredicate(s -> true);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public FilteredList<Transaction> getFilteredTransactions() {
        return filteredTransactions;
    }

    @Override
    public RuleManager getRuleManager() {
        return ruleManager;
    }

    @Override
    public AccountsManager getAccountsManager() {
        return accountsManager;
    }

    //=========== ScriptLibrary ==============================================================================

    @Override
    public ScriptLibrary getScriptLibrary() {
        return scriptLibrary;
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return ruleManager.equals(other.ruleManager)
                && accountsManager.equals(other.accountsManager)
                && loansManager.equals(other.loansManager)
                && scriptLibrary.equals(other.scriptLibrary)
                && userPrefs.equals(other.userPrefs)
                && filteredTransactions.equals(other.filteredTransactions);
    }

}

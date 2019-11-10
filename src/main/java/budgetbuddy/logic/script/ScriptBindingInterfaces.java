package budgetbuddy.logic.script;

import budgetbuddy.model.account.Account;
import budgetbuddy.model.transaction.Transaction;

/**
 * The following are functional interfaces used to bind the methods into the Nashorn environment.
 * Nashorn requires functional interfaces to be public in order for them to be recognised as functions
 * but we can hide them in a private inner class to prevent them from being accessed from outside.
 */
class ScriptBindingInterfaces {
    /**
     * Prevents construction of an instance of this static-only class.
     */
    private ScriptBindingInterfaces() {}

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface StringString {
        Object apply(String a0, String a1) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface StringOnly {
        Object apply(String a0) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface AccountTransaction {
        Object apply(Account a0, Transaction a1) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface TransactionOnly {
        Object apply(Transaction a1) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface AccountOnly {
        Object apply(Account a1) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface ObjectOnly {
        Object apply(Object a1) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface ObjectObjects {
        Object apply(Object a1, Object... a2) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface AccountObjects {
        Object apply(Account a0, Object... a2) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface AccountTransactionObjects {
        Object apply(Account a0, Transaction a1, Object... a2) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface TransactionObjects {
        Object apply(Transaction a1, Object... a2) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface LongStringStringObjects {
        Object apply(long a1, String a2, String a3, Object... a4) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface IntObjects {
        Object apply(int a1, Object... a2) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface Int {
        Object apply(int a1) throws Exception;
    }

    /**
     * Helps to bind a Java method with the same signature into the script environment.
     */
    @FunctionalInterface
    public interface Void {
        Object apply() throws Exception;
    }
}

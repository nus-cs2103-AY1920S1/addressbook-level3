package seedu.address.model.reminders.conditions;

import java.util.function.Predicate;

import seedu.address.model.person.AutoExpense;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;
import seedu.address.model.person.Wish;

/**
 * Condition is met when entry is of a specific class.
 */
public class ClassCondition extends Condition {
    /**
     * determines what type of entry will meet the condition.
     */
    public enum EntryType {
        ENTRY,
        EXPENSE,
        INCOME,
        WISH,
        AUTOEXPENSE,
        BUDGET;

        @Override
        public String toString() {
            switch (this) {
            case EXPENSE:
                return "expense";
            case INCOME:
                return "income";
            case WISH:
                return "wish";
            case AUTOEXPENSE:
                return "autoexpense";
            case BUDGET:
                return "budget";
            default:
                return "entry";
            }
        }
    }
    private EntryType entryType;
    private Predicate<Entry> classPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            switch (entryType) {
            case EXPENSE:
                return entry instanceof Expense;
            case INCOME:
                return entry instanceof Income;
            case WISH:
                return entry instanceof Wish;
            case AUTOEXPENSE:
                return entry instanceof AutoExpense;
            case BUDGET:
                return entry instanceof Budget;
            default:
                assert (entryType.equals(EntryType.ENTRY));
                return true;
            }
        }
    };
    public ClassCondition(String entryType) {
        super("Class Condition");
        super.setPred(classPredicate);
        parseType(entryType);
    }
    /**
     * Reads String input to determine specific class to target.
     * @param entryType
     */
    private void parseType(String entryType) {
        switch(entryType.toLowerCase()) {
        case "entry":
            this.entryType = EntryType.ENTRY;
            break;
        case "expense":
            this.entryType = EntryType.EXPENSE;
            break;
        case "income":
            this.entryType = EntryType.INCOME;
            break;
        case "wish":
            this.entryType = EntryType.WISH;
            break;
        case "autoexpense":
            this.entryType = EntryType.AUTOEXPENSE;
            break;
        case "budget":
            this.entryType = EntryType.BUDGET;
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Called when storing ClassCondition
     * @return String representation of classType;
     */
    public String typeToString() {
        switch(entryType) {
        case EXPENSE:
            return "expense";
        case INCOME:
            return "income";
        case WISH:
            return "wish";
        case AUTOEXPENSE:
            return "autoexpense";
        case BUDGET:
            return "budget";
        default:
            assert(entryType.equals(EntryType.ENTRY));
            return "entry";
        }
    }

    public EntryType getEntryType() {
        return entryType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            if (!(other instanceof ClassCondition)) {
                return false;
            } else {
                ClassCondition otherCondition = (ClassCondition) other;
                return this.entryType.equals(((ClassCondition) other).entryType);
            }
        }
    }
}

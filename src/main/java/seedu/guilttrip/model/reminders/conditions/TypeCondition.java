package seedu.guilttrip.model.reminders.conditions;

import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;

/**
 * Condition is met when entry is of a specific class.
 */
public class TypeCondition extends Condition {
    /**
     * determines what type of entry will meet the condition.
     */
    public enum EntryType {
        EXPENSE,
        INCOME;

        @Override
        public String toString() {
            switch (this) {
            case INCOME:
                return "income";
            default:
                return "expense";
            }
        }
    }
    private EntryType entryType;
    private Predicate<Entry> classPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            switch (entryType) {
            case INCOME:
                return entry instanceof Income;
            default:
                return entry instanceof Expense;
            }
        }
    };
    public TypeCondition(String entryType) {
        super("Class Condition");
        parseType(entryType);
        super.setPred(classPredicate);
    }
    /**
     * Reads String input to determine specific class to target.
     * @param entryType
     */
    private void parseType(String entryType) {
        switch(entryType.toLowerCase()) {
        case "expense":
            this.entryType = EntryType.EXPENSE;
            break;
        case "income":
            this.entryType = EntryType.INCOME;
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Called when storing TypeCondition
     * @return String representation of classType;
     */
    public String typeToString() {
        switch(entryType) {
        case INCOME:
            return "income";
        default:
            return "expense";
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
            if (!(other instanceof TypeCondition)) {
                return false;
            } else {
                TypeCondition otherCondition = (TypeCondition) other;
                return this.entryType.equals(((TypeCondition) other).entryType);
            }
        }
    }
}

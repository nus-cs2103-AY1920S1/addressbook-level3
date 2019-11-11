package seedu.guilttrip.model.reminders.conditions;

import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Entry;
/**
 * Condition is met when entry amount is greater or equal to specified quota.
 */
public class QuotaCondition extends Condition {
    private double quota;
    private boolean isLowerBound;
    private Predicate<Entry> quotaPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            if (isLowerBound) {
                return entry.getAmount().value >= quota;
            } else {
                return entry.getAmount().value <= quota;
            }
        }
    };
    public QuotaCondition(Double quota, boolean isLowerBound) {
        super("Quota Condition");
        this.quota = quota;
        this.isLowerBound = isLowerBound;
        super.setPred(quotaPredicate);
    }

    public boolean isLowerBound() {
        return this.isLowerBound;
    }
    public double getQuota() {
        return quota;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof QuotaCondition)) {
            return false;
        } else {
            return this.quota == (((QuotaCondition) other).quota)
                    && this.isLowerBound == ((QuotaCondition) other).isLowerBound;
        }
    }
}

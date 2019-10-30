package seedu.address.model.reminders.conditions;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Entry;

/**
 * Condition is met when entry description contains keywords.
 */
public class KeyWordsCondition extends Condition {
    private List<String> keywords;
    private Predicate<Entry> hasKeyWordsPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(entry.getDesc().fullDesc, keyword));
        }
    };
    public KeyWordsCondition(List<String> keywords) {
        super("Keyword Condition");
        this.keywords = keywords;
        super.setPred(hasKeyWordsPredicate);
    }
    public List<String> getKeywords() {
        return keywords;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof KeyWordsCondition)) {
            return false;
        } else {
            return this.keywords.equals(((KeyWordsCondition) other).keywords);
        }
    }
}

package seedu.address.model.reminders.conditions;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Description;
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
    public KeyWordsCondition(Description desc, List<String> keywords) {
        super(desc);
        this.keywords = keywords;
        super.setPred(hasKeyWordsPredicate);
    }
    public List<String> getKeywords() {
        return keywords;
    }
}

package seedu.guilttrip.model.reminders.conditions;

import java.util.List;
import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.tag.Tag;

/**
 * Condition is met when entry contains all tag in taglist.
 */
public class TagsCondition extends Condition {
    private List<Tag> tags;
    private Predicate<Entry> hasTagsPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            return tags.stream()
                    .anyMatch(tag -> entry.getTags().contains(tag));
        }
    };
    public TagsCondition(List<Tag> tags) {
        super("Tags Condition");
        this.tags = tags;
        super.setPred(hasTagsPredicate);
    }
    public List<Tag> getTagList() {
        return tags;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof TagsCondition)) {
            return false;
        } else {
            return this.tags.equals(((TagsCondition) other).tags);
        }
    }
}

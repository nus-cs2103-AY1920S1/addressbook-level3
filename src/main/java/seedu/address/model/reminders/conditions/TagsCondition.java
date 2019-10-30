package seedu.address.model.reminders.conditions;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Entry;
import seedu.address.model.tag.Tag;

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
}

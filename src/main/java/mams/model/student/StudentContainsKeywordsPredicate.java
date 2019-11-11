package mams.model.student;

import java.util.List;
import java.util.function.Predicate;

import mams.commons.util.StringUtil;

/**
 * Tests that a {@code Student}'s {@code Name} matches any of the keywords given.
 */
public class StudentContainsKeywordsPredicate implements Predicate<Student> {
    private final List<String> keywords;

    public StudentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isEmpty() {
        return keywords.get(0).trim().isEmpty();
    }

    public int getListSize() {
        return keywords.size();
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(student.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(student.getMatricId().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((StudentContainsKeywordsPredicate) other).keywords)); // state check
    }
}

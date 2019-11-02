package seedu.address.model.employee;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that a {@code Employee}'s {@code EmployeeTag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Employee> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        List<String> employeeTag = employee.getTags().stream().map(Objects::toString)
                .map(x -> x.substring(1, x.length() - 1)).collect(Collectors.toList());
        System.out.println(employeeTag);
        System.out.println(keywords);
        return employeeTag.containsAll(keywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}

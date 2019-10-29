package seedu.revision.model.quiz;

import seedu.revision.model.answerable.Answerable;

import java.util.function.Predicate;

/**
 * Tests that a {@code Answerable}'s {@code category} matches the category given.
 */
public class NormalModePredicate implements Predicate<Answerable> {

    private String type = "normal";

    @Override
    public boolean test(Answerable answerable) {
        return true;
//        return levelFilter(answerable) && categoryFilter(answerable);
    }

    private boolean levelFilter(Answerable answerable) {
        return answerable.getDifficulty().value.equals("1") || answerable.getDifficulty().value.equals("2");
    }

    private boolean categoryFilter(Answerable answerable){
        return answerable.getCategories()
                .stream()
                .map(category -> category.categoryName)
                .anyMatch(categoryName -> categoryName.equalsIgnoreCase("introduction"));
    }

    public String getType() {
        return type;
    }
}


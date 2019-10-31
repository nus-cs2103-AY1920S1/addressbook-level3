package seedu.revision.model.quiz;

import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;
import seedu.revision.model.category.Category;

public class CustomMode extends Mode {
    private int time;
    private DifficultyPredicate difficultyPredicate;
    private CategoryPredicate categoryPredicate;

    public CustomMode(String value) {
        super(value);
        this.time = Mode.NORMAL_MODE_TIME;
        this.categoryPredicate = Mode.NORMAL_MODE_CATEGORY_PREDICATE;
        this.difficultyPredicate = Mode.NORMAL_MODE_DIFFCULTY_PREDICATE;

    }

    public CustomMode(String value, int time,
                        CategoryPredicate categoryPredicate, DifficultyPredicate difficultyPredicate) {
        super(value);
        this.time = time;
        this.categoryPredicate = categoryPredicate;
        this.difficultyPredicate = difficultyPredicate;
    }

    public CustomMode withTime(int time) {
        this.time = time;
        return this;
    }

    public CustomMode withCategoryPredicate(Category category) {
        this.categoryPredicate = new CategoryPredicate(category);
        return this;
    }

    public CustomMode withDifficultyPredicate(Difficulty difficulty) {
        this.difficultyPredicate = new DifficultyPredicate(difficulty);
        return this;
    }

    public CustomMode build() {
        return new CustomMode(value, time, categoryPredicate, difficultyPredicate);
    }


}

}

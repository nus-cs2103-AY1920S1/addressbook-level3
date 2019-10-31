package seedu.revision.model.quiz;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;
import seedu.revision.model.category.Category;

public class CustomMode extends Mode {
    private int time;
    private DifficultyPredicate difficultyPredicate;
    private CategoryPredicate categoryPredicate;

    public CustomMode(String value) {
        super(value);
        this.time = NormalMode.NORMAL_MODE_TIME;
        this.combinedPredicate = NormalMode.NORMAL_MODE_PREDICATE;
    }

    public CustomMode(String value, int time, Predicate<Answerable> combinedPredicate){
        super(value);
        this.time = time;
        this.combinedPredicate = combinedPredicate;
    }

    public CustomMode withTime(int time) {
        this.time = time;
        return this;
    }

    public CustomMode withCategoryPredicate(Predicate<Answerable> combinedPredicate) {
        this.combinedPredicate = combinedPredicate;
        return this;
    }



    public CustomMode build() {
        return new CustomMode(value, time, combinedPredicate);
    }
}

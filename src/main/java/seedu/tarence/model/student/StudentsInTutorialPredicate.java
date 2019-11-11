package seedu.tarence.model.student;

import java.util.function.Predicate;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;

/**
 * Tests that a {@code Student}'s {@code TutName} matches the given index
 */
public class StudentsInTutorialPredicate implements Predicate<Student> {
    private final Index tutorialIndex;
    private TutName tutName;
    private ModCode modCode;

    public StudentsInTutorialPredicate(Index tutorialIndex) {
        this.tutorialIndex = tutorialIndex;
    }

    public void setTutName(TutName tutName) {
        this.tutName = tutName;
    }

    public void setModCode(ModCode modCode) {
        this.modCode = modCode;
    }

    public int getIndex() {
        return this.tutorialIndex.getZeroBased();
    }

    @Override
    public boolean test(Student student) {
        return (student.tutName.equals(this.tutName) && student.modCode.equals(this.modCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentsInTutorialPredicate // instanceof handles nulls
                && tutorialIndex.equals(((StudentsInTutorialPredicate) other).tutorialIndex)); // state check
    }
}

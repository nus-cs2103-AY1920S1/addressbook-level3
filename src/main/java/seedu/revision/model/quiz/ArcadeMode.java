package seedu.revision.model.quiz;

/** ArcadeMode class which has increasing difficulty each level and ends any time a user gets a question wrong. **/
public class ArcadeMode extends Mode {
    /**
     * Constructs an {@code ArcadeMode}.
     */
    public ArcadeMode() {
        super("arcade");
        this.time = 20;
        this.combinedPredicate = NormalMode.NORMAL_MODE_PREDICATE;
    }

    public int getTime(int nextLevel) {
        switch(nextLevel) {
        case 2:
            return 15;
        case 3:
            return 10;
        default:
            return this.time;
        }
    }
}


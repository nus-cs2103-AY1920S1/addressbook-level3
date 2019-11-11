package seedu.revision.model.quiz;

/** ArcadeMode class which has increasing difficulty each level and ends any time a user gets a question wrong. **/
public class ArcadeMode extends Mode {
    /**
     * Constructs an {@code ArcadeMode}.Uses defensive programming to immediately provide default values to
     * time and combinedPredicate.
     */
    public ArcadeMode() {
        super(Modes.ARCADE.toString());
        this.time = 30;
        this.combinedPredicate = NormalMode.NORMAL_MODE_PREDICATE;
    }

    public int getTime(int nextLevel) {
        switch(nextLevel) {
        case 2:
            return 25;
        case 3:
            return 20;
        default:
            return this.time;
        }
    }
}


package seedu.revision.model.quiz;

/** ArcadeMode class which has increasing difficulty each level and ends any time a user gets a question wrong. **/
public class ArcadeMode extends Mode {
    /**
     * Constructs an {@code ArcadeMode}.
     */
    public ArcadeMode() {
        super("arcade");
        this.time = 20;
    }

    public int getLevelTwoTime() {
        assert time < 5 : "invalid time";
        return time - 5;
    }

    public int getLevelThreeTime() {
        assert time < 10 : "invalid time";
        return time - 10;
    }


}


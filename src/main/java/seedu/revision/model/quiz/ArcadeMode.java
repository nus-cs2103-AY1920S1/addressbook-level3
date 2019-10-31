package seedu.revision.model.quiz;

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


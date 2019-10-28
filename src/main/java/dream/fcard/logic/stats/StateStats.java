package dream.fcard.logic.stats;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import dream.fcard.model.Deck;
import dream.fcard.model.State;

/**
 * A class that extends Statistics that contains statistics unique to the State.
 */
public class StateStats extends Statistics {
    private long runtimeInSecs;
    private ArrayList<Statistics> allDeckStatistics;

    public StateStats(State s) {
        super();

        allDeckStatistics = new ArrayList<>();
        ArrayList<Deck> allDecks = s.getDecks();
        for (Deck d : allDecks) {
            allDeckStatistics.add(d.getStatistics());
        }
    }

    /**
     * Calculates the runtime of this instance of the program.
     */
    public void calcRuntime() {
        LocalDateTime curr = LocalDateTime.now();
        this.runtimeInSecs = ChronoUnit.SECONDS.between(this.createdDate, curr);
    }

}

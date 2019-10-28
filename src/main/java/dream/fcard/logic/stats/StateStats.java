package dream.fcard.logic.stats;

import dream.fcard.model.Deck;
import dream.fcard.model.State;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class StateStats extends Statistics {
    public long runtimeInSecs;
    ArrayList<Statistics> allDeckStatistics;

    public StateStats(State s) {
        super();

        allDeckStatistics = new ArrayList<>();
        ArrayList<Deck> allDecks = s.getDecks();
        for(Deck d : allDecks) {
            allDeckStatistics.add(d.getStatistics());
        }
    }

    public void calcRuntime(){
        LocalDateTime curr = LocalDateTime.now();
        this.runtimeInSecs = ChronoUnit.SECONDS.between(this.createdDate, curr);
    }

}

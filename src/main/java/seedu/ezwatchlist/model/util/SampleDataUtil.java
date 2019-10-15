package seedu.ezwatchlist.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.actor.Actor;

/**
 * Contains utility methods for populating {@code WatchList} with sample data.
 */
public class SampleDataUtil {
    public static Show[] getSampleShows() {
        return new Show[] {
            new Show(new Name("Joker"), new Description("In Gotham City, mentally-troubled comedian Arthur Fleck embarks on a downward-spiral of social revolution and bloody crime. This path brings him face-to-face with his infamous alter-ego: \"The Joker\"."),
                    new IsWatched(false), new Date("4 October 2019"), new RunningTime(122),
                    getActorSet("Joaquin Phoenix, Robert De Niro"))
        };
    }

    public static ReadOnlyWatchList getSampleWatchList() {
        WatchList sampleWl = new WatchList();
        for (Show sampleShow : getSampleShows()) {
            sampleWl.addShow(sampleShow);
        }
        return sampleWl;
    }

    /**
     * Returns an Actor set containing the list of strings given.
     */
    public static Set<Actor> getActorSet(String... strings) {
        return Arrays.stream(strings)
                .map(Actor::new)
                .collect(Collectors.toSet());
    }

}

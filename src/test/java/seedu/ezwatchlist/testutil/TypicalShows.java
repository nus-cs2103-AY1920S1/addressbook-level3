package seedu.ezwatchlist.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.show.Show;

/**
 * A utility class containing a list of {@code Show} objects to be used in tests.
 */
public class TypicalShows {

    public static final Show JOKER = new ShowBuilder().withName("Joker").withDescription(
            "Forever alone in a crowd, failed comedian Arthur Fleck seeks connection as he walks the streets of Gotham "
                    + "City. Arthur wears two masks -- the one he paints for his day job as a clown, and the guise he "
                    + "projects in a futile attempt to feel like he's part of the world around him. Isolated, bullied "
                    + "and disregarded by society, Fleck begins a slow descent into madness as he transforms into the "
                    + "criminal mastermind known as the Joker."
    ).withIsWatched("false").withDateOfRelease("3/10/2019").withType("Movie")
            .withRunningTime(122).withActors("Joaquin Phoenix").build();

    public static final Show AVENGERSENDGAME = new ShowBuilder().withName("Avenger Endgame").withDescription(""
            + "Adrift in space with no food or water, Tony Stark sends a message to Pepper Potts as his oxygen supply "
            + "starts to dwindle. Meanwhile, the remaining Avengers -- Thor, Black Widow, Captain America and Bruce "
            + "Banner -- must figure out a way to bring back their vanquished allies for an epic showdown with Thanos"
            + " -- the evil demigod who decimated the planet and the universe.")
            .withIsWatched("false").withDateOfRelease("24/04/2019").withRunningTime(182)
            .withActors("Chris Hemsworth").withType("Movie").build();

    /*public static final Show BLACKPANTHER = new ShowBuilder().withName("Black Panther").withDescription("After the"
            + " death of his father, T'Challa returns home to the African nation of Wakanda to take his rightful place"
            + " as king. When a powerful enemy suddenly reappears, T'Challa's mettle as king -- and as Black Panther"
            + " -- gets tested when he's drawn into a conflict that puts the fate of Wakanda and the entire world at"
            + " risk. Faced with treachery and danger, the young king must rally his allies and release the full power"
            + " of Black Panther to defeat his foes and secure the safety of his people.").withIsWatched(true)
            .withDateofRelease("29 January 2018").withRunningTime(135).withActors("Chadwick Boseman").build();
*/

    // Manually added
    public static final Show FIGHTCLUB = new ShowBuilder().withName("Fight Club").withDescription(
            "A depressed man (Edward Norton) suffering from insomnia meets a strange soap salesman named Tyler Durden "
                    + "(Brad Pitt) and soon finds himself living in his squalid house after his perfect apartment is "
                    + "destroyed. The two bored men form an underground club with strict rules and fight other men who "
                    + "are fed up with their mundane lives. Their perfect partnership frays when Marla (Helena Bonham"
                    + " Carter), a fellow support group crasher, attracts Tyler's attention.")
            .withIsWatched("true").withDateOfRelease("4/11/1999").withRunningTime(151).withActors().build();
    /*
        // Manually added - Show's details found in {@code CommandTestUtil}
        public static final Show SAVINGPRIVATERYAN = new ShowBuilder().withName("Saving Private Ryan")
                .withDescription(
                "Captain John Miller (Tom Hanks) takes his men behind enemy lines to find Private James"
                + " Ryan, whose three brothers have been killed in combat. Surrounded by the brutal realties of"
                + " war, while searching for Ryan, each man embarks upon a personal journey and discovers their"
                + " own strength to triumph over an uncertain future with honor, decency and courage.")
                .withIsWatched(false).withDateofRelease("Saving Private Ryan")
                .withRunningTime(170).withActors("Tom Hanks").build();
    */
    public static final Show GODFATHER2 = new ShowBuilder().withName("God Father 2").withDescription("The compelling"
            + " sequel to \"The Godfather,\" contrasting the life of Corleone father and son. Traces the problems "
            + "of Michael Corleone (Al Pacino) in 1958 and that of a young immigrant Vito Corleone (Robert De Niro) "
            + "in 1917's Hell's Kitchen. Michael survives many misfortunes and Vito is introduced to a life of crime.")
            .withIsWatched("true").withDateOfRelease("12/12/1974")
            .withRunningTime(202).withActors("Al Pacino").build();

    public static final Show WATCHEDJOKER = new ShowBuilder().withName("Joker").withDescription(
            "Forever alone in a crowd, failed comedian Arthur Fleck seeks connection as he walks the streets of Gotham "
                    + "City. Arthur wears two masks -- the one he paints for his day job as a clown, and the guise he "
                    + "projects in a futile attempt to feel like he's part of the world around him. Isolated, bullied "
                    + "and disregarded by society, Fleck begins a slow descent into madness as he transforms into the "
                    + "criminal mastermind known as the Joker."
    ).withIsWatched("true").withDateOfRelease("3/10/2019").withType("Movie")
            .withRunningTime(122).withActors("Joaquin Phoenix").build();

    public static final Show THEOFFICE = new ShowBuilder().withName("The Office").withDescription("A mockumentary"
            + " on a group of typical office workers, where the workday consists of ego clashes,"
            + " inappropriate behavior, and tedium.").withType("Tv Show")
            .withIsWatched("false").withDateOfRelease("2005-03-24")
            .withRunningTime(202).withActors("Steve Carrell", "Jenna Fischer").withNumOfEpisodesWatched(3)
            .withTotalNumOfEpisodes(195).withSeasons(new SeasonGenerator()
                    .withTvSeason(6).withTvSeason(22).withTvSeason(23)
                    .withTvSeason(19).withTvSeason(26).withTvSeason(26)
                    .withTvSeason(26).withTvSeason(24).withTvSeason(23).getTvSeasons()).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalShows() {} // prevents instantiation

    /**
     * Returns an {@code WatchList} with all the typical show.
     */
    public static WatchList getTypicalWatchList() {
        WatchList wl = new WatchList();
        for (Show show : getTypicalShow()) {
            wl.addShow(show);
        }
        return wl;
    }
    public static WatchList getTypicalWatchList2() {
        WatchList wl = new WatchList();
        for (Show show : getTypicalShow2()) {
            wl.addShow(show);
        }
        return wl;
    }

    public static List<Show> getTypicalShow() {
        return new ArrayList<>(Arrays.asList(AVENGERSENDGAME, FIGHTCLUB));
    }
    public static List<Show> getTypicalShow2() {
        return new ArrayList<>(Arrays.asList(JOKER, AVENGERSENDGAME, FIGHTCLUB,
                GODFATHER2, THEOFFICE));
    }
}

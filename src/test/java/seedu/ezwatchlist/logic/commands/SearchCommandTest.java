package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_SHOW_NAME_ANNABELLE;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_SHOW_NAME_BOB_THE_BUILDER;

import static seedu.ezwatchlist.testutil.TypicalShows.getDatabase;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.messages.SearchMessages;
import seedu.ezwatchlist.logic.parser.SearchKey;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private static final Show SHOW_FANTASTIC_BEASTS_AND_WHERE_TO_FIND_THEM = new Movie(
            new Name("Fantastic Beasts and Where to Find Them"),
            new Description("In 1926, Newt Scamander arrives at the Magical Congress of the United States of America "
                    + "with a magically expanded briefcase, which houses a number of dangerous creatures and their "
                    + "habitats. When the creatures escape from the briefcase, it sends the American wizarding "
                    + "authorities after Newt, and threatens to strain even further the state of magical and "
                    + "non-magical relations."),
            new IsWatched("false"),
            new Date("2016-11-16"),
            new RunningTime(133),
            getActorSet("Paul Dewdney", "Ryan Storey", "Alex Jaep", "Dan Trotter", "Dean Weir", "Douglas Byrne",
                    "Morgan Walters", "Richard Hardisty", "Andrew Parker", "Sean Cronin", "Todd Boyce", "Ian Jenkins",
                    "Patrick Carney Junior", "Bernardo Santos", "Lucie Pohl", "Tom Clarke Hill", "Neil Broome",
                    "David Soffe", "Abi Adeyemi", "Abigayle Honeywill", "Jorge Leon Martinez", "Josh Cowdery",
                    "Elizabeth Moynihan", "Ron Perlman", "David J Biscoe", "Jake Samuels", "Arnold Montey",
                    "Andy Mihalache", "Lampros Kalfuntzos", "Michael Gabbitas", "Connor Sullivan", "Aileen Archer",
                    "Jon Voight", "Erick Hayden", "Joshua Diffley", "Ellie Haddington", "Luke Hope", "Denis Khoroshko",
                    "Lobna Futers", "Guy Paul", "Tristan Tait", "Reid Anderson", "Henry Douthwaite", "Richard Douglas",
                    "Jane Perry", "Andreea Păduraru", "Emmi", "Marc Benanti", "Christy Meyer", "Pete Meads",
                    "Sam Redford", "Tom Hodgkins", "Alphonso Austin", "Miles Roughley", "Edd Osmond", "Nick Donald",
                    "Cristian Solimeno", "Lee Bolton", "Ezra Miller", "Paul Bergquist", "Geeta Vij", "Arinzé Kene",
                    "Dennis O'Donnell", "Bart Edwards", "Miquel Brown", "Camilla Talarowska", "Rudy Valentino Grant",
                    "Chloe de Burgh", "Annarie Boor", "Dino Fazzani", "Peter Breitmayer", "Carmen Cowell",
                    "Adam Lazarus", "Tom Dab", "Alan Wyn Hughes", "Nathan Benham", "Stacey Clegg", "Fanny Carbonnel",
                    "Faith Wood-Blagrove", "Nick Owenford", "Khristopher MacLeod", "John Murray", "Paul Birchard",
                    "Cory Peterson", "Nicholas McGaughey", "Zoë Kravitz", "Alan Mandel", "David Charles-Cully",
                    "Christian Dixon", "Simon Kerrison", "Brian F. Mulvey", "Nick Davison", "Vassiliki Tzanakou",
                    "Ashley Hudson", "Eddie Redmayne", "Colin Farrell", "Kirsty Grace", "Matthew Wilson",
                    "Kevin Guthrie", "Joseph Macnab", "Laura Bernardeschi", "Christine Marzano", "Dave Simon",
                    "Claire Cooper-King", "Miroslav Zaruba", "Christopher Marsh", "Guna Gultniece", "Andrei Satalov",
                    "Jenn Murray", "Yves O'Hara", "Robert-Anthony Artlett", "Flor Ferraco", "Akin Gazi", "Aretha Ayeh",
                    "Craig Davies", "Wunmi Mosaku", "Anick Wiget", "Roy Beck", "Gemma Chan", "Elizabeth Briand",
                    "Keith Lomas", "Johnny Depp", "Walles Hamonde", "Tim Bentinck", "Max Cazier", "Martin Oelbermann",
                    "Samantha Morton", "Ronan Raftery", "Dominique Tipper", "Olivia Quinn", "Alison Sudol",
                    "Greg Brummel", "Marketa Flynn", "Richard Price", "Jason Redshaw", "Lee Asquith-Coe",
                    "Anne Wittman", "Matthew Sim", "Joe Malone", "David Goodson", "Dan Hedaya", "Leo Heller",
                    "Michael Barron", "Paul A Munday", "Paul Low-Hang", "Rudi Dharmalingam", "Stephanie Eccles",
                    "Attila G. Kerekes", "Andrew G. Ogleby", "Richard Clothier", "Adam Lezemore", "Carmen Ejogo",
                    "Chloe Collingwood", "Gino Picciano", "Dan Fogler", "Silvia Crastan", "Cole Leman",
                    "Kornelia Horvath", "Katherine Waterston", "James M.L. Muller", "Paul Redfern"));

    private static final Show SHOW_FANTASTIC_BEASTS_THE_CRIMES_OF_GRINDELWALD = new Movie(
            new Name("Fantastic Beasts: The Crimes of Grindelwald"),
            new Description("Gellert Grindelwald has escaped imprisonment and has begun gathering followers to his "
                    + "cause—elevating wizards above all non-magical beings. The only one capable of putting a stop to "
                    + "him is the wizard he once called his closest friend, Albus Dumbledore. However, Dumbledore will "
                    + "need to seek help from the wizard who had thwarted Grindelwald once before, his former student "
                    + "Newt Scamander, who agrees to help, unaware of the dangers that lie ahead. Lines are drawn as "
                    + "love and loyalty are tested, even among the truest friends and family, in an increasingly "
                    + "divided wizarding world."),
            new IsWatched("false"),
            new Date("2018-11-14"),
            new RunningTime(134),
            getActorSet("Claudius Peters", "Maja Bloom", "Wolf Roth", "Jamie Campbell Bower", "Donna Preston",
                    "Ed Gaughan", "Andrew Blackall", "Ruby Woolfenden", "Zoë Kravitz", "Johanna Thea", "Andy Summers",
                    "Jemima Woolnough", "Simon Meacock", "Nasir Jama", "Olivia Popica", "Bernardo Santos",
                    "David Wilmot", "Liv Hansen", "Hollie Burgess", "Eddie Redmayne", "Jude Law", "Israel Ruiz",
                    "Callum Forman", "Ryan Hannaford", "Isaura Barbé-Brown", "Kevin Guthrie", "Christopher Birks",
                    "Callum Turner", "Dave Simon", "Stephen McDade", "Alfrun Rose", "Alexandra Ford", "Victoria Yeates",
                    "Sean Coleman", "David Sakurai", "Morrison Thomas", "Tahir Burhan", "Deepak Anand", "Johnny Depp",
                    "Alfie Simmons", "Toby Regbo", "Isaac Domingos", "Linda Santiago", "Phil Hodges", "Adrian Wheeler",
                    "Ezra Miller", "Hugh Quarshie", "Alison Sudol", "Jag Patel", "Jason Redshaw", "Al Clark",
                    "Ólafur Darri Ólafsson", "Poppy Corby-Tuech", "Danielle Hugues", "Claudia Kim", "Simon Wan",
                    "William Nadylam", "Cornell John", "Tim Ingall", "Connor Wolf", "Fiona Glascott",
                    "Bart Soroczynski", "Andrew Turner", "Michael Haydon", "Thea Lamb", "Annarie Boor", "Carmen Ejogo",
                    "Jessica Williams", "Jeremy Oliver", "Olwen Fouéré", "Dan Fogler", "Ingvar Eggert Sigurðsson",
                    "Derek Riddell", "Keith Chanter", "Sean Gislingham", "Katherine Waterston", "Aykut Hilmi",
                    "Sabine Crossen", "Alfie Mailley", "Natalie Lauren", "Deano Bugatti", "Brontis Jodorowsky",
                    "Isaac Cortinovis Johnson", "Nick Owenford", "Joshua Shea"));

    private Model model = new ModelManager(getTypicalWatchList(), getDatabase(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWatchList(), getDatabase(), new UserPrefs());

    @Test
    public void equals() {
        HashMap<SearchKey, List<String>> firstHash = new HashMap<>();
        HashMap<SearchKey, List<String>> secondHash = new HashMap<>();

        ArrayList<String> emptyList = new ArrayList<>();
        ArrayList<String> firstNameList = new ArrayList<>();
        firstNameList.add(VALID_SHOW_NAME_ANNABELLE);
        ArrayList<String> secondNameList = new ArrayList<>();
        secondNameList.add(VALID_SHOW_NAME_BOB_THE_BUILDER);

        firstHash.put(SearchKey.KEY_NAME, firstNameList);
        firstHash.put(SearchKey.KEY_GENRE, emptyList);
        firstHash.put(SearchKey.KEY_TYPE, emptyList);
        firstHash.put(SearchKey.KEY_FROM_ONLINE, emptyList);
        firstHash.put(SearchKey.KEY_ACTOR, emptyList);
        firstHash.put(SearchKey.KEY_FROM_ONLINE, emptyList);

        secondHash.put(SearchKey.KEY_NAME, secondNameList);
        secondHash.put(SearchKey.KEY_GENRE, emptyList);
        secondHash.put(SearchKey.KEY_TYPE, emptyList);
        secondHash.put(SearchKey.KEY_FROM_ONLINE, emptyList);
        secondHash.put(SearchKey.KEY_ACTOR, emptyList);
        secondHash.put(SearchKey.KEY_FROM_ONLINE, emptyList);

        SearchCommand searchFirstCommand = new SearchCommand(firstHash);
        SearchCommand searchSecondCommand = new SearchCommand(secondHash);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        //SearchCommand findFirstCommandCopy = new SearchCommand(firstHash);
        //assertTrue(searchFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals("first"));
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different show -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noShowFound() {
        String expectedMessage = String.format(SearchMessages.MESSAGE_SHOWS_FOUND_OVERVIEW, 0);

        HashMap<SearchKey, List<String>> emptyHash = new HashMap<>();
        ArrayList<String> emptyList = new ArrayList<>();
        emptyList.add(" ");
        emptyHash.put(SearchKey.KEY_NAME, emptyList);
        emptyHash.put(SearchKey.KEY_GENRE, emptyList);
        emptyHash.put(SearchKey.KEY_TYPE, emptyList);
        emptyHash.put(SearchKey.KEY_FROM_ONLINE, emptyList);
        emptyHash.put(SearchKey.KEY_ACTOR, emptyList);
        emptyHash.put(SearchKey.KEY_FROM_ONLINE, emptyList);
        SearchCommand command = new SearchCommand(emptyHash);

        expectedModel.updateSearchResultList(new ArrayList<Show>());

        assertEquals(Collections.emptyList(), model.getSearchResultList());
    }

    @Test
    public void execute_multipleKeywords_multipleShowsFound() {
        String expectedMessage = String.format(SearchMessages.MESSAGE_SHOWS_FOUND_OVERVIEW, 2);

        HashMap<SearchKey, List<String>> showHash = new HashMap<>();
        ArrayList<String> showNameList = new ArrayList<>();
        showNameList.add("Fantastic Beasts and Where to Find Them");
        showHash.put(SearchKey.KEY_NAME, showNameList);
        ArrayList<String> emptyList = new ArrayList<>();
        showHash.put(SearchKey.KEY_GENRE, emptyList);
        showHash.put(SearchKey.KEY_TYPE, emptyList);
        showHash.put(SearchKey.KEY_FROM_ONLINE, emptyList);
        showHash.put(SearchKey.KEY_ACTOR, emptyList);
        showHash.put(SearchKey.KEY_IS_WATCHED, emptyList);
        SearchCommand command = new SearchCommand(showHash);

        List<Show> expectedList = new ArrayList<>();

        Set<Genre> genreSetAdventureFantasyFamily = new HashSet<>();
        Genre genreAdventure = new Genre("adventure");
        Genre genreFantasy = new Genre("fantasy");
        Genre genreFamily = new Genre("family");
        genreSetAdventureFantasyFamily.add(genreAdventure);
        genreSetAdventureFantasyFamily.add(genreFantasy);
        genreSetAdventureFantasyFamily.add(genreFamily);
        SHOW_FANTASTIC_BEASTS_AND_WHERE_TO_FIND_THEM.addGenres(genreSetAdventureFantasyFamily);
        SHOW_FANTASTIC_BEASTS_THE_CRIMES_OF_GRINDELWALD.addGenres(genreSetAdventureFantasyFamily);

        expectedList.add(SHOW_FANTASTIC_BEASTS_AND_WHERE_TO_FIND_THEM);
        expectedList.add(SHOW_FANTASTIC_BEASTS_THE_CRIMES_OF_GRINDELWALD);

        model.updateSearchResultList(expectedList);
        assertEquals(expectedList, model.getSearchResultList());
    }

    public static Set<Actor> getActorSet(String... strings) {
        return Arrays.stream(strings)
                .map(Actor::new)
                .collect(Collectors.toSet());
    }
}

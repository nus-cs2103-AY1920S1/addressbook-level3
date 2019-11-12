package seedu.ezwatchlist.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;

/**
 * Contains utility methods for populating {@code WatchList} with sample data.
 */
public class DataBaseUtil {
    private static final Genre GENRE_ACTION = new Genre("Action");
    private static final Genre GENRE_COMEDY = new Genre("Comedy");
    private static final Genre GENRE_ADVENTURE = new Genre("Adventure");
    private static final Genre GENRE_FANTASY = new Genre("Fantasy");
    private static final Genre GENRE_FAMILY = new Genre("Family");
    private static final Genre GENRE_SCIENCE_FICTION = new Genre("Science Fiction");

    private static final Show SHOW_AVENGER_INFINITY_WAR = new Movie(
            new Name("Avengers: Infinity War"),
            new Description("As the Avengers and their allies have continued to protect the world from threats too "
                    + "large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A "
                    + "despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of "
                    + "unimaginable power, and use them to inflict his twisted will on all of reality. Everything the "
                    + "Avengers have fought for has led up to this moment - the fate of Earth and existence itself has "
                    + "never been more uncertain."),
            new IsWatched("false"),
            new Date("2018-04-25"),
            new RunningTime(149),
            getActorSet("Cobie Smulders", "Idris Elba", "Isabella Amara", "William Hurt", "Olaniyan Thurmon",
                    "Kenneth Branagh", "Zoe Saldana", "Mark Ruffalo", "Matthew Zuk", "Josh Brolin", "Chris Evans",
                    "Jacob Batalon", "Laura Miller", "Danai Gurira", "Chadwick Boseman", "Elizabeth Olsen",
                    "Bradley Cooper", "Kerry Condon", "Sebastian Stan", "Letitia Wright", "Robert Downey Jr.",
                    "Tom Hiddleston", "Stephen McFeely", "Terry Notary", "Ariana Greenblatt", "Anthony Mackie",
                    "Benedict Cumberbatch", "Blair Jasin", "Paul Bettany", "Chris Hemsworth", "Michael James Shaw",
                    "Aaron Lazar", "Stan Lee", "Winston Duke", "Don Cheadle", "Scarlett Johansson", "Ross Marquand",
                    "Tom Holland", "Karen Gillan", "Ethan Dizon", "Ameenah Kaplan", "Benedict Wong", "Gwyneth Paltrow",
                    "Michael Anthony Rogers", "Carrie Coon", "Florence Kasumba", "Samuel L. Jackson", "Peter Dinklage",
                    "Sean Gunn", "Monique Ganderton", "Tom Vaughan-Lawlor", "Robert Pralgo", "Pom Klementieff",
                    "Vin Diesel", "Tiffany Espensen", "Dave Bautista", "Benicio del Toro", "Chris Pratt"));

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
                    + "cause—elevating wizards above all non-magical beings. The only one capable of putting a stop "
                    + "to "
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

    public static ArrayList<Show> getShowData() {
        ArrayList<Show> showDataList = new ArrayList<>();

        Set<Genre> genreSetActionAdventureScienceFiction = new HashSet<>();
        genreSetActionAdventureScienceFiction.add(GENRE_ACTION);
        genreSetActionAdventureScienceFiction.add(GENRE_ADVENTURE);
        genreSetActionAdventureScienceFiction.add(GENRE_SCIENCE_FICTION);
        SHOW_AVENGER_INFINITY_WAR.addGenres(genreSetActionAdventureScienceFiction);
        SHOW_AVENGER_INFINITY_WAR.setPoster(new Poster("/images/Avengers__Infinity_War2018423968.png"));
        showDataList.add(SHOW_AVENGER_INFINITY_WAR);

        Set<Genre> genreSetAdventureFantasyFamily = new HashSet<>();
        genreSetAdventureFantasyFamily.add(GENRE_ADVENTURE);
        genreSetAdventureFantasyFamily.add(GENRE_FANTASY);
        genreSetAdventureFantasyFamily.add(GENRE_FAMILY);
        SHOW_FANTASTIC_BEASTS_AND_WHERE_TO_FIND_THEM.addGenres(genreSetAdventureFantasyFamily);
        SHOW_FANTASTIC_BEASTS_AND_WHERE_TO_FIND_THEM.setPoster(
                new Poster("/images/Fantastic_Beasts_and_Where_to_Find_Them-427997046.png"));
        SHOW_FANTASTIC_BEASTS_THE_CRIMES_OF_GRINDELWALD.setPoster(
                new Poster("/images/Fantastic_Beasts__The_Crimes_of_Grindelwald-392145351.png"));
        SHOW_FANTASTIC_BEASTS_THE_CRIMES_OF_GRINDELWALD.addGenres(genreSetAdventureFantasyFamily);
        showDataList.add(SHOW_FANTASTIC_BEASTS_AND_WHERE_TO_FIND_THEM);
        showDataList.add(SHOW_FANTASTIC_BEASTS_THE_CRIMES_OF_GRINDELWALD);

        return showDataList;
    }

    public static ReadOnlyWatchList getShowDatabaseList() {
        WatchList database = new WatchList();
        for (Show show : getShowData()) {
            database.addShow(show);
        }
        return database;
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

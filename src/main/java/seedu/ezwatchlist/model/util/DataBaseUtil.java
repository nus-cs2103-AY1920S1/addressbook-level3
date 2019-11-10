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
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;

/**
 * Contains utility methods for populating {@code WatchList} with sample data.
 */
public class DataBaseUtil {
    private static final Genre GENRE_ACTION = new Genre("action");
    private static final Genre GENRE_COMEDY = new Genre("comedy");
    private static final Genre GENRE_ADVENTURE = new Genre("adventure");
    private static final Genre GENRE_SCIENCE_FICTION = new Genre("science fiction");

    private static final Show SHOW_JOKER = new Movie(
            new Name("Joker"),
            new Description("In Gotham City, mentally-troubled comedian Arthur Fleck embarks on a downward-spiral of "
                    + "social revolution and bloody crime. This path brings him face-to-face with his infamous "
                    + "alter-ego: \"The Joker\"."),
            new IsWatched("false"),
            new Date("4 October 2019"),
            new RunningTime(122),
            getActorSet("Joaquin Phoenix, Robert De Niro"));

    private static final Show SHOW_AVENGER_INFINITY_WAR = new Movie(
            new Name("Avengers: Infinity War"),
            new Description("As the Avengers and their allies have continued to protect the world from threats too "
                    + "large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A "
                    + "despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of "
                    + "unimaginable power, and use them to inflict his twisted will on all of reality. Everything the "
                    + "Avengers have fought for has led up to this moment - the fate of Earth and existence itself has "
                    + "never been more uncertain."),
            new IsWatched("false"),
            new Date("25 April 2018"),
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

    public static ArrayList<Show> getShowData() {
        ArrayList<Show> showDataList = new ArrayList<>();

        Set<Genre> genreJoker = new HashSet<>();
        genreJoker.add(GENRE_COMEDY);
        SHOW_JOKER.addGenres(genreJoker);
        showDataList.add(SHOW_JOKER);

        Set<Genre> genreAvengerInfinityWar = new HashSet<>();
        genreAvengerInfinityWar.add(GENRE_ACTION);
        genreAvengerInfinityWar.add(GENRE_ADVENTURE);
        genreAvengerInfinityWar.add(GENRE_SCIENCE_FICTION);
        SHOW_AVENGER_INFINITY_WAR.addGenres(genreAvengerInfinityWar);
        showDataList.add(SHOW_AVENGER_INFINITY_WAR);

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

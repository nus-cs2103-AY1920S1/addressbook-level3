package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.testutil.ShowBuilder;
import seedu.ezwatchlist.testutil.TypicalShows

class TvShowTest {


    @Test
    void getNumOfEpisodesWatched() {
        Set<Actor> actors = new HashSet<>();

        List<TvSeason> episodeList = new ArrayList<>();

        ArrayList<Episode> episodeList1 = new ArrayList<>();
        Episode episodee = new Episode("test", 1);
        episodeList1.add(episodee);

        TvSeason tvSeason = new TvSeason(1 , 1, episodeList1);
        episodeList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("des"), new IsWatched("false"), new Date("1/1/1"),
                new RunningTime(1), actors, 1, 1, episodeList);

        assertEquals(tvShow.getNumOfEpisodesWatched(), 1);
    }

    @Test
    void getTvSeasons() {
        Set<Actor> actors = new HashSet<>();

        List<TvSeason> episodeList = new ArrayList<>();

        ArrayList<Episode> episodeList1 = new ArrayList<>();
        Episode episodee = new Episode("test", 1);
        episodeList1.add(episodee);

        TvSeason tvSeason = new TvSeason(1 , 1, episodeList1);
        episodeList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("des"), new IsWatched("false"), new Date("1/1/1"),
                new RunningTime(1), actors, 1, 1, episodeList);

        assertEquals(tvShow.getTvSeasons(), episodeList);
    }

    @Test
    void getTotalNumOfEpisodes() {
        Set<Actor> actors = new HashSet<>();

        List<TvSeason> episodeList = new ArrayList<>();

        ArrayList<Episode> episodeList1 = new ArrayList<>();
        Episode episodee = new Episode("test", 1);
        episodeList1.add(episodee);

        TvSeason tvSeason = new TvSeason(1 , 1, episodeList1);
        episodeList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("des"), new IsWatched("false"), new Date("1/1/1"),
                new RunningTime(1), actors, 1, 1, episodeList);

        assertEquals(tvShow.getTotalNumOfEpisodes(), 1);
    }

    @Test
    void getLastWatchedSeasonNum() {
        Set<Actor> actors = new HashSet<>();

        List<TvSeason> episodeList = new ArrayList<>();

        ArrayList<Episode> episodeList1 = new ArrayList<>();
        Episode episodee = new Episode("test", 1);
        episodeList1.add(episodee);

        TvSeason tvSeason = new TvSeason(1 , 1, episodeList1);
        episodeList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("des"), new IsWatched("false"), new Date("1/1/1"),
                new RunningTime(1), actors, 1, 1, episodeList);

        assertEquals(tvShow.getLastWatchedSeasonNum(), 1);
    }

    @Test
    void getLastWatchedSeasonEpisode() {
        Set<Actor> actors = new HashSet<>();

        List<TvSeason> episodeList = new ArrayList<>();

        ArrayList<Episode> episodeList1 = new ArrayList<>();
        Episode episodee = new Episode("test", 1);
        episodeList1.add(episodee);

        TvSeason tvSeason = new TvSeason(1 , 1, episodeList1);
        episodeList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("des"), new IsWatched("false"), new Date("1/1/1"),
                new RunningTime(1), actors, 1, 1, episodeList);

        assertEquals(tvShow.getLastWatchedSeasonEpisode(), 1);


    }

    @Test
    void getNumOfSeasons() {
        Set<Actor> actors = new HashSet<>();

        List<TvSeason> episodeList = new ArrayList<>();

        ArrayList<Episode> episodeList1 = new ArrayList<>();
        Episode episodee = new Episode("test", 1);
        episodeList1.add(episodee);

        TvSeason tvSeason = new TvSeason(1 , 1, episodeList1);
        episodeList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("des"), new IsWatched("false"), new Date("1/1/1"),
                new RunningTime(1), actors, 1, 1, episodeList);

        assertEquals(tvShow.getNumOfSeasons(), 1);
    }

    @Test
    void getNumOfEpisodesOfSeason() {
        Set<Actor> actors = new HashSet<>();

        List<TvSeason> episodeList = new ArrayList<>();

        ArrayList<Episode> episodeList1 = new ArrayList<>();
        Episode episodee = new Episode("test", 1);
        episodeList1.add(episodee);

        TvSeason tvSeason = new TvSeason(1 , 1, episodeList1);
        episodeList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("des"), new IsWatched("false"), new Date("1/1/1"),
                new RunningTime(1), actors, 1, 1, episodeList);

        assertEquals(tvShow.getNumOfEpisodesOfSeason(1), 1);

    private Show showWatchedAll = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(195).build();
    private Show showWatched100 = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(100).build();
    private Show showWatched0 = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(0).build();

    @Test
    public void getNumOfEpisodesWatched() {
        assertEquals(showWatched0.getNumOfEpisodesWatched(), 0);
        assertEquals(showWatchedAll.getNumOfEpisodesWatched(), 195);
    }

    @Test
    public void getTotalNumOfEpisodes() {
        assertEquals(showWatched0.getTotalNumOfEpisodes(), showWatched100.getTotalNumOfEpisodes());
    }

    @Test
    public void getNumberOfSeasons() {
        assertEquals(showWatchedAll.getNumOfSeasons(), 9);
    }

    @Test
    public void getNumOfEpisodesOfSeason() {
        assertEquals(showWatchedAll.getNumOfEpisodesOfSeason(2), 22);
        assertEquals(showWatchedAll.getNumOfEpisodesOfSeason(9), 23);
    }

    @Test
    public void getLastWatchedSeasonNum() {
        assertEquals(showWatchedAll.getLastWatchedSeasonNum(), 9);
        assertEquals(showWatched100.getLastWatchedSeasonNum(), 6);
        assertEquals(showWatched0.getLastWatchedSeasonNum(), 0);
    }

    @Test
    public void getLastWatchedSeasonEpisode() {
        assertEquals(showWatchedAll.getLastWatchedSeasonEpisode(), 23);
        assertEquals(showWatched100.getLastWatchedSeasonEpisode(), 4);
        assertEquals(showWatched0.getLastWatchedSeasonEpisode(), 0);

    }

package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.actor.Actor;

class TvShowTest {

    @Test
    void getNumOfEpisodesWatched() {
        Set<Actor> actors = new HashSet<>();

        List<TvSeason> episodeList = new ArrayList<>();

        ArrayList<Episode> episodeList1 = new ArrayList<>();
        Episode episodee = new Episode("test", 1);
        episodeList1.add(episodee);

        TvSeason tvSeason = new TvSeason(1, 1, episodeList1);
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

        TvSeason tvSeason = new TvSeason(1, 1, episodeList1);
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

        TvSeason tvSeason = new TvSeason(1, 1, episodeList1);
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

        TvSeason tvSeason = new TvSeason(1, 1, episodeList1);
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

        TvSeason tvSeason = new TvSeason(1, 1, episodeList1);
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

        TvSeason tvSeason = new TvSeason(1, 1, episodeList1);
        episodeList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("des"), new IsWatched("false"), new Date("1/1/1"),
                new RunningTime(1), actors, 1, 1, episodeList);

        assertEquals(tvShow.getNumOfSeasons(), 1);
    }

}

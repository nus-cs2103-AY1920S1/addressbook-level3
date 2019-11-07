package seedu.ezwatchlist.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.TmdbTvSeasons;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeason;
import info.movito.themoviedbapi.model.tv.TvSeries;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.model.ImageRetrieval;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.Episode;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Contains utility methods for extracting information from movies and tv shows.
 */
public class ApiUtil {

    /**
     * Passes the movies from the movies page into the movies list with the new Movie model used in the application.
     *
     * @param movies  list of movies to be added into.
     * @param page    results from the API database.
     * @param apiCall API call to retrieve more information.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public static void extractMovies(ArrayList<Movie> movies, MovieResultsPage page,
                                     TmdbApi apiCall) throws OnlineConnectionException {
        for (MovieDb m : page.getResults()) {
            Movie toAdd = extractMovie(apiCall, m);
            movies.add(toAdd);
        }
    }

    /**
     * Morphs the MovieDb model into Movie model.
     * @param apiCall API call to retrieve more information.
     * @param movieDb model used by the API.
     * @return Movie used by the application.
     * @throws OnlineConnectionException when not connected to the internet/
     */
    public static Movie extractMovie(TmdbApi apiCall, MovieDb movieDb) throws OnlineConnectionException {
        Name movieName = new Name(movieDb.getTitle());
        final int movieId = movieDb.getId(); //movie id to retrieve instance
        TmdbMovies apiMovie = apiCall.getMovies();
        //gets the instance of the movie in the database
        MovieDb movie = apiMovie.getMovie(movieId, null, TmdbMovies.MovieMethod.credits);

        //movie fields
        RunningTime runtime = new RunningTime(movie.getRuntime());
        Description overview = new Description(movie.getOverview());
        Date releaseDate = new Date(movie.getReleaseDate());
        IsWatched isWatched = new IsWatched("false");

        //actors
        Set<Actor> actors = getActors(movie.getCast());

        Movie toAdd = new Movie(movieName, overview,
                isWatched, releaseDate, runtime, actors);

        //retrieve image
        addImage(movieDb.getPosterPath(), movieName, toAdd, apiCall);

        //genres
        setGenres(movie.getGenres(), toAdd);
        return toAdd;
    }

    /**
     * Uses the ImageRetrieval class to set an image to the show.
     *
     * @param posterPath path of the image online
     * @param name       name of the show
     * @param toAdd      the show to be modified
     * @param apiCall    the API call used
     * @throws OnlineConnectionException when not connected to the internet.
     */
    private static void addImage(String posterPath, Name name, Show toAdd, TmdbApi apiCall)
            throws OnlineConnectionException {
        try {
            ImageRetrieval instance = new ImageRetrieval(apiCall, posterPath, name.showName);
            String imagePath = instance.retrieveImage();
            toAdd.setPoster(new Poster(imagePath));
        } catch (IllegalArgumentException e) {
            toAdd.setPoster(new Poster());
        }
    }

    /**
     * Passes the Tv Shows from the Tv Show page into the tvShow list with the new TvShow model used in the application.
     *
     * @param tvShows list of tvShows to be added into.
     * @param page    results from the API database.
     * @param apiCall API call to retrieve more information.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public static void extractTvShows(ArrayList<TvShow> tvShows, TvResultsPage page,
                                      TmdbApi apiCall) throws OnlineConnectionException {
        for (TvSeries tv : page.getResults()) {
            TvShow tvShowToAdd = extractTvShow(apiCall, tv);

            tvShows.add(tvShowToAdd);
        }
    }

    /**
     * Morphs the TvSeries model into TvShow model.
     * @param apiCall API call to retrieve more information.
     * @param tv model used by the API.
     * @return TvShow model used by the application.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public static TvShow extractTvShow(TmdbApi apiCall, TvSeries tv) throws OnlineConnectionException {
        final int tvId = tv.getId();
        TmdbTV apiCallTvSeries = apiCall.getTvSeries();
        TvSeries series = apiCallTvSeries.getSeries(tvId, null);
        TmdbTvSeasons tvSeasons = apiCall.getTvSeasons();
        final int numberOfSeasons = series.getNumberOfSeasons();

        //seasons
        ArrayList<seedu.ezwatchlist.model.show.TvSeason> seasonsList = new ArrayList<>();
        extractSeasons(tvId, tvSeasons, numberOfSeasons, seasonsList);

        //runtime
        List<Integer> episodeRuntime = series.getEpisodeRuntime();
        RunningTime runTime = new RunningTime(episodeRuntime.isEmpty() ? 0 : getAverageRuntime(episodeRuntime));

        //tv show fields.
        Name tvName = new Name(tv.getName()); //name
        Date date = new Date(series.getFirstAirDate()); //date of release
        int totalNumOfEpisodes = getTotalNumOfEpisodes(seasonsList);
        Description description = new Description(tv.getOverview()); //description
        IsWatched isWatched = new IsWatched("false");

        //actors
        Credits credits = apiCallTvSeries.getCredits(tvId, null);
        Set<Actor> actors = getActors(credits.getCast());

        TvShow tvShowToAdd = new TvShow(tvName, description,
                isWatched, date, runTime,
                actors, 0, totalNumOfEpisodes, seasonsList);

        //image
        addImage(tv.getPosterPath(), tvName, tvShowToAdd, apiCall);

        //genres
        setGenres(series.getGenres(), tvShowToAdd);
        return tvShowToAdd;
    }

    /**
     * Extracts the seasons into the season list.
     *
     * @param tvId            id of the TV Show in the database.
     * @param tvSeasons       the api TvSeason database.
     * @param numberOfSeasons number of seasons of the show.
     * @param seasonsList     list to be added into.
     */
    private static void extractSeasons(int tvId, TmdbTvSeasons tvSeasons, int numberOfSeasons,
                                       ArrayList<seedu.ezwatchlist.model.show.TvSeason> seasonsList) {
        for (int seasonNo = 1; seasonNo <= numberOfSeasons; seasonNo++) {
            TvSeason tvSeason = tvSeasons.getSeason(tvId, seasonNo,
                    null);

            seedu.ezwatchlist.model.show.TvSeason season = extractEpisodes(tvSeason);

            seasonsList.add(season);
        }
    }

    /**
     * Extracts the episodes of each season.
     *
     * @param tvSeason the data from the API of the season.
     * @return a season list with the episodes added in.
     */
    private static seedu.ezwatchlist.model.show.TvSeason extractEpisodes(TvSeason tvSeason) {
        List<TvEpisode> episodes = tvSeason.getEpisodes();
        ArrayList<Episode> episodeList = new ArrayList<>();

        for (TvEpisode episode : episodes) {
            episodeList.add(new Episode(
                    episode.getName(), episode.getEpisodeNumber()));
        }

        return new seedu.ezwatchlist.model.show.TvSeason(tvSeason.getSeasonNumber(), episodes.size(),
                episodeList);
    }

    /**
     * Sets the genres taken from the API.
     *
     * @param genres      the genre list to be added into
     * @param tvShowToAdd the show to be modified.
     */
    private static void setGenres(List<Genre> genres, Show tvShowToAdd) {
        ArrayList<seedu.ezwatchlist.model.show.Genre> genreList = new ArrayList<>();
        genres.forEach(genre -> genreList.add(new seedu.ezwatchlist.model.show.Genre(genre.getName())));
        Set<seedu.ezwatchlist.model.show.Genre> genreSet = new HashSet<>(genreList);
        tvShowToAdd.addGenres(genreSet);
    }

    /**
     * Calculates the average runtime.
     *
     * @param episodesRuntime list of runtimes
     * @return the average of the runtime.
     */
    private static int getAverageRuntime(List<Integer> episodesRuntime) {
        float totalRuntime = 0;
        int noOfEpisodes = episodesRuntime.size();

        for (int i = 0; i < noOfEpisodes; i++) {
            int individualRuntime = episodesRuntime.get(i);
            totalRuntime += individualRuntime;
        }

        int averageRunTime = Math.round(totalRuntime / noOfEpisodes);
        return averageRunTime;
    }

    /**
     * Retrieves the actors from the online database.
     *
     * @param cast the list of cast taken online.
     * @return a set of actors.
     */
    private static Set<Actor> getActors(List<PersonCast> cast) {
        Set<Actor> actors = new HashSet<>();
        for (PersonCast personCast : cast) {
            Actor actor = new Actor(personCast.getName());
            actors.add(actor);
        }
        return actors;
    }

    /**
     * Returns the total number of episodes
     *
     * @param tvSeasons the online tv season database
     * @return total number of episodes
     */
    private static int getTotalNumOfEpisodes(List<seedu.ezwatchlist.model.show.TvSeason> tvSeasons) {
        int totalEpisodes = 0;
        for (seedu.ezwatchlist.model.show.TvSeason season : tvSeasons) {
            totalEpisodes += season.getEpisodes().size();
        }
        return totalEpisodes;
    }

    /**
     * Filters the list of Shows to a list of Movies.
     * @param shows the list of Shows.
     * @return a list of movies from the Shows.
     */
    public static List<Movie> filterToMovieFromShow(List<Show> shows) {
        List<Movie> movies = new LinkedList<>();
        for (Show show: shows) {
            if (show.getType().equals("Movie")) {
                movies.add((Movie) show);
            }
        }
        return movies;
    }

    /**
     * Filters the list of Shows to a list of Tv Shows.
     * @param shows the list of Shows.
     * @return a list of Tv Shows from the Shows.
     */
    public static List<TvShow> filterToTvShowsFromShow(List<Show> shows) {
        List<TvShow> tvShows = new LinkedList<>();
        for (Show show: shows) {
            if (show.getType().equals("Tv Show")) {
                tvShows.add((TvShow) show);
            }
        }
        return tvShows;
    }

    /**
     * Retrieves a Tv Show from it's ID
     * @param tmdbApi
     * @param tvId the ID of the Tv Show
     * @return TvShow
     */
    public static TvShow getTvShow(TmdbApi tmdbApi, Integer tvId) {
        try {
            TvSeries tvSeries = tmdbApi.getTvSeries().getSeries(tvId, null, TmdbTV.TvMethod.values());
            return extractTvShow(tmdbApi, tvSeries);
        } catch (OnlineConnectionException e) {
            return null;
        }
    }

    /**
     * Retrieves a Movie from it's ID.
     * @param tmdbApi
     * @param movieId the ID of the Movie.
     * @return Movie
     */
    public static Movie getMovie(TmdbApi tmdbApi, Integer movieId) {
        try {
            MovieDb movie = tmdbApi.getMovies().getMovie(movieId, null, TmdbMovies.MovieMethod.values());
            return extractMovie(tmdbApi, movie);
        } catch (OnlineConnectionException e) {
            return null;
        }
    }
}

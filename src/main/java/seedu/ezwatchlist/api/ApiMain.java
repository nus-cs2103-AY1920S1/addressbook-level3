package seedu.ezwatchlist.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.TmdbTvSeasons;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeason;
import info.movito.themoviedbapi.model.tv.TvSeries;
import info.movito.themoviedbapi.tools.MovieDbException;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.model.ApiInterface;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.Episode;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Main class for the API to connect to the server
 */
public class ApiMain implements ApiInterface {
    //API key is to connect with the tmdb server.
    private static final String API_KEY = "44ed1d7975d7c699743229199b1fc26e";
    private static final String CONNECTION_ERROR_MESSAGE = "Looks like you're not connected to the internet";
    private TmdbApi apiCall;
    private boolean isConnected = false;

    /**
     * Constructor for ApiMain object used to interact with the API.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public ApiMain() throws OnlineConnectionException {
        try {
            apiCall = new TmdbApi(API_KEY);
            isConnected = true;
        } catch (MovieDbException e) {
            //when not connected to the internet
            notConnected();
        }
    }

    /**
     * Checks if the API is connected to the internet.
     *
     * @return true if connected to the API.
     */
    public boolean isConnected() {
        try {
            apiCall = new TmdbApi(API_KEY);
            isConnected = true;
        } catch (MovieDbException e) {
            isConnected = false;
        } finally {
            return isConnected;
        }
    }

    /**
     * Helper function to call when not connected to the API.
     *
     * @throws OnlineConnectionException
     */
    private void notConnected() throws OnlineConnectionException {
        throw new OnlineConnectionException(CONNECTION_ERROR_MESSAGE);
    }

    public List<Movie> getUpcomingMovies() throws OnlineConnectionException {
        try {
            MovieResultsPage upcoming = apiCall.getMovies().getUpcoming(null, null, null);
            ArrayList<Movie> movies = new ArrayList<>();

            for (MovieDb m : upcoming.getResults()) {
                String movieName = m.getTitle();
                RunningTime runtime = new RunningTime(m.getRuntime());
                String overview = m.getOverview();
                String releaseDate = m.getReleaseDate();

                Movie toAdd = new Movie(new Name(movieName), new Description(overview), new IsWatched(false),
                        new Date(releaseDate), runtime , new HashSet<Actor>());

                //retrieve image
                ImageRetrieval instance = new ImageRetrieval(apiCall, m.getPosterPath());
                toAdd.setPoster(new Poster(instance.retrieveImage(movieName)));

                movies.add(toAdd);
            }
            return movies;
        } catch (MovieDbException e) {
            notConnected();
            return new ArrayList<Movie>();
        }
    }

    /**
     * Retrieves the movies from the API by the string given.
     *
     * @param name the name of the movie that the user wants to search.
     * @return a list of movies that are returned from the API search call.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public List<Movie> getMovieByName(String name) throws OnlineConnectionException {
        ArrayList<Movie> movies = new ArrayList<>();
        try {

            MovieResultsPage page = apiCall.getSearch().searchMovie(name,
                    null, null, true, 1);

            for (MovieDb m : page.getResults()) {
                String movieName = m.getTitle();
                final int movieId = m.getId();
                TmdbMovies apiMovie = apiCall.getMovies();
                MovieDb movie = apiMovie.getMovie(movieId, null, TmdbMovies.MovieMethod.credits);

                RunningTime runtime = new RunningTime(movie.getRuntime());
                String overview = m.getOverview();
                String releaseDate = m.getReleaseDate();

                //actors
                Set<Actor> actors = getActors(movie.getCast());

                Movie toAdd = new Movie(new Name(movieName), new Description(overview),
                        new IsWatched(false), new Date(releaseDate), runtime , actors);

                //retrieve image
                ImageRetrieval instance = new ImageRetrieval(apiCall, m.getPosterPath());
                String imagePath = instance.retrieveImage(movieName);
                toAdd.setPoster(new Poster(imagePath));

                movies.add(toAdd);
            }
            return movies;
        } catch (MovieDbException e) {
            notConnected();
            return movies;
        }
    }

    /**
     * Retrieves the tv shows from the API by the string given.
     *
     * @param name the name of the tv show that the user wants to search.
     * @return a list of tv shows that are returned from the API search call.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public List<TvShow> getTvShowByName(String name) throws OnlineConnectionException {
        ArrayList<TvShow> tvShows = new ArrayList<>();

        try {
            TvResultsPage page = apiCall.getSearch().searchTv(name, null, 1);

            for (TvSeries tv : page.getResults()) {
                final int tvId = tv.getId();
                List<TvSeason> seasons = tv.getSeasons();
                ArrayList<seedu.ezwatchlist.model.show.TvSeason> seasonsList = new ArrayList<>();
                TmdbTvSeasons tvSeasons = apiCall.getTvSeasons();
                final int numberOfSeasons = tv.getNumberOfSeasons();

                //seasons
                for (int seasonNo = 1; seasonNo < numberOfSeasons; seasonNo++) {
                    TvSeason tvSeason = tvSeasons.getSeason(tvId, seasonNo,
                            null, TmdbTvSeasons.SeasonMethod.values());

                    List<TvEpisode> episodes = tvSeason.getEpisodes();
                    ArrayList<Episode> episodeList = new ArrayList<>();

                    for (TvEpisode episode : episodes) {
                        episodeList.add(new seedu.ezwatchlist.model.show.Episode(
                               episode.getName(), episode.getEpisodeNumber()));
                    }

                    seedu.ezwatchlist.model.show.TvSeason tvS =
                            new seedu.ezwatchlist.model.show.TvSeason(tvSeason.getSeasonNumber(), episodes.size(),
                                    episodeList);
                    seasonsList.add(tvS);
                }

                Credits credits = apiCall.getTvSeries().getCredits(tvId, null);
                Date date = new Date(apiCall.getTvSeries().getSeries(tvId, null,
                        TmdbTV.TvMethod.external_ids).getFirstAirDate());
                //actors
                Set<Actor> actors = getActors(credits.getCast());

                TvShow tvShowToAdd = new TvShow(new Name(tv.getName()), new Description(tv.getOverview()),
                        new IsWatched(false), date, new RunningTime(20),
                        actors, 0, tv.getNumberOfEpisodes(), seasonsList);


                //image
                ImageRetrieval instance = new ImageRetrieval(apiCall, tv.getPosterPath());
                tvShowToAdd.setPoster(new Poster(instance.retrieveImage(tv.getName())));

                tvShows.add(tvShowToAdd);
            }
            return tvShows;
        } catch (MovieDbException e) {
            notConnected();
            return tvShows;
        }
    }

    private Set<Actor> getActors(List<PersonCast> cast) {
        Set<Actor> actors = new HashSet<>();
        for (PersonCast personCast: cast) {
            Actor actor = new Actor(personCast.getName());
            actors.add(actor);
        }
        return actors;
    }
}

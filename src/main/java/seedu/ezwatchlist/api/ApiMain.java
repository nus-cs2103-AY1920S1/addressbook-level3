package seedu.ezwatchlist.api;


import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.Artwork;
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
     * Checks whether the API is connected
     * @return boolean value indicating the connection of API
     */
    public boolean isApiConnected() {
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
     * sets the isConnected flag to false.
     * @throws OnlineConnectionException
     */
    public void notConnected() throws OnlineConnectionException {
        isConnected = false;
        throw new OnlineConnectionException(CONNECTION_ERROR_MESSAGE);
    }

    // can add upcoming movie methods
    public List<Movie> getMovieByName(String name) throws OnlineConnectionException {
        try {
            MovieResultsPage page = apiCall.getSearch().searchMovie(name, null, null, true, null);
            ArrayList<Movie> movies = new ArrayList<>();

            for (MovieDb m : page.getResults()) {
                movies.add(new Movie(new Name(m.getTitle()), new Description("placeholder")/*m.getTagline())*/,
                        new IsWatched(false), new Date(m.getReleaseDate()),
                        new RunningTime(m.getRuntime()), new HashSet<Actor>()));
                m.getPosterPath();
                List<Artwork> artworkTypes = m.getImages();
                artworkTypes.get(0);
            }
            isConnected = true;
            return movies;
        } catch (MovieDbException e) {
            notConnected();
            return new ArrayList<Movie>();
        }
    }

    public List<TvShow> getTvShowByName(String name) throws OnlineConnectionException {
        try {
            TvResultsPage page = apiCall.getSearch().searchTv(name, null, null);
            ArrayList<TvShow> tvShows = new ArrayList<>();

            for (TvSeries tv : page.getResults()) {
                List<TvSeason> seasons = tv.getSeasons();
                ArrayList<seedu.ezwatchlist.model.show.TvSeason> seasonsList = new ArrayList<>();
                Credits credits = tv.getCredits();
                List<PersonCast> cast = credits.getCast();
                PersonCast personCast = cast.get(0);
                personCast.getName();
                for (TvSeason tvSeason : seasons) {
                    List<TvEpisode> episodes = tvSeason.getEpisodes();
                    ArrayList<Episode> episodeList = new ArrayList<>();
                    for (TvEpisode episode : episodes) {
                        episodeList.add(new seedu.ezwatchlist.model.show.Episode(new Name(episode.getName()),
                                episode.getEpisodeNumber()));
                    }
                    seedu.ezwatchlist.model.show.TvSeason tvS =
                            new seedu.ezwatchlist.model.show.TvSeason(tvSeason.getSeasonNumber(), episodes.size(),
                                    episodeList);
                    seasonsList.add(tvS);
                }

                tvShows.add(new TvShow(new Name(tv.getName()), new Description(tv.getOverview()), new IsWatched(false),
                        new Date(tv.getFirstAirDate()),
                        new RunningTime(tv.getEpisodeRuntime().get(0)), null, 0,
                        tv.getNumberOfEpisodes(), seasonsList));
            }
            isConnected = true;
            return tvShows;
        } catch (MovieDbException e) {
            notConnected();
            return new ArrayList<TvShow>();
        }
    }

    /**
     * Somehow tests the image haha wtf am I writing
     * @param name
     */
    public void testImage(String name) {
        MovieResultsPage page = apiCall.getSearch().searchMovie(name, null, null, true, null);
        List<MovieDb> movies = page.getResults();
        MovieDb firstMovie = movies.get(0);
        ImageRetrieval imageRetrieval = new ImageRetrieval(apiCall, firstMovie.getPosterPath());
        imageRetrieval.downloadImage(firstMovie.getTitle());
    }



    /**
     * test function
     * @param args
     * @throws IOException
    */

    public static void main(String[] args) throws IOException, OnlineConnectionException {
        ApiMain apiMain = new ApiMain();

        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        apiMain.testImage(input);
//        TmdbMovies movies = tmdbApi.getMovies();
//        MovieDb movie = movies.getMovie(5353, null, TmdbMovies.MovieMethod.similar, TmdbMovies.MovieMethod.keywords,
//                TmdbMovies.MovieMethod.credits, TmdbMovies.MovieMethod.images);
//        System.out.println(movie.getOriginalTitle());
//        p2( movie.getSimilarMovies());
//        List<Artwork> artworks = movie.getImages();
//        String filePath = artworks.get(0).getFilePath();
//        TmdbConfiguration configuration = tmdbApi.getConfiguration();
//        final String baseUrl = configuration.getBaseUrl() + "w500";
//        BufferedImage img = ImageIO.read(new URL(baseUrl + filePath));
//        Graphics g = img.getGraphics();
//        g.drawImage(img, 0, 0, null);
//
//        //keywords
//        List<Keyword> keywordList = movie.getKeywords();
//        p(keywordList);
//        List<Genre> genres = movie.getGenres();
//        System.out.println("genres");
//        p(genres);
//        System.out.println("casts");
//        List<PersonCast> cast = movie.getCast();
//        p(cast);
//        System.out.println("crew");
//        p(movie.getCrew());
    }

    /**
     * p method
     * @param s
     * @param <T>
     */
    public static <T> void p (List<T> s) {
        for (T t : s) {
            System.out.println(t);
        }
    }

    /**
     * p2 method
     * @param l
     */
    public static void p2 (List<MovieDb> l) {
        for (MovieDb m : l) {
            System.out.println(m.getOriginalTitle());
        }
    }
}

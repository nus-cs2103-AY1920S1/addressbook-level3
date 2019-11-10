package seedu.ezwatchlist.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import seedu.ezwatchlist.api.exceptions.NoRecommendationsException;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.model.ApiInterface;
import seedu.ezwatchlist.api.model.ApiManager;
import seedu.ezwatchlist.api.util.ApiUtil;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvShow;
import seedu.ezwatchlist.model.show.UniqueShowList;

/**
 * Represents a Statistics object that contains relevant information.
 */
public class Statistics {
    private final Model model;
    private ApiInterface apiManager;

    public Statistics (Model model) throws OnlineConnectionException {
        this.model = model;
        try {
            apiManager = new ApiManager();
        } catch (OnlineConnectionException e) {
            //have to handle
            apiManager = null;
            e.printStackTrace();
        }
    }

    /**
     * Gets the movies that are likely to be forgotten by the user.
     * @return an observable list of forgotten shows
     */
    public ObservableList<Show> getForgotten() {
        ObservableList<Show> watchlist = model.getWatchList().getShowList().filtered(show -> !show.isWatched().value);
        UniqueShowList forgotten = new UniqueShowList();
        if (watchlist != null && watchlist.size() > 4) {
            forgotten.add(watchlist.get(0));
            forgotten.add(watchlist.get(1));
            forgotten.add(watchlist.get(2));
        }
        return forgotten.asUnmodifiableObservableList();
    }

    /**
     * Gets the favourite genre of the user.
     * @return an observable list of genres strings
     */
    public ObservableMap<String, Integer> getFavouriteGenre() {
        HashMap<String, Integer> genreRecords = new HashMap<>();
        if (model.getWatchList().getShowList() == null) {
            return FXCollections.observableHashMap();
        }
        model.getWatchList().getShowList().stream().forEach(show -> {
            show.getGenres().stream().forEach(genre -> {
                if (genreRecords.containsKey(genre.getGenreName())) {
                    genreRecords.put(genre.getGenreName(), genreRecords.get(genre.getGenreName()) + 1);
                } else {
                    genreRecords.put(genre.getGenreName(), 1);
                }
            });
        });

        List<String> keyList = new ArrayList<>();
        keyList.addAll(genreRecords.keySet());
        Collections.sort(keyList, (key1, key2) -> genreRecords.get(key2) - genreRecords.get(key1));

        ObservableMap<String, Integer> favouriteGenres = FXCollections.observableHashMap();
        for (int i = 0; i < 3 && i < keyList.size(); i++) {
            favouriteGenres.put(keyList.get(i), genreRecords.get(keyList.get(i)));
        }
        return favouriteGenres;
    }

    public ObservableList<Movie> getMovieRecommendations() throws OnlineConnectionException {
        List<Movie> recommendations = null;
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        List<Movie> movieList = ApiUtil.filterToMovieFromShow(model.getWatchList().getShowList());
        if (movieList.isEmpty()) {
            System.out.println("movie split is empty");
        }
        try {
            recommendations = apiManager.getMovieRecommendations(movieList, 3);
            return FXCollections.observableArrayList(recommendations);
        } catch (OnlineConnectionException e) {
            e.printStackTrace();
            return null;
        } catch (NoRecommendationsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<TvShow> getTvShowRecommendations() throws OnlineConnectionException {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        List<TvShow> tvList = ApiUtil.filterToTvShowsFromShow(model.getWatchList().getShowList());
        List<TvShow> recommendations = null;

        try {
            recommendations = apiManager.getTvShowRecommendations(tvList, 3);
            return FXCollections.observableArrayList(recommendations);
        } catch (OnlineConnectionException e) {
            return null;
        } catch (NoRecommendationsException e) {
            return null;
        }
    }
}

package seedu.ezwatchlist.model.show;

import java.util.ArrayList;
import java.util.List;

public class Genres {
    private List<String> genres;

    public Genres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}

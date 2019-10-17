package seedu.ezwatchlist.api.model;

import java.util.List;
import java.util.Optional;

/**
 * Interface to contain information retrieved from API.
 */
public abstract class Information {
    private Optional<String> name;
    private Optional<String> description;
    private Optional<List<String>> actors;
    private Optional<String> date;

    public void addName(String name) {
        this.name = Optional.ofNullable(name);
    }

    public void addDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    public void addActors(List<String> actors) {
        this.actors = Optional.ofNullable(actors);
    }

    public void addDate(String date) {
        this.date = Optional.ofNullable(date);
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<String> getDate() {
        return date;
    }

    public Optional<List<String>> getActors() {
        return actors;
    }

    public Optional<String> getDescription() {
        return description;
    }
}

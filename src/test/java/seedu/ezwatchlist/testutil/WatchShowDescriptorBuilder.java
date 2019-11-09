package seedu.ezwatchlist.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ezwatchlist.logic.commands.WatchCommand.WatchShowDescriptor;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvSeason;

/**
 * A utility class to help with building WatchShowDescriptor objects.
 */
public class WatchShowDescriptorBuilder {

    private WatchShowDescriptor descriptor;

    public WatchShowDescriptorBuilder() {
        descriptor = new WatchShowDescriptor();
    }

    public WatchShowDescriptorBuilder(WatchShowDescriptor descriptor) {
        this.descriptor = new WatchShowDescriptor(descriptor);
    }

    /**
     * Returns an {@code WatchShowDescriptor} with fields containing {@code show}'s details
     */
    public WatchShowDescriptorBuilder(Show show) {
        descriptor = new WatchShowDescriptor();
        descriptor.setName(show.getName());
        descriptor.setType(show.getType());
        descriptor.setDescription(show.getDescription());
        descriptor.setIsWatched(show.isWatched());
        descriptor.setDateOfRelease(show.getDateOfRelease());
        descriptor.setRunningTime(show.getRunningTime());
        descriptor.setActors(show.getActors());
        descriptor.setPoster(show.getPoster());
        if (show.getType().equals("Tv Show")) {
            descriptor.setNumOfEpisodesWatched(show.getNumOfEpisodesWatched());
            descriptor.setTotalNumOfEpisodes(show.getTotalNumOfEpisodes());
            descriptor.setSeasons(show.getTvSeasons());
        }
    }

    /**
     * Sets the {@code Name} of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code type} of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withType(String type) {
        descriptor.setType(type);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code IsWatched} of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withIsWatched(String isWatched) {
        descriptor.setIsWatched(new IsWatched(isWatched));
        return this;
    }

    /**
     * Sets the {@code DateOfRelease} of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withDateOfRelease(String date) {
        descriptor.setDateOfRelease(new Date(date));
        return this;
    }

    /**
     * Sets the {@code RunningTime} of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withRunningTime(int runningTime) {
        descriptor.setRunningTime(new RunningTime(runningTime));
        return this;
    }

    /**
     * Parses the {@code actors} into a {@code Set<Tag>} and set it to the {@code WatchShowDescriptor}
     * that we are building.
     */
    public WatchShowDescriptorBuilder withActors(String... actors) {
        Set<Actor> actorSet = Stream.of(actors).map(Actor::new).collect(Collectors.toSet());
        descriptor.setActors(actorSet);
        return this;
    }

    /**
     * Sets the number of episodes watched of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withNumOfEpisodesWatched(int numOfEpisodesWatched) {
        descriptor.setNumOfEpisodesWatched(numOfEpisodesWatched);
        return this;
    }

    /**
     * Sets the total number of episodes of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withTotalNumOfEpisodes(int totalNumOfEpisodes) {
        descriptor.setTotalNumOfEpisodes(totalNumOfEpisodes);
        return this;
    }

    /**
     * Sets the number of seasons watched of the {@code WatchShowDescriptor} that we are building.
     */
    public WatchShowDescriptorBuilder withNumOfSeasonsWatched(int numOfSeasonsWatched) {
        descriptor.setNumOfSeasonsWatched(numOfSeasonsWatched);
        return this;
    }

    /**
     * Parses the {@code seasons} into a {@code List<TvSeason>} and set it to the {@code WatchShowDescriptor}
     * that we are building.
     */
    public WatchShowDescriptorBuilder withSeasons(TvSeason... seasons) {
        List<TvSeason> seasonSet = new ArrayList<>();
        for (TvSeason season : seasons) {
            seasonSet.add(season);
        }
        descriptor.setSeasons(seasonSet);
        return this;
    }

    public WatchShowDescriptor build() {
        return descriptor;
    }
}

package seedu.address.model.feed;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents a feed in the feed list.
 * Guarantees: details are present and not null, `name` and `address` field values are validated, immutable.
 */
public class Feed {
    private final String name;
    private final String address;
    private final Set<FeedPost> posts = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Feed(String name, String address) {
        requireAllNonNull(name, address);
        this.name = name;
        this.address = address;
    }

    /**
     * Constructor that takes in initial feed data in addition to the usual fields.
     */
    public Feed(String name, String address, Set<FeedPost> posts) {
        this(name, address);
        requireAllNonNull(posts);
        this.setPosts(posts);
    }

    /**
     * Fetches the most recent 5 posts from this feed.
     *
     * @return List of maximum 5 posts.
     */
    public Set<FeedPost> fetchPosts() {
        String feedData = this.fetchFeedData();

        if (feedData == null) {
            return this.posts;
        } else {
            Set<FeedPost> feedPosts = this.parseFeedData(feedData);
            return feedPosts;
        }
    }

    /**
     * Fetches the feed's data as a String. This method first attempts to fetch data from the feed's remote
     * address. If unsuccessful, it returns the cached feed data.
     *
     * @return String representing the feed data.
     */
    private String fetchFeedData() {
        StringBuilder feedData = new StringBuilder();
        int matchCount = 0;

        try {
            // Fetch remote
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String titleBuffer = in.readLine();
            String linkBuffer;

            // Filter data
            while (
                    titleBuffer != null
                            && (linkBuffer = in.readLine()) != null
                            && matchCount < 5
            ) {
                titleBuffer = titleBuffer.strip();
                linkBuffer = linkBuffer.strip();

                if (titleBuffer.contains("<title>") && linkBuffer.contains("<link>")) {
                    feedData.append(String.format("%s%n", titleBuffer));
                    feedData.append(String.format("%s%n", linkBuffer));
                    matchCount = matchCount + 1;
                }

                titleBuffer = linkBuffer;
            }

            // Cleanup
            in.close();
            conn.disconnect();

            LogsCenter.getLogger(Feed.class).info(
                    String.format("[Feed: %s] Successfully fetched remote feed data", name));
        } catch (Exception e) {
            LogsCenter.getLogger(Feed.class).warning(
                    String.format("[Feed: %s] Failed to fetch remote feed data, using local cache", name));
        }


        return feedData.length() == 0 ? null : feedData.toString();
    }

    /**
     * Parses the raw feed data into a set of feed posts.
     *
     * @param feedData String representing the raw feed data.
     * @return Set of feed posts.
     */
    private Set<FeedPost> parseFeedData(String feedData) {
        Set<FeedPost> feedPosts = new HashSet<>();

        try {
            Scanner sc = new Scanner(feedData);

            String titleBuffer;
            String linkBuffer;
            String title;
            String link;

            while (sc.hasNextLine()
                    && (titleBuffer = sc.nextLine()) != null
                    && (linkBuffer = sc.nextLine()) != null
            ) {
                title = Jsoup.parse(titleBuffer).text()
                        .replace("<title>", "").replace("</title>", "").strip();
                link = Jsoup.parse(linkBuffer).text()
                        .replace("<link>", "").replace("</link>", "").strip();

                FeedPost feedPost = new FeedPost(name, title, link);
                feedPosts.add(feedPost);
            }

            sc.close();
            this.setPosts(feedPosts);

            LogsCenter.getLogger(Feed.class).info(
                    String.format("[Feed: %s] Successfully parsed feed posts and updated local cache", name));
        } catch (Exception e) {
            LogsCenter.getLogger(Feed.class).warning(
                    String.format("[Feed: %s] Failed to parse feed posts", name));
        }

        return feedPosts;
    }

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }

    public void setPosts(Set<FeedPost> posts) {
        this.posts.clear();
        this.posts.addAll(posts);
    }

    public Set<FeedPost> getPosts() {
        return posts;
    }

    /**
     * Returns true if both feeds have the same name and address.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Feed)) {
            return false;
        }

        Feed otherFeed = (Feed) other;
        return otherFeed.getName().equals(getName())
                && otherFeed.getAddress().equals(getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", getName(), getAddress());
    }
}

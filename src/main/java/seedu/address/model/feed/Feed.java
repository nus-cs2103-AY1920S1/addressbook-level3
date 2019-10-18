package seedu.address.model.feed;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import org.jsoup.Jsoup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;

/**
 * Represents a feed in the feed list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Feed {
    private final String name;
    private final String address;

    /**
     * Every field must be present and not null.
     */
    public Feed(String name, String address) {
        requireAllNonNull(name, address);
        this.name = name;
        this.address = address;
    }

    /**
     * Fetches the most recent 5 posts from this feed.
     *
     * @return List of maximum 5 posts.
     */
    public ObservableList<FeedPost> fetchPosts() {
        ObservableList<FeedPost> posts = FXCollections.observableArrayList();

        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            int count = 0;
            String title = null;
            String link = null;
            String buffer1;
            String buffer2;

            while ((buffer1 = in.readLine()) != null && count < 5) {
                if (buffer1.contains("<title>")) {
                    buffer2 = in.readLine();

                    if (buffer2.contains("<link>")) {
                        title = Jsoup.parse(buffer1).text()
                                .replace("<title>", "").replace("</title>", "").strip();
                        link = Jsoup.parse(buffer2).text()
                                .replace("<link>", "").replace("</link>", "").strip();

                        FeedPost feedPost = new FeedPost(name, title, link);
                        posts.add(feedPost);
                        count = count + 1;
                    }
                }

                title = null;
                link = null;
            }

            in.close();
            conn.disconnect();
        } catch (Exception e) {
            LogsCenter.getLogger(Feed.class).warning("Failed to fetch posts from " + toString());
        }

        return posts;
    }

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
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

package seedu.address.model.show;

public class Episode {
    private Name name;
    private int episodeNum;

    public Episode(Name name, int episodeNum) {
        this.name = name;
        this.episodeNum = episodeNum;
    }

    public Name getName() {
        return name;
    }

    public int getEpisodeNum() {
        return episodeNum;
    }
}

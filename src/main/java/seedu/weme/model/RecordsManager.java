package seedu.weme.model;

import static java.util.Objects.requireNonNull;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.meme.Description;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.Name;

/**
 * Stores all past records of command arguments.
 */
public class RecordsManager implements Records {
    private Set<String> pathRecords;
    private Set<String> descriptionRecords;
    private Set<String> tagRecords;
    private Set<String> nameRecords;
    private Set<String> colorRecords;

    public RecordsManager() {
        this.pathRecords = new HashSet<>();
        this.descriptionRecords = new HashSet<>();
        this.tagRecords = new HashSet<>();
        this.nameRecords = new HashSet<>();
        this.colorRecords = new HashSet<>();
    }

    public RecordsManager(Set<String> pathRecords,
                          Set<String> descriptionRecords,
                          Set<String> tagRecords,
                          Set<String> nameRecords,
                          Set<String> colorRecords) {
        this();
        this.pathRecords.addAll(pathRecords);
        this.descriptionRecords.addAll(descriptionRecords);
        this.tagRecords.addAll(tagRecords);
        this.nameRecords.addAll(nameRecords);
        this.colorRecords.addAll(colorRecords);
    }

    public RecordsManager(Records records) {
        this(records.getPaths(), records.getDescriptions(), records.getTags(), records.getNames(), records.getColors());
    }

    @Override
    public Set<String> getPaths() {
        return new HashSet<>(pathRecords);
    }

    @Override
    public Set<String> getDescriptions() {
        return new HashSet<>(descriptionRecords);
    }

    @Override
    public Set<String> getTags() {
        return new HashSet<>(tagRecords);
    }

    @Override
    public Set<String> getNames() {
        return new HashSet<>(nameRecords);
    }

    @Override
    public Set<String> getColors() {
        return new HashSet<>(colorRecords);
    }

    @Override
    public void addPath(ImagePath path) {
        pathRecords.add(path.toString());
    }

    @Override
    public void addDescription(Description description) {
        if (!description.toString().isEmpty()) {
            descriptionRecords.add(description.toString());
        }
    }

    @Override
    public void addTags(Set<Tag> tags) {
        for (Tag tag: tags) {
            tagRecords.add(tag.getTagName());
        }
    }

    @Override
    public void addName(Name name) {
        nameRecords.add(name.toString());
    }

    @Override
    public void addColor(Color color) {
        colorRecords.add(color.toString());
    }

    /**
     * Resets the existing records of this {@code RecordsManager} with {@code newRecords}.
     */
    @Override
    public void resetRecords(Records records) {
        requireNonNull(records);

        pathRecords.clear();
        descriptionRecords.clear();
        tagRecords.clear();
        nameRecords.clear();
        colorRecords.clear();

        pathRecords.addAll(records.getPaths());
        descriptionRecords.addAll(records.getDescriptions());
        tagRecords.addAll(records.getTags());
        nameRecords.addAll(records.getNames());
        colorRecords.addAll(records.getColors());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("All paths: \n");
        for (String path: pathRecords) {
            sb.append(path + "\n");
        }
        sb.append("All descriptions: \n");
        for (String description: getDescriptions()) {
            sb.append(description + "\n");
        }
        sb.append("All tags: \n");
        for (String tag: tagRecords) {
            sb.append(tag + "\n");
        }
        sb.append("All names: \n");
        for (String name: nameRecords) {
            sb.append(name + "\n");
        }
        sb.append("All colors: \n");
        for (String color: colorRecords) {
            sb.append(color + "\n");
        }
        return sb.toString();
    }
}

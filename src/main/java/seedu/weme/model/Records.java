package seedu.weme.model;

import java.awt.Color;
import java.util.Set;

import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.meme.Description;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.Name;

/**
 * Interface for storing all past records of command arguments.
 */
public interface Records {

    public Set<String> getPaths();

    public Set<String> getDescriptions();

    public Set<String> getTags();

    public Set<String> getNames();

    public Set<String> getColors();

    public void addPath(ImagePath path);

    public void addDescription(Description description);

    public void addTags(Set<Tag> tags);

    public void addName(Name name);

    public void addColor(Color color);

    public void resetRecords(Records records);

}

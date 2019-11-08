package seedu.weme.model.records;

import java.util.Set;

import seedu.weme.model.meme.Description;
import seedu.weme.model.path.ImagePath;
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

    public Set<String> getTexts();

    public void addPath(ImagePath path);

    public void addDescription(Description description);

    public void addTags(Set<Tag> tags);

    public void addName(Name name);

    public void addText(String text);

    public void resetRecords(Records records);

}

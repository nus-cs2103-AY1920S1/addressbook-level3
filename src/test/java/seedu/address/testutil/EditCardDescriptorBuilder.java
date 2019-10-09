package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCardDescriptor objects.
 */
public class EditCardDescriptorBuilder {

    private EditCommand.EditCardDescriptor descriptor;

    public EditCardDescriptorBuilder() {
        descriptor = new EditCommand.EditCardDescriptor();
    }

    public EditCardDescriptorBuilder(EditCommand.EditCardDescriptor descriptor) {
        this.descriptor = new EditCommand.EditCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCommand.EditCardDescriptor} with fields containing {@code card}'s details
     */
    public EditCardDescriptorBuilder(Card card) {
        descriptor = new EditCommand.EditCardDescriptor();
        descriptor.setWord(card.getWord());
        descriptor.setMeaning(card.getMeaning());
        descriptor.setTags(card.getTags());
    }

    /**
     * Sets the {@code Word} of the {@code EditCommand.EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withWord(String word) {
        descriptor.setWord(new Word(word));
        return this;
    }

    /**
     * Sets the {@code Meaning} of the {@code EditCommand.EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withMeaning(String meaning) {
        descriptor.setMeaning(new Meaning(meaning));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCommand.EditCardDescriptor}
     * that we are building.
     */
    public EditCardDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditCardDescriptor build() {
        return descriptor;
    }
}

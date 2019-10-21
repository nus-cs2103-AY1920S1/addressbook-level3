package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;


/**
 * Adds tag(s) to item identified using its displayed index from the expiry date tracker.
 */
public class TagCommand extends Command {

    /**
     * Private enum to indicate whether command shows all tags or tags and item
     */
    enum TagMode { SHOW, TAG }

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the item identified by the index number used in the displayed item list.\n"
            + "Format: <index>|<tag>[<other tags>]...\n"
            + "(index must be a positive integer; each tag must be prefixed with a '#')\n"
            + "Example: " + COMMAND_WORD + "|1|#Food #Fruit";

    public static final String MESSAGE_TAG_ITEM_SUCCESS = "Tagged item: %1$s";
    public static final String MESSAGE_TAG_SHOW_SUCCESS = "All item tags:";
    public static final String MESSAGE_TAG_SHOW_FAILURE = "There are no tags.";

    private final Index index;
    private final TagItemDescriptor tagItemDescriptor;
    private final TagMode mode;



    public TagCommand(Index index, TagItemDescriptor tagItemDescriptor) {
        this.index = index;
        this.tagItemDescriptor = new TagItemDescriptor(tagItemDescriptor);
        this.mode = TagMode.TAG;
    }

    public TagCommand(Index index, String[] str) {
        this.index = index;
        this.tagItemDescriptor = new TagItemDescriptor();
        this.tagItemDescriptor.setTags(Arrays.stream(str).map(Tag::new).collect(Collectors.toSet()));
        this.mode = TagMode.TAG;
    }

    public TagCommand() {
        this.index = null;
        this.tagItemDescriptor = null;
        this.mode = TagMode.SHOW;
    }

    public TagMode getMode() {
        return this.mode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        switch (this.mode) {
        case TAG:
            if (this.index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
            }
            Item itemToTag = lastShownList.get(this.index.getZeroBased());
            Item taggedItem = createTaggedItem(itemToTag, this.tagItemDescriptor);
            model.setItem(itemToTag, taggedItem);
            return new CommandResult(String.format(MESSAGE_TAG_ITEM_SUCCESS, taggedItem));

        case SHOW:
            Set<Tag> tagSet = new TreeSet<>(new TagComparator());
            List<Item> itemList = model.getAllItemList();
            itemList.forEach(item -> tagSet.addAll(item.getTags()));
            if (tagSet.isEmpty()) {
                return new CommandResult(MESSAGE_TAG_SHOW_FAILURE);
            }
            List<String> tagNameList = tagSet.stream().map(Tag::toString).collect(Collectors.toList());
            StringBuilder str = appendTagsToFeedback(tagNameList, new StringBuilder(MESSAGE_TAG_SHOW_SUCCESS));
            return new CommandResult(str.toString());

        default:
        }
        throw new CommandException(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Appends tags to user feedback to show all tags.
     *
     * @param tagNameList List of tag names.
     * @param str StringBuilder to append to.
     */
    public static StringBuilder appendTagsToFeedback(List<String> tagNameList, StringBuilder str) {
        for (String tagName: tagNameList) {
            str.append("\n").append(tagName);
        }
        return str;
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToTag}
     * edited with {@code tagItemDescriptor}.
     */
    private static Item createTaggedItem(Item itemToTag, TagItemDescriptor tagItemDescriptor) {
        assert itemToTag != null;
        Set<Tag> updatedTags = updateTags(itemToTag, tagItemDescriptor);
        return new Item(itemToTag.getName(), itemToTag.getExpiryDate(), itemToTag.getQuantity(),
                updatedTags, itemToTag.getReminderThreshold());
    }

    /**
     * Returns an updated set of tags.
     *
     * @param itemToTag Item to be tagged.
     * @param tagItemDescriptor Descriptor that specifies additional tags to be added on or tags to be cleared.
     * @return Set containing updated tags.
     */
    private static Set<Tag> updateTags(Item itemToTag, TagItemDescriptor tagItemDescriptor) {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.addAll(itemToTag.getTags());
        set.addAll(tagItemDescriptor.getTags());
        return set;
    }

    @Override
    public int hashCode() {
        return this.tagItemDescriptor.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        // state check
        TagCommand e = (TagCommand) other;
        if (mode.equals(TagMode.SHOW)) {
            return mode.equals(e.mode);
        }
        return index.equals(e.index)
                && tagItemDescriptor.equals(e.tagItemDescriptor)
                && mode.equals(e.mode);
    }

    /**
     * Stores the tags to edit the item with.
     */
    public static class TagItemDescriptor {
        private Set<Tag> tags;

        public TagItemDescriptor() {}

        public TagItemDescriptor(TagItemDescriptor toCopy) {
            setTags(toCopy.tags);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            if (tags != null) {
                TreeSet<Tag> set = new TreeSet<>(new TagComparator());
                set.addAll(tags);
                this.tags = set;
            } else {
                this.tags = null;
            }
        }

        public Set<Tag>getTags() {
            return (this.tags != null) ? Collections.unmodifiableSet(this.tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof TagItemDescriptor)) {
                return false;
            }

            // state check
            TagItemDescriptor e = (TagItemDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }



}

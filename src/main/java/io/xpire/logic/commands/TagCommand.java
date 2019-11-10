package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.commons.util.CollectionUtil;
import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import io.xpire.model.tag.TagItemDescriptor;
import javafx.collections.ObservableList;

//@@author Kalsyc
/**
 * Adds tag(s) to xpireItem identified using its displayed index from the expiry date tracker.
 */
public class TagCommand extends Command {

    /**
     * Private enum to indicate whether command shows all tags or tags and xpireItem
     */
    enum TagMode { SHOW, TAG }

    public static final String COMMAND_WORD = "tag";
    public static final String COMMAND_SHORTHAND = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD

            + ": Tags the xpireItem identified by the index number used in the displayed item list.\n"
            + "Note that only 5 tags are allowed per item. \n"
            + "Format: tag|<index>|<tag>[<other tags>]...\n"
            + "(index must be a positive integer; each tag must be prefixed with a '#')\n"
            + "Example: " + COMMAND_WORD + "|1|#Food #Fruit";

    public static final String MESSAGE_TAG_ITEM_SUCCESS = "Tagged item: %1$s";
    public static final String MESSAGE_TAG_SHOW_SUCCESS = "All item tags:";
    public static final String MESSAGE_TAG_SHOW_FAILURE = "There are no tags.";
    public static final String MESSAGE_TOO_MANY_TAGS = "Only 5 tags are allowed per item.";
    public static final String MESSAGE_TAG_ITEM_SUCCESS_TRUNCATION_WARNING = "Warning! "
            + "Some tag(s) have been truncated. Maximum tag length accepted is 20.\nTagged item: %1$s";

    private final Index index;
    private final TagItemDescriptor tagItemDescriptor;
    private final TagMode mode;
    private boolean containsLongTags = false;
    private Item item = null;
    private String result = "";

    private final ListType listType;

    public TagCommand(ListType listType, Index index, TagItemDescriptor tagItemDescriptor) {
        this.index = index;
        this.tagItemDescriptor = new TagItemDescriptor(tagItemDescriptor);
        this.mode = TagMode.TAG;
        this.listType = listType;
    }

    public TagCommand(ListType listType, Index index, String[] str) {
        this.index = index;
        this.tagItemDescriptor = new TagItemDescriptor();
        this.tagItemDescriptor.setTags(Arrays.stream(str)
                .map(StringUtil::convertToSentenceCase)
                .map(Tag::new)
                .collect(Collectors.toSet()));
        this.mode = TagMode.TAG;
        this.listType = listType;
    }

    public TagCommand(ListType listType) {
        this.index = null;
        this.tagItemDescriptor = null;
        this.mode = TagMode.SHOW;
        this.listType = listType;
    }

    public TagMode getMode() {
        return this.mode;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        this.requireNonEmptyCurrentList(model);

        switch (this.mode) {
        case TAG:
            return executeAddTags(model, stateManager);
        case SHOW:
            return executeShowTags(model);
        default:
        }
        throw new CommandException(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes the show tag command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    private CommandResult executeShowTags(Model model) {
        Set<Tag> allTags = model.getCurrentList()
                                .stream()
                                .flatMap(item -> item.getTags().stream())
                                .collect(Collectors.toSet());

        if (allTags.isEmpty()) {
            return new CommandResult(MESSAGE_TAG_SHOW_FAILURE);
        }
        Collection<String> tagNameList = CollectionUtil.stringifyCollection(allTags);
        StringBuilder str = appendTagsToFeedback(tagNameList, new StringBuilder(MESSAGE_TAG_SHOW_SUCCESS));
        return new CommandResult(str.toString());
    }

    /**
     * Executes the add tag(s) command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param stateManager {@code StackManager} which manages the state of each command.
     * @return feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    private CommandResult executeAddTags(Model model, StateManager stateManager) throws CommandException {
        ObservableList<? extends Item> currentList = model.getCurrentList();
        if (this.index.getZeroBased() >= currentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Item itemToTag = currentList.get(this.index.getZeroBased());
        Item taggedItem;
        this.item = itemToTag;
        if (itemToTag instanceof XpireItem) {
            taggedItem = createTaggedXpireItem((XpireItem) itemToTag, this.tagItemDescriptor);
        } else {
            taggedItem = createTaggedReplenishItem(itemToTag, this.tagItemDescriptor);
        }

        stateManager.saveState(new ModifiedState(model));
        model.setItem(this.listType, itemToTag, taggedItem);

        return getTagCommandResult(taggedItem);
    }

    /**
     * Gets the appropriate CommandResult based on whether any tags in the item have been truncated.
     *
     * @param taggedItem Item that has been tagged.
     * @return CommandResult
     */
    private CommandResult getTagCommandResult(Item taggedItem) {
        if (this.tagItemDescriptor.getTags().stream().anyMatch(Tag::isTruncated)) {
            this.result = String.format(MESSAGE_TAG_ITEM_SUCCESS_TRUNCATION_WARNING, taggedItem);
            setShowInHistory(true);
            return new CommandResult(this.result);
        } else {
            this.result = String.format(MESSAGE_TAG_ITEM_SUCCESS, taggedItem);
            setShowInHistory(true);
            return new CommandResult(this.result);
        }
    }

    /**
     * Appends tags to user feedback to show all tags.
     *
     * @param tagNameList Collection of tag names.
     * @param str StringBuilder to append to.
     */
    public static StringBuilder appendTagsToFeedback(Collection<String> tagNameList, StringBuilder str) {
        for (String tagName: tagNameList) {
            str.append("\n").append(tagName);
        }
        return str;
    }

    /**
     * Creates and returns a {@code XpireItem} with the details of {@code xpireItemToTag}
     * edited with {@code tagItemDescriptor}.
     */
    private static XpireItem createTaggedXpireItem(XpireItem xpireItemToTag, TagItemDescriptor tagItemDescriptor)
            throws CommandException {
        assert xpireItemToTag != null;
        Set<Tag> updatedTags = updateTags(xpireItemToTag, tagItemDescriptor);
        if (updatedTags.size() >= 6) {
            throw new CommandException(MESSAGE_TOO_MANY_TAGS);
        }
        return new XpireItem(xpireItemToTag.getName(), xpireItemToTag.getExpiryDate(), xpireItemToTag.getQuantity(),
                updatedTags, xpireItemToTag.getReminderThreshold());
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code replenishItemToTag}
     * edited with {@code tagItemDescriptor}.
     */
    private static Item createTaggedReplenishItem(Item replenishItemToTag, TagItemDescriptor tagItemDescriptor) {
        assert replenishItemToTag != null;
        Set<Tag> updatedTags = updateTags(replenishItemToTag, tagItemDescriptor);
        return new Item(replenishItemToTag.getName(), updatedTags);
    }

    /**
     * Returns an updated set of tags.
     *
     * @param xpireItemToTag XpireItem to be tagged.
     * @param tagItemDescriptor Descriptor that specifies additional tags to be added on or tags to be cleared.
     * @return Set containing updated tags.
     */
    private static Set<Tag> updateTags(Item xpireItemToTag, TagItemDescriptor tagItemDescriptor) {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.addAll(xpireItemToTag.getTags());
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

    @Override
    public String toString() {
        return "Tag command";
    }

}

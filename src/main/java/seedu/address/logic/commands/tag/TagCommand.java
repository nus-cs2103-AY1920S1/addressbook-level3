package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Represents a Tag Command.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags the student identified "
            + "by the index number used in the displayed student list, to show you which subjects student"
            + " is weak at\n"
            + "Parameters: " + PREFIX_INDEX + "[INDEX]"
            + PREFIX_TAG + "[TAG_NAME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_TAG + "Chemistry"
            + "NOTE: Index has to be > 0 and within the max index number of the current student list";

    public static final String MESSAGE_TAG_STUDENT_SUCCESS = "Updated student: %1$s";
    public static final String MESSAGE_TAG_EXISTED = "\nTag(s) %1$s already exist and will be ignored.";
    public static final String MESSAGE_NOT_TAGGED = "At least one tag must be provided.";
    public static final String MESSAGE_NO_NEW_TAGS = "Specified tag(s) already exist, the student was not updated.";

    private final Index index;
    private final Set<Tag> tagSet;
    private final StringBuilder existedTags;
    private Index actualIndex;
    private Student studentToTag;
    private Student updatedStudent;

    /**
     * Creates a TagCommand to tag the specified {@code Student}
     *
     * @param index  of the student in the filtered student list to update
     * @param tagSet of new tags to be added to the current set of tags, without duplicates.
     */
    public TagCommand(Index index, Set<Tag> tagSet) {
        requireNonNull(index);
        requireNonNull(tagSet);

        this.index = index;
        this.tagSet = tagSet;
        existedTags = new StringBuilder();
        this.actualIndex = null;
        this.studentToTag = null;
        this.updatedStudent = null;
    }

    /**
     * Executes the tag command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return command result if the command was executed succesfully
     * @throws CommandException if the input was in the wrong format/the tag already exists with the
     *                          student/the student to be tagged was given an invalid index.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToTag = lastShownList.get(index.getZeroBased());
        updatedStudent = createTaggedStudent(studentToTag, tagSet);
        if (studentToTag.getIsMarked()) {
            updatedStudent.setMarked();
        }
        String taggedStudentNotification = String.format(MESSAGE_TAG_STUDENT_SUCCESS, updatedStudent);
        String existedTagsNotification = existedTags.length() == 0
                ? ""
                : String.format(MESSAGE_TAG_EXISTED, existedTags.toString());

        actualIndex = model.getIndexFromStudentList(studentToTag).orElse(index);
        model.setStudentWithIndex(actualIndex, updatedStudent);

        return new CommandResult(generateSuccessMessage(taggedStudentNotification, existedTagsNotification));
    }

    /**
     * Generates a command execution success message.
     *
     * @param taggedStudentNotification notification of tagged student
     * @param existedTagsNotification   notification of existed tags
     */
    private String generateSuccessMessage(String taggedStudentNotification, String existedTagsNotification) {
        return taggedStudentNotification + existedTagsNotification;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && index.equals(((TagCommand) other).index)
                && tagSet.equals(((TagCommand) other).tagSet));
    }

    /**
     * Creates and returns a {@code Student}
     * with the new tags from {@code tagSet} appended without duplicates.
     */
    private Student createTaggedStudent(Student studentToTag,
                                        Set<Tag> tagSet) throws CommandException {
        assert studentToTag != null;

        Name name = studentToTag.getName();

        Set<Tag> updatedTags = new HashSet<Tag>(studentToTag.getTags());

        int noOfTagsIgnored = 0;

        for (Tag newTag : tagSet) {
            if (updatedTags.contains(newTag)) {
                existedTags.append("[" + newTag.tagName + "]");
                noOfTagsIgnored++;
            } else {
                updatedTags.add(newTag);
            }
        }

        if (tagSet.size() == noOfTagsIgnored) {
            throw new CommandException(MESSAGE_NO_NEW_TAGS);
        }

        return new Student(name, updatedTags);

    }
}

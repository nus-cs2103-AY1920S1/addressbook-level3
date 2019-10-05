package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.AlfredException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entitylist.IssueList;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Issue;

/**
 * An Immutable IssueList that is serializable to JSON format.
 */
@JsonRootName(value = "issuelist")
class JsonSerializableIssueList {

    public static final String MESSAGE_DUPLICATE_ENTITY = "Issue list contains duplicate issue(s).";

    private final List<JsonAdaptedIssue> issues = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableIssueList} with the given issues.
     */
    @JsonCreator
    public JsonSerializableIssueList(@JsonProperty("issues") List<JsonAdaptedIssue> issues) {
        this.issues.addAll(issues);
    }

    /**
     * Converts a given {@code IssueList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableIssueList}.
     */
    public JsonSerializableIssueList(IssueList source) {
        issues.addAll(source.list()
                            .stream()
                            .map((Entity i) -> new JsonAdaptedIssue((Issue) i))
                            .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code IssueList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public IssueList toModelType() throws IllegalValueException, AlfredException {
        IssueList issueList = new IssueList();
        for (JsonAdaptedIssue jsonAdaptedIssue : issues) {
            Issue issue = jsonAdaptedIssue.toModelType();
            //TODO: Check whether this checking of existing issues is necessary with the team
            //if (issueList.hasIssue(issue)) {
            //    throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            //}
            issueList.add(issue);
        }
        return issueList;
    }

}

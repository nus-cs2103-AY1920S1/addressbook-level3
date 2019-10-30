package seedu.jarvis.storage.history.commands.cca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.cca.AddProgressCommand;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.cca.JsonAdaptedCca;
import seedu.jarvis.storage.cca.JsonAdaptedCcaMilestone;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link AddProgressCommand}.
 */
public class JsonAdaptedAddProgressCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_ATTRIBUTES = "Invalid attributes.";

    private final JsonAdaptedIndex index;
    private final List<JsonAdaptedCcaMilestone> milestones = new ArrayList<>();
    private final JsonAdaptedCca cca;


    /**
     * Constructs a {@code JsonAdaptedAddProgressCommand}.
     *
     * @param index {@code Index}.
     * @param milestones {@code CcaMilestoneList}.
     * @param cca {@code Cca}.
     */
    @JsonCreator
    public JsonAdaptedAddProgressCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                         @JsonProperty("milestones") List<JsonAdaptedCcaMilestone> milestones,
                                         @JsonProperty("cca") JsonAdaptedCca cca) {
        this.index = index;
        this.milestones.addAll(milestones);
        this.cca = cca;
    }

    /**
     * Converts a given {@code AddProgressCommand} into this class for Jackson use.
     *
     * @param addProgressCommand {@code AddProgressCommand} to be used to construct the
     * {@code JsonAdaptedAddProgressCommand}.
     */
    public JsonAdaptedAddProgressCommand(AddProgressCommand addProgressCommand) {
        index = new JsonAdaptedIndex(addProgressCommand.getTargetIndex());
        cca = new JsonAdaptedCca(addProgressCommand.getTargetCca());
        this.milestones.addAll(addProgressCommand.getCcaMilestoneList()
                .asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedCcaMilestone::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted {@code AddProgressCommand} object into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code AddProgressCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code AddProgressCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (index == null || cca == null) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTRIBUTES);
        }
        CcaMilestoneList ccaMilestones = new CcaMilestoneList();
        for (JsonAdaptedCcaMilestone jsonAdaptedCcaMilestone : milestones) {
            ccaMilestones.add(jsonAdaptedCcaMilestone.toModelType());
        }
        return new AddProgressCommand(index.toModelType(), ccaMilestones, cca.toModelType());
    }
}

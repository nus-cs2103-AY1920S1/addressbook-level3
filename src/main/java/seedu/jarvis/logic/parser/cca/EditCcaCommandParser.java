package seedu.jarvis.logic.parser.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_TYPE;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_EQUIPMENT_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_PROGRESS_LEVEL;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_PROGRESS_LEVEL_NAMES;

import java.util.Collection;
import java.util.Optional;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.cca.EditCcaCommand;
import seedu.jarvis.logic.commands.cca.EditCcaCommand.EditCcaDescriptor;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.cca.EquipmentList;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.cca.exceptions.DuplicateCcaMilestoneException;
import seedu.jarvis.model.cca.exceptions.DuplicateEquipmentException;

/**
 * Parses input arguments and creates a new EditCcaCommand object
 */
public class EditCcaCommandParser implements Parser<EditCcaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCcaCommandParser
     * and returns an EditCcaCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCcaCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CCA_NAME, PREFIX_CCA_TYPE, PREFIX_EQUIPMENT_NAME,
                        PREFIX_PROGRESS_LEVEL, PREFIX_PROGRESS_LEVEL_NAMES);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCcaCommand.MESSAGE_USAGE), pe);
        }

        EditCcaDescriptor editCcaDescriptor = new EditCcaDescriptor();
        if (argMultimap.getValue(PREFIX_CCA_NAME).isPresent()) {
            editCcaDescriptor.setCcaName(CcaParserUtil.parseCcaName(argMultimap.getValue(PREFIX_CCA_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CCA_TYPE).isPresent()) {
            editCcaDescriptor.setCcaType(CcaParserUtil.parseCcaType(argMultimap.getValue(PREFIX_CCA_TYPE).get()));
        }

        try {
            parseEquipmentListForEdit(argMultimap.getAllValues(PREFIX_EQUIPMENT_NAME))
                    .ifPresent(editCcaDescriptor::setEquipmentList);
        } catch (DuplicateEquipmentException e) {
            throw new ParseException(EditCcaCommand.MESSAGE_DUPLICATE_EQUIPMENT);
        }

        try {
            parseMilestoneListForEdit(argMultimap.getAllValues(PREFIX_PROGRESS_LEVEL_NAMES))
                    .ifPresent(editCcaDescriptor::setMilestoneList);
        } catch (DuplicateCcaMilestoneException e) {
            throw new ParseException(EditCcaCommand.MESSAGE_DUPLICATE_CCA_MILESTONES);
        }

        if (argMultimap.getValue(PREFIX_PROGRESS_LEVEL).isPresent()) {
            editCcaDescriptor.setCcaCurrentProgress(CcaParserUtil.parseCcaCurrentProgress(argMultimap
                    .getValue(PREFIX_PROGRESS_LEVEL).get()));
        }

        if (!editCcaDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCcaCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCcaCommand(index, editCcaDescriptor);
    }

    /**
     * Parses {@code Collection<String> milestone} into a {@code CcaMilestoneList} if {@code milestones} is non-empty.
     * If {@code milestones} contain only one element which is an empty string, it will be parsed into a
     * {@code CcaMilestoneList} containing zero milestones.
     */
    private Optional<CcaMilestoneList> parseMilestoneListForEdit(Collection<String> milestones) throws ParseException,
            DuplicateCcaMilestoneException {
        assert milestones != null;

        if (milestones.isEmpty()) {
            return Optional.empty();
        }

        if (milestones.size() == 1 && milestones.contains("")) {
            return Optional.of(new CcaMilestoneList());
        }

        return Optional.of(CcaParserUtil.parseCcaMilestones(milestones));
    }


    /**
     * Parses {@code Collection<String> equipment} into a {@code EquipmentList} if {@code equipments} is non-empty.
     * If {@code equipments} contain only one element which is an empty string, it will be parsed into a
     * {@code EquipmentList} containing zero equipment.
     */
    private Optional<EquipmentList> parseEquipmentListForEdit(Collection<String> equipments) throws ParseException,
            DuplicateEquipmentException {
        assert equipments != null;

        if (equipments.isEmpty()) {
            return Optional.empty();
        }

        if (equipments.size() == 1 && equipments.contains("")) {
            return Optional.of(new EquipmentList());
        }

        return Optional.of(CcaParserUtil.parseEquipments(equipments));
    }

}

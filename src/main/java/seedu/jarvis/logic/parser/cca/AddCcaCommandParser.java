package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.cca.CcaTrackerCliSyntax.PREFIX_CCA_NAME;
import static seedu.jarvis.logic.parser.cca.CcaTrackerCliSyntax.PREFIX_CCA_TYPE;
import static seedu.jarvis.logic.parser.cca.CcaTrackerCliSyntax.PREFIX_EQUIPMENT_NAME;

import java.util.stream.Stream;

import seedu.jarvis.logic.commands.cca.AddCcaCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.EquipmentList;

/**--
 * Parses input arguments and creates a new {@code AddCcaCommand} object.
 */
public class AddCcaCommandParser implements Parser <AddCcaCommand> {

    @Override
    public AddCcaCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_CCA_NAME, PREFIX_CCA_TYPE, PREFIX_EQUIPMENT_NAME);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_CCA_NAME, PREFIX_CCA_TYPE, PREFIX_EQUIPMENT_NAME)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCcaCommand.MESSAGE_USAGE));
        }

        CcaName ccaName = ParserUtil.parseCcaName(argumentMultimap.getValue(PREFIX_CCA_NAME).get());
        CcaType ccaType = ParserUtil.parseCcaType(argumentMultimap.getValue(PREFIX_CCA_TYPE).get());
        EquipmentList equipmentList = ParserUtil.parseEquipments(argumentMultimap.getAllValues(PREFIX_EQUIPMENT_NAME));
        Cca cca = new Cca(ccaName, ccaType, equipmentList);

        return new AddCcaCommand(cca);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

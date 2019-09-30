package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListParticipantCommand extends ListCommand {

    /* Possible Fields? */
    public static final String MESSAGE_SUCCESS = "Listed all participants";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.getParticipantList().list().stream()
                                  .forEach(p -> {
                                      HashMap<String, String> fieldMap = p.viewMinimal();
                                      StringBuilder toPrint = new StringBuilder();
                                      for (String key : fieldMap.keySet()) {
                                          toPrint.append(StringUtil.capitalize(key))
                                                 .append(" : ")
                                                 .append(fieldMap.get(key))
                                                 .append(" ");
                                      }
                                      System.out.println(toPrint.toString().trim());
                                  });

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
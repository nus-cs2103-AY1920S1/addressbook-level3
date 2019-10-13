package calofit.logic.commands;

import calofit.model.Model;

public class SuggestCommand extends Command{

    public static final String COMMAND_WORD = "suggest";

    public static final String MESSAGE_SUCCESS = "These are the suggested meals";

    @Override
    public CommandResult execute(Model model) {
        System.out.println(model.suggestMeal());

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}
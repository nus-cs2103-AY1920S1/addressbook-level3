package com.dukeacademy.logic.commands.factory;

import com.dukeacademy.logic.commands.Command;

/**
 * This interface is used to inject dependencies into command class instances without breaking the open close
 * principle. When a user command is received, it will be matched to the factory which has the same command word and
 * the factory will be used to instantiate the command instance instead. The logic class would not have to know about
 * the dependencies for the command constructor.
 */
public interface CommandFactory {
    /**
     * Gives the command word that will be used to match to this factory.
     * @return the command word.
     */
    String getCommandWord();

    /**
     * Returns the corresponding command class instance.
     * @param commandArguments the command text from the user's input.
     * @return the corresponding command class instance.
     */
    Command getCommand(String commandArguments);
}

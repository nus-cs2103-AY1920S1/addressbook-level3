// @@author sreesubbash
package seedu.address.logic.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Uses java reflections to process a list of classes.
 */
@SuppressWarnings("unchecked")
public class ClassUtil {

    private List<ClassPair> classPairs;

    public ClassUtil () {
        this.classPairs = new ArrayList<>();
    }

    /**
     * Adds classPair into internal list.
     * @param classPair
     */
    public void add(ClassPair classPair) {
        classPairs.add(classPair);
    }

    /**
     * Gets List of attribute values from Command Classes in classUtil.
     * @param attr key of attribute
     * @return List of attribute values
     */
    public List<String> getAttribute(String attr) {
        List<String> result = new ArrayList<>();
        for (ClassPair clsPair : classPairs) {
            try {
                Class commandClass = clsPair.getCommand();
                Field field = commandClass.getField(attr);
                String value = (String) field.get(null);
                result.add(value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println(e);
            }
        }
        return result;
    }

    /**
     * Gets Command based on Parser and Command Classes in classUtil.
     * @param commandWord from user input
     * @param arguments from user input
     * @return Command after parsing
     * @throws ParseException
     */
    public Command getCommandInstance(String commandWord, String arguments)
            throws ParseException {
        for (ClassPair classPair : classPairs) {
            try {
                Class commandClass = classPair.getCommand();
                Field field = commandClass.getField("COMMAND_WORD");
                String value = (String) field.get(null);
                if (value.equals(commandWord)) {
                    Class parser = classPair.getParser();
                    if (parser == null) {
                        Constructor constructor = commandClass.getConstructor();
                        Command command = (Command) constructor.newInstance();
                        return command;
                    } else {
                        Constructor constructor = parser.getConstructor();
                        Parser commandParser = (Parser) constructor.newInstance();
                        return commandParser.parse(arguments);
                    }
                }
            } catch (NoSuchFieldException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | InvocationTargetException e) {
                System.err.println(e);
            }
        }
        return null;
    }

}

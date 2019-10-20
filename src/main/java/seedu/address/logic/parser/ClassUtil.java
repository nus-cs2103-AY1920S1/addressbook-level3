package seedu.address.logic.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.ArrayList;


import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ClassUtil {



    public static List<String> getAttribute(List<ClassPair> classes, String attr) {
        List<String> result = new ArrayList<>();
        for (ClassPair clsPair : classes) {
            try {
                Class cls = clsPair.getCommand();
                Field f = cls.getField(attr);
                String strValue = (String) f.get(null);
                result.add(strValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                result.add(null);
            }
        }
        return result;
    }

    public static Command getCommandInstance(List<ClassPair> classes, String commandWord, String arguments)
            throws ParseException {
        for (ClassPair clsPair : classes) {
            try {
                Class cls = clsPair.getCommand();
                Field f = cls.getField("COMMAND_WORD");
                String strValue = (String) f.get(null);

                if (strValue.equals(commandWord)) {
                    Class parser = clsPair.getParser();
                    if (parser == null) {
                        Constructor cons = cls.getConstructor();
                        Command test = (Command)cons.newInstance();
                        return test;
                    } else {
                        Constructor cons = parser.getConstructor();
                        Parser test = (Parser)cons.newInstance();
                        return test.parse(arguments);
                    }

                }
            } catch (NoSuchFieldException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

            }
        }
        return null;
    }

}

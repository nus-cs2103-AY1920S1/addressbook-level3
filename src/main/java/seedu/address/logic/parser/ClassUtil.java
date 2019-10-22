package seedu.address.logic.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ClassUtil {

    private Model model;
    private List<ClassPair> classPairs;
    private List<ClassPair> filteredList;

    public ClassUtil (Model model) {
        this.model = model;
        this.classPairs = new ArrayList<>();
        this.filteredList = new ArrayList<>();
    }

    public void add(ClassPair classPair) {
        classPairs.add(classPair);
    }

    private void filterList() {
        filteredList.clear();
        for (ClassPair clsPair : classPairs) {
            System.out.println("FILTERING list");
            try {
                Class cls = clsPair.getCommand();
                Constructor cons = cls.getConstructor();
                Command test = (Command) cons.newInstance();
                boolean temp = test.precondition(model);
                System.out.println(temp);
                if (temp) {
                    filteredList.add(clsPair);
                }
            } catch ( NoSuchMethodException | InstantiationException | IllegalAccessException
                    | InvocationTargetException e) {
                System.out.println("Erorrorror");
            }
        }
    }

    public List<String> getAttribute(String attr) {
        filterList();
        List<String> result = new ArrayList<>();
        for (ClassPair clsPair : filteredList) {
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

    public Command getCommandInstance(String commandWord, String arguments)
            throws ParseException {
        filterList();
        for (ClassPair clsPair : filteredList) {
            try {
                Class cls = clsPair.getCommand();
                Field f = cls.getField("COMMAND_WORD");
                String strValue = (String) f.get(null);

                if (strValue.equals(commandWord)) {
                    Class parser = clsPair.getParser();
                    if (parser == null) {
                        Constructor cons = cls.getConstructor();
                        Command test = (Command) cons.newInstance();
                        return test;
                    } else {
                        Constructor cons = parser.getConstructor();
                        Parser test = (Parser) cons.newInstance();
                        return test.parse(arguments);
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

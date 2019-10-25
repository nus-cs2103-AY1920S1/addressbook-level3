package seedu.address.model.quiz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simulates a class which handles the exporting of quizzes.
 */
public class QuizExporter {

    public static boolean exportQuiz(String quizId, String formattedQuiz) throws IOException {
        String fileName = quizId + ".html";
        String filePath = fileName;
        System.out.println(filePath);

        File file = new File(filePath);
        if(file.exists()) {
            return false;
        } else {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(formattedQuiz);
            fileWriter.close();
            return true;
        }
    }

    private static String txtToHtml(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
            case '<':
                builder.append("&lt;");
                break;
            case '>':
                builder.append("&gt;");
                break;
            case '&':
                builder.append("&amp;");
                break;
            case '"':
                builder.append("&quot;");
                break;
            case '\n':
                builder.append("<br>");
                break;
            // We need Tab support here, because we print StackTraces as HTML
            case '\t':
                builder.append("&nbsp; &nbsp; &nbsp;");
                break;
            default:
                builder.append(c);

            }
        }
        String converted = builder.toString();
        String str = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?«»“”‘’]))";
        Pattern patt = Pattern.compile(str);
        Matcher matcher = patt.matcher(converted);
        converted = matcher.replaceAll("<a href=\"$1\">$1</a>");
        return converted;
    }
}

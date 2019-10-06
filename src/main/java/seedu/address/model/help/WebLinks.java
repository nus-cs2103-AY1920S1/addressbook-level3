package seedu.address.model.help;

public class WebLinks {

    public WebLinks() {
    }

    public static String getLink (SecondaryCommand secondaryCommand){
        switch (secondaryCommand.toString()){

        case "help":
            return "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html#viewing-help-code-help-code";

        case "exit":
            return "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html#exiting-the-program-code-exit-code";

        default: return "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html";
        }
    }
}

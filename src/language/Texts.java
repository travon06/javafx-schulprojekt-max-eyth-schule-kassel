package language;

import java.util.ArrayList;

public class Texts {
    private final static ArrayList<Text> texts = new ArrayList<>();

    public static ArrayList<Text> getTexts() {
        return texts;
    }

    public static Text getTextByName(String name) {
        for(Text text : Texts.getTexts()) {
            if(text.getName().equals(name)) {
                return text;
            }
        }

        throw new Error(String.format("Text %s does not exist!", name));
    }
}

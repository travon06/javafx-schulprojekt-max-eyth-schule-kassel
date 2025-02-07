package language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextReader {
    public static void readTexts() {
        Path path = Paths.get("src/language/texts.txt");
        Path absolutePath = path.toAbsolutePath();

        try {
            String data = Files.readString(absolutePath);
            data = data.replaceAll("\\s", "");

            String[] texts = data.split("\\}");

            for(String text : texts) {
                String name = text.split("\\{")[0];
                String languages = text.split("\\{")[1];
                String en = "";
                String de = "";
                String ne = "";
                String schw = "";

                for(String language : languages.split("\\)")) {
                    if(language.startsWith("EN")) {
                        en = language.split("\\(")[1];
                    }

                    if(language.startsWith("DE")) {
                        de = language.split("\\(")[1];
                    }

                    if(language.startsWith("NE")) {
                        ne = language.split("\\(")[1];
                    }
                    if(language.startsWith("SCHW")) {
                        schw = language.split("\\(")[1];
                    }
                }

                Texts.getTexts().add(new Text(name, formatText(en), formatText(de), formatText(ne), formatText(schw)));

            }        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatText(String text) {
        return text.replace("_", " ");
    }
}

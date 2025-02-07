package language;

import utils.config.ConfigArguments;

public class Text {
    private final String name;
    private final String en;
    private final String de;
    private final String ne;
    private final String schw;

    public Text(String name, String en, String de, String ne, String schw) {
        this.name = name;
        this.en = en;
        this.de = de;
        this.ne = ne;
        this.schw = schw;
    }

    public String getTextInLanguage() {
        
        switch (ConfigArguments.getConfigArgumentValue("LANGUAGE")) {
            case "EN":
                return this.en;
            case "DE":
                return this.de;
            case "NE":
                return this.ne;
            case "SCHW":
                return this.schw;
            default:
                return null;
        }
    }

    //#region getter & setter 

    public String getDe() {
        return de;
    }

    public String getEn() {
        return en;
    }

    public String getName() {
        return name;
    }

    public String getNe() {
        return ne;
    }

    public String getSchw() {
        return schw;
    }
    //#endregion
}

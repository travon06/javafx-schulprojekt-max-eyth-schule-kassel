package language;

public class Text {
    private final String name;
    private final String en;
    private final String de;
    private final String ne;

    public Text(String name, String en, String de, String ne) {
        this.name = name;
        this.en = en;
        this.de = de;
        this.ne = ne;
    }

    public String getTextInLanguage(String language) {
        switch (language) {
            case "EN":
                return this.en;
            case "DE":
                return this.de;
            case "NE":
                return this.ne;
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

    //#endregion
}

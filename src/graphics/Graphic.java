package graphics;

public class Graphic {
    private final String name;
    private final String url;

    public Graphic(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
            return url;
    }
}
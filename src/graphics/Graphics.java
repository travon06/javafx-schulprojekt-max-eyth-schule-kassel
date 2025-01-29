package graphics;

import java.util.ArrayList;

public class Graphics {
    private static ArrayList<Graphic> graphics = new ArrayList<>();

    public static String getGraphicUrl(String name) {
        for(Graphic graphic : Graphics.getGraphics()) {
            if(graphic.getName().equals(name)) {
                return graphic.getUrl();
            }
        }
        throw new Error(String.format("Graphic '%s' does not exist!", name)); 
    }

    public static void setGraphics(ArrayList<Graphic> graphics) {
        Graphics.graphics = graphics;
    }

    public static ArrayList<Graphic> getGraphics() {
        return graphics;
    }    
}

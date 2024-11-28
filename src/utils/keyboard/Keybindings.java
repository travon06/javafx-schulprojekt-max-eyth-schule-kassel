package utils.keyboard;

import java.util.ArrayList;

public class Keybindings {
    private final static ArrayList<Keybinding> keybindings = new ArrayList<>();

    public static ArrayList<Keybinding> getKeybindings() {
        return Keybindings.keybindings;
    }

    public static String getKeybindingValue(String argument) {
        for(Keybinding keybinding : Keybindings.getKeybindings()) {
            if(keybinding.getArgument().equals(argument)) {
                return keybinding.getValue();
            }
        }

        throw new Error(String.format("The Argument: %s does not exist!", argument));
    }
}

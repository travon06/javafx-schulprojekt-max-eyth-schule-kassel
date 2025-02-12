package utils.keyboard;

public class Keybinding {
    private final String argument;
    private String value;

    public Keybinding(String argument, String value) {
        this.argument = argument;
        this.value = value;
    }

    public String getArgument() {
        return this.argument;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

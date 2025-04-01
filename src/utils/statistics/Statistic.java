package utils.statistics;

public class Statistic {
    private final String argument;
    private String value;

    public Statistic(String argument, String value) {
        this.argument = argument;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("argument=%s; value=%s", this.argument, this.value);
    }

    //#region getter & setter
    public String getArgument() {
        return argument;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    //#endregion
}

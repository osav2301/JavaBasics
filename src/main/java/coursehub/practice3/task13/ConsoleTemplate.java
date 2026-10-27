package coursehub.practice3.task13;

public enum ConsoleTemplate {
    STANDART(""),
    URGENT("URGENT -> ");

    private final String prefix;

    ConsoleTemplate(String prefix)
    {
        this.prefix = prefix;
    }

    public String decorate(String text)
    {
        return prefix + text;
    }
}

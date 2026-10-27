package coursehub.practice3.task13;

public enum EmailTemplate {
    STANDART(""),
    SPAM("SPAM -> ");

    private final String prefix;

    EmailTemplate(String prefix)
    {
        this.prefix = prefix;
    }

    public String decorate(String text)
    {
        return prefix + text;
    }
}

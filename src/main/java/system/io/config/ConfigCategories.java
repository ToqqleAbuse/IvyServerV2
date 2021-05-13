package system.io.config;

public enum ConfigCategories {

    DATABASE("Database"),
    MAIL("Mail"),
    GITHUB("github"),
    URLS("urls"),
    IOSYSTEM("iosystem"),
    SOCKET("socket");

    private final String name;

    ConfigCategories(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

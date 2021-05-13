package system.io.config;

import system.tools.skills.OSManager;

import java.util.Arrays;
import java.util.HashMap;

public enum ConfigEntries {

    DB_HOST(ConfigCategories.DATABASE, "db_host", "abc"),
    DB_PORT(ConfigCategories.DATABASE, "db_port", "3306"),
    DB_USERNAME(ConfigCategories.DATABASE, "db_username", "root"),
    DB_PASSWORD(ConfigCategories.DATABASE, "db_password", ""),
    DB_DATABASE(ConfigCategories.DATABASE, "db_database", "test"),

    CLIENT_SOCKET_PORT(ConfigCategories.SOCKET, "client_socket_port", "12319"),
    SYSTEM_SOCKET_PORT(ConfigCategories.SOCKET, "system_socket_port", "12310"),
    DISCORD_NEWSLETTER_PORT(ConfigCategories.SOCKET, "discord_newsletter_port", "12300"),

    GITHUB_ACCESS_TOKEN(ConfigCategories.GITHUB, "github_access_token", "6d747f610dad6eb79d64a2ebdeb003ab87cc9e95"),
    GITHUB_ORGANISATION_NAME(ConfigCategories.GITHUB, "github_organisation_name", "Ivy-Systems"),

    TWO_FA_URL(ConfigCategories.URLS, "two_fa_url", "https://api.keinweb.com/checkTwoFA/"),
    WEBSITE(ConfigCategories.URLS, "website", "https://www.ivy-systems.de"),

    MAIL_PORT(ConfigCategories.MAIL, "mail_port", "25"),
    MAIL_HOST(ConfigCategories.MAIL, "mail_host", "ivy-systems.de"),
    MAIL_STARTTLS(ConfigCategories.MAIL, "mail_starttls", "true"),
    MAIL_AUTH(ConfigCategories.MAIL, "mail_auth", "true"),
    MAIL_ADDRESS(ConfigCategories.MAIL, "mail_address", "ivy-systems.de"),
    MAIL_PASSWORD(ConfigCategories.MAIL, "mail_password", "KugZu3hGEPHzxYgt");

    private String keyName;
    private String defaultValue;
    private ConfigCategories category;

    private static HashMap<String, ConfigEntries> configEntries = new HashMap<>();

    static {
        Arrays.stream(ConfigEntries.values()).forEach(value -> configEntries.put(value.getKeyName(), value));
    }

    ConfigEntries(final ConfigCategories category, final String keyName, final String defaultValue) {
        this.category = category;
        this.keyName = keyName;
        this.defaultValue = defaultValue;
    }

    public static ConfigEntries getConfigByName(final String name) {
        return configEntries.get(name);
    }

    public String getKeyName() {
        return this.keyName;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public ConfigCategories getCategory() {
        return this.category;
    }

    public void setDefaultValue(String newValue) {
        this.defaultValue = newValue;
    }

}

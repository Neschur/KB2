package com.neschur.kb2.app;

public interface I18n {
    public String translate(String key);

    public String translate(int key);

    public String translate(String key, String... replaces);

    public String translate(String key, int... replaces);
}

package model;

public enum MODE {

    GAME("view/resources/modeChoser/modebot.png"),

    MAP_CREATOR("view/resources/modeChoser/modedev.png");

    private String urlMode;

    MODE(String urlMode) {
        this.urlMode = urlMode;
    }

    public String getUrlMode() {
        return this.urlMode;
    }
}

package model;

public enum MODE {

    NORMAL("view/resources/modeChoser/modebot.png"),
    DEVIANT("view/resources/modeChoser/modedeviant.png"),
    MAP_CREATOR("view/resources/modeChoser/modedev.png");

    private String urlMode;

    MODE(String urlMode) {
        this.urlMode = urlMode;
    }

    public String getUrlMode() {
        return this.urlMode;
    }
}

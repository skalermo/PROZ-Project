package model;

public enum MODE {

    GAME("view/resources/modeChoser/gameMod.png"),

    MAP_CREATOR("view/resources/modeChoser/editorMod.png");

    private String urlMode;

    MODE(String urlMode) {
        this.urlMode = urlMode;
    }

    public String getUrlMode() {
        return this.urlMode;
    }
}

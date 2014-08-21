package ua.samosfator.gmm.mapcamp.lviv;

public enum EditStatus {
    PUBLISHED("Опубліковано"),
    PENDING("Очікує"),
    DELETED("Видалено"),
    UNDEFINED("Не визначено"),
    NO_LINK("Немає посилання"),
    LINK_MALFORMED("Неправильне посилання");

    final static String PENDING_ICON = "/mapmaker/mapfiles/marker_orangeA-k.png";
    final static String PUBLISHED_ICON = "/mapmaker/mapfiles/markerA-k.png";
    final static String PLACE_DELETED = "/mapmaker/mapfiles/marker_greyA-k.png";

    private String name;

    EditStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

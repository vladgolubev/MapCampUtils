package ua.samosfator.gmm.mapcamp.lviv;

public enum EditStatus {
    PUBLISHED("Опубліковано"),
    PENDING("Очікує"),
    UNDEFINED("Не визначено");

    private String name;

    EditStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

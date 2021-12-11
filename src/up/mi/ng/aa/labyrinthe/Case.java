package up.mi.ng.aa.labyrinthe;

public enum Case {
    LIBRE('.', 1.f, "green"),
    MUR('#', Float.POSITIVE_INFINITY, "black"),
    FEU('F', Float.POSITIVE_INFINITY, "red"),
    DEBUT('D', 1.f, "blue"),
    SORTIE('S', 1.f, "yellow"),
    FLAMMESPROPAGEES('A', Float.POSITIVE_INFINITY, "red"),
    DEJAPARCOURU('L', Float.POSITIVE_INFINITY, "pink");

    private final char value;
    private final float time;
    private final String color;

    Case(char value, float time, String color) {
        this.value = value;
        this.time = time;
        this.color = color;
    }

    public float getTime() {
        return this.time;
    }

    public String getColor() {
        return color;
    }

    public static Case getValueFromChar(char ch) {
        return switch (ch) {
            case '.' -> LIBRE;
            case '#' -> MUR;
            case 'F' -> FEU;
            case 'D' -> DEBUT;
            case 'S' -> SORTIE;
            case 'A' -> FLAMMESPROPAGEES;
            case 'L' -> DEJAPARCOURU;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}

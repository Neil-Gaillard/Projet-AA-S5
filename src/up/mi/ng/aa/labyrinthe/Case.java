package up.mi.ng.aa.labyrinthe;

public enum Case {
    LIBRE('.'),
    MUR('#'),
    FEU('F'),
    DEBUT('D'),
    SORTIE('S'),
    FLAMMESPROPAGEES('A'),
    DEJAPARCOURU('L');

    private final char value;

    Case(char value) {
        this.value = value;
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

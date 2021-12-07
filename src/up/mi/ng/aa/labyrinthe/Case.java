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

    public char getValue() {
        return value;
    }

    public static Case getValueFromChar(char ch) {
        switch (ch) {
            case '.':
                return LIBRE;
            case '#':
                return MUR;
            case 'F':
                return FEU;
            case 'D':
                return DEBUT;
            case 'S':
                return SORTIE;
            case 'A':
                return FLAMMESPROPAGEES;
            case 'L':
                return DEJAPARCOURU;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }
}

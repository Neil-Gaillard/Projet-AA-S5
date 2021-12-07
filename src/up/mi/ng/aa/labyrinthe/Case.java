package up.mi.ng.aa.labyrinthe;

public enum Case {
    LIBRE('.'),
    MUR('#'),
    FEU('F'),
    DEBUT('D'),
    SORTIE('F');

    private final char value;

    private Case(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}

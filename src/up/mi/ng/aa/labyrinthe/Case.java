package up.mi.ng.aa.labyrinthe;

/**
 * Enumeration représentant la donnée stockée dans les cases du labyrinthe du projet.
 *
 * @author Neil GAILLARD
 * @version 1.0
 */
public enum Case {
    LIBRE('.', 1.f, "green"),
    MUR('#', Float.POSITIVE_INFINITY, "gray"),
    FEU('F', Float.POSITIVE_INFINITY, "red"),
    DEBUT('D', 1.f, "blue"),
    SORTIE('S', 1.f, "yellow"),
    FLAMMESPROPAGEES('A', Float.POSITIVE_INFINITY, "orange"),
    DEJAPARCOURU('L', Float.POSITIVE_INFINITY, "pink");

    private final char value;
    private final float time;
    private final String color;

    Case(char value, float time, String color) {
        this.value = value;
        this.time = time;
        this.color = color;
    }

    /**
     * Permet d'obtenir la valeur de l'énumération à partir d'un char pour la définition de données à partir de
     * fichiers textes
     *
     * @param ch le char à lire
     * @return la valeur de l'énumération correspondante à ce char
     */
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

    /**
     * Permet d'obtenir le temps nécessaire pour franchir une case
     *
     * @return le temps nécessaire pour franchir une case
     */
    public float getTime() {
        return this.time;
    }

    /**
     * Permet d'obtenir la couleur qui sera utilisée pour afficher la case
     *
     * @return la couleur définie pour afficher la case
     */
    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}

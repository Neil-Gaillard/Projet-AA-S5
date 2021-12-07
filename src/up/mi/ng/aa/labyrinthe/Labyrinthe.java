package up.mi.ng.aa.labyrinthe;

public class Labyrinthe {
    private final int sizeX;
    private final int sizeY;

    private Case[][] map;

    public Labyrinthe(int x, int y) {
        this.sizeX = x;
        this.sizeY = y;

        this.map = new Case[sizeX][sizeY];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Case getMapValue(int x, int y) {
        return this.map[x][y];
    }
}

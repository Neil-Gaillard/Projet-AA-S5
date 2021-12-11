package up.mi.ng.aa.labyrinthe;

public class Labyrinthe {

    private final int sizeX;
    private final int sizeY;

    private final Case[][] map;

    public Labyrinthe(int x, int y) {
        this.sizeX = x;
        this.sizeY = y;

        this.map = new Case[sizeY][sizeX];
    }

    /**
     * Brule les cases adjacentes à la case initiale
     *
     * @param x la position x de la case initiale
     * @param y la position y de la case initiale
     * @return vrai si il y a un gameover lors de la propagation du feu, faux sinon
     */
    private boolean burnAround(int x, int y) {
        if (y != 0) {
            if (this.map[y - 1][x] == Case.LIBRE)
                this.map[y - 1][x] = Case.FLAMMESPROPAGEES;
            else if (this.map[y - 1][x] == Case.DEBUT || this.map[y - 1][x] == Case.SORTIE)
                return true;
        }
        if (x != 0) {
            if (this.map[y][x - 1] == Case.LIBRE)
                this.map[y][x - 1] = Case.FLAMMESPROPAGEES;
            else if (this.map[y][x - 1] == Case.DEBUT || this.map[y][x - 1] == Case.SORTIE)
                return true;
        }
        if (y != this.sizeY - 1) {
            if (this.map[y + 1][x] == Case.LIBRE)
                this.map[y + 1][x] = Case.FLAMMESPROPAGEES;
            else if (this.map[y + 1][x] == Case.DEBUT || this.map[y + 1][x] == Case.SORTIE)
                return true;
        }
        if (x != this.sizeX - 1) {
            if (this.map[y][x + 1] == Case.LIBRE)
                this.map[y][x + 1] = Case.FLAMMESPROPAGEES;
            else return this.map[y][x + 1] == Case.DEBUT || this.map[y][x + 1] == Case.SORTIE;
        }
        return false;
    }

    /**
     * Determine si le mouvement est possible dans une case adjacante à une case initiale selon une direction donnée
     *
     * @param x   la position x de la case initiale
     * @param y   la position y de la case initiale
     * @param dir la direction du mouvement
     * @return vrai si le mouvement est possible, faux sinon
     */
    private boolean canMoveDir(int x, int y, Direction dir) {
        return switch (dir) {
            case UP -> y != 0 && this.map[y - 1][x] == Case.LIBRE;
            case DOWN -> y != this.sizeY - 1 && this.map[y + 1][x] == Case.LIBRE;
            case LEFT -> x != 0 && this.map[y][x - 1] == Case.LIBRE;
            case RIGHT -> x != this.sizeX - 1 && this.map[y][x + 1] == Case.LIBRE;
        };
    }

    /**
     * Determine le nombre de déplacements uniques possibles à partir d'une case initiale
     *
     * @param x la position x de la case initiale
     * @param y la position y de la case initiale
     * @return le nombre de déplacements uniques possibles à partir d'une case initiale
     */
    private int canMove(int x, int y) {
        int value = this.canMoveDir(x, y, Direction.UP) ? 1 : 0;
        value += this.canMoveDir(x, y, Direction.DOWN) ? 1 : 0;
        value += this.canMoveDir(x, y, Direction.LEFT) ? 1 : 0;
        value += this.canMoveDir(x, y, Direction.RIGHT) ? 1 : 0;
        return value;
    }

    /**
     * Determine le nombre de déplacements possibles à partir d'une case initiale
     *
     * @param x la position x de la case initiale
     * @param y la position y de la case initiale
     * @return le nombre de déplacements possibles à partir d'une case initiale
     */
    private boolean winMove(int x, int y) {
        boolean top = (y != 0 && this.map[y - 1][x] == Case.SORTIE);
        boolean bottom = (y != this.sizeY - 1 && this.map[y + 1][x] == Case.SORTIE);
        boolean left = (x != 0 && this.map[y][x - 1] == Case.SORTIE);
        boolean right = (x != this.sizeX - 1 && this.map[y][x + 1] == Case.SORTIE);
        return (top || bottom || left || right);
    }

    /**
     * Déplace le prisionnier
     *
     * @return vrai si le prisonnier s'échappe, faux sinon
     */
    private boolean movePrisoner() {
        Integer[] prisonerPosition = new Integer[2];
        Integer[] exitPosition = new Integer[2];
        for (int i = 0; i < this.sizeY; ++i) {
            for (int j = 0; j < this.sizeX; ++j) {
                if (this.map[i][j] == Case.SORTIE) {
                    exitPosition[0] = i;
                    exitPosition[1] = j;
                } else if (this.map[i][j] == Case.DEBUT) {
                    prisonerPosition[0] = i;
                    prisonerPosition[1] = j;
                }
            }
        }

        int deltaX = prisonerPosition[1] - exitPosition[0];
        int deltaY = prisonerPosition[1] - exitPosition[0];

        boolean win = winMove(prisonerPosition[1], prisonerPosition[0]);
        if (win)
            return true;
        else {
            int nbMove = canMove(prisonerPosition[1], prisonerPosition[0]);
            if (nbMove > 0) {
                int x = prisonerPosition[1];
                int y = prisonerPosition[0];
                boolean top = canMoveDir(x, y, Direction.UP);
                boolean bottom = canMoveDir(x, y, Direction.DOWN);
                boolean left = canMoveDir(x, y, Direction.LEFT);
                boolean right = canMoveDir(x, y, Direction.RIGHT);
                if (nbMove == 1) {
                    this.map[x][y] = Case.DEJAPARCOURU;
                    if (top)
                        this.map[y - 1][x] = Case.DEBUT;
                    else if (bottom)
                        this.map[y + 1][x] = Case.DEBUT;
                    else if (right)
                        this.map[y][x + 1] = Case.DEBUT;
                    else if (left)
                        this.map[y][x - 1] = Case.DEBUT;
                } else {
                    if (deltaX > 0 && left)
                        this.map[y][x - 1] = Case.DEBUT;
                    else if (deltaX < 0 && right)
                        this.map[y][x + 1] = Case.DEBUT;
                    else if (deltaY < 0 && bottom)
                        this.map[y + 1][x] = Case.DEBUT;
                    else if (deltaY > 0 && top)
                        this.map[y - 1][x] = Case.DEBUT;
                    else if (top)
                        this.map[y - 1][x] = Case.DEBUT;
                    else if (bottom)
                        this.map[y + 1][x] = Case.DEBUT;
                    else if (right)
                        this.map[y][x + 1] = Case.DEBUT;
                    else if (left)
                        this.map[y][x - 1] = Case.DEBUT;
                }
                System.out.println(canMove(prisonerPosition[1], prisonerPosition[0]));
                return true;
            } else
                return false;
        }
    }

    public boolean runInstance() {
        int turn = 0;
        while (turn < this.sizeX * this.sizeY) {
            for (int i = 0; i < this.sizeY; ++i)
                for (int j = 0; j < this.sizeX; ++j)
                    if (this.map[i][j] == Case.FLAMMESPROPAGEES)
                        this.map[i][j] = Case.FEU;

            for (int i = 0; i < this.sizeY; ++i)
                for (int j = 0; j < this.sizeX; ++j)
                    if (this.map[i][j] == Case.FEU)
                        if (burnAround(j, i))
                            return false;
            if (movePrisoner())
                return true;
            System.out.println(this);
            turn++;
        }
        return false;
    }

    public void setValue(int x, int y, Case value) {
        this.map[x][y] = value;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.sizeY; ++i) {
            for (int j = 0; j < this.sizeX; ++j) {
                s.append(this.map[i][j].toString());
            }
            s.append(System.lineSeparator());
        }
        return s.toString();
    }
}
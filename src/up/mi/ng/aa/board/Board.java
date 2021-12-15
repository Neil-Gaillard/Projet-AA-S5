package up.mi.ng.aa.board;

import up.mi.ng.aa.graph.Graph;
import up.mi.ng.aa.labyrinthe.Case;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Classe pour gérer l'affichage
 * Pour le cours "IF05X040 Algorithmique avancée", de l'Université de Paris, 11/2020
 * Pour le TP 7 - Partie B
 * Originale modifiée et adaptée par Neil GAILLARD pour le Projet Labyrinthe
 *
 * @author Sylvain LOBRY
 * @author Neil GAILLARD
 * @version 2.0
 */
public class Board extends JComponent {
    private static final long serialVersionUID = 1L;
    private final int pixelSize;
    private final int ncols;
    private final int nlines;
    private final HashMap<Case, String> colors;
    private final int start;
    private final int end;
    private final double max_distance;
    private Graph<Case> graph;
    private int current;
    private LinkedList<Integer> path;

    /**
     * Construit un Graph(Case) sous forme de tableau à afficher
     *
     * @param graph     le graph à afficher
     * @param pixelSize la taille en pixel pour chaque sommet du graph
     * @param ncols     le nombre de colonnes du graph
     * @param nlines    le nombre de lignes du graph
     * @param colors    hashmap représentant la couleur définie pour chaque type de sommet
     * @param start     sommet défini en tant que sommet de départ à mettre en évidence
     * @param end       sommet défini en tant que sommet d'arrivée à mettre en évidence
     */
    public Board(Graph<Case> graph, int pixelSize, int ncols, int nlines, HashMap<Case, String> colors, int start, int end) {
        super();
        this.graph = graph;
        this.pixelSize = pixelSize;
        this.ncols = ncols;
        this.nlines = nlines;
        this.colors = colors;
        this.start = start;
        this.end = end;
        this.max_distance = ncols * nlines;
        this.current = -1;
        this.path = null;
    }

    /**
     * Met à jour l'affichage
     *
     * @param g objet Graphics
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //Ugly clear of the frame
        g2.setColor(Color.cyan);
        g2.fill(new Rectangle2D.Double(0, 0, this.ncols * this.pixelSize, this.nlines * this.pixelSize));

        int num_case = 0;
        for (int numVertex = 0; numVertex < graph.getNbVertex(); ++numVertex) {
            Case typeCase = graph.getVertex(numVertex).getData();
            int i = num_case / this.ncols;
            int j = num_case % this.ncols;

            if (colors.get(typeCase).equals("green"))
                g2.setPaint(Color.green);
            if (colors.get(typeCase).equals("gray"))
                g2.setPaint(Color.darkGray);
            if (colors.get(typeCase).equals("blue"))
                g2.setPaint(Color.blue);
            if (colors.get(typeCase).equals("yellow"))
                g2.setPaint(Color.yellow);
            if (colors.get(typeCase).equals("pink"))
                g2.setPaint(Color.pink);
            if (colors.get(typeCase).equals("red"))
                g2.setPaint(Color.red);
            if (colors.get(typeCase).equals("orange"))
                g2.setPaint(Color.orange);
            g2.fill(new Rectangle2D.Double(j * this.pixelSize, i * this.pixelSize, this.pixelSize, this.pixelSize));

            if (num_case == this.current) {
                g2.setPaint(Color.red);
                g2.draw(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2.,
                        i * this.pixelSize + this.pixelSize / 2., 6, 6));
            }
            if (num_case == this.start) {
                g2.setPaint(Color.white);
                g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2.,
                        i * this.pixelSize + this.pixelSize / 2., 4, 4));

            }
            if (num_case == this.end) {
                g2.setPaint(Color.black);
                g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2.,
                        i * this.pixelSize + this.pixelSize / 2., 4, 4));
            }

            num_case += 1;
        }

        num_case = 0;
        for (int numVertex = 0; numVertex < this.graph.getNbVertex(); ++numVertex) {
            int i = num_case / this.ncols;
            int j = num_case % this.ncols;
            if (this.graph.getVertex(numVertex).getData().getTime() < Double.POSITIVE_INFINITY) {
                float g_value = (float) (1 - this.graph.getVertex(numVertex).getData().getTime() / this.max_distance);
                if (g_value < 0)
                    g_value = 0;
                g2.setPaint(new Color(g_value, g_value, g_value));
                g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2.,
                        i * this.pixelSize + this.pixelSize / 2., 4, 4));
                Graph<Case>.Vertex previous = graph.getVertex(numVertex).getPrev();
                if (previous != null) {
                    int i2 = previous.getId() / this.ncols;
                    int j2 = previous.getId() % this.ncols;
                    g2.setPaint(Color.black);
                    g2.draw(new Line2D.Double(j * this.pixelSize + this.pixelSize / 2.,
                            i * this.pixelSize + this.pixelSize / 2., j2 * this.pixelSize + this.pixelSize / 2.,
                            i2 * this.pixelSize + this.pixelSize / 2.));
                }
            }

            num_case += 1;
        }

        int prev = -1;
        if (this.path != null) {
            g2.setStroke(new BasicStroke(3.0f));
            for (int cur : this.path) {
                if (prev != -1) {
                    g2.setPaint(Color.red);
                    int i = prev / this.ncols;
                    int j = prev % this.ncols;
                    int i2 = cur / this.ncols;
                    int j2 = cur % this.ncols;
                    g2.draw(new Line2D.Double(j * this.pixelSize + this.pixelSize / 2.,
                            i * this.pixelSize + this.pixelSize / 2., j2 * this.pixelSize + this.pixelSize / 2.,
                            i2 * this.pixelSize + this.pixelSize / 2.));
                }
                prev = cur;
            }
        }
    }

    /**
     * Mise à jour du graphe (à appeler avant de mettre à jour l'affichage)
     *
     * @param graph   le graph à afficher
     * @param current un sommet à mettre en évidence
     */
    public void update(Graph<Case> graph, int current) {
        this.graph = graph;
        this.current = current;
        repaint();
    }

    /**
     * Indiquer un chemin sur un graph affiché
     *
     * @param graph le graph affiché
     * @param path  le chemin à afficher
     */
    public void addPath(Graph<Case> graph, LinkedList<Integer> path) {
        this.graph = graph;
        this.path = path;
        this.current = -1;
        repaint();
    }
}
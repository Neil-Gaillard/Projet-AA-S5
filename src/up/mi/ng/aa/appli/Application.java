package up.mi.ng.aa.appli;

import up.mi.ng.aa.board.Board;
import up.mi.ng.aa.graph.Graph;
import up.mi.ng.aa.labyrinthe.Case;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Projet Labyrinthe
 * Algorithmie Avancée - S’échapper d’Ayutthaya
 *
 * @author Neil GAILLARD
 * @version 1.0
 */
public class Application {

    /**
     * Méthode statique permettant de déclarer une fenêtre dans laquelle le graph sera affiché sous forme de carte
     *
     * @param board     le tableau à afficher sur l'écran
     * @param nlines    le nombre de lignes du tableau à afficher sur l'écran
     * @param ncols     le nombre de colonnes du tableau à afficher sur l'écran
     * @param pixelSize la taille en pixel d'une cellule du tableau à afficher sur l'écran
     * @author Proviens du TP Partie B (Algorithmie Avancée)
     */
    private static void drawBoard(Board board, int nlines, int ncols, int pixelSize) {
        JFrame window = new JFrame("S'échapper d'Ayutthaya");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, ncols * pixelSize + 20, nlines * pixelSize + 40);
        window.getContentPane().add(board);
        window.setVisible(true);
    }

    /**
     * Méthode permettant de calculer la distance de Tchebychev entre deux points
     *
     * @param x1 la composante x du point de départ
     * @param y1 la composante y du point de départ
     * @param x2 la composante x du point d'arrivée
     * @param y2 la composante y du point d'arrivée
     * @return la distance de Tchebychev entre ces deux points
     */
    private static float manhattanDistance(int x1, int y1, int x2, int y2) {
        return (Math.abs(x2 - x1) + Math.abs(y2 - y1));
    }

    /**
     * Algorithme A* permettant de trouver un plus court chemin dans un Graph(Case) donné
     *
     * @param graph le graphe représentant la carte
     * @param start un entier représentant la case de départ
     *              (entier unique correspondant à la case obtenue dans le sens de la lecture)
     * @param end   un entier représentant la case d'arrivée
     *              (entier unique correspondant à la case obtenue dans le sens de la lecture)
     * @param ncols le nombre de colonnes dans la carte
     * @param board l'affichage
     * @return une liste d'entiers correspondant au chemin
     * @author Adaptée à partir du TP Partie B (Algorithmie Avancée)
     */
    private static LinkedList<Integer> AStar(Graph<Case> graph, int start, int end, int ncols, Board board) {
        HashSet<Integer> toVisit = new HashSet<Integer>();
        for (int i = 0; i < graph.getNbVertex(); ++i)
            toVisit.add(graph.getVertex(i).getId());
        graph.getVertex(start).setTimeFromSource(0.f);

        for (int i = 0; i < graph.getNbVertex(); ++i)
            graph.getVertex(i).setHeuristic(Application.manhattanDistance(i % ncols, i / ncols, end % ncols, end / ncols));

        while (toVisit.contains(end)) {
            float costSoFar = Float.POSITIVE_INFINITY;
            int current = -1;
            for (Integer i : toVisit) {
                if (graph.getVertex(i).getTimeFromSource() + graph.getVertex(i).getHeuristic() <= costSoFar) {
                    costSoFar = graph.getVertex(i).getTimeFromSource();
                    current = graph.getVertex(i).getId();
                }
            }

            toVisit.remove(current);

            for (int i = 0; i < graph.getVertex(current).getAdjList().size(); i++) {
                int to_try = graph.getVertex(current).getAdjList().get(i).getDestination().getId();
                if (graph.getVertex(to_try).getTimeFromSource() > (costSoFar + graph.getVertex(current).getAdjList().get(i).getWeight())) {
                    graph.getVertex(to_try).setTimeFromSource(costSoFar + graph.getVertex(current).getAdjList().get(i).getWeight());
                    graph.getVertex(to_try).setPrev(graph.getVertex(current));
                }
            }
            board.update(graph, current);
        }

        LinkedList<Integer> path = new LinkedList<Integer>();
        if (graph.getVertex(end).getPrev() != null) {
            while (!path.contains(start)) {
                path.addFirst(end);
                if (graph.getVertex(end).getId() != graph.getVertex(start).getId())
                    end = graph.getVertex(end).getPrev().getId();
            }
            //path.addFirst(start);
        }

        board.addPath(graph, path);
        return path;
    }

    /**
     * Main Method
     *
     * @param args args
     */
    public static void main(String[] args) {
        File fileIn = new File("lab.txt");

        final HashMap<Case, String> groundColor = new HashMap<Case, String>();
        groundColor.put(Case.DEBUT, Case.DEBUT.getColor());
        groundColor.put(Case.SORTIE, Case.SORTIE.getColor());
        groundColor.put(Case.LIBRE, Case.LIBRE.getColor());
        groundColor.put(Case.FLAMMESPROPAGEES, Case.FLAMMESPROPAGEES.getColor());
        groundColor.put(Case.FEU, Case.FEU.getColor());
        groundColor.put(Case.MUR, Case.MUR.getColor());
        groundColor.put(Case.DEJAPARCOURU, Case.DEJAPARCOURU.getColor());

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
            String data = bufferedReader.readLine();
            final int NBINSTANCES = Integer.parseInt(data);
            for (int k = 0; k < NBINSTANCES; ++k) {
                data = bufferedReader.readLine();
                int nlines = Integer.parseInt(data.split(" ")[0]);
                int ncols = Integer.parseInt(data.split(" ")[1]);

                int startV = 0;
                int endV = 0;

                Graph<Case> graph = new Graph<Case>();

                //Ajout des sommets
                for (int i = 0; i < nlines; ++i) {
                    data = bufferedReader.readLine();
                    for (int j = 0; j < ncols; ++j) {
                        graph.addVertex(Case.getValueFromChar(data.charAt(j)));
                        if (Case.getValueFromChar(data.charAt(j)) == Case.DEBUT)
                            startV = i * ncols + j;
                        if (Case.getValueFromChar(data.charAt(j)) == Case.SORTIE)
                            endV = i * ncols + j;
                    }
                }

                //Ajout des arêtes
                for (int line = 0; line < nlines; ++line) {
                    for (int col = 0; col < ncols; ++col) {
                        int source = line * ncols + col;
                        int dest;
                        float weight;
                        if (graph.getVertex(source).getData() != Case.MUR) {
                            if (line > 0) {
                                dest = (line - 1) * ncols + col;
                                weight = (graph.getVertex(source).getData().getTime() +
                                        graph.getVertex(source).getData().getTime()) / 2.f;
                                graph.addEdge(source, dest, weight);
                            }
                            if (col > 0) {
                                dest = (line) * ncols + col - 1;
                                weight = (graph.getVertex(source).getData().getTime() +
                                        graph.getVertex(dest).getData().getTime()) / 2.f;
                                graph.addEdge(source, dest, weight);
                            }
                        }
                    }
                }

                int pixelSize = 50;
                Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
                drawBoard(board, nlines, ncols, pixelSize);
                board.repaint();

                LinkedList<Integer> path = AStar(graph, startV, endV, ncols, board);

                boolean escaped = true;
                if (!path.isEmpty()) {
                    for (int i = 0; i < path.size() - 1 && escaped; ++i) {
                        for (int j = 0; j < graph.getNbVertex(); ++j)
                            if (graph.getVertex(j).getData() == Case.FEU)
                                for (Graph<Case>.Edge e : graph.getVertex(j).getAdjList())
                                    e.getDestination().setData(Case.FLAMMESPROPAGEES);
                        for (int j = 0; j < graph.getNbVertex(); ++j)
                            if (graph.getVertex(j).getData() == Case.FLAMMESPROPAGEES)
                                graph.getVertex(j).setData(Case.FEU);
                        for (int j = i; j < path.size() && escaped; ++j)
                            if (graph.getVertex(path.get(j)).getData() == Case.FEU)
                                escaped = false;
                    }
                } else
                    escaped = false;
                if (escaped)
                    System.out.println("Y");
                else
                    System.out.println("N");
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
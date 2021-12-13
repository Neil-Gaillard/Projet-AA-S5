package up.mi.ng.aa.appli;

import up.mi.ng.aa.board.Board;
import up.mi.ng.aa.graph.Graph;
import up.mi.ng.aa.labyrinthe.Case;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Application {

    private static void drawBoard(Board board, int nlines, int ncols, int pixelSize) {
        JFrame window = new JFrame("Plus court chemin");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, ncols * pixelSize + 20, nlines * pixelSize + 40);
        window.getContentPane().add(board);
        window.setVisible(true);
    }

    private static float chebyshevDistance(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(y2 - y1), Math.abs(x2 - x1));
    }

    /**
     * Algorithme A*
     *
     * @param graph le graphe représentant la carte
     * @param start un entier représentant la case de départ
     *              (entier unique correspondant à la case obtenue dans le sens de la lecture)
     * @param end   un entier représentant la case d'arrivée
     *              (entier unique correspondant à la case obtenue dans le sens de la lecture)
     * @param ncols le nombre de colonnes dans la carte
     * @param board l'affichage
     * @return une liste d'entiers correspondant au chemin
     */
    private static LinkedList<Integer> AStar(Graph<Case> graph, int start, int end, int ncols, Board board) {
        HashSet<Integer> toVisit = new HashSet<Integer>();
        for (int i = 0; i < graph.getNbVertex(); ++i)
            toVisit.add(graph.getVertex(i).getId());
        graph.getVertex(start).setTimeFromSource(0.f);

        for (int i = 0; i < graph.getNbVertex(); ++i)
            graph.getVertex(i).setHeuristic(Application.chebyshevDistance(i % ncols, i / ncols, end % ncols, end / ncols));

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
        while (!path.contains(start)) {
            path.addFirst(end);
            if (graph.getVertex(end).getId() != graph.getVertex(start).getId())
                if (graph.getVertex(end).getPrev() != null)
                    end = graph.getVertex(end).getPrev().getId();
        }
        path.addFirst(start);

        board.addPath(graph, path);
        return path;
    }

    public static void main(String[] args) {
        File fileIn = new File("up\\mi\\ng\\aa\\lab.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
            String data = bufferedReader.readLine();
            final int NBINSTANCES = Integer.parseInt(data);
            for (int k = 0; k < NBINSTANCES; ++k) {

                HashMap<Case, String> groundColor = new HashMap<Case, String>();
                groundColor.put(Case.DEBUT, Case.DEBUT.getColor());
                groundColor.put(Case.SORTIE, Case.SORTIE.getColor());
                groundColor.put(Case.LIBRE, Case.LIBRE.getColor());
                groundColor.put(Case.FLAMMESPROPAGEES, Case.FLAMMESPROPAGEES.getColor());
                groundColor.put(Case.FEU, Case.FEU.getColor());
                groundColor.put(Case.MUR, Case.MUR.getColor());
                groundColor.put(Case.DEJAPARCOURU, Case.DEJAPARCOURU.getColor());

                data = bufferedReader.readLine();
                int nlines = Integer.parseInt(data.split(" ")[0]);
                int ncols = Integer.parseInt(data.split(" ")[1]);

                int startV = 0;
                int endV = 0;

                Case[][] testTableau = new Case[nlines][ncols];
                Graph<Case> graph = new Graph<Case>();

                //Ajout des sommets
                for (int i = 0; i < nlines; ++i) {
                    data = bufferedReader.readLine();
                    for (int j = 0; j < ncols; ++j) {
                        testTableau[i][j] = Case.getValueFromChar(data.charAt(j));
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
                        if (line > 0) {
                            if (col > 0) {
                                dest = (line - 1) * ncols + col - 1;
                                weight = (graph.getVertex(source).getData().getTime() +
                                        graph.getVertex(dest).getData().getTime()) / 2.f;
                                graph.addEdge(source, dest, weight);
                                graph.addEdge(dest, source, weight);
                            }
                            dest = (line - 1) * ncols + col;
                            weight = (graph.getVertex(source).getData().getTime() +
                                    graph.getVertex(source).getData().getTime()) / 2.f;
                            graph.addEdge(source, dest, weight);
                            graph.addEdge(dest, source, weight);
                        }
                        if (col > 0) {
                            dest = (line) * ncols + col - 1;
                            weight = (graph.getVertex(source).getData().getTime() +
                                    graph.getVertex(dest).getData().getTime()) / 2.f;
                            graph.addEdge(source, dest, weight);
                            graph.addEdge(dest, source, weight);
                        }
                    }
                }

                int pixelSize = 40;
                Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
                drawBoard(board, nlines, ncols, pixelSize);
                board.repaint();

                LinkedList<Integer> path = AStar(graph, startV, endV, ncols, board);

                try {
                    File fileOut = new File("N:\\out.txt");
                    if (!fileOut.exists()) {
                        if (!fileOut.createNewFile())
                            throw new IOException("Could not create the fileIn");
                    }
                    FileWriter fw = new FileWriter(fileOut.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);

                    for (int i : path) {
                        bw.write(String.valueOf(i));
                        bw.write('\n');
                    }
                    bw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //TODO Lire le chemin, placer dans un tableau
                //TODO Chaque indice, avancer le feu
                //TODO Si le feu se touche une case que le héros doit parcourir dans le futur / la case ou il est alors
                //TODO il a pas perdu
                //TODO Sinon le héros peut accédère à la fin avant le feu
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package up.mi.ng.aa.appli;

import up.mi.ng.aa.board.Board;
import up.mi.ng.aa.graph.Graph;
import up.mi.ng.aa.labyrinthe.Case;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Application {
    private static void drawBoard(Board board, int nlines, int ncols, int pixelSize) {
        JFrame window = new JFrame("Plus court chemin");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, ncols * pixelSize + 20, nlines * pixelSize + 40);
        window.getContentPane().add(board);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        File file = new File("N:\\Projet-AA-S5\\src\\up\\mi\\ng\\aa\\lab.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
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
                                graph.addEdge(graph.getVertex(source), graph.getVertex(dest), weight);
                            }
                            dest = (line - 1) * ncols + col;
                            weight = (graph.getVertex(source).getData().getTime() +
                                    graph.getVertex(source).getData().getTime()) / 2.f;
                            graph.addEdge(graph.getVertex(source), graph.getVertex(dest), weight);
                        }
                        if (col > 0) {
                            dest = (line) * ncols + col - 1;
                            weight = (graph.getVertex(source).getData().getTime() +
                                    graph.getVertex(dest).getData().getTime()) / 2.f;
                            graph.addEdge(graph.getVertex(source), graph.getVertex(dest), weight);
                        }
                    }
                }

                int pixelSize = 30;
                Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
                drawBoard(board, nlines, ncols, pixelSize);
                board.repaint();

                System.out.println(Arrays.deepToString(testTableau));
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
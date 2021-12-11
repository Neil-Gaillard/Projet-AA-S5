package up.mi.ng.aa.appli;

import up.mi.ng.aa.labyrinthe.Case;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Application {
    public static void main(String[] args) {
        File file = new File("N:\\Projet-AA-S5\\src\\up\\mi\\ng\\aa\\lab.txt");

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String data = bufferedReader.readLine();
            final int NBINSTANCES = Integer.parseInt(data);
            for (int k = 0; k < NBINSTANCES; ++k) {
                data = bufferedReader.readLine();

                int nlines = Integer.parseInt(data.split(" ")[0]);
                int ncols = Integer.parseInt(data.split(" ")[1]);

                Case[][] testTableau = new Case[nlines][ncols];

                for (int i = 0; i < nlines; ++i) {
                    data = bufferedReader.readLine();
                    for (int j = 0; j < ncols; ++j) {
                        testTableau[i][j] = Case.getValueFromChar(data.charAt(j));
                    }
                }
                System.out.println(Arrays.deepToString(testTableau));
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
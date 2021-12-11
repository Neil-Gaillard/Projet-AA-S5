package up.mi.ng.aa.appli;

import up.mi.ng.aa.labyrinthe.Case;
import up.mi.ng.aa.labyrinthe.Labyrinthe;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        int N_INSTANCES;
        Scanner sc = new Scanner(System.in);
        N_INSTANCES = sc.nextInt();
        for (int i = 0; i < N_INSTANCES; ++i) {
            int sizeY = sc.nextInt();
            int sizeX = sc.nextInt();
            sc.nextLine();
            Labyrinthe labyrinthe = new Labyrinthe(sizeX, sizeY);
            for (int j = 0; j < sizeY; ++j) {
                Case[] entree = new Case[sizeX];
                String input = sc.nextLine();
                for (int k = 0; k < sizeX; ++k) {
                    entree[k] = Case.getValueFromChar(input.charAt(k));
                    labyrinthe.setValue(j, k, entree[k]);
                }
            }
            System.out.println(labyrinthe);
            if (labyrinthe.runInstance())
                System.out.println('Y');
            else
                System.out.println('N');
        }
    }
}
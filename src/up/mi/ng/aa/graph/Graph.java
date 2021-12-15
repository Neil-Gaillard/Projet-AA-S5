package up.mi.ng.aa.graph;

import java.util.ArrayList;

/**
 * Classe représentant un graphe et ses différents composants
 * Ce graph est un graph non orienté, mais qui utilise deux arêtes orientérs de sens différents pour une connexion
 * entre deux sommets.
 *
 * @param <T> le type de donné qui sera stocké dans les sommets du graph
 * @author Neil GAILLARD
 * @version 1.0
 */
public class Graph<T> {
    private final ArrayList<Vertex> vertices;

    private int nbVertex = 0;

    /**
     * Construit une structure de données Graph vide.
     */
    public Graph() {
        this.vertices = new ArrayList<>();
    }

    /**
     * Ajoute un sommet au graph.
     *
     * @param data La donnée à stocker dans ce sommet
     */
    public void addVertex(T data) {
        Vertex v = new Vertex(data);
        this.vertices.add(v);
        ++this.nbVertex;
    }

    /**
     * Ajoute une arête entre deux sommets.
     *
     * @param source Le premier sommet
     * @param dest   Le second sommet
     * @param weight Le poids de l'arête
     */
    public void addEdge(int source, int dest, float weight) {
        this.vertices.get(source).getAdjList().add(new Edge(dest, weight));
        this.vertices.get(dest).getAdjList().add(new Edge(source, weight));
    }

    /**
     * Permet d'obtenir un sommet du graph.
     *
     * @param i l'indice du sommet à récupérer dans la liste des sommets du graph
     * @return le sommet à l'indice i dans la liste des sommets du graph
     */

    public Vertex getVertex(int i) {
        return this.vertices.get(i);
    }

    /**
     * Permet d'obtenir le nombre de sommets présents dans le graph.
     *
     * @return le nombre de sommets présents dans le graph
     */
    public int getNbVertex() {
        return nbVertex;
    }

    /**
     * Classe représentant un sommet.
     */
    public class Vertex {
        private final ArrayList<Edge> adjList;
        private final int id;
        private T data;
        private float timeFromSource;
        private float heuristic;

        private Vertex prev;

        private Vertex(T data) {
            this.data = data;
            this.heuristic = 0.f;

            this.adjList = new ArrayList<Edge>();

            this.id = nbVertex;
            this.prev = null;

            this.timeFromSource = Float.POSITIVE_INFINITY;
        }

        /**
         * Permet d'obtenir la donnée stockée dans le sommet.
         *
         * @return la donnée stockée dans le sommet
         */
        public T getData() {
            return data;
        }

        /**
         * Permet de mettre à jour la donnée stockée dans le sommet
         *
         * @param data la donnée à stocker dans le sommet
         */
        public void setData(T data) {
            this.data = data;
        }

        /**
         * Permet d'obtenir l'identifiant d'un sommet dans un graph, soit l'indice auquel il est stocké dans la
         * liste de sommets du graph.
         *
         * @return l'identifiant du sommet
         */
        public int getId() {
            return id;
        }

        /**
         * Utilisée par des algorithmes, permet d'obtenir le champ qui contient la valeur du chemin parcouru d'un sommet
         * défini dans l'algorithme jusqu'à cette instance de sommet.
         *
         * @return la valeur du chemin parcouru d'un sommet défini dans l'algorithme jusqu'à cette instance de sommet
         */
        public float getTimeFromSource() {
            return timeFromSource;
        }

        /**
         * Utilisée par des algorithmes, permet de mettre à jour le champ qui contiens la valeur du chemin parcouru
         * d'un sommet défini dans l'algorithme jusqu'à cette instance de sommet.
         *
         * @param timeFromSource la nouvelle valeur
         */
        public void setTimeFromSource(float timeFromSource) {
            this.timeFromSource = timeFromSource;
        }

        /**
         * Permet d'obtenir la valeur stockée dans le champ qui représente l'heuristique du sommet.
         *
         * @return La valeur de l'heuristique du sommet
         */
        public float getHeuristic() {
            return heuristic;
        }

        /**
         * Permet de mettre à jour la valeur stockée dans le champ qui représente l'heuristique du sommet.
         *
         * @param heuristic la nouvelle valeur
         */
        public void setHeuristic(float heuristic) {
            this.heuristic = heuristic;
        }

        /**
         * Utilisée par des algorithmes, permet d'obtenir le sommet défini en tant que sommet parent de l'instance
         * actuelle.
         *
         * @return Le sommet dit parent de l'instance actuelle
         */
        public Vertex getPrev() {
            return prev;
        }

        /**
         * Utilisée par des algorithmes, permet de mettre à jour le sommet défini en tant que sommet parent de
         * l'instance actuelle.
         *
         * @param prev le sommet à définir en tant que parent de l'instance actuelle
         */
        public void setPrev(Vertex prev) {
            this.prev = prev;
        }

        /**
         * Retourne la liste d'adjacence (référence) du sommet.
         *
         * @return une référence vers la liste d'adjacence du sommet
         */
        public ArrayList<Edge> getAdjList() {
            //Mauvaise pratique, autant mettre le champ en public,
            //mais utile pour ce projet figé et les algorithmes implémentés.
            return adjList;
        }
    }

    /**
     * Classe représentant une arête entre deux sommets.
     */
    public class Edge {
        private final Vertex destination;

        private final float weight;

        private Edge(int destination, float weight) {
            this.destination = vertices.get(destination);
            this.weight = weight;
        }

        /**
         * Permet d'obtenir le poids de l'arête.
         *
         * @return le poids de l'arête
         */
        public float getWeight() {
            return weight;
        }

        /**
         * Permet d'obtenir le sommet vers lequel l'arête pointe.
         *
         * @return le sommet vers lequel l'arête pointe
         */
        public Vertex getDestination() {
            return destination;
        }
    }
}
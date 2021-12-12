package up.mi.ng.aa.graph;

import java.util.ArrayList;

/**
 * Classe représentant un graphe et ses différents composants
 *
 * @param <T>
 */
public class Graph<T> {
    private final ArrayList<Vertex> vertices;

    private int nbVertex = 0;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    /**
     * Adds a vertex to the graph
     *
     * @param data The data of the vertex to add to the graph
     */
    public void addVertex(T data) {
        Vertex v = new Vertex(data);
        this.vertices.add(v);
        ++this.nbVertex;
    }

    /**
     * Adds an edge between two vertices (in both ways)
     *
     * @param dest   The vertex from where the edge ends
     * @param weight The weight of the edge
     */
    public void addEdge(int source, int dest, float weight) {
        this.vertices.get(source).getAdjList().add(new Edge(dest, weight));
    }

    public Vertex getVertex(int i) {
        return this.vertices.get(i);
    }

    public int getNbVertex() {
        return nbVertex;
    }

    /**
     *
     */
    public class Vertex {
        private final T data;

        private final ArrayList<Edge> adjList;

        private final int id;

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
         * Returns the data present in the vertex
         *
         * @return the data present in the vertex
         */
        public T getData() {
            return data;
        }

        public int getId() {
            return id;
        }

        public float getTimeFromSource() {
            return timeFromSource;
        }

        public void setTimeFromSource(float timeFromSource) {
            this.timeFromSource = timeFromSource;
        }

        public float getHeuristic() {
            return heuristic;
        }

        public void setHeuristic(float heuristic) {
            this.heuristic = heuristic;
        }

        public Vertex getPrev() {
            return prev;
        }

        public void setPrev(Vertex prev) {
            this.prev = prev;
        }

        public ArrayList<Edge> getAdjList() {
            return adjList;
        }
    }

    /**
     *
     */
    public class Edge {
        private final Vertex destination;

        private final float weight;

        private Edge(int destination, float weight) {
            this.destination = vertices.get(destination);
            this.weight = weight;
        }

        public float getWeight() {
            return weight;
        }

        public Vertex getDestination() {
            return destination;
        }
    }
}
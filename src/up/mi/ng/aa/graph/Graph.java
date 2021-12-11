package up.mi.ng.aa.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Classe représentant un graphe et ses différents composants
 *
 * @param <T>
 */
public class Graph<T> {
    private final ArrayList<Vertex> vertices;
    private final HashMap<Vertex, ArrayList<Vertex>> adjList;

    private int nbVertex = 0;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.adjList = new HashMap<Vertex, ArrayList<Vertex>>();
    }

    /**
     * Adds a vertex to the graph
     *
     * @param data The data of the vertex to add to the graph
     */
    public void addVertex(T data) {
        Vertex v = new Vertex(data);
        this.vertices.add(v);
        this.adjList.put(v, new ArrayList<Vertex>());
        ++this.nbVertex;
    }

    /**
     * Adds an edge between two vertices (in both ways)
     *
     * @param key    The vertex from where the edge starts
     * @param vertex The vertex from where the edge ends
     */
    public void addEdge(Vertex key, Vertex vertex) {
        this.adjList.get(key).add(vertex);
        this.adjList.get(vertex).add(key);

        key.adjList.add(new Edge(key, vertex));
        vertex.adjList.add(new Edge(vertex, key));
    }

    public void addEdge(Vertex key, Vertex vertex, float weight) {
        this.adjList.get(key).add(vertex);
        this.adjList.get(vertex).add(key);

        key.adjList.add(new Edge(key, vertex, weight));
        vertex.adjList.add(new Edge(vertex, key, weight));
    }

    /**
     * Checks if two vertices are adjacent
     *
     * @param key    The vertex from where the edge starts
     * @param vertex The vertex from where the edge ends
     */
    public boolean areAdjacent(Vertex key, Vertex vertex) {
        return this.adjList.get(key).contains(vertex) || this.adjList.get(vertex).contains(key);
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

        private final HashSet<Edge> adjList;

        private int id;

        private float heuristic;

        private Vertex prev;

        private Vertex(T data) {
            this.data = data;
            this.heuristic = 0.f;

            this.adjList = new HashSet<Edge>();

            this.id = nbVertex;
            this.prev = null;
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

        public float getHeuristic() {
            return heuristic;
        }

        public Vertex getPrev() {
            return prev;
        }
    }

    /**
     *
     */
    public class Edge {
        private Vertex firstVertex;
        private Vertex secondVertex;

        private float weight;

        private Edge(Vertex firstVertex, Vertex secondVertex, float weight) {
            this.firstVertex = firstVertex;
            this.secondVertex = secondVertex;
            this.weight = weight;
        }

        private Edge(Vertex firstVertex, Vertex secondVertex) {
            this(firstVertex, secondVertex, 0.f);
        }

        public float getWeight() {
            return weight;
        }

        public Vertex getFirstVertex() {
            return firstVertex;
        }

        public Vertex getSecondVertex() {
            return secondVertex;
        }
    }
}
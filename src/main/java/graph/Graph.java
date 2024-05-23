package graph;

import java.util.*;

public class Graph {
    private final static Set<Vertex> HASH = new LinkedHashSet<>();

    private final List<Vertex> vertexList;
    private final Map<Vertex, Set<Vertex>> adjVerticesMap;
    private final int[][] adjVertices;

    // Конструктор
    public Graph(int size) {
        this.adjVerticesMap = new HashMap<>();
        this.adjVertices = new int[size][size];
        this.vertexList = new ArrayList<>();
    }

    // Добавление вершины
    public void addVertex(Vertex vertex) {
        if (!HASH.contains(vertex)) {
            HASH.add(vertex);
            vertexList.add(vertex);
            adjVerticesMap.putIfAbsent(vertex, new HashSet<>());
        }
    }

    // Добавление ребра
    public void addEdge(Vertex start, Vertex end) {
        addVertex(start);
        addVertex(end);

        int row = findNum(start);
        int col = findNum(end);

        adjVertices[row][col] = 1;
        adjVertices[col][row] = 1;

        adjVerticesMap.get(start).add(end);
        adjVerticesMap.get(end).add(start);
    }

    private int findNum(Vertex vertex) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).equals(vertex)) {
                return i;
            }
        }
        return 0;
    }

    public void dfs(Vertex start) {
        Queue<Vertex> stack = new PriorityQueue<>();

        start.setVisited(true);
        stack.offer(start);

        while (!stack.isEmpty()) {
            Vertex current = stack.poll();
            printVertex(current);

            for (Vertex vertex : adjVerticesMap.get(current)) {
                if (!vertex.isVisited()) {
                    vertex.setVisited(true);
                    stack.offer(vertex);
                }
            }
        }

        reset();
    }

    public void printVertex(Vertex vertex) {
        System.out.println(vertex);
    }

    private void reset() {
        for (Vertex vertex : HASH) {
            vertex.setVisited(false);
        }
    }

    public void printEdgeMap() {
        for (Vertex vertex : adjVerticesMap.keySet()) {
            System.out.print(vertex.getName() + ": ");
            for (Vertex neighbor : adjVerticesMap.get(vertex)) {
                System.out.print(neighbor.getName() + ", ");
            }
            System.out.println();
        }
    }

    public void printEdgeInt() {
        for (int i = 0; i < vertexList.size(); i++) {
            System.out.println(i + ": " + vertexList.get(i));
        }

        for (int[] array : adjVertices) {
            for (int value : array) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}


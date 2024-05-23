package graph;

import java.util.*;

public class Graph {
    private final static Set<Vertex> HASH = new HashSet<>();

    private final Map<Vertex, List<Vertex>> adjVerticesMap;

    // Конструктор
    public Graph(int size) {
        this.adjVerticesMap = new HashMap<>();
    }

    // Добавление вершины
    public void addVertex(Vertex vertex) {
        if (!HASH.contains(vertex)) {
            HASH.add(vertex);
            adjVerticesMap.putIfAbsent(vertex, new ArrayList<>());
        }
    }

    // Добавление ребра
    public void addEdge(Vertex start, Vertex end) {
        addVertex(start);
        addVertex(end);

        adjVerticesMap.get(start).add(end);
        adjVerticesMap.get(end).add(start);
    }

    public void dfs(Vertex start) {
        Queue<Vertex> queue = new PriorityQueue<>();

        start.setVisited(true);
        queue.offer(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();

            for (Vertex vertex : adjVerticesMap.get(current)) {
                if (!vertex.isVisited()) {
                    vertex.setVisited(true);
                    queue.offer(vertex);
                }
            }
        }

        reset();
    }

    private void reset() {
        for (Vertex vertex : HASH) {
            vertex.setVisited(false);
        }
    }

    public void printEdge() {
        for (Vertex vertex : adjVerticesMap.keySet()) {
            System.out.print(vertex.getName() + ": ");
            for (Vertex neighbor : adjVerticesMap.get(vertex)) {
                System.out.print(neighbor.getName() + ", ");
            }
            System.out.println();
        }
    }
}


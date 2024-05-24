package graph;

import java.util.*;

public class Graph {
    private static final Set<Vertex> VERTEX_HASH = new HashSet<>();

    private final Map<Vertex, Set<Vertex>> adjMap;

    private final List<Vertex> listVertex;
    private final Vertex[][] adjMatrix;
    private final List<Edge> listEdge;

    private final int size;

    public Graph(int size) {
        this.size = size;
        this.adjMap = new HashMap<>();
        this.listVertex = new ArrayList<>();
        this.adjMatrix = new Vertex[size][size];
        this.listEdge = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        if (!VERTEX_HASH.contains(vertex)) {
            VERTEX_HASH.add(vertex);
            listVertex.add(vertex);
            adjMap.put(vertex, new HashSet<>());
        }
    }

    public void addEdge(Vertex start, Vertex end) {
        addVertex(start);
        addVertex(end);

        listEdge.add(new Edge(start, end));

        int row = findNumber(start);
        int col = findNumber(end);

        if (row == -1 || col == -1) throw new RuntimeException("row == -1 || col == -1");

        adjMatrix[row][col] = end;
        adjMatrix[col][row] = start;

        adjMap.get(start).add(end);
        adjMap.get(end).add(start);
    }

    private int findNumber(Vertex vertex) {
        for (int i = 0; i < listVertex.size(); i++) {
            if (listVertex.get(i).equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    public Edge getEdge(int cell) {
        return listEdge.get(cell);
    }

    public void dfsMap(Vertex start) {
        Stack<Vertex> stack = new Stack<>();

        start.setVisited(true);
        stack.push(start);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();
            printVertex(current);

            for (Vertex neighbor : adjMap.get(current)) {
                if (!neighbor.isVisited()) {
                    neighbor.setVisited(true);
                    stack.push(neighbor);
                }
            }
        }

        reset();
    }

    public void dfsInt(Vertex start) {
        Stack<Vertex> stack = new Stack<>();

        start.setVisited(true);
        stack.push(start);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();
            printVertex(current);

            for (Vertex neighbor : adjMatrix[findNumber(current)]) {
                if (neighbor != null && !neighbor.isVisited()) {
                    neighbor.setVisited(true);
                    stack.push(neighbor);
                }
            }
        }

        reset();
    }

    private void reset() {
        for (Vertex vertex : VERTEX_HASH) {
            vertex.setVisited(false);
        }
    }

    public String printVertex(Vertex vertex) {
        return vertex.toString();
    }

    public String printAdjMap() {
        StringBuilder builder = new StringBuilder("AdjMap\n");
        for (Vertex vertex : VERTEX_HASH) {
            builder.append(vertex.getName()).append(": ");
            for (Vertex neighbor : adjMap.get(vertex)) {
                builder.append(neighbor.getName()).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public String printAdjMatrix() {
        StringBuilder builder = new StringBuilder("AdjMatrix\n");
        for (int row = 0; row < size; row++) {
            builder.append(listVertex.get(row).getName())
                    .append(": ");
            for (int col = 0; col < size; col++) {
                Vertex vertex = adjMatrix[row][col];
                if (vertex != null) {
                    builder.append(adjMatrix[row][col].getName())
                            .append(" ");
                } else {
                    builder.append(". ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private static class Edge implements Comparable<Edge> {
        private final Vertex start;
        private final Vertex end;
        private final int weight;

        private Edge(Vertex start, Vertex end) {
            this.start = start;
            this.end = end;
            this.weight = weight();
        }

        private int weight() {
            return (int) Math.round(start.distanceTo(end));
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return "Edge:" +
                    " start=" + start.getName() +
                    " to" +
                    " end=" + end.getName() +
                    " weight=" + weight;
        }
    }
}

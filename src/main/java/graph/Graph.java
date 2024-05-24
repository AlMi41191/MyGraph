package graph;

import java.util.*;

public class Graph {
    private static final Set<Vertex> HASH = new LinkedHashSet<>();
    
    private final Map<Vertex, Set<Vertex>> adjMap;
    
    private final List<Vertex> listVertex;
    private final Vertex[][] adjMatrix;
    private final List<Edge> listEdge;
    
    private final int size;
    
    public Graph(int size) {
        this.size = size;
        this.adjMap = new HashMap<>();
        this.listVertex = new ArrayList();
        this.adjMatrix = new Vertex[size][size];
        this.listEdge = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        if (!HASH.contains(vertex)) {
            HASH.add(vertex);
            addToListVertex(vertex);
            adjMap.put(vertex, new HashSet<>());
        }
    }
    
    private void addToListVertex(Vertex vertex) {
        listVertex.add(vertex);
        
        if (listVertex.size() > 1) {
            Collections.sort(listVertex);
        }
    }
    
    public void addEdge(Vertex start, Vertex end) {
        addVertex(start);
        addVertex(end);
        
        addToListEdge(new Edge(start, end));
        
        int row = findNumber(start);
        int col = findNumber(end);
        
        if (row == -1 || col == -1) throw new RuntimeException("row == 0 || col == 0");
        
        adjMatrix[row][col] = end; // в start добавляю end (start связываю с end)
        adjMatrix[col][row] = start; // в end добавляю start (end связываю с start)
        
        adjMap.get(start).add(end); // в start добавляю end (start связываю с end)
        adjMap.get(end).add(start); // в end добавляю start (end связываю с start)
    }
    
    private void addToListEdge(Edge edge) {
        listEdge.add(edge);
        
        if (listEdge.size() > 1) {
            Collections.sort(listEdge);
        }
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
        Stack<Vertex> stack = new Stack();
        
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
        
        // Сброс флагов после обхода
        reset();
    }
    
    public void dfsInt(Vertex start) {
        Stack<Vertex> stack = new Stack();
        
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
        
        // Сброс флагов после обхода
        reset();
    }
    
    private void reset() {
        for (Vertex vertex : HASH) {
            vertex.setVisited(false);
        }
    }
    
    public void printVertex(Vertex vertex) {
        System.out.println(vertex);
    }
    
    public void printAdjMap() {
        System.out.println("AdjMap");
        for (Vertex vertex : HASH) {
            System.out.print(vertex.getName() + ": ");
            for (Vertex neighbor : adjMap.get(vertex)) {
                System.out.print(neighbor.getName() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void printAdjMatrix() {
        System.out.println("AdjMatrix");
        StringBuilder builder = new StringBuilder();
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
        System.out.println(builder);
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
            return Integer.compare(this.weight(), other.weight());
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

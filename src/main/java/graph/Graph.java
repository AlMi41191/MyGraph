package graph;

import java.util.*;

public class Graph {
    private final Map<Vertex, Set<Vertex>> adjMap; // матрица смежностей
    private final Vertex[][] adjMatrix; // матрица смежностей
    
    private final List<Vertex> listVertex; // список Vertex
    private final List<Edge> listEdge; // список Edge
    
    private final int size;
    
    public Graph(int size) {
        this.size = size;
        
        this.adjMap = new HashMap<>();
        this.adjMatrix = new Vertex[size][size];
        
        this.listVertex = new ArrayList<>();
        this.listEdge = new ArrayList<>();
    }
    
    // Размер listEdge
    public int getSizeListEdge() {
        return listEdge.size();
    }
    
    // Размер listVertex
    public int getSizeListVertex() {
        return listVertex.size();
    }
    
    // Добавляем Vertex в Graph
    public void addVertex(Vertex vertex) {
        if (!listVertex.contains(vertex)) {
            addToListVertex(vertex);
            adjMap.put(vertex, new HashSet<>());
        }
    }
    
    // Добавляем Vertex список listVertex и сортируем listVertex
    private void addToListVertex(Vertex vertex) {
        listVertex.add(vertex);
        
        if (listVertex.size() > 1) {
            Collections.sort(listVertex);
        }
    }
    
    // Создаем Edge из двух Vertex и добавляем их в Graph
    public void addEdge(Vertex start, Vertex end) {
        addVertex(start);
        addVertex(end);
        
        addToListEdge(new Edge(start, end));
        
        int row = findNumber(start);
        int col = findNumber(end);
        
        if (row != -1 && col != -1) {
            adjMatrix[row][col] = end; // в start добавляю end (start связываю с end)
            adjMatrix[col][row] = start; // в end добавляю start (end связываю с start)
        }
        
        adjMap.get(start).add(end); // в start добавляю end (start связываю с end)
        adjMap.get(end).add(start); // в end добавляю start (end связываю с start)
    }
    
    // Добавляем Edge список listEdge и сортируем listEdge
    private void addToListEdge(Edge edge) {
        listEdge.add(edge);
        
        if (listEdge.size() > 1) {
            Collections.sort(listEdge);
        }
    }
    
    // Ищем номе Vertex в списке listVertex
    private int findNumber(Vertex vertex) {
        for (int i = 0; i < listVertex.size(); i++) {
            if (listVertex.get(i).equals(vertex)) {
                return i;
            }
        }
        return -1;
    }
    
    // Получаем Edge из списка listEdge
    public Edge getEdge(int cell) {
        return listEdge.get(cell);
    }
    
    // Поиск Vertex в глубину
    public void dfsMap(Vertex start) {
        System.out.println("-----DfsMap-----");
        
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
        
        // Сброс флагов после обхода
        reset();
    }
    
    // Поиск Vertex в глубину
    public void dfsInt(Vertex start) {
        System.out.println("-----DfsInt-----");
        
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
        
        // Сброс флагов после обхода
        reset();
    }
    
    // Поиск Vertex в ширину
    public void bfsInt(Vertex start, Vertex end) {
        System.out.println("-----BfsInt-----");
        
        Queue<Vertex> queue = new PriorityQueue<>();
        
        start.setVisited(true);
        queue.add(start);
        
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            printVertex(current);
            
            if (current.equals(end)) {
                break;
            }
            
            int max = Integer.MAX_VALUE;
            Vertex best = null;
            for (Vertex neighbor : adjMatrix[findNumber(current)]) {
                if (neighbor != null && !neighbor.isVisited()) {
                    
                    if (neighbor.equals(end)) {
                        neighbor.setVisited(true);
                        queue.add(neighbor);
                        break;
                    }
                    
                    int path = (int) Math.round(current.distanceTo(neighbor));
                    if (path < max) {
                        max = path;
                        neighbor.setVisited(true);
                        best = neighbor;
                    }
                }
            }
            if (best != null) {
                queue.add(best);
            }
        }
        
        // Сброс флагов после обхода
        reset();
    }
    
    // Сброс флагов после обхода
    private void reset() {
        for (Vertex vertex : listVertex) {
            vertex.setVisited(false);
        }
    }
    
    // Выводим в консоль Vertex
    public void printVertex(Vertex vertex) {
        System.out.println(vertex);
    }
    
    // Выводим в консоль матрицу смежностей AdjMap
    public void printAdjMap() {
        StringBuilder builder = new StringBuilder("-----AdjMap-----\n");
        for (Vertex vertex : listVertex) {
            builder.append(vertex.getName())
            .append(": ");
            for (Vertex neighbor : adjMap.get(vertex)) {
                builder.append(neighbor.getName())
                .append(" ");
            }
            builder.append("\n");
        }
        System.out.println(builder);
    }
    
    // Выводим в консоль матрицу смежностей AdjMatrix
    public void printAdjMatrix() {
        StringBuilder builder = new StringBuilder("-----AdjMatrix-----\n");
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

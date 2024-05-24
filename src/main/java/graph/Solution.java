package graph;

public class Solution {
    private static final int SIZE = 5;
    
    public static void main(String[] args) {
        Vertex A = Vertex.createVertex('A', 0, 0);
        Vertex B = Vertex.createVertex('B', 0, SIZE - 1);
        Vertex C = Vertex.createVertex('C', SIZE - 1, 0);
        Vertex D = Vertex.createVertex('D', SIZE - 1, SIZE - 1);
        Vertex E = Vertex.createVertex('E', SIZE/2, SIZE/2);
        
        Graph graph = new Graph(SIZE);
        
        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(E);
        graph.addVertex(C);
        graph.addVertex(D);
        
        graph.addEdge(E, A);
        graph.addEdge(E, B);
        graph.addEdge(E, D);
        graph.addEdge(E, C);
        graph.addEdge(A, B);
        graph.addEdge(A, C);
        
        // graph.dfsMap(A);
        graph.dfsInt(A);
        
        graph.printAdjMap();
        graph.printAdjMatrix();
        
        for (int i = 0; i < 6; i++) {
            System.out.println(graph.getEdge(i));
        }
    }
}

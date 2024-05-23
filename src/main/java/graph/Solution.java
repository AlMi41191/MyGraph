package graph;

public class Solution {
    public static void main(String[] args) {
        Vertex A = Vertex.createVertex('A', 0, 0);
        Vertex B = Vertex.createVertex('B', 0, 4);
        Vertex C = Vertex.createVertex('C', 4, 0);
        Vertex D = Vertex.createVertex('D', 4, 4);
        Vertex E = Vertex.createVertex('E', 2, 2);

        Graph graph = new Graph(5);

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(D);
        graph.addVertex(C);
        graph.addVertex(E);

        graph.addEdge(A, B);
        graph.addEdge(B, D);
        graph.addEdge(B, A);
        graph.addEdge(D, C);
        graph.addEdge(C, A);

        System.out.println("Visits: ");

        graph.dfs(A);
        
        graph.printEdgeInt();
        graph.printEdgeMap();
    }
}
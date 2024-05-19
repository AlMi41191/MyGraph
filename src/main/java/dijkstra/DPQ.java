package dijkstra;

// Java implementation of Dijkstra's Algorithm
// using Priority Queue
import java.util.*;
public class DPQ {
    private int dist[];
    private Set<Integer> set;
    private PriorityQueue<Node> priorityQueue;
    private int V; // Number of vertices
    List<List<Node>> list;

    public DPQ(int V) {
        this.V = V;
        dist = new int[V];
        set = new HashSet<>();
        priorityQueue = new PriorityQueue<>(V, new Node());
    }

    // Function for Dijkstra's Algorithm
    public void dijkstra(List<List<Node>> adj, int src) {
        this.list = adj;

        print();

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        // Add source node to the priority queue
        priorityQueue.add(new Node(src, 0));

        // Distance to the source is 0
        dist[src] = 0;
        while (set.size() != V) {
            //when the priority queue is empty, return
            if (priorityQueue.isEmpty()) {
                return;
            }

            // remove the minimum distance node
            // from the priority queue
            int u = priorityQueue.remove().value;

            // adding the node whose distance is
            // finalized
            set.add(u);

            e_Neighbours(u);
        }
    }

    // Function to process all the neighbours
    // of the passed node
    private void e_Neighbours(int u) {
        int edgeDistance = -1;
        int newDistance = -1;

        // All the neighbors of v
        for (int i = 0; i < list.get(u).size(); i++) {
            Node node = list.get(u).get(i);

            // If current node hasn't already been processed
            if (!set.contains(node.value)) {
                edgeDistance = node.cost;
                newDistance = dist[u] + edgeDistance;

                // If new distance is cheaper in cost
                if (newDistance < dist[node.value]) {
                    dist[node.value] = newDistance;
                }

                // Add the current node to the queue
                priorityQueue.add(new Node(node.value, dist[node.value]));
            }
        }
    }

    // Driver code
    public static void main(String[] args) {
        int V = 5;
        int source = 0;

        // Adjacency list representation of the
        // connected edges
        List<List<Node> > adj = new ArrayList<>();

        // Initialize list for every node
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<>();
            adj.add(item);
        }

        // Inputs for the DPQ graph
        adj.getFirst().add(new Node(1, 9));
        adj.getFirst().add(new Node(2, 6));
        adj.getFirst().add(new Node(3, 5));
        adj.getFirst().add(new Node(4, 3));

        adj.get(2).add(new Node(1, 2));
        adj.get(2).add(new Node(3, 4));

        // Calculate the single source shortest path
        DPQ dpq = new DPQ(V);
        dpq.dijkstra(adj, source);

        // Print the shortest path to all the nodes
        // from the source node
        System.out.println("The shorted path from node :");
        for (int i = 0; i < dpq.dist.length; i++) {
            System.out.println(source + " to " + i + " is " + dpq.dist[i]);
        }
    }

    private void print() {
        for (List<Node> nodes : list) {
            System.out.println(nodes);
        }
    }
}

// Class to represent a node in the graph
class Node implements Comparator<Node> {
    public int value;
    public int cost;

    public Node() {}

    public Node(int value, int cost) {
        this.value = value;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
        return Integer.compare(node1.cost, node2.cost);
    }

    @Override
    public String toString() {
        return "Node{" +
                "node=" + value +
                ", cost=" + cost +
                '}';
    }
}

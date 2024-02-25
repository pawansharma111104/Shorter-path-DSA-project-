import java.util.*;
class Graph {
    private final int vertices;
    private final LinkedList<Edge>[] adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        adjacencyList[source].add(new Edge(destination, weight));
        adjacencyList[destination].add(new Edge(source, weight)); // Assuming bidirectional routes
    }

    public void display() {
        for (int i = 0; i < vertices; i++) {
            System.out.print("Vertex " + i + ": ");
            for (Edge e : adjacencyList[i]) {
                System.out.print("(" + e.destination + ", " + e.weight + ") ");
            }
            System.out.println();
        }
    }

    public List<Integer> findShortestRoute(int start, int end) {
        int[] distance = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        distance[start] = 0;

        PriorityQueue<Node> minHeap = new PriorityQueue<>
                (Comparator.comparingInt(node -> node.distance));
        minHeap.add(new Node(start, 0));

        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.poll();
            int currentVertex = currentNode.vertex;

            for (Edge neighbor : adjacencyList[currentVertex]) {
                int neighborVertex = neighbor.destination;
                int newDistance = distance[currentVertex] + neighbor.weight;

                if (newDistance < distance[neighborVertex]) {
                    distance[neighborVertex] = newDistance;
                    parent[neighborVertex] = currentVertex;
                    minHeap.add(new Node(neighborVertex, newDistance));
                }
            }
        }

        List<Integer> route = new ArrayList<>();
        int current = end;
        while (current != -1) {
            route.add(current);
            current = parent[current];
        }
        Collections.reverse(route);

        return route;
    }

    private static class Edge {
        int destination;
        int weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    private static class Node {
        int vertex;
        int distance;

        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int vertices = 6;
        Scanner sc=new Scanner(System.in);
        Graph g = new Graph(vertices);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 7);
        g.addEdge(2, 4, 3);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 2);
        g.addEdge(3, 5, 2);
        g.addEdge(4, 5, 5);
         System.out.print("Enter the start vertex from 0 to "+(vertices-1)+": ");
        int start = sc.nextInt();

        System.out.print("Enter the end vertex from 0 to "+(vertices-1)+": ");
        int end = sc.nextInt();

        System.out.println("Representation Detail:");
        System.out.println("Vertex (Starting Node Name): (Adjacent Node Name, Cost of Traversing)\n");
        System.out.println("Delivery Network:");
        g.display();
        System.out.println("\nStart: "+start+"\nEnd: "+end);
        List<Integer> route = g.findShortestRoute(start, end);
        System.out.println("\nShortest Route from " + start + " to " + end + ": " + route);
    }
}

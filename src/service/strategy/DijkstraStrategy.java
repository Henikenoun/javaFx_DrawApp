package service.strategy;

import model.Node;
import model.Edge;

import java.util.*;

public class DijkstraStrategy implements AlgorithmStrategy {

    @Override
    public List<Node> findPath(List<Node> nodes, List<Edge> edges, Node start, Node end) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing(distances::get));

        // Initialisation
        for (Node node : nodes) {
            distances.put(node, Double.POSITIVE_INFINITY);
            previous.put(node, null);
        }
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.equals(end)) break;

            for (Edge edge : edges) {
                if (edge.getFrom().equals(current)) {
                    Node neighbor = edge.getTo();
                    double newDistance = distances.get(current) + edge.getWeight();
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, current);
                        queue.remove(neighbor); // mise à jour dans la queue
                        queue.add(neighbor);
                    }
                }
            }
        }

        // Reconstruction du chemin
        List<Node> path = new ArrayList<>();
        for (Node at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path.size() > 1 ? path : null;
    }
}

package service.strategy;

import model.Node;
import model.Edge;

import java.util.*;

public class BFSStrategy implements AlgorithmStrategy {

    @Override
    public List<Node> findPath(List<Node> nodes, List<Edge> edges, Node start, Node end) {
        Map<Node, Node> previous = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.equals(end)) break;

            for (Edge edge : edges) {
                if (edge.getFrom().equals(current)) {
                    Node neighbor = edge.getTo();
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        previous.put(neighbor, current);
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

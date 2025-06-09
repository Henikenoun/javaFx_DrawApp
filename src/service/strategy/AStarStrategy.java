package service.strategy;

import model.Node;
import model.Edge;

import java.util.*;

public class AStarStrategy implements AlgorithmStrategy {

    @Override
    public List<Node> findPath(List<Node> nodes, List<Edge> edges, Node start, Node end) {
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparing(fScore::get));

        for (Node node : nodes) {
            gScore.put(node, Double.POSITIVE_INFINITY);
            fScore.put(node, Double.POSITIVE_INFINITY);
        }

        gScore.put(start, 0.0);
        fScore.put(start, heuristic(start, end));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.equals(end)) break;

            for (Edge edge : edges) {
                if (edge.getFrom().equals(current)) {
                    Node neighbor = edge.getTo();
                    double tentativeG = gScore.get(current) + edge.getWeight();

                    if (tentativeG < gScore.get(neighbor)) {
                        previous.put(neighbor, current);
                        gScore.put(neighbor, tentativeG);
                        fScore.put(neighbor, tentativeG + heuristic(neighbor, end));

                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
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

    // Fonction heuristique basée sur la distance euclidienne
    private double heuristic(Node a, Node b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }
}

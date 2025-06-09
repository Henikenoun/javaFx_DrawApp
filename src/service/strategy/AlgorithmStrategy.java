package service.strategy;

import model.Node;
import model.Edge;
import java.util.List;

public interface AlgorithmStrategy {
    List<Node> findPath(List<Node> nodes, List<Edge> edges, Node start, Node end);
}

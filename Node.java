import java.util.ArrayList;
import java.util.List;

/**
 * in each adjacency list, this represents a single row
 */
public class Node {
  int name;
  List<Edge> edges;
  boolean visited = false;

  public Node() {
    edges = new ArrayList<Edge>();
  }
}

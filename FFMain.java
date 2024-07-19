import java.util.Arrays;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;


/**
 * This implements the Ford-Fulkerson Method
 */
public class FFMain {
  public static void main(String[] args) {
    Scanner kb = new Scanner(System.in);
    int numOfInstances = Integer.parseInt(kb.nextLine());
    for (int m = 0; m < numOfInstances; m++) {

      int numOfNodes;
      int numOfEdges;
      {
        String[] line = kb.nextLine().split(" ");
        numOfNodes = Integer.parseInt(line[0]);
        numOfEdges = Integer.parseInt(line[1]);
      }
      Node[] nodes = new Node[numOfNodes];
      nodes = Arrays.stream(nodes).map(e -> new Node()).toArray(size -> new Node[size]);
      for (int i = 0; i < nodes.length; i++) {
        nodes[i].name = i;
      }
      for (int i = 0; i < numOfEdges; i++) {
        String[] line = kb.nextLine().split(" ");
        nodes[Integer.parseInt(line[0]) - 1].edges
            .add(new Edge(nodes[Integer.parseInt(line[0]) - 1],
                nodes[Integer.parseInt(line[1]) - 1], Integer.parseInt(line[2])));
      }

      combineEdges(nodes);
      List<Edge> augmentingPath = getAugmentingPath(nodes);
      // System.out.println(augmentingPath);
      Integer maxFlow = 0;


      while (augmentingPath != null) {
        Integer minCapacity =
            augmentingPath.stream().map(e -> e.capacity).min((e1, e2) -> e1 - e2).get();
        maxFlow += minCapacity;
        // System.out.println(maxFlow);
        // enumerating over each edge in the path
        for (int i = 0; i < augmentingPath.size(); i++) {
          // add an edge in the opposite direction with minCapacity as its capacity or add it to the
          // existing edge in that direction
          boolean found = false;
          for (int j = 0; j < augmentingPath.get(i).end.edges.size(); j++) {
            if (augmentingPath.get(i).end.edges.get(j).end == augmentingPath.get(i).start) {
              augmentingPath.get(i).end.edges.get(j).capacity += minCapacity;
              found = true;
            }
          }
          if (!found) {
            nodes[augmentingPath.get(i).end.name].edges
                .add(new Edge(augmentingPath.get(i).end, augmentingPath.get(i).start, minCapacity));
          }
          // if the edge should be erased
          if (augmentingPath.get(i).capacity == minCapacity) {
            // find the edge within the node its coming from and remove it
            for (int j = 0; j < nodes[augmentingPath.get(i).start.name].edges.size(); j++) {
              if (nodes[augmentingPath.get(i).start.name].edges.get(j).end == augmentingPath
                  .get(i).end) {
                nodes[augmentingPath.get(i).start.name].edges.remove(j);
              }
            }
          }
          // we should reduce the edge by minCapacity
          else {
            augmentingPath.get(i).capacity -= minCapacity;
          }
        }
        augmentingPath = getAugmentingPath(nodes);
      }

      System.out.println(maxFlow);
    }


    kb.close();
  }

  /**
   * 
   * @param nodes
   * @return null if there is no augmenting path and the path if there is one
   */
  public static List<Edge> getAugmentingPath(Node[] nodes) {
    // int[] previous = new int[nodes.length];
    // previous[0] = -1;
    Edge[] previousEdge = new Edge[nodes.length];

    boolean found = false;
    Queue<Node> BFSQueue = new LinkedList<Node>();
    BFSQueue.add(nodes[0]);
    nodes[0].visited = true;

    while (!found && !BFSQueue.isEmpty()) {
      Node curNode = BFSQueue.remove();
      // System.out.println(curNode.name);
      for (int i = 0; i < curNode.edges.size(); i++) {
        if (!curNode.edges.get(i).end.visited) {
          BFSQueue.add(curNode.edges.get(i).end);
          curNode.edges.get(i).end.visited = true;
          // previous[curNode.edges.get(i).end.name] = curNode.name;
          previousEdge[curNode.edges.get(i).end.name] = curNode.edges.get(i);
        }
      }
      found = nodes[nodes.length - 1] == curNode;
    }


    if (!nodes[nodes.length - 1].visited) {
      // System.out.println("returned null");
      Arrays.stream(nodes).forEach(e -> {
        e.visited = false;
      });
      return null;
    }
    Arrays.stream(nodes).forEach(e -> {
      e.visited = false;
    });

    // Backtrace
    List<Edge> returnList = new LinkedList<Edge>();
    Node curNode = nodes[nodes.length - 1];
    while (previousEdge[curNode.name] != null) {
      returnList.add(0, previousEdge[curNode.name]);
      curNode = previousEdge[curNode.name].start;

    }

    return returnList;
  }

  /**
   * this method combines the edges in the multigraph so they're a simple digraph
   */
  public static void combineEdges(Node[] nodes) {
    for (int i = 0; i < nodes.length; i++) {
      nodes[i].edges.sort((e1, e2) -> e1.end.name - e2.end.name);
      for (int j = 0; j < nodes[i].edges.size() - 1; j++) {
        if (nodes[i].edges.get(j).end == nodes[i].edges.get(j + 1).end) {
          nodes[i].edges.get(j).capacity += nodes[i].edges.get(j + 1).capacity;
          nodes[i].edges.remove(j + 1);
          j--;
        }
      }
    }
  }



}

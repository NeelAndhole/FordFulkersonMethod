
public class Edge {
  Node start;
  Node end;
  int capacity;

  public Edge(Node start, Node end, int capacity) {
    this.start = start;
    this.end = end;
    this.capacity = capacity;
  }

  public String toString() {
    return "" + start.name + " " + end.name + " ";
  }
}

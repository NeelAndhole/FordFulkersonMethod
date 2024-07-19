import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FFTester {
  @Test
  public void getAugmentingPathTester() {
    Node[] nodes = new Node[] {new Node(), new Node(), new Node()};
    for (int i = 0; i < nodes.length; i++) {
      nodes[i].name = i;
    }
    nodes[0].edges.add(new Edge(nodes[0], nodes[1], 5));
    nodes[1].edges.add(new Edge(nodes[1], nodes[2], 4));
    System.out.println(FFMain.getAugmentingPath(nodes));


  }



}

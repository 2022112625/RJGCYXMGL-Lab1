import java.util.*;

class FriendshipGraphTest {
    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");

        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);

        graph.addEdge(rachel, ross);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);

        System.out.println(graph.getDistance(rachel, ross)); // 输出：1
        System.out.println(graph.getDistance(rachel, ben));  // 输出：2
        System.out.println(graph.getDistance(rachel, rachel)); // 输出：0
        System.out.println(graph.getDistance(rachel, kramer)); // 输出：-1
    }
}

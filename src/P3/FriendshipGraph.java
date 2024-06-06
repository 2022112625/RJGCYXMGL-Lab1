import java.util.*;

class FriendshipGraph {
    private Map<Person, List<Person>> adjList;

    public FriendshipGraph() {
        adjList = new HashMap<>();
    }

    // 添加顶点
    public void addVertex(Person person) {
        adjList.putIfAbsent(person, new ArrayList<>());
    }

    // 添加边
    public void addEdge(Person person1, Person person2) {
        adjList.get(person1).add(person2);
        adjList.get(person2).add(person1);
    }

    // 计算两人之间的距离
    public int getDistance(Person person1, Person person2) {
        if (!adjList.containsKey(person1) || !adjList.containsKey(person2))
            return -1;

        if (person1.equals(person2))
            return 0;

        Set<Person> visited = new HashSet<>();
        Queue<Person> queue = new LinkedList<>();
        Map<Person, Integer> distanceMap = new HashMap<>();

        queue.offer(person1);
        visited.add(person1);
        distanceMap.put(person1, 0);

        while (!queue.isEmpty()) {
            Person currentPerson = queue.poll();

            for (Person neighbor : adjList.get(currentPerson)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                    distanceMap.put(neighbor, distanceMap.get(currentPerson) + 1);

                    if (neighbor.equals(person2))
                        return distanceMap.get(neighbor);
                }
            }
        }

        return -1; // 未找到路径
    }
}

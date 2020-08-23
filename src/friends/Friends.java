package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

import java.lang.reflect.Array;
import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {

		int source = g.map.get(p1);
		int target = g.map.get(p2);
		boolean[] visited = new boolean[g.members.length];
		Queue<Integer> queue = new Queue<Integer>();
		ArrayList path = bfsmatch(g,source,visited, queue, p2);
		path.add(0, p1);
		System.out.println(path);
		return path;

	}
	private static ArrayList<String> bfsmatch(Graph g, int source, boolean[] visited, Queue<Integer> queue, String p2)
	{

			Map<Person, Person> path = new HashMap<>();

			visited[source] = true;
			queue.enqueue(source);
			while(!queue.isEmpty())
			{
				int i = queue.dequeue();

				for (Friend fr = g.members[i].first; fr != null; fr = fr.next)
				{
					Person previous = g.members[i];
					int fnum = fr.fnum;
					if(!visited[fnum])
					{

						visited[fnum] = true;
						queue.enqueue(fnum);
						path.put(g.members[fnum], previous);
						if(g.members[fr.fnum].name.equals(p2))
						{

							return shortestPath(g,path,g.members[g.map.get(p2)], source);
						}
					}
				}
			}
				return null;
	}
	private static ArrayList<String> shortestPath(Graph g, Map<Person,Person> friendMap, Person target, int source)
	{
		ArrayList<String> path = new ArrayList<>();
		String name = target.name;
		outerloop:
		while(name != null)
		{
			path.add(name);
			
			name = friendMap.get(g.members[g.map.get(name)]).name;

			if(name.equals(g.members[source].name)){
				break outerloop;
			}
		}
		Collections.reverse(path);
		return path;


	}
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		/** COMPLETE THIS METHOD **/
		boolean[] visited = new boolean[g.members.length];
		ArrayList<ArrayList<String>> allCliques = new ArrayList<>();
		
		for(int i = 0; i < g.members.length; i++) {
			Person p = g.members[i];
			if(visited[i] || !p.student) 
				continue;
			
			ArrayList<String> newClique = new ArrayList<>();
			cliqueDFS(g, visited, newClique, school, i);
			
			if(newClique != null && newClique.size() > 0)
				allCliques.add(newClique);
		}
		
		System.out.println(allCliques);
		
		return allCliques;
	}
	
	private static void cliqueDFS(Graph graph, boolean[] visited, 
			ArrayList<String> cliqueMembers, String school, int index) {
		Person person = graph.members[index];
		
		if(!visited[index] && person.student && person.school.equals(school))
			cliqueMembers.add(person.name);
		
		visited[graph.map.get(person.name)] = true;

		Friend curr = graph.members[index].first;
		while(curr != null) {
			int num = curr.fnum;
			Person friendPerson = graph.members[num];
			
			if(visited[num] == false && friendPerson.student
					&& friendPerson.school.equals(school)) {
				cliqueDFS(graph, visited, cliqueMembers, school, num);
			}
			
			curr = curr.next;
		}
		
	}
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		/** COMPLETE THIS METHOD **/
		boolean[] visited = new boolean[g.members.length];
		ArrayList<String> allConnectors = new ArrayList<>();
		HashMap<String, Integer> dfsNums = new HashMap<>();
		HashMap<String, Integer> backNums = new HashMap<>();
		HashSet<String> backedUp = new HashSet<>();
		
		for(int i = 0; i < g.members.length; i++) {
			if(visited[i])
				continue;
			
			connectorDFS(g, visited, allConnectors, new int[] {0,0}, i,
					true, dfsNums, backNums, backedUp);
		}
		
		System.out.println(allConnectors);
		return allConnectors;

		
	}
	private static void connectorDFS(Graph graph, boolean[] visited, 
			ArrayList<String> connectors, int[] nums, int index, boolean startingPoint,
			HashMap<String, Integer> dfsNums, HashMap<String, Integer> backNums,
			HashSet<String> backedUp) {
		Person person = graph.members[index];		
		visited[graph.map.get(person.name)] = true;
		
		dfsNums.put(person.name, nums[0]);
		backNums.put(person.name, nums[1]);

		Friend curr = graph.members[index].first;
		while(curr != null) {
			int personIndex = curr.fnum;
			Person friendPerson = graph.members[personIndex];
			
			if(!visited[personIndex]) {
				nums[0]++;
				nums[1]++;
				
				connectorDFS(graph, visited, connectors, nums, personIndex,
						false, dfsNums, backNums, backedUp);
				
				if(dfsNums.get(person.name) > backNums.get(friendPerson.name)) {
					int minBack = Math.min(backNums.get(person.name), 
							backNums.get(friendPerson.name));
					
					backNums.put(person.name, minBack);
				}
				
				if(dfsNums.get(person.name) <= backNums.get(friendPerson.name)) {
					if(!startingPoint || backedUp.contains(person.name)) {
						if(!connectors.contains(person.name))
							connectors.add(person.name);
					}
				}
				
				backedUp.add(person.name);
				
			} else {
				int minBack = Math.min(backNums.get(person.name), 
						dfsNums.get(friendPerson.name));
				
				backNums.put(person.name, minBack);
			}
			
			curr = curr.next;
		}
		
	}
}


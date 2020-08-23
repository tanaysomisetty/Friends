package friends;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import friends.Friends;
import friends.Graph;

public class FriendsDriver {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		Graph g = null;
		String file = null;
		String method = null;
		ArrayList<String> result = new ArrayList<>();
		String start = null;
		String end = null;
		boolean quit = false;
		String bfs_start = null;
		String school = null;
		ArrayList<ArrayList<String>> resultschool = new ArrayList<>();

		
		while (quit != true) {
			System.out.print("Enter graph input file name, or quit: ");
			file = sc.nextLine();

			if (file.length() != 0) {
				g = new Graph(new Scanner(new File(file)));
			}

			if (file == "quit") {
				quit = true;
			}

			System.out.print("Enter (1) for shortestChain, (2) for cliques, (3) for connectors, (4) for BFS, (5) for adjLists, (6) for DFS ");
			method = sc.nextLine();

			if (method.equals("1")) {
				System.out.println("Enter the person with whom the chain originates: ");
				start = sc.nextLine();
				System.out.println("Enter the person with whom the chain terminates: ");
				end = sc.nextLine();

				result = Friends.shortestChain(g, start, end);
			} else if (method.equals("2")) {
				System.out.println("Enter the school name");
				school = sc.nextLine();
				resultschool = Friends.cliques(g, school);
			} else if (method.equals("3")) {
				result = Friends.connectors(g);
			} /*else if (method.equals("4")) {
				System.out.println();
				System.out.println("---");
				System.out.println("BFS Traversal...");
				System.out.println("---");
				System.out.println();
				
				System.out.println("Enter person to start with: ");
				bfs_start = sc.nextLine();
				
				Friends.bfsDriver(g, bfs_start);
			} else if (method.equals("5")) {
				System.out.println("Adjacency Lists: ");
				
				Friends.printAdjLists(g);
			} else if (method.equals("6")) {
				
				
				Friends.dfsDriver(g);
				
			}*/
			
			if (result == null || result.isEmpty()) {
			
				if (resultschool == null || resultschool.isEmpty()) {
					//System.out.println("Result: Empty");
				} else {
					//System.out.println("Result (School): ");
					//System.out.println("[");
					
					for (int i = 0; i<resultschool.size(); i++) {
						ArrayList<String> temp = resultschool.get(i);
						//System.out.println("\t[");
						for(int j = 0; j<temp.size(); j++) {
							//System.out.println("\t"+temp.get(j));
						}
						//System.out.println("\t]");
						//System.out.println();

					}
					//System.out.println("]");
				}
			} else {
				//System.out.print("Result: ");

				for (int i = 0; i < result.size(); i++) {
					//System.out.print(result.get(i)+" ");
				}
				//System.out.println("");
				
				//System.out.println("]");
			}
		}
		
		sc.close();
	}
	
}

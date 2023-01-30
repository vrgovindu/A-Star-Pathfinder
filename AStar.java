import java.util.*;

public class AStar {
	private static TileNode[] board;
	private static TileNode startTile;
	private static TileNode goalTile;
	private static PriorityQueue<TileNode> frontier;
	private static ArrayList<TileNode> visited;
	private static ArrayList<TileNode> path;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int decision = -1;
		
		// Introduction
		System.out.println("Welcome to A-Star Pathfinder");
		System.out.println("----------------------------");
		System.out.print("\nWould you like to generate a random world? y/n: ");
		String str = scan.nextLine();
		
		if(str.equals("y")) {
			randomizeWorld();
			while(decision != 3) {
				System.out.println("\nSelect Options:");
				System.out.println("\t1. Randomize tiles");
				System.out.println("\t2. Choose player and goal location");
				System.out.println("\t3. Quit program");
				System.out.print("Choose an option by typing the integer: ");
				decision = scan.nextInt();
				
				switch(decision) {
					case 1:
						randomizeWorld();
						break;
					case 2:
						selectLocs();
						System.out.println("A-star will now run to find the shortest path.");
						frontier = new PriorityQueue<TileNode>(new TileNodeComparator());
						visited = new ArrayList<TileNode>();
						path = new ArrayList<TileNode>();
						if(findPath()) {
							System.out.println("Path Exists!");
							printBoard();
							System.out.print("Would you like to animate the path? y/n: ");
							scan.nextLine();
							String str2 = scan.nextLine();
							if(str2.equals("y")) {
								animatePath();
							}
							
						}
						else {
							System.out.println("Path Does Not Exist!");
						}
						break;		
				}
	
			}
		}
	}
	
	public static void printBoard() {
		System.out.println("\n----------------------");
		System.out.println("Current World:");
		
		int[][] coordArray = to2DArray();
		
		for(int i = 14; i >= 0; i--) {
			if(i < 10) {
				System.out.print(i + "  ");
			}
			else {
				System.out.print(i + " ");	
			}
				
			for(int j = 0; j < 15; j++) {
				if(coordArray[j][i] == 0) {
					System.out.print("[ ]");
				}
				else if(coordArray[j][i] == 1){
					System.out.print("[X]");
				}
				else if(coordArray[j][i] == 2){
					System.out.print("[S]");
				}
				else if(coordArray[j][i] == 3) {
					System.out.print("[G]");
				}
				else if(coordArray[j][i] == 4) {
					System.out.print("[*]");
				}
			}
			System.out.println();
		}
		
		System.out.println("    0  1  2  3  4  5  6  7  8  9 10 11 12 13 14");
		
		System.out.println("----------------------");
		System.out.println("LEGEND:");
		System.out.println("[S] = Start Location");
		System.out.println("[G] = Goal Location");
		System.out.println("[X] = Blocked Tile");
		System.out.println("[*] = Path Tile");
		System.out.println("[ ] = Empty Tile");
		System.out.println("----------------------");
	}
	
	public static void printBoard2() {

		int[][] coordArray = to2DArray();
		
		for(int i = 14; i >= 0; i--) {
			if(i < 10) {
				System.out.print(i + "  ");
			}
			else {
				System.out.print(i + " ");	
			}
				
			for(int j = 0; j < 15; j++) {
				if(coordArray[j][i] == 0) {
					System.out.print("[ ]");
				}
				else if(coordArray[j][i] == 1){
					System.out.print("[X]");
				}
				else if(coordArray[j][i] == 2){
					System.out.print("[S]");
				}
				else if(coordArray[j][i] == 3) {
					System.out.print("[G]");
				}
				else if(coordArray[j][i] == 4) {
					System.out.print("[*]");
				}
			}
			System.out.println();
		}
		
		System.out.println("    0  1  2  3  4  5  6  7  8  9 10 11 12 13 14");
	}
	
	//converts TileNode[] array into 2D int array
	public static int[][] to2DArray(){
		int[][] coordArray = new int[15][15];
		
		for(int i = 0; i < board.length; i++) {
			coordArray[board[i].getX()][board[i].getY()] = board[i].getStatus();
		}
		
		return coordArray;
	}
	
	// creates and randomizes new board
	public static void randomizeWorld(){
		board = new TileNode[225];
		for(int i = 0; i < 225; i++) {
			int xCoord = i % 15;
			int yCoord = i / 15;
			board[i] = new TileNode(xCoord, yCoord);
		}
		ArrayList<Integer> numArray = randomNumbers();
		
		for(int i = 0; i < numArray.size(); i++) {
			board[numArray.get(i)].setStatus(1);
		}
		
		printBoard();
	}
	
	// generates 23 unique random numbers
	public static ArrayList<Integer> randomNumbers(){
		Random rand = new Random();
		ArrayList<Integer> numArray = new ArrayList<Integer>();
	
		
		// Generates 23 unique random numbers
		while(numArray.size() < 23) {
			int num = rand.nextInt(225);
			if(!numArray.contains(num)) {
				numArray.add(num);				
			}
		}
		
		return numArray;
	}
	
	// prompts users to select location of
	public static void selectLocs() {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("\nYou will now select the coordinates for the Player and Goal.");
		boolean free = false;
		while(!free) {
			System.out.print("Please enter an integer for the Start tile's x-coordinate (0-14): ");
			int sX = scan.nextInt();
			System.out.print("Please enter an integer for the Start tile's y-coordinate (0-14): ");
			int sY = scan.nextInt();
			System.out.print("Please enter an integer for the Goal tile's x-coordinate (0-14): ");
			int gX = scan.nextInt();
			System.out.print("Please enter an integer for the Goal tile's y-coordinate (0-14): ");
			int gY = scan.nextInt();
			
			
			if(board[sX + (15 * sY)].getStatus() == 1 || board[gX + (15 * gY)].getStatus() == 1) {
				System.out.println("\nOne or more of the locations you selected was not empty, try again.\n");
			}
			else {
				free = true;
				board[sX + (15 * sY)].setStatus(2);
				board[gX + (15 * gY)].setStatus(3);				
				startTile = board[sX + (15 * sY)];
				goalTile = board[gX + (15 * gY)];
				
			}
		}
		
		
		printBoard();
	}
	
	public static boolean findPath() {
		TileNode curr;
		
		startTile.setG(0);
		startTile.setH(Math.abs(startTile.getX() - goalTile.getX()) + Math.abs(startTile.getY() - goalTile.getY()));
		startTile.setF();
		frontier.add(startTile);
		while(!frontier.isEmpty()) {
			curr = frontier.poll();
			visited.add(curr);
			
			if(curr.getX() == goalTile.getX() && curr.getY() == goalTile.getY()) {
				setPath();
				return true;
			}
			
			generateNeighbors(curr);
		}
		
		return false;
	}
	
	public static void setPath() {
		TileNode currTile = goalTile.getParent();
		while(!(currTile.getX() == startTile.getX() && currTile.getY() == startTile.getY())) {
			currTile.setStatus(4);
			path.add(currTile);
			currTile = currTile.getParent();
			
		}
	}
	
	private static class TileNodeComparator implements Comparator<TileNode> {
        @Override
        public int compare(TileNode tile1, TileNode tile2)
        {
            return Integer.compare(tile1.getF(), tile2.getF());
        }

    }
	
	public static boolean isValidIndex(int x, int y){
		if(x < 0 || x > 14 || y < 0 || y > 14) {
			return false;
		}
		return true;
	}
	
	public static void generateNeighbors(TileNode currNode) {
		int currX = currNode.getX();
		int currY = currNode.getY();
		for(int i = currX - 1; i <= currX + 1; i++) {
			for(int j = currY - 1; j <= currY + 1; j++) {
				if(isValidIndex(i, j)) {
					int neighborIndex = i + (15 * j);
					if(!visited.contains(board[neighborIndex]) && board[neighborIndex].getStatus() != 1) {
						board[neighborIndex].setParent(currNode);
						board[neighborIndex].setG(currNode.getG() + 1);
						board[neighborIndex].setH(Math.abs(goalTile.getX() - i) + Math.abs(goalTile.getY() - j));
						board[neighborIndex].setF();
						frontier.add(board[neighborIndex]);
					}
				}
			}
		}
	}
	
	public static void animatePath() {
		System.out.println("Animating Path!");
		printBoard2();
		startTile.setStatus(0);
		for(int i = path.size() - 1; i >= 0; i--) {
			path.get(i).setStatus(2);
			printBoard2();
			path.get(i).setStatus(0);
		}
		goalTile.setStatus(2);
		printBoard2();
			
	}
}



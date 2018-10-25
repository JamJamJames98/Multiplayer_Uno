import java.util.Scanner;
import java.util.StringTokenizer;

public class Uno_Score_Counter {
	public static void main(String[] args) {
		
		System.out.println("WARNING");
		System.out.println("Two player implementation only");
		Scanner sc = new Scanner(System.in);
		System.out.println("Player 1's name:");
		String user1 = sc.nextLine();
		while (user1.length() == 0) {
			System.out.println("Error, please provide a username for player 1");
			System.out.println("Player 1's name:");
			user1 = sc.nextLine();
		}
		int user1_score = 0;
		System.out.println("Player 2's name:");
		String user2 = sc.nextLine();
		while (user2.length() == 0 || user1.equals(user2)) {
			if (user2.length() == 0) {
				System.out.println("Error, please provide a username for player 2");
				System.out.println("Player 2's name:");
				user2 = sc.nextLine();
			} else {
				System.out.println("Error, please use a different name for player 2");
				System.out.println("Player 2's name:");
				user2 = sc.nextLine();
			}
		}
		int user2_score = 0;
		int temp_value = 0;
		String buffer = "";
		System.out.println("Scoreboard Started");
		
		while (true) {
			String input = sc.nextLine();
			if (input.equals("finish") || input.equals("Finish")) {
				System.out.println("Game Finished!");
				System.out.println("Current Scores:");
				System.out.println(user1 + ": " + user1_score);
				System.out.println(user2 + ": " + user2_score);
				if (user1_score < user2_score) {
					System.out.println(user1 + " Wins!");
				} else if (user2_score < user1_score) {
					System.out.println(user2 + " Wins!");
				} else {
					System.out.println("Its a Draw!");
				}
				sc.close();
				System.exit(0);
			}
			if (input.equals("quit") || input.equals("Quit")) {
				sc.close();
				System.exit(0);
			}
			if (input.equals("score") || input.equals("scores") || input.equals("Scores") || input.equals("Score")) {
				System.out.println("Current Scores:");
				System.out.println(user1 + ": " + user1_score);
				System.out.println(user2 + ": " + user2_score);
				continue;
			}
			if (input.equals("help") || input.equals("Help")) {
				System.out.println("Usage:");
				System.out.println("score/scores/Score/Scores: Prints the current scoreboard");
				System.out.println("quit/Quit: Quits the scoreboard");
				System.out.println("help/Help: Shows command usage");
				System.out.println("finish/Finish: Finishes the game and displays winner");
				System.out.println("<user1> <number>: Adds score to the user");
				continue;
			}
			if (input.equals("clear") || input.equals("Clear")) {
				user2_score = 0;
				user1_score = 0;
				System.out.println("Scores Cleared!");
				continue;
			}
			StringTokenizer st = new StringTokenizer(input);
			while (st.hasMoreTokens()) {
				if (st.countTokens() != 2) {
					System.out.println("Invalid Usage!");
					break;
				}
				buffer = st.nextToken();
				if (buffer.equals(user1)) {
					try { 
	    					temp_value = Integer.parseInt(st.nextToken());
	    					if (temp_value < 0) {
	    						System.out.println("Invalid Usage!");
	    						break;
	    					}
	    				} catch (NumberFormatException e) {
	    					System.out.println("Invalid Usage!");
    						break;
	    				} catch (NullPointerException e) {
	    					System.out.println("Invalid Usage!");
    						break;
	    				}
					user1_score = user1_score + temp_value;
				} else if (buffer.equals(user2)) {
					try { 
	    					temp_value = Integer.parseInt(st.nextToken());
	    					if (temp_value < 0) {
	    						System.out.println("Invalid Usage!");
	    						break;
	    					}
	    				} catch (NumberFormatException e) {
	    					System.out.println("Invalid Usage!");
    						break;
	    				} catch (NullPointerException e) {
	    					System.out.println("Invalid Usage!");
    						break;
	    				}
					user2_score = user2_score + temp_value;
				} else {
					System.out.println("Invalid Usage!");
					break;
				}
			}
		}
	}
}

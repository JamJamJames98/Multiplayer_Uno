import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class Uno_instance {
	
	public static Scanner sc = new Scanner(System.in);
	public static String white = "\u001B[1;37m";
	public static String red = "\u001B[1;31m";
	public static String purple = "\u001B[1;35m";
	public static String green = "\u001B[1;32m";
	public static String yellow = "\u001B[1;33m";
	public static String blue = "\u001B[1;34m";
	public static String black = "\u001B[1;30m";
	public static String default_setting = "\u001B[0m";
	public static String[][] source_cards = new String [15][2];
	public static String[][] deck = new String[108][3];
	public static String[][] blue_deck = new String[27][3];
	public static String[][] red_deck = new String[27][3];
	public static String[][] yellow_deck = new String[27][3];
	public static String[][] green_deck = new String[27][3];
	public static String[][] curr_card = new String[1][3];
	public static Doubly_Linked_List players = new Doubly_Linked_List();
	public static int player_amount = 0;
	public static int hand_index = 0;
	public static boolean correct_play = false;
	public static int card_index_counter = 1;
	public static String[][] played_card = new String[1][3];
	public static String[][] prev_played = new String[1][3];
	public static String card_index = "";
	public static boolean passed = false;
	public static boolean played = false;
	public static boolean active_plus = false;
	public static int plus_count = 0;
	public static int skip_counter = 0;
	public static String plus_owner = "";
	public static int reverse_counter = 0;
	
	public static void help() {
		
		System.out.println("Currently not implemented");
	}
	
	public static void error() {
		
		System.out.println("Incorrect Usage");
		
	}
	
	public static void quit() {
		
		System.out.println("Goodbye!");
		System.exit(0);
		
	}	

	public static void test_colours() {
		
		System.out.println(red + "Hello world" + white);
		System.out.println(green + "Hello world" + white);
		System.out.println(yellow + "Hello world" + white);
		System.out.println(blue + "Hello world" + white);
		System.out.println(black + "Hello world" + white);
		System.out.println("This should be white");
		System.out.println(default_setting);
		
	}
	
	public static void initialise_source_cards() {
		
		source_cards[0][0] = "zero";
		source_cards[1][0] = "one";
		source_cards[2][0] = "two";
		source_cards[3][0] = "three";
		source_cards[4][0] = "four";
		source_cards[5][0] = "five";
		source_cards[6][0] = "six";
		source_cards[7][0] = "seven";
		source_cards[8][0] = "eight";
		source_cards[9][0] = "nine";
		for (int i = 0; i < 10; i++) {
			source_cards[i][1] = Integer.toString(i);
		}
		source_cards[10][0] = "skip";
		source_cards[10][1] = "S";
		source_cards[11][0] = "reverse";
		source_cards[11][1] = "R";
		source_cards[12][0] = "plus_two";
		source_cards[12][1] = "+2";
		source_cards[13][0] = "wild_card";
		source_cards[13][1] = "W";
		source_cards[14][0] = "plus_four";
		source_cards[14][1] = "+4";
		
	}
	
	public static void create_component_deck(String[][] deck, String colour) {
		
		for (int i = 0; i < 27; i++) {
			if (i < 10) {
				deck[i][0] = source_cards[i][0];
				deck[i][1] = source_cards[i][1];
				deck[i][2] = colour;
			}
			if (i >= 10 && i < 19) {
				deck[i][0] = source_cards[i-9][0];
				deck[i][1] = source_cards[i-9][1];
				deck[i][2] = colour;
			}
			if (i >= 19 && i < 21) {
				deck[i][0] = source_cards[10][0];
				deck[i][1] = source_cards[10][1];
				deck[i][2] = colour;
			}
			if (i >= 21 && i < 23) {
				deck[i][0] = source_cards[11][0];
				deck[i][1] = source_cards[11][1];
				deck[i][2] = colour;
			}
			if (i >= 23 && i < 25) {
				deck[i][0] = source_cards[12][0];
				deck[i][1] = source_cards[12][1];
				deck[i][2] = colour;
			}
			if (i == 25) {
				deck[i][0] = source_cards[13][0];
				deck[i][1] = source_cards[13][1];
				deck[i][2] = black;
			}
			if (i == 26) {
				deck[i][0] = source_cards[14][0];
				deck[i][1] = source_cards[14][1];
				deck[i][2] = black;
			}
		}
		
	}
	
	public static void create_main_deck() {
		
		create_component_deck(blue_deck, blue);
		create_component_deck(red_deck, red);
		create_component_deck(yellow_deck, yellow);
		create_component_deck(green_deck, green);
		deck = merge_deck(merge_deck(blue_deck, red_deck), merge_deck(yellow_deck, green_deck));
		
	}
	
	public static void print_deck(String[][] deck) {
		
		for (int i = 0; i < deck.length; i++) {
			if (deck[i][0] != null && deck[i][1] != null && deck[i][2] != null) {
				System.out.print(deck[i][2] + deck[i][1] + " ");
			}
		}
		System.out.println(default_setting);
		
	}
		
	public static String[][] merge_deck(String[][] deck1, String[][] deck2) {
		
		return Stream.concat(Arrays.stream(deck1), Arrays.stream(deck2)).toArray(String[][]::new);
		
	}
	
	public static void shuffle_main_deck() {
		
		Random random = new Random();
		int shuffle_amount = random.nextInt(50) + 100;
		for (int i = 0; i < shuffle_amount; i++) {
			Collections.shuffle(Arrays.asList(deck));
		}
		
	}
	
	public static void create_player_decks() {
		
		Node curr = players.getHead();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < players.getLength(); j++) {
				curr.getCards()[i] = deck[(4*i)+j];
				deck[(4*i)+j] = new String[3];
				curr = curr.getNext();
			}
		}
		
	}
	
	public static void print_player_decks() {
		
		Node curr = players.getHead();
		for (int i = 0; i < players.getLength(); i++) {
			System.out.println("Player " + (i+1) + "'s deck (" + curr.getName() + ")");
			print_deck(curr.getCards());
			curr = curr.getNext();
		}
		
	}
	
	public static void find_player_amount() {
		
		while (true) {
			System.out.println("How many players are playing today?");
			String player_count = sc.nextLine();
			try { 
				player_amount = Integer.parseInt(player_count);
	 		} catch (NumberFormatException e) {
	 			System.out.println("Error, please input an integer");
	 			continue;
	 		} catch (NullPointerException e) {
	 			System.out.println("Error, please input an integer");
	 			continue;
	 		}
			if (player_amount < 2) {
				System.out.println("Error, please provide a number above 1");
			} else if (player_amount > 4) {
				System.out.println("Unsupported player amount, please provide a number below 5");
			} else {
				break;
			}
		}
		System.out.println("Number of players is " + player_amount);
		
	}
	
	public static void create_player_names() {
		
		for (int i = 0; i < player_amount; i++) {
			System.out.println("Please provide player " + (i+1) + "'s name:");
			String user = sc.nextLine();
			while (user.length() == 0) {
				System.out.println("Error, please provide a username for player " + (i+1));
				System.out.println("Player " + (i+1) + "'s name:");
				user = sc.nextLine();
			}
			if (players.checkSameName(user) == true) {
				System.out.println("Error, please provide a unique username for player " + (i+1));
				i = i - 1;
				continue;
			}
			if (i == 0) {
				players.getHead().setName(user);
				players.getHead().setNext(players.getHead());
				players.getHead().setPrev(players.getHead());
			} else {
				players.append(user);
			}
			
		}
		
	}
	
	public static void draw_first_card() {
		
		for (int i = 0; i < deck.length; i++) {
			if (deck[i][0] != null && deck[i][1] != null && deck[i][2] != null) {
				if (deck[i][1].length() == 1) {
					try {
						Integer.parseInt(deck[i][1]);
						//System.out.println("We know its an integer");
						curr_card[0] = deck[i];
						deck[i] = new String[3];
						break;
						
					} catch (NumberFormatException e) {
			 			//System.out.println("We know its a string");
			 		}
				} else {
					//System.out.println("We know its a string");
				}
			}
		}
		
	}
	
	public static void show_hand(Node curr)  {
		
		System.out.println("Your Cards:");
		print_deck(curr.getCards());
		
	}
	
	public static void show_curr_card() {
		
		System.out.println("Current Card:");
		print_deck(curr_card);
		
	}
	
	public static void check_for_winner() {
		
		int player_cards = 0;
		Node curr = players.getHead();
		for (int i = 0; i < players.getLength(); i++) {
			for (int j = 0; j < curr.getCards().length; j++) {
				if (curr.getCards()[j][0] != null && curr.getCards()[j][1] != null && curr.getCards()[j][2] != null) {
					player_cards++;
				}
			}
			if (player_cards == 0) {
				System.out.println("Player " + (i+1) + " Wins! (" + curr.getName() + ")");
				System.exit(0);
			}
			player_cards = 0;
			curr = curr.getNext();
		}
		
	}
	
	public static void prepare_turn(Node curr) {
		
		hand_index = 0;
		correct_play = false;
		System.out.println("Player " + (curr.getIndex()+1) + "'s turn! (" + curr.getName() + ")");
		card_index_counter = 1;
		played_card = new String[1][3];
		
		
	}
	
	public static void draw_card(Node curr) {
		
		for (int i = 0; i < deck.length; i++) {
			if (deck[i][0] != null && deck[i][1] != null && deck[i][2] != null) {
				for (int j = 0; j < curr.getCards().length; j++) {
					if (curr.getCards()[j][0] == null && curr.getCards()[j][1] == null && curr.getCards()[j][2] == null) {
						curr.getCards()[j] = deck[i];
						System.out.println("You drew:");
						System.out.println(deck[i][2] + deck[i][1] + default_setting);
						show_hand(curr);
						deck[i] = new String[3];
						break;
					}
				}
				break;
			}
		}
		
	}
	
	public static void draw_card_multiple(Node curr, int amount) {
		
		String[] draw_details = new String [amount];
		for (int a = 0; a < amount; a++) {
			for (int i = 0; i < deck.length; i++) {
				if (deck[i][0] != null && deck[i][1] != null && deck[i][2] != null) {
					for (int j = 0; j < curr.getCards().length; j++) {
						if (curr.getCards()[j][0] == null && curr.getCards()[j][1] == null && curr.getCards()[j][2] == null) {
							curr.getCards()[j] = deck[i];
							draw_details[a] = deck[i][2] + deck[i][1] + default_setting;
							deck[i] = new String[3];
							break;
						}
					}
					break;
				}
			}
		}
		System.out.println("Plus " + amount);
		System.out.println("You drew:");
		for (int i = 0; i < amount; i++) {
			System.out.println(draw_details[i]);
		}
		show_hand(curr);
		
	}
	
	public static boolean check_index() {
		
		try { 
			hand_index = Integer.parseInt(card_index);
 		} catch (NumberFormatException e) {
 			System.out.println("Error, please play a valid card");
 			System.out.println("You cannot pass twice");
 			System.out.println("You cannot pass after playing");
 			System.out.println("You cannot end without playing OR passing first");
 			System.out.println("SPECIAL CASE");
 			System.out.println("You cannot pass with an active plus, you MUST end");
 			System.out.println();
 			System.out.println();
 			return false;
 		} catch (NullPointerException e) {
 			System.out.println("Error, please play a valid card");
 			System.out.println("You cannot pass twice");
 			System.out.println("You cannot pass after playing");
 			System.out.println("You cannot end without playing OR passing first");
 			System.out.println("SPECIAL CASE");
 			System.out.println("You cannoy pass with an active plus, you MUST end");
 			System.out.println();
 			System.out.println();
 			return false;
 		}
		if (hand_index < 0) {
			System.out.println("Error, please provide a number above 0");
			System.out.println();
			System.out.println();
			return false;
		}
		return true;
		
	}
	
	public static boolean check_pass(Node curr) {
		
		System.out.println("Play a card number or pass or end");
		card_index = sc.nextLine();
		if (card_index.equals("pass")) {
			if (passed == false && played == false && active_plus == false) {
				passed = true;
				draw_card(curr);
				return true;
			}
		}
		if (card_index.equals("end")) {
			if ((passed == true || played == true) || active_plus == true) {
				return true;
			}
		}
		return false;
		
	}
	
	public static boolean turn_over() {
		
		while (true) {
			System.out.println("Is your turn finished?");
			System.out.println("y \\ n");
			String player_count = sc.nextLine();
			if (player_count.equals("y")) {
				return true;
			}
			if (player_count.equals("n")) {
				return false;
			}
		}
		
	}
	
	public static void reverse() {
		
		Node curr = players.getHead();
		Node temp = null;
		for (int i = 0; i < players.getLength(); i++) {
			temp = curr.getPrev();
			curr.setPrev(curr.getNext());
			curr.setNext(temp);
			curr = curr.getPrev();
		}
		
	}
	
	public static void play_card(Node curr) {
		
		for (int i = 0; i < curr.getCards().length; i++) {
			if (curr.getCards()[i][0] != null && curr.getCards()[i][1] != null && curr.getCards()[i][2] != null) {
				
				//System.out.println("ITERATED INDEX COUNTER");
				//THIS IS THE CORRECT CARD INDEX - TIME TO PLAY OUT THE TURN
				
				if (card_index_counter == hand_index) {
					
					//reset the index for future calculations
					card_index_counter = 1;
					//this is the card the user refered to
					//now save it and check if it can be played.
					played_card[0] = curr.getCards()[i];
					
					System.out.println("Selected Card:");
					System.out.println(played_card[0][2] + played_card[0][1] + default_setting);
					
					//check if int or string
					boolean is_int = false;
					if (played_card[0][1].length() == 1) {
						try {
							Integer.parseInt(played_card[0][1]);
							//System.out.println("We know its an integer");
							is_int = true;
						} catch (NumberFormatException e) {
				 			//System.out.println("We know its a string");
				 			is_int = false;
				 		}
					} else {
						//System.out.println("We know its a string");
			 			is_int = false;
					}
					
					if (played_card[0][2].equals(black) && played == false) {
						//this refers to playing the black cards!
						
						//wildcard
						if (played_card[0][1].equals("W") && active_plus == false) {
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							while (true) {
								System.out.println("Which colour will you select?");
								System.out.println("r \\ g \\ b \\ y");
								String changed_colour = sc.nextLine();
								if (changed_colour.equals("r")) {
									curr_card[0][0] = "red";
									curr_card[0][1] = "W";
									curr_card[0][2] = red;
									break;
								}
								if (changed_colour.equals("g")) {
									curr_card[0][0] = "green";
									curr_card[0][1] = "W";
									curr_card[0][2] = green;
									break;
								}
								if (changed_colour.equals("b")) {
									curr_card[0][0] = "blue";
									curr_card[0][1] = "W";
									curr_card[0][2] = blue;
									break;
								}
								if (changed_colour.equals("y")) {
									curr_card[0][0] = "yellow";
									curr_card[0][1] = "W";
									curr_card[0][2] = yellow;
									break;
								}
							}
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						
						//plus4
						if (played_card[0][1].equals("+4")) {
							plus_owner = curr.getName();
							active_plus = true;
							plus_count = plus_count + 4;
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							while (true) {
								System.out.println("Which colour will you select?");
								System.out.println("r \\ g \\ b \\ y");
								String changed_colour = sc.nextLine();
								if (changed_colour.equals("r")) {
									curr_card[0][0] = "plus_4";
									curr_card[0][1] = "+4";
									curr_card[0][2] = red;
									break;
								}
								if (changed_colour.equals("g")) {
									curr_card[0][0] = "plus_4";
									curr_card[0][1] = "+4";
									curr_card[0][2] = green;
									break;
								}
								if (changed_colour.equals("b")) {
									curr_card[0][0] = "plus_4";
									curr_card[0][1] = "+4";
									curr_card[0][2] = blue;
									break;
								}
								if (changed_colour.equals("y")) {
									curr_card[0][0] = "plus_4";
									curr_card[0][1] = "+4";
									curr_card[0][2] = yellow;
									break;
								}
							}
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}

					} else if (played_card[0][2].equals(curr_card[0][2]) && played == false) {
						//this refers to the colour of the card
						//this covers playing a number of same colour
						
						//number
						if (is_int == true && active_plus == false) {
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							curr_card[0] = played_card[0];
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						
						//skip
						if (played_card[0][1].equals("S") && active_plus == false) {
							skip_counter++;
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							curr_card[0] = played_card[0];
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						
						//reverse
						if (played_card[0][1].equals("R") && active_plus == false) {
							reverse_counter++;
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							curr_card[0] = played_card[0];
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						
						//plus2
						if (played_card[0][1].equals("+2")) {
							plus_owner = curr.getName();
							active_plus = true;
							plus_count = plus_count + 2;
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							curr_card[0] = played_card[0];
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						
					} else if (played_card[0][1].equals(curr_card[0][1]) || played_card[0][1].charAt(0) == curr_card[0][1].charAt(0)) {
						//this refers to the type of card i.e. 2 2 or S S
						//or the first part i.e. +2 +4, +4 +2
						//this covers playing a number the same value but different colour
						
						//number
						if (is_int == true && active_plus == false) {
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							curr_card[0] = played_card[0];
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						//skip
						if (played_card[0][1].equals("S") && active_plus == false) {
							skip_counter++;
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							curr_card[0] = played_card[0];
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						
						//reverse
						if (played_card[0][1].equals("R") && active_plus == false) {
							reverse_counter++;
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							curr_card[0] = played_card[0];
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						
						//plus2
						if (played_card[0][1].equals("+2")) {
							plus_owner = curr.getName();
							active_plus = true;
							plus_count = plus_count + 2;
							System.out.println("Valid Move!");
							System.out.println();
							System.out.println();
							curr_card[0] = played_card[0];
							curr.getCards()[i] = new String [3];
							correct_play = true;
							break;
						}
						
					}
					break;
				}
				
				card_index_counter++;
			}
		}
		
	}
	
	public static void excecute_turn(Node curr) {
		
		while (true) {
			
			card_index_counter = 1;
			show_curr_card();
			show_hand(curr);
			
			if (check_pass(curr)) {
				break;
			}
			
			if (!check_index()) {
				continue;
			}
			
			
			play_card(curr);
			
			
			if (correct_play == true) {
				played = true;
				correct_play = false;
				break;
			} else {
				System.out.println("Error, please play a valid card");
				System.out.println();
				System.out.println();
			}
		}
		
	}
	
	public static void end_plus_chain(Node curr) {
		
		if ((active_plus == true) && (curr.getName().equals(plus_owner) == false)) {
			//plus was not countered and player must draw
			draw_card_multiple(curr, plus_count);
			plus_owner = "";
			active_plus = false;
			plus_count = 0;
		}
		played_card = new String[1][3];
		passed = false;
		played = false;
		
	}
		
	public static void main(String[] args) {
		
		initialise_source_cards();
		create_main_deck();
		shuffle_main_deck();
		
		find_player_amount();
		create_player_names();

		create_player_decks();
		//print_player_decks();
		
		Node curr = players.getHead();
		
		draw_first_card();
		while (true) {
			
			check_for_winner();
			prepare_turn(curr);
			excecute_turn(curr);	

			if (card_index.equals("end")) {
				end_plus_chain(curr);
				curr = curr.getNext();
				if (players.getLength() == 2) {
					//sort out any skips
					for (int i = 0; i < (skip_counter*2)-1; i++) {
						curr = curr.getNext();
					}
					//sort out any reverses
					for (int i = 0; i < (reverse_counter*2)-1; i++) {
						curr = curr.getNext();
					}
				} else {
					//sort out any skips
					for (int i = 0; i < skip_counter; i++) {
						curr = curr.getNext();
					}
					//sort out any reverses
					for (int i = 0; i < reverse_counter; i++) {
						reverse();
					}
				}
				reverse_counter = 0;
				skip_counter = 0;
			}
			else if (turn_over() == false) {
				continue;
			} else {
				end_plus_chain(curr);
				curr = curr.getNext();
				if (players.getLength() == 2) {
					//sort out any skips
					for (int i = 0; i < (skip_counter*2)-1; i++) {
						curr = curr.getNext();
					}
					//sort out any reverses
					for (int i = 0; i < (reverse_counter*2)-1; i++) {
						curr = curr.getNext();
					}
				} else {
					//sort out any skips
					for (int i = 0; i < skip_counter; i++) {
						curr = curr.getNext();
					}
					//sort out any reverses
					for (int i = 0; i < reverse_counter; i++) {
						reverse();
					}
				}
				reverse_counter = 0;
				skip_counter = 0;
			}
		}
	}
}

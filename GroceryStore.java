package groceryStore;
/*
 * ---
 * ICS3U ~ December 22, 2024
 * = Grocery Store =
 */

// https://patorjk.com/software/taag/#p=display&f=The%20Edge&t=Fresh%20Foods - title
// https://github.com/djmdjm/jBCrypt
// https://www.w3schools.com/java/java_ref_string.asp
// https://www.w3schools.com/java/ref_string_format.asp
// https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java

import java.util.*;
import java.io.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.regex.Pattern;
public class GroceryStore {
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);

		String fileNames[] = { "users.txt", "produce.txt", "packagedFoods.txt", "bakery.txt", "beverages.txt", "meat.txt", "homeEssentials.txt" };
		String items[][] = {
				// Produce
				{ "apple", "carrot", "strawberry", "cucumber", "blueberries", "avocado", "banana", "blackberries", "grapefruit", "orange" },
				// Packaged foods
				{ "cereal", "frozen vegetables", "processed meat", "lays", "doritos", "crisps", "pasta", "rice", "canned beans", "soup" },
				// bakery
				{ "bread", "croissant", "bagels", "muffins", "cookies", "cake", "pie", "donuts", "scones", "buns" },
				// drinks
				{ "water", "orange juice", "coffee", "apple juice", "tea", "milk", "mountain dew", "lemonade", "sprite", "pepsi" },
				// meat
				{ "chicken", "beef", "goat", "lamb", "turkey", "fish", "shrimp", "sausages", "steak", "ribs" },
				// essentials
				{ "toothpaste", "toilet paper", "soap", "shampoo", "detergent", "trash bags", "dish soap", "paper towels", "hand sanitizer", "cleaning spray" }
		};
		double prices[][] = {
				{ 0.99, 1.29, 2.49, 1.59, 3.99, 1.49, 0.59, 3.49, 2.99, 1.69 }, // produce
				{ 3.49, 2.99, 4.99, 2.49, 1.99, 2.79, 1.49, 0.99, 0.89, 1.69 }, // Packaged Foods
				{ 2.49, 1.99, 1.59, 2.99, 1.79, 3.99, 4.49, 1.99, 2.29, 1.69 }, // Bakery
				{ 1.99, 2.49, 3.49, 2.99, 1.89, 1.49, 2.99, 1.29, 5.99, 4.99 }, // Drinks
				{ 4.99, 5.49, 3.99, 6.99, 5.79, 7.99, 3.89, 4.59, 6.49, 8.99 }, // Meat
				{ 2.49, 3.29, 2.99, 6.49, 5.99, 1.99, 2.79, 2.29, 2.99, 3.19 } // essentials
		};
		for(int i=0;i<fileNames.length;i++) {
			File file = new File(fileNames[i]);
			if (!file.exists()) {
				file.createNewFile();
			}
			if(file.length() == 0 && !fileNames[i].equals("users.txt")) {
				PrintWriter print = new PrintWriter(file);
				for (int x = 0; x < items[i-1].length; x++) {
					String name = items[i-1][x];
					double price = prices[i-1][x];
					int quantity = generateInt(15,60);
					print.println(name + ", " + quantity + ", " + price);
				}
				print.close();
			} else if(file.length() == 0 && fileNames [i].equals("users.txt")) {
				PrintWriter print = new PrintWriter(file); // example users:
				print.print("exponentLoki,"
						+ "abcde@gmail.com,"
						+ "$2a$10$.H0hQTsoS0YMel2VcM7jZ.Bs7cT8/nJ0am2cW5wzuB2zBfDLEFIqi,"
						+ "07/11/2008,"
						+ "banana-apple-grapefruit,"
						+ "What is my favourite sport?,"
						+ "football,"
						+ "true\n");
				print.print("Bob,"
						+ "Bob@gmail.com,"
						+ "$2a$10$.H0hQTsoS0YMel2VcM7jZ.Bs7cT8/nJ0am2cW5wzuB2zBfDLEFIq3i,"
						+ "11/07/2004,"
						+ "banana,"
						+ "What is my favourite car?,"
						+ "toyota,"
						+ "false\n");
				print.close();
			}
		}

		System.out.println();
		title();
		wait(1000);
		System.out.print("\n\n\t\t\t   Welcome to Fresh Foods.");

		System.out.println("\n\n  You are able to exit the program at any moment by typing 'exit'.");
		wait(700);
		while (true){
			System.out.println("  Would you Like to Login or create an Account? ('login' | 'create')");
			wait(900);
			arrow();
			String choice = input.nextLine();

			switch(choice.toLowerCase()){
			case "login":{
				subTitle("Login");
				for(;;){
					System.out.println("\n\n  Please Enter Your Username");
					arrow();
					String username = input.nextLine();
					checkExit(username);
					System.out.println("\n  Please Enter Your Password");
					arrow();
					String password = input.nextLine();
					checkExit(password);

					String users[][] = readFile(fileNames[0]);
					String mainUser[] = new String[0];

					boolean success = false;

					for (String[] user : users) {
						if (user[0].equals(username) && checkHash(password, user[2])) {
							success = true;
							mainUser = user;
							System.out.println("\n  Login successful!");
							wait(1200);
							System.out.println("\n\n\n\n"); // unfortunetly no easy way to clear the console so this is the best option.
							wait(200);
							title();
							System.out.println();
							wait(600);
							System.out.println("\n\n  Welcome, "+ username + "!");
						}
					}
					if (!success) {
						System.out.println("\n  Invalid username or password. Would you like to reset your password? ( y / n )");
						arrow();
						String ans = input.nextLine();
						checkExit(ans);

						if (ans.equalsIgnoreCase("y") ||ans.equalsIgnoreCase("yes")) {
							subTitle("Reset Password");
							for(;;) {
								System.out.println("\n\n  Please Enter Your Username.");
								arrow();
								String reset_Username = input.nextLine();
								checkExit(reset_Username);
								System.out.println("\n  Please Enter Your Email Address.");
								arrow();
								String reset_Email = input.nextLine();
								checkExit(reset_Email);
								boolean found = false;

								String[] userFound = new String[1];
								int userFoundIndex = -1;

								for (int i = 0; i < users.length; i++) {
									if (users[i][0].equalsIgnoreCase(reset_Username) && users[i][1].equals(reset_Email)) {
										found = true;
										userFound = users[i];
										userFoundIndex = i;
										break;
									}
								}
								if (found) {
									System.out.println("\n  Correct! Now we will ask a security question that you had setup.");
									System.out.println("\n  "+ userFound[5]); // security question
									arrow();
									String reset_ans = input.nextLine();
									if (reset_ans.equals(userFound[6])) { // security questions answer
										wait(500);
										System.out.println("\n  Correct! Please enter a new password...");
										arrow();
										String newPassword = input.nextLine();
										checkExit(newPassword);
										String hashedPassword = hash(newPassword);
										updateFile(fileNames[0],userFoundIndex, 2, hashedPassword );
										System.out.println("\n  Successfully changed password to : " + newPassword);
										System.out.println("  Please Login With your new password...");
										subTitle("Login");
										break;
									} else {
										System.out.println("\n  Incorrect! Please try again or type 'exit' to quit.");
									}
								} else {
									System.out.println("\n  Invalid username or Email. Try again!");
								}
							}
						} else {
							System.out.print("\n  Okay, please try to login again.");
						}
					} else {
						if(mainUser[7].equalsIgnoreCase("true")) { // staff member
							for(;;) {
								System.out.println("\n  Would you like to Shop or Manage?");
								arrow();
								String staff_choice = input.nextLine();
								if(staff_choice.equalsIgnoreCase("shop") || staff_choice.equalsIgnoreCase("buy")) {
									shop(true, mainUser);
									input.close();
								} else if (staff_choice.equalsIgnoreCase("manage store") || staff_choice.equalsIgnoreCase("manage")) {
									input.close();
									// manageStore(); wasnt able to get to this.
								} else {
									System.out.println("\n  Please enter a valid option");
								}
							}
						} else { // regular member
							shop(false, mainUser);
							input.close();
						}

						break;
					}

				}
			}
			break;
			case "create":
			case "create an account":{
				String newUsername, newEmail, newPassword, newBirthday, newFavFoods, newSecurityQuestion, newSecurityAnswer;

				subTitle("Create Account");
				System.out.println();

				for(;;) {
					System.out.println("\n  Create a Username. ");
					arrow();
					newUsername = input.nextLine();
					checkExit(newUsername);
					if (newUsername.trim().isEmpty() || newUsername.length() > 20) {
						System.out.println("\n  Please enter a valid Username!");
						continue;
					}
					break;
				}
				for(;;){
					System.out.println("\n  Enter Your Email Address. ");
					arrow();
					newEmail = input.nextLine();
					checkExit(newEmail);
					Pattern reg = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
					Boolean match = reg.matcher(newEmail).matches();
					if (newEmail.trim().isEmpty() || newEmail.length() > 40 || !match) {
						System.out.println("\n  Please enter a valid Email Address!");
						continue;
					}
					break;
				}
				for(;;){
					System.out.println("\n  Create a Password. ");
					arrow();
					newPassword = input.nextLine();
					checkExit(newPassword);
					if (newPassword.trim().isEmpty() || newPassword.length() > 32) {
						System.out.println("\n  Please enter a valid Username!");
						continue;
					} else {
						newPassword = hash(newPassword);
					}
					break;
				};
				for(;;){
					System.out.println("\n  Enter Your Birthday (mm/dd/yyyy). ");
					arrow();
					newBirthday = input.nextLine();
					checkExit(newBirthday);
					Pattern reg = Pattern.compile("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$");
					Boolean match = reg.matcher(newBirthday).matches();
					if (newBirthday.trim().isEmpty() || newBirthday.length() > 32 || !match) {
						System.out.println("\n  Please enter a valid Birthday!");
						continue;
					}
					break;
				}
				for(;;){
					System.out.println("\n  Enter Some of your favourite foods. Seperate them with '-'. ");
					arrow();
					newFavFoods = input.nextLine();
					checkExit(newFavFoods);
					Pattern reg = Pattern.compile ("^[a-zA-Z]+(-[a-zA-Z]+)*$", Pattern.CASE_INSENSITIVE);
					Boolean match = reg.matcher(newFavFoods).matches();
					if ( newFavFoods.trim().isEmpty() || newFavFoods.length() > 55 || !match) {
						System.out.println("\n  Please enter a valid foods string!");
						continue;
					}
					break;
				}
				for(;;){
					System.out.println("\n  Please enter a security question. We will use this question, for when we need to verify. ");
					arrow();
					newSecurityQuestion = input.nextLine();
					checkExit(newSecurityQuestion);
					if (newSecurityQuestion.trim().isEmpty() || newSecurityQuestion.length() > 55) {
						System.out.println("\n  Please enter a valid security question!");
						continue;
					}
					break;
				}
				for(;;){
					System.out.println("\n  Please enter the answer to your security question. Make sure to keep this answer saved somewhere! ");
					arrow();
					newSecurityAnswer = input.nextLine();
					checkExit(newSecurityAnswer);
					if (newSecurityAnswer.trim().isEmpty() || newSecurityAnswer.length() > 40) {
						System.out.println("\n  Please enter a valid security Answer!");
						continue;
					}
					break;
				}
				FileWriter usersFile = new FileWriter(fileNames[0], true);
				usersFile.write(
						newUsername + ","+
						newEmail + "," +
						newPassword + "," +
						newBirthday + "," +
						newFavFoods + "," +
						newSecurityQuestion + "," +
						newSecurityAnswer + ",false\n"
				);
				usersFile.close();
				System.out.println("\n  Successfully created an account!");
				System.out.println("\n  You are able to login now!\n");
			}
			break;
			case "exit":
				checkExit("exit");
			default:
				System.out.println("\n  Please Enter A Valid Option\n");
			}
		}
	}
	
	
				// methods/functions \\

	/*
	 * Purpose: generates a randomized integer
	 * Pre: int start,int end
	 * Post: int
	 */
	public static int generateInt(int start, int end){
		return (int)(Math.random()*(end-start+1)+start);
	}
	/*
	 * Purpose: pauses the program forthe amount of ms given
	 * Pre: int ms (short for milliseconds)
	 * Post: none
	 */
	public static void wait(int ms){ // https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
		try {
			Thread.sleep(ms);
		}
		catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		}
	}
	/*
	 * Purpose: displays a title screen
	 * Pre: none
	 * Post: none
	 */
	public static void title(){
		System.out.print(
				"\n    ▄████  █▄▄▄▄ ▄███▄     ▄▄▄▄▄    ▄  █     ▄████  ████▄ ████▄ ██▄      ▄▄▄▄▄   "
						+"\n    █▀   ▀ █  ▄▀ █▀   ▀   █     ▀▄ █   █     █▀   ▀ █   █ █   █ █  █    █     ▀▄ "
						+"\n    █▀▀    █▀▀▌  ██▄▄   ▄  ▀▀▀▀▄   ██▀▀█     █▀▀    █   █ █   █ █   █ ▄  ▀▀▀▀▄   "
						+"\n    █      █  █  █▄   ▄▀ ▀▄▄▄▄▀    █   █     █      ▀████ ▀████ █  █   ▀▄▄▄▄▀    "
						+"\n     █       █   ▀███▀                █       █                 ███▀             "
						+"\n      ▀     ▀                        ▀         ▀                                 ");
	}
	/*
	 * Purpose: displays an arrow
	 * Pre: none
	 * Post: none
	 */
	public static void arrow(){
		System.out.print("\n   > ");
	}
	/*
	 * Purpose: hashes a password that is given to it.
	 * Pre: String unHashedPassword
	 * Post: String
	 */
	public static String hash(String unHashedPassword){ // https://github.com/djmdjm/jBCrypt
		return BCrypt.hashpw(unHashedPassword, BCrypt.gensalt());
	}
	/*
	 * Purpose: checks the plain text and the given hash to see if they match
	 * Pre:  String plain,String hash
	 * Post: boolean
	 */
	public static boolean checkHash(String plain,String hash){
		return BCrypt.checkpw(plain, hash); // returns true or false.
	}
	/*
	 * purpose: exits the program if choice is exit.
	 * Pre: String choice
	 * Post: none
	 */
	public static void checkExit(String choice){
		if(choice.equalsIgnoreCase("exit")){
			System.out.println("\n\n Thank you for shopping!");
			wait(600);
			System.out.println(" Come back again!");
			System.exit(0);
		}
	}
	/*
	 * Purpose: prints a formated sub title.
	 * Pre: String title
	 * Post: none
	 */
	public static void subTitle(String title) {
		String formatedTitle = String.format("\n\t\t\t     - %s -", title);
		System.out.print(formatedTitle);
	}
	/*
	 * Purpose: reads the given file and constructs a 2-D array which it returns for use
	 * Pre: String fileName
	 * Post: String[][]
	 */
	public static String[][] readFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists() || file.length() == 0) {
			return new String[0][0];
		}
		Scanner lineCounter = new Scanner(file);
		int rowCount = 0;
		while (lineCounter.hasNextLine()) {
			String line = lineCounter.nextLine().trim();
			if (!line.isEmpty()) {
				rowCount++;
			}
		}
		lineCounter.close();
		String[][] data = new String[rowCount][];
		Scanner input = new Scanner(file);
		int currentRow = 0;

		while (input.hasNextLine()) {
			String line = input.nextLine().trim();
			if (!line.isEmpty()) {
				data[currentRow] = line.split(",");
				currentRow++;
			}
		}
		input.close();
		return data;
	}
	/*
	 * Purpose: Updates the file given. uses arrayIndex and change Index to locate the change and replaces it with String change
	 * Pre: String filename, int arrayIndex, int changeIndex, String change
	 * Post: none
	 */
	public static void updateFile(String filename, int arrayIndex, int changeIndex, String change) throws IOException {
		String[][] data = readFile(filename); // retrieves the data from the file as a 2D array

		if (arrayIndex >= 0 && arrayIndex < data.length && changeIndex >= 0 && changeIndex < data[arrayIndex].length) {
			data[arrayIndex][changeIndex] = change; // makes the change at the correct index
		}

		FileWriter file_writer = new FileWriter(filename);
		PrintWriter writer = new PrintWriter(file_writer);

		for (String[] row : data) { // converts back into the correct format.
			String line = "";
			for (int i = 0; i < row.length; i++) {
				line += row[i];
				if (i < row.length - 1) {
					line += ",";
				}
			}
			writer.println(line);
		}
		writer.close();
	}
	/*
	 * Purpose: shopping experience. displays categories to shoppers and asks for inputs where in the end it leads to a checkout.
	 * Pre:boolean isStaff, String[] user
	 * Post: none
	 */
	public static void shop(boolean isStaff, String[] user) throws IOException {
		subTitle("Shop");

		Scanner input = new Scanner(System.in);

		String[] categories = { "produce", "packaged Foods", "bakery", "beverages", "meat", "home Essentials" };
		String fileNames[] = { "produce.txt", "packagedFoods.txt", "bakery.txt", "beverages.txt", "meat.txt", "homeEssentials.txt" };
		String[] cartItems = new String[999];
		int[] cartQuantities = new int[999];
		double[] cartPrices = new double[9999];
		int cartSize = 0;

		System.out.println("\n\n  Welcome to the Shop! You are able to buy foods by entering into their categories and entering in their names.");
		wait(700);
		System.out.println("  This will add them to your shopping cart. Once you're finished shopping, type 'checkout', to proceed to the checkout section!");
		wait(2000);

		while (true) {
			System.out.println("\n\t Categories: ");
			for (String category : categories) {
				System.out.println("\n   - " + category);
				wait(300);
			}
			System.out.println("\n  Please Enter a category you'd like to enter into");
			arrow();
			String categoryIn = input.nextLine().trim();
			checkExit(categoryIn);
			System.out.println();

			boolean isValidCategory = false;
			for (int i = 0; i < categories.length; i++) {
				if (categories[i].equalsIgnoreCase(categoryIn)) {
					isValidCategory = true;
					subTitle(categories[i]);
					System.out.println("\n");
					String selectedFile = fileNames[i];
					String[][] items = readFile(selectedFile);
					String[] favoriteFoods = user[4].split("-"); // makes an array for the favourite foods. for later use

					for (String[] item : items) { // itterates through each item in items and prints a formated item.
						String name = item[0].trim();
						String quantity = item[1].trim();
						String price = item[2].trim();
						boolean isFavorite = Arrays.asList(favoriteFoods).contains(name.trim());
						int quantityInt = Integer.parseInt(quantity);
						if (quantityInt > 0) {
							String almostSoldOut = (quantityInt <= 5) ? "ALMOST SOLD OUT" : "";
							System.out.printf("\t%-15s | %-8s | %-6s | %-3s%s%n", name, quantity + " left", "$" + price, isFavorite ? "❤️" : "", almostSoldOut);
						}
					}

					while (true) {
						System.out.println("\n  Please Enter an item you'd like to buy. If you wish to return to pick a new category, type 'return'.");
						arrow();
						String itemC = input.nextLine().trim();
						checkExit(itemC);
						if(itemC.equalsIgnoreCase("return")) {
							break;
						}else {
							boolean itemFound = false;
							for (String[] item : items) {// itterate through each item. checks if the item matches any from the list given.
								if (item[0].trim().equalsIgnoreCase(itemC)) {
									itemFound = true;
									int availableQuantity = Integer.parseInt(item[1].trim());
									double price = Double.parseDouble(item[2].trim());
									if (availableQuantity > 0) {
										System.out.println("\n  How many would you like to buy?");
										arrow();
										int quantityToBuy = input.nextInt();
										input.nextLine();
										if (quantityToBuy > availableQuantity || quantityToBuy < 0) { // if the quantity exceeds stock then it returns an invalid response message.
											System.out.println("\n  Not enough stock! Please enter a quantity within the available stock.");
										} else {
											boolean alreadyInCart = false;
											for (int j = 0; j < cartSize; j++) {
												if (cartItems[j].equalsIgnoreCase(itemC)) {
													if (cartQuantities[j] + quantityToBuy > availableQuantity) { // edge case. we make sure that hte user cant just add max amount and then add max amount again
														System.out.println("\n  Silly, You already have " + cartQuantities[j] +" "+ itemC + "s in your cart. ");
														alreadyInCart = true;
														break;
													}
													cartQuantities[j] += quantityToBuy;
													alreadyInCart = true;
													break;
												}
											}
											if (!alreadyInCart) { // if the item is not in the car already, then add it in.
												cartItems[cartSize] = itemC;
												cartQuantities[cartSize] = quantityToBuy;
												cartPrices[cartSize] = price;
												cartSize++;
												System.out.println("\n  " + quantityToBuy + " " + itemC + " added to cart.");
											}
										}
									} else {
										System.out.println("Item out of stock.");
									}
									break;
								}
							}

							if (!itemFound) {
								System.out.println("\n  Item not found, please try again.");
							}
						}
						showCart(cartItems, cartQuantities, cartPrices, cartSize);
					}
					break;
				}
			}
			if (!isValidCategory) {
				System.out.println("\n  Invalid category. Please try again.");
				continue;
			}
			if(cartSize > 0) {
				System.out.println("\n  Type 'checkout' to finish your shopping or continue shopping.");
				arrow();
				String checkout = input.nextLine().trim();
				if (checkout.equalsIgnoreCase("checkout")) {
					System.out.println("\n  Do you wish to proceed with the purchase? (y/n)");
					arrow();
					String confirmation = input.nextLine();
					if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
						for (int i = 0; i < cartSize; i++) { // iterates through each server, and retrieves its data. then figures out where to make the changes for the item quantities.
							for (int k=0;k<6;k++) {
								String selectedFile = fileNames[k];
								String[][] items = readFile(selectedFile);
								for (int z = 0; z<items.length; z++) {
									if (items[z][0].trim().equalsIgnoreCase(cartItems[i])) {
										int availableQuantity = Integer.parseInt(items[z][1].trim());
										int updatedQuantity = availableQuantity - cartQuantities[i]; // calculates the new quantity 
										updateFile(selectedFile, z, 1, String.valueOf(updatedQuantity)); // updates the quantity of the item in its position (z, 1), where z is the line index or the index of the item in the 2D array
										break;
									}
								}
							}
						}
						System.out.println("\n  You have successfully bought all items in your cart.");
						checkExit("exit");
						break;
					} else {
						System.out.println("\n  You have declined the purchase. Returning to categories...");
						continue;
					}
				}
			} else {
				continue;
			}
		}
		input.close();
	}
	/*
	 * Purpose: Displays the users shopping cart
	 * Pre: String[] cartItems, int[] cartQuantities, double[] cartPrices, int cartSize
	 * Post: none
	 */
	public static void showCart(String[] cartItems, int[] cartQuantities, double[] cartPrices, int cartSize) {
		System.out.println("\n    Shopping Cart:\n");
		double total = 0;
		for (int i = 0; i < cartSize; i++) {
			
			// item details
			String itemName = cartItems[i];
			int itemQuantity = cartQuantities[i];
			double itemPrice = cartPrices[i];
			double itemTotal = itemPrice * itemQuantity;
			total += itemTotal;
			String formatedPrice = String.format("%.2f", itemPrice);
			String formattedTotal = String.format("%.2f", itemTotal);
			
			// item and its info is printed out.
			System.out.println("  " + itemName + " | Quantity: " + itemQuantity + " | Price: $" + formatedPrice + " | Total: $" + formattedTotal);
		}
		String totalP = String.format("%.2f", total);
		System.out.println("\n  Total: $" + totalP);
	}
}
import java.io.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

//Game soundtrack: Zelda Fi's Farewell


public class MainGame implements Runnable {

	public static void main(String[] args) {

		(new Thread(new MainGame())).start(); //starting the main thread
		song.run();; // the song thread for playing the song in the background

	}

	List<String> items = new ArrayList<>(); // a list for the items used in the game (especially useful since some methods require that the user must have a certain item before hand)


	/* Planet Distance from Earth (in light-years)
        Kepler-186f 490
  Gliese 581g 20
  Gliese 667Cc 22.7
  Kepler-22b 600
  HD 40307g 42
  HD 85512b 36
  Tau Ceti e 11.905
  Gliese 163c 49
  Gliese 581d 20.3
  Tau Ceti f 11.906 */
	static int tempOfEarth = 0; // temperature of Earth (just initialized)
	static int fuelPoints = 0; //fuel points
	static int cash = 0;
	static int progress = 0; // for recording progress in order to save/load
	String name = " ";
	static int[] gameInfo = { fuelPoints, cash, tempOfEarth, progress }; // array of important variables
	static int score = 0;
	List<String> planets = new ArrayList<>(); // list that stores planet names as the user visits them
	List<String> people = new ArrayList<>(); // list that contains important people whom the user has met with
    
	//save method
	public void writeInfo(int p, int[] a) { //parameters are p for progress and a is the array gameInfo

		String saveName = IBIO.inputString("What do want to save the game as? "); // user specifies file name
		File file = new File(saveName + ".txt"); // initialize a file with the file name specified by the user
		File dir = new File("C:\\Users\\Ayudiv\\Desktop\\Divyam's Stuff\\Unit 1 Comp Sci\\TextBasedGame"); // list directory where file saves are stored
		File[] files = dir.listFiles(new FilenameFilter() { // this method ensures that too many saves can't be made. It uses the file name filter to find files ending with .txt in the specified directory and stores all the returned file names in an array (if accept returns true that means that the file ends with .txt and it is stored in the array but if it returns false then the file doesn't get stored).
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".txt"); 
			}
		});
		
		if (files.length > 8) {  // if the array files has more than 8 files, run the following code (since there are too many save files)
			print("You have too many save files!");
			char overWrite = IBIO.inputChar("Overwrite a file? (y/n) ");
			if (overWrite == 'y') {
				saveName = IBIO.inputString("Which file? ");
				file = new File(saveName + ".txt");
				bufferedReader(file, p, a); // pass file name, progress, and array to bufferedReader method (which performs the writing)
			}
		}
		else { 
			if (file.exists() == true) { // check to see if a certain file already exists
				System.out.println("File already exists!");
				char overWrite = IBIO.inputChar("Do you want to overwrite file? (y/n) ");
				if (overWrite == 'y') {
					file = new File(saveName + ".txt");
					bufferedReader(file, p, a); // pass file name, progress, and array to bufferedReader method (which performs the writing)
				}
				else {
					saveName = IBIO.inputString("What do want to save the game as? ");
					file = new File(saveName + ".txt"); 
					bufferedReader(file, p, a);
				}
			}
			else {
				bufferedReader(file, p, a); // pass the appropriate parameters(file, progress, and the main array to bufferedReader)
			}
		}
	}

	public void bufferedReader(File f, int p, int[] array) { 
		Writer writer = null; // initializes a writer to be null
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8")); // initializes the writer again to be a bufferedWriter and open a new output stream to the specified file (or the parameter f)
			for(int i = 0; i < array.length; i++) { // creates a for loop and fetches the array parameter to write all its contents in the file
				writer.write(Integer.toString(array[i])); // converts the integer to a string since the write method needs a String parameter to be able to write something to another file
				writer.write(System.lineSeparator()); // ensures that each time a separate line is created
			}
			// writer.write(Integer.toString(p));
		} catch (IOException e) {
			print("could not write to file");
		}
		finally {
			try {
				writer.close(); // closes the writer and the stream
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void readInfo() {
		BufferedReader reader = null; // initializes a bufferedReader to be null
		String saveName = IBIO.inputString("Which save file do you want to load? "); // asks the user on which save file to load
		File file = new File(saveName + ".txt");
		while(file.exists() == false) { // a while loop to keep asking the user to enter a save file if the one that they specified does not exist already
			System.out.println("File does not exist!");
			saveName = IBIO.inputString("Which save file do you want to load? ");    
			file = new File(saveName + ".txt");
		}
		try {
			reader = new BufferedReader(new FileReader(saveName + ".txt")); //opens a bufferedReader with a fileReader that reads from the specified file
			String cline; // cline stands for current line
			int[] sampleArray = new int[4]; // creates a temporary array for storing the read values
			cline = reader.readLine(); // reads the first line
			int i = 0;

			while (cline != null) { // makes sure that the current line is not null before reading it
				//   for(int i = 0; i < sampleArray.length; i++){
				//  if ((cline != null) {
				sampleArray[i] = Integer.parseInt(cline); // parses what the reader reads into an integer (since we need integers to work with and we know that only integers will get stored there)
				i++;
				cline = reader.readLine();
			}

			for (int j = 0; j < sampleArray.length; j++) // displays the loaded content
				System.out.println(sampleArray[j]);

			updatePlayer(sampleArray[3], sampleArray); // assigns the variables and values fetched from the loading to the specific variables in the game
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				reader.close(); // closes the reader
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updatePlayer(int p, int[] s)  { // assigns values passed in as a parameter within the array to in-game variables

		fuelPoints = s[0];
		cash = s[1];
		tempOfEarth = s[2];
		progress = s[3];
		respawn(p, cash, fuelPoints); // passes the progress parameter onto the respawn method which decides on which checkpoint to respawn
	}

	public void writeHighScore(String n) { // a highscore method that records your name and your highscore at the end of the game (the intended purpose was to display the highscores of the people who played this game at the very beginning to encourage users to beat them like leaderboards)
		Writer writer = null;
		File file = new File("highscores.txt");
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			writer.write(n);
			writer.append(Integer.toString(score));
		} catch (IOException e) {
			System.out.println("Could not record highscore!");
		}
	}

	public void readHighScore() { // for reading the highscores

		BufferedReader reader = null;
		File file = new File("highscores.txt");
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println("Couldn't load highscores!");
		}

	}
	static Thread song = new Thread() { // individual threads like this one for creating songs
		public void run() {
			try {
				startSong("fc3.wav"); // the method responsible for opening a clip with the specified file name and playing the sound
				Thread.yield();
			} catch (Exception e) {
				System.out.println("The audio file couldn't play!");
			}
		}
	}; 

	static Thread otherSong = new Thread() {
		public void run() {
			try {
				/* AudioInputStream input = AudioSystem.getAudioInputStream(MainGame.class.getResource("Star_Wars_Theme_Song_By_John_Williams.wav"));
    Clip clip = AudioSystem.getClip();
    clip.open(input);
    clip.start();
    while(!Thread.interrupted()) {
     clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    clip.stop(); */
				startSong("Star_Wars_Theme_Song_By_John_Williams.wav");
				Thread.yield();

			} catch (Exception e) {
				System.out.println("The audio file couldn't play!");
				e.printStackTrace();
			}
		}

	}; 

	static Thread acSong = new Thread() {
		public void run() {
			try {
				/* AudioInputStream input = AudioSystem.getAudioInputStream(MainGame.class.getResource("Assassin_39_s_Creed_2_OST_-_Track_23_-_Ezio_In_Flo.wav"));
    Clip clip = AudioSystem.getClip();
    clip.open(input);
    clip.start();
    while(!Thread.interrupted()) {
     clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    clip.stop(); */
				startSong("Assassin_39_s_Creed_2_OST_-_Track_23_-_Ezio_In_Flo.wav");
				Thread.yield();

			} catch (Exception e) {
				System.out.println("The audio file couldn't play!");
				e.printStackTrace();
			}
		}

	}; 

	static void startSong(String f) { // accepts a string (or the file name) as the parameter
		try {
			AudioInputStream input = AudioSystem.getAudioInputStream(MainGame.class.getResource(f)); // uses AudioInputStream to fetch the desired file
			Clip clip = AudioSystem.getClip(); // creates a clip object
			clip.open(input); // passes in the input object for opening the stream for the song
			clip.start(); // starts the song
			while(!Thread.interrupted()) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);  // keeps on looping until the specific thread in which this method is in is interrupted (to allow for other songs to play)
			}
			clip.stop(); // stops the clip after the while loop evaluates to true (meaning the thread has been interrupted and it is time to stop
		}
		catch (Exception e) {
			System.out.println("Could not play audio clip!");
		}
	}

	static Thread zeldaSong = new Thread() {
		public void run() {
			try {
				/* AudioInputStream input = AudioSystem.getAudioInputStream(MainGame.class.getResource("Fi_39_s_Farewell_-_The_Legend_of_Zelda-_Skyward_Sw.wav"));
    Clip clip = AudioSystem.getClip();
    clip.open(input);
    clip.start();
    while(!Thread.interrupted()) {
     clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    clip.stop(); */
				startSong("Fi_39_s_Farewell_-_The_Legend_of_Zelda-_Skyward_Sw.wav");
				Thread.yield();

			} catch (Exception e) {
				System.out.println("The audio file couldn't play!");
				e.printStackTrace();
			}
		}

	};



	public void intro() {
		// firstPlanet();
		// friendHouse();
		String name = (String) JOptionPane.showInputDialog(null, "What is your name?");
		while (name == null || name.equals("") || name.trim().isEmpty() || name.equals(" ") ) {
			name = JOptionPane.showInputDialog(null, "What is your name?", "Enter your name"); // input dialog to allow the user to enter their name
		}

		System.out.println("__________.__                        __ __________                                     \n" + 
				"\\______   \\  | _____    ____   _____/  |\\______   \\_____    ____    ____   ___________ \n" + 
				" |     ___/  | \\__  \\  /    \\_/ __ \\   __\\       _/\\__  \\  /    \\  / ___\\_/ __ \\_  __ \\\n" +
				" |    |   |  |__/ __ \\|   |  \\  ___/|  | |    |   \\ / __ \\|   |  \\/ /_/  >  ___/|  | \\/\n" + 
				" |____|   |____(____  /___|  /\\___  >__| |____|_  /(____  /___|  /\\___  / \\___  >__|   \n" +
				"                    \\/     \\/     \\/            \\/      \\/     \\//_____/      \\/       \n" );

		char save = IBIO.inputChar("Do you want to load a previously saved game? ");

		 if (save == 'y') {
            readInfo();
            loadingClause();
         } 

		print("\nHello " + name + ". You are in the year 2015. The world is in deep trouble. ");
		print("Your species has destroyed this planet and the damage is now upto the point that ");
		print("the world can end in as little as 7 days. It is upto you to change your lifestyle ");
		print("in order to set an example for the rest of mankind and save this doomed planet. Or else");
		loadingClause();

		print("You will have to move to a completely new planet and establish civilization all over ");
		print("again. Either change your choices and save the planet or rediscover the world on ");
		print("an exoplanet. The choice is yours.");

		char choice = IBIO.inputChar("Stay on this planet? (y/n) ");

		if (choice == 'y' || choice == 'Y') {
			earth();
		}
		else { 
			move();
		}

	}
	int carbonPoints = 500;
	public void earth() {
		song.interrupt();
		acSong.start();
		System.out.println("               ,,ggddY\"\"\"Ybbgg,,");
		System.out.println("          ,agd888b,_ \"Y8, ___`\"\"Ybga,");
		System.out.println("       ,gdP\"\"88888888baa,.\"\"8b    \"888g,");
		System.out.println("     ,dP\"     ]888888888P'  \"Y     `888Yb,");
		System.out.println("   ,dP\"      ,88888888P\"  db,       \"8P\"\"Yb,");
		System.out.println("  ,8\"       ,888888888b, d8888a           \"8,");
		System.out.println(" ,8'        d88888888888,88P\"' a,          `8,");
		System.out.println(",8'         88888888888888PP\"  \"\"           `8,");
		System.out.println("d'          I88888888888P\"                   `b");
		System.out.println("8           `8\"88P\"\"Y8P'                      8");
		System.out.println("8            Y 8[  _ \"                        8");
		System.out.println("8              \"Y8d8b  \"Y a                   8");
		System.out.println("8                 `\"\"8d,   __                 8");
		System.out.println("Y,                    `\"8bd888b,             ,P");
		System.out.println("`8,                     ,d8888888baaa       ,8'");
		System.out.println(" `8,                    888888888888'      ,8'");
		System.out.println("  `8a                   \"8888888888I      a8'");
		System.out.println("   `Yba                  `Y8888888P'    adP'");
		System.out.println("     \"Yba                 `888888P'   adY\"");
		System.out.println("       `\"Yba,             d8888P\" ,adP\"'  ");
		System.out.println("          `\"Y8baa,      ,d888P,ad8P\"'           ");
		System.out.println("               ``\"\"YYba8888P\"\"''          ");

		print("Good choice!");
		print("Let's begin by understanding the planet. Its summer of 2015 and you are living in Toronto, Canada");
		print(". Temperatures have risen to 45 degrees Celcius here in Toronto and that is really alarming, ");
		print("especially for a place like Toronto. Carbon dioxide emmissions are at 4.55 metric tons per capita ");
		print("and the planet is about to rip apart due to so much heat. It is upto you to save the planet ");
		print("by bringing these harmful greenhouse gas emmissions down to a minimum. ");
		print("That could mean downsizing, driving a less expensive car, or even taking transit to get to work. ");
		print("Whatever you do, it must be for the benefit of the planet. There will be bonus questions thrown ");
		print("at you from time to time. Upon answering these questions correctly, you will be awarded with a ");
		print("small decrease in global greenhouse gas emmissions or eco-friendly products or cash. The game uses something called carbon points");
		print("and you have to bring the carbon points down to as close to 0 as possible. Answering questions and spreading awareness");
		print("about global warming will get you that reduction in carbon points");

		/* Thread t = new Thread() {
   public void run() {
    for (int i = 1296000; i > 0; i--) {
     try {
      Thread.sleep (1000);
     } catch (Exception e) {
      ;
     }
     System.out.print(i % 60 + " minutes");
     i = i / 60;
     System.out.println(" " + i / 60 + " hours till doomsday");
    }
   }
  };
  t.start(); */

		print("Try this: What is the square root of 64 to the power of 0 ");
		System.out.println("a. 1 \n b. 8 \n c. 4 \n d. 0");
		char answer = IBIO.inputChar("Your answer: ");
		while(answer != 'a') {

			print("That is the wrong answer!");
			answer = IBIO.inputChar("Try again: ");

		}
		print("Nice!! Your reward is a reduction of 5 points in the total amount of carbon points you have");
		carbonPoints -= 5;
		System.out.println("Carbon points: " + carbonPoints);

		print("Good luck on your path to save the planet! ");

		day1();


	}

	public void day1() {
		progress = 10;
		int[] gameInfo = { carbonPoints, cash, tempOfEarth, progress };
		char save = IBIO.inputChar("Do you want to save the game now? (y/n) ");

		if (save == 'y') {
			writeInfo(progress, gameInfo);
		}

		System.out.println("________                  ____ ");
		System.out.println("\\______ \\ _____  ___.__. /_   |");
		System.out.println(" |    |  \\\\__  \\<   |  |  |   |");
		System.out.println(" |    `   \\/ __ \\\\___  |  |   |");
		System.out.println("/_______  (____  / ____|  |___|");
		System.out.println("        \\/     \\/\\/            ");

		print("Alright. So, your first day as a Planet Ranger begins today!");
		print("Its the morning and you have just woken up. You brush your teeth,");
		print("take a shower, and get ready for the day ahead of you");
		print("You can go to the following places: ");
		System.out.println("a. Work ");
		System.out.println("b. Shopping");
		System.out.println("c. Friend's house");

		char whereToGo = IBIO.inputChar("Where do you want to go ");

		if (whereToGo == 'a')
			work();
		else if (whereToGo == 'b') 
			shopping();
		else if (whereToGo == 'c')
			friendHouse();



	}

	int morale = 0;

	public void work() {
		System.out.println("                                           $\"   *.      ");
		System.out.println("               d$$$$$$$P\"                  $    J");
		System.out.println("                   ^$.                     4r  \"");
		System.out.println("                   d\"b                    .db");
		System.out.println("                  P   $                  e\" $");
		System.out.println("         ..ec.. .\"     *.              zP   $.zec..");
		System.out.println("     .^        3*b.     *.           .P\" .@\"4F      \"4");
		System.out.println("   .\"         d\"  ^b.    *c        .$\"  d\"   $         %");
		System.out.println("  /          P      $.    \"c      d\"   @     3r         3");
		System.out.println(" 4        .eE........$r===e$$$$eeP    J       *..        b");
		System.out.println(" $       $$$$$       $   4$$$$$$$     F       d$$$.      4");
		System.out.println(" $       $$$$$       $   4$$$$$$$     L       *$$$\"      4");
		System.out.println(" 4         \"      \"\"3P ===$$$$$$\"     3                  P");
		System.out.println("  *                 $       \"\"\"        b                J");
		System.out.println("   \".             .P                    %.             @");
		System.out.println("     %.         z*\"                      ^%.        .r\"");
		System.out.println("        \"*==*\"\"                             ^\"*==*\"\"   '");

		print("Your workplace is 10 km from here."); 
		print("Here is a map of where you are and where your workplace is.");
		print("There is a bus that you can take to get to your workplace or ");
		print("you can travel through your car. You also have a bike which you ");
		print("can pick up to get to work ");

		char travel = IBIO.inputChar("Do you want to take the car? ");

		if (travel == 'n') {
			travel = IBIO.inputChar("Then do you want to take a bus? ");
			if (travel == 'n') {
				travel = IBIO.inputChar("How about a bike");
				if (travel == 'y') {
					print("It took you really long to get to work and now your boss is angry with you ");
					angryBoss();
				}
			}
			else {
				print("Let's go!");
			}
		}
		else {
			print("Let's go!");
			print("An addition of 5 fuel points is added to your total...for 20 km you choose ");
			print("to use your fuel hogging car instead of the transit. But, don't worry!");
			print("You'll get plenty of other choices in order to prove that you are the true saviour of the planet");
		}



	}


	public void shopping() {
		
		print("You are at the supermart now! Here are the items available: ");
		print("LED bulbs      | $20 | Reduction of 30 carbon points");
		print("Recycled paper | $30 | Reduction of 10 carbon points");
		print("");


	}

	public void friendHouse() {

		loadingClause();
		
		System.out.println("");
		System.out.println("                           (   )");
		System.out.println("                          (    )");
		System.out.println("                           (    )");
		System.out.println("                          (    )");
		System.out.println("                            )  )");
		System.out.println("                           (  (                  /\\");
		System.out.println("                            (_)                 /  \\  /\\");
		System.out.println("                    ________[_]________      /\\/    \\/  \\");
		System.out.println("           /\\      /\\        ______    \\    /   /\\/\\  /\\/\\");
		System.out.println("          /  \\    //_\\       \\    /\\    \\  /\\/\\/    \\/    \\");
		System.out.println("   /\\    / /\\/\\  //___\\       \\__/  \\    \\/");
		System.out.println("  /  \\  /\\/    \\//_____\\       \\ |[]|     \\");
		System.out.println(" /\\/\\/\\/       //_______\\       \\|__|      \\");
		System.out.println("/      \\      /XXXXXXXXXX\\                  \\");
		System.out.println("        \\    /_I_II  I__I_\\__________________\\");
		System.out.println("               I_I|  I__I_____[]_|_[]_____I");
		System.out.println("               I_II  I__I_____[]_|_[]_____I");
		System.out.println("               I II__I  I     XXXXXXX     I");
		System.out.println("            ~~~~~\"   \"~~~~~~~~~~~~~~~~~~~~~~~~");
		
		Object[] arrayOfChoices = {"Bus", "Bike", "Car"};
		
		String n = (String)JOptionPane.showInputDialog(null, "How would you like to go to your friend's house? Bus, bike, or car?", "Transportation"
				, JOptionPane.PLAIN_MESSAGE, null, arrayOfChoices, arrayOfChoices[0]);
		
		carbonPoints = changeCarbonPoints(n, carbonPoints);
		
		print("You are now at your friend's house! When you enter, you see that appliances are running for no reason and ");
		print("all the lights are on even in broad daylight. Your mission is to shut down these unnecessary appliances and");
		print("lights down that plague the atmosphere of our planet. Let's go.");
		
		friendActualHouse();



	}
	
	public void friendActualHouse() {
		print("In order to shut down all the lights, you need energy. You start off with 10 energy but there are a lot more");
		print("appliances to be shut down and they must be shut down within 5 minutes. You can stop in between to drink juice");
		print("in order to gain energy but that will also waste your time. ");
		int energy = 10;
		int time = 5;
		livingRoom(time, energy);
		
		print("Yay! You shut down all the appliances! A reduction of 50 carbon points has been awarded to you!");
		carbonPoints -= 5;
		/* print("a) Living room \nb) Dining room \nc) Kitchen \nd) Basement");
		
		char choice = IBIO.inputChar("Where do you want to go? ");
		
		if (choice == 'a' || choice == 'A') {
			livingRoom(time, energy);
		}
		
		else if (choice == 'b' || choice == 'B') {
			diningRoom(time, energy);
		}
		
		else if (choice == 'c' || choice == 'C') {
			kitchen(time, energy);
		}
		
		else if (choice == 'd' || choice == 'D') {
			basement(time, energy);
		}
		
		else {
			print("Not a valid place to go!");
			friendActualHouse();
		} */
	}
	
	public void livingRoom(int t, int e) {
		loadingClause();
		
		System.out.println(".-------------------------------------------------------------.");
		System.out.println("|(_)-',_. |:         | | ~    | |      | |          _         |");
		System.out.println("|~.(( )_ (|:         | |      | | `    | |         (_)_       |");
		System.out.println("| ))__`  .|:         | |  ~~  | |    ` | |       *-;)(_)      |");
		System.out.println("|_____]   |:         | |______| |______| |        .-(_).-.    |");
		System.out.println("|    /    |:         |___________________|       (/ ||  ( )   |");
		System.out.println("| __/     |:        (_____________________)        ===== \\|   |");
		System.out.println("|---------':  _.-    }___________________{        _\\   /_     |");
		System.out.println("|\"\"\"\"\"\"\"\"\"_.-'   ;           .---.               ( |\\_/| )    |");
		System.out.println("|       .'   _.-'|           |(\")|                 `---'      |");
		System.out.println("|       ;_.-'|  ||           |(\")|                 |___|      |");
		System.out.println("|       ||  ||(\\||           `---'                 |   |      |");
		System.out.println("|_____.-||`-||_\\\\|_________________________________)---(______|");
		System.out.println("| _.-'  ||  ||_.\\\\     /       |       \\       `(_/     \\_)   |");
		System.out.println("|[-._-._||_.-_.-;\\\\__ /________| ~~~ ___\\ _______`,__________ |");
		System.out.println("|||  `-.||.-'  .||\\\\ /       ~~~.===.~~  \\         `.         |");
		System.out.println("|||     ||,'  _.|| \\\\        ~.' ~~~ `.~~ \\          `.       |");
		System.out.println("|||`-._ ||_.-'__||(\\\\\\\\--.__~o_.-----._o~__\\ __________`._____|");
		System.out.println("|||   ,`||      ||/|/\\\\  //~~~\\`-...-'/~~   \\            `,   |");
		System.out.println("||| ,'  ||       / / /\\)//~~~~~\\     /~~     \\             `, |");
		System.out.println("| ,'    ||      /  \\/__// ~~~~~~`---'~~~      \\              `|");
		System.out.println("|MJP           /   )__)'    ~~~|~~~~~~         \\              |");
		System.out.println("|____________ /________________|_____________ __\\ ____________|");
		
		while (!loseEnergyorTime(t, e)) {
		print("You are in the living room. There are 4 lights and 3 appliances running in this room.");
		int lights = IBIO.inputInt("How many lights to shut down? ");
		
		e = shutDown(lights, e);
		
		print("You have " + e + " energy left.");
		char juiceChoice = IBIO.inputChar("Consume juice? (y/n)");
		
		if (juiceChoice == 'y' || juiceChoice == 'Y') {
			print("It took you 30 seconds to consume juice! You gained 5 energy!");
			t -= 0.5;
			e += 5;
		}
		
		else 
			;
		
		int appliances = IBIO.inputInt("How many appliances to shut down? ");
		
		e = shutDown(lights, e);
		
		diningRoom(t, e);
		
       /* print("a) Dining room \nb) Kitchen \nc) Basement");
		
		char choice = IBIO.inputChar("Where do you want to go? ");
		
		while (choice != 'A' && choice != 'a' && choice != 'b' && choice != 'B' && choice != 'c' && choice != 'C') {
			print("Not a valid choice!");
			choice = IBIO.inputChar("Where do you want to go? ");
		}
		if (choice == 'a' || choice == 'A') {
			diningRoom(t, e);
		}
		
		else if (choice == 'b' || choice == 'B') {
			kitchen(t, e);
		}
		
		else if (choice == 'c' || choice == 'C') {
			basement(t, e);
		} */
		}
		
	}
	
	public int shutDown(int h, int e) {
		if (h == 4) {
			return e -= 4;
		}
		
		else if (h == 3) {
			return e -= 3;
		}
		
		else if (h == 2) {
			return e -= 2;
		}
		
		else if (h == 1) {
			return e -= 1;
		}
		
		else
			return e;
	}
	
	public void diningRoom(int t, int e) {
		loadingClause();
		
		print("You are now in the dining room!");
		print ( "There is one large appliance running that will require 5 energy");
		print("Drink juice? (y/n)");
		char choice = IBIO.inputChar("Your choice: ");
		
		if (choice == 'y') {
			e += 5;
			print("You gained 5 energy but it took you 2 minutes.");
			t -= 2;
		}
		
		else {
			;
		}
		
		char userChoice = IBIO.inputChar("Shut down the appliance? (y/n) ");
		
		if (userChoice == 'y') {
			e -= 5;
		}
		
		
	}
	
	public boolean loseEnergyorTime(int t, int e) {
		if (t <= 0) {
			print("You ran out of time :(");
			return true;
		}
		
		else if (e <= 0) {
			print("You ran out of energy :(");
			return true;
		}
		else 
			return false;
	}
	
	public void kitchen(int t, int e) {
		loadingClause();
	}
	
	public void basement(int t, int e) {
		loadingClause();
	}
	
	public int changeCarbonPoints(String s, int c) {
		if (s.equals("Bus")) {
			return c -= Transportation.BUS.returnFuel();
		}
		
		else if (s.equals("Car")) {
			return c += Transportation.CAR.returnFuel();
		}
		
		else
			return c -= Transportation.BIKE.returnFuel();
	}
	
	public enum Transportation {
		
		BUS(12),
		CAR(18),
		BIKE(18);
		
		int carbP;
		Transportation(int f) {
			this.carbP = f;
		}
		
		public int returnFuel() {
			return carbP;
		}
	}

	public void angryBoss() {

		print("Here are the possible reasons that you could give to your boss for you coming late to work ");
		System.out.println("a. I was trying to help the environment \n b. I was too lazy to drive a car \n c. There was a traffic jam in between ");

		char reason = IBIO.inputChar("Your boss demands a valid reason for why you are late to work: "); 

		if (reason == 'a') {

			print("Boss: Very good! We need the help of every citizen to overcome this issue. But make sure you come on time. ");
			print("You: Sure thing. ");
			print("You just raised the morale of your coworkers to perform tasks that benefit the planet! ");
			morale += 5;

		}

		else if (reason == 'b') {

			print("Boss: Oh really! Well, if you are that lazy, how about you never come here again? ");
			print("You: No boss. Give me one more chance! ");
			print("Boss: Alright. First off, find a better excuse to not drive a car. ");

		}

		else if (reason == 'c') {

			print("Boss: I didn't know that bikes were affected by traffic jams as well! ");
			cash -= 5; 
			print("Boss: I am going to cut off 5 dollars from you salary. Beware to come on time next time!");

		}

		print("You are dismissed from work. Do you want to do anything else before going home?");
		print("a. Grocery shopping \n b. Purchase energy saving products \n c. Play a game at the local playdium");

		char choice = IBIO.inputChar("Where would you like to go? ");
		if (choice == 'a') 
			shopping();
		else if (choice == 'b') {
			supermarket();
		}
		else 
			game();




	}

	private void game() {

		loadingClause();

	}
	private void supermarket() {
		loadingClause();
		print("You are at the supermarket now!");
		shopping();

	}
	
	public void day5() {
		loadingClause();
		print("You have been called upon The Late Night Show for your heroic efforts! Upon answering a few questions the right way, ");
		print("you will gain morale points! Let's begin!");
		
		print("Jimmy Fallon: Welcome, Mr. " + name + ". Welcome on the Late Night Show. ");
		print("You: Thanks, Jimmy! Glad to be here!");
		print("Jimmy: Let's begin. What made you pursue this action of saving the planet? ");
		print("a) Nothing, really \nb) I felt like it was a time for change \nc) We would be dead, otherwise \nd) It's the right thing to do");
		char choice = IBIO.inputChar("Your choice: ");
		
		if (choice == 'a' || choice == 'A') {
			print("Jimmy: Really? Nothing at all? Alright...");
			print("Morale points decreased by 10");
			morale -= 10;
		}
		
		else if (choice == 'b' || choice == 'B') {
			print("Jimmy: That is the case, my friend. And you are one of the few people who recognized that ");
			print("and took action on it. Well done!");
			print("Morale points went up by 10");
			morale += 10;
		}
		
		else if (choice == 'c' || choice == 'C') {
			print("Jimmy: Yup that is a definite motivation factor! Ladies and gentlemen, he is the saviour of your lives!");
			print("Morale went up 10");
			morale += 10;
		}
		else {
			print("Jimmy: It sure is. And I am glad that at least someone took action on it.");
			print("Morale went up by 5");
			morale += 5;
		}
		
		print("Jimmy: Here's the second question: ");
	}
	public void move() {
		song.interrupt();
		otherSong.start();
		progress = 1;
		// char save = IBIO.inputChar("Do you want to save the game now? (y/n) ");
		int[] gameInfo = { fuelPoints, cash, tempOfEarth, progress };

		/*  if (save == 'y') {
   writeInfo(progress, gameInfo);
  } */

		System.out.println("\n");
		System.out.println("                    8888888888  888    88888");
		System.out.println("                   88     88   88 88   88  88");
		System.out.println("                    8888  88  88   88  88888");
		System.out.println("                       88 88 888888888 88   88");
		System.out.println("                88888888  88 88     88 88    888888");
		System.out.println(" ");
		System.out.println("                88  88  88   888    88888    888888");
		System.out.println("                88  88  88  88 88   88  88  88");
		System.out.println("                88 8888 88 88   88  88888    8888");
		System.out.println("                 888  888 888888888 88   88     88");
		System.out.println("                  88  88  88     88 88    8888888");

		print("Brace yourselves for one of the most grueling journeys of your lifetime. It won't be easy");
		print("but don't give up. The cosmos of space is yours to conquer. ");
		System.out.print("Prepare for takeoff!");

		System.out.println("          /\\ "   );
		System.out.println("         //\\"   );
		System.out.println("        ||##||"   );
		System.out.println("       //##mm\\"  );
		System.out.println("      //##*mmm\\"  );
		System.out.println("     //###**mmm\\\\" );
		System.out.println("    //###***nmmm\\\\" );
		System.out.println("   //####***@nmmm\\\\" );
		System.out.println("   ||####***@nnmm||" );
		System.out.println("   ||####**@@@nnm||" );
		System.out.println("   |______________|" );
		System.out.println("   |    DivAir    |" );
		System.out.println("    \\____________//" );
		System.out.println("     |           |"  );
		System.out.println("   //|     //\\\\|\\\\" );
		System.out.println("   /_|    || /\\ |_\\" );
		System.out.println("     |      NaSA|"  );
		System.out.println("     |       \\/ |");
		System.out.println("     |           | ");
		System.out.println("    /|     /\\   | \\");
		System.out.println("   / |     ||    |   \\");
		System.out.println("  /  |     ||    |    \\");
		System.out.println(" /  /\\    ||    /\\  \\");
		System.out.println(" |__/  \\   ||   /  \\__|");
		System.out.println("  /____\\      /____\\");
		System.out.println("  |    |       |    |");
		System.out.println("  |    |______ |    |");
		System.out.println("  |    | /--\\ |    |");
		System.out.println("  |____|/----\\|____|");
		System.out.println("  \\||/ //##\\\\ \\||/");
		System.out.println("   /##\\//####\\\\/##\\");
		System.out.println("  //##\\\\/####\\//##\\\\");
		System.out.println("  ||/::\\||/##\\||/::\\||");
		System.out.println(" \\\\\\''///:**:\\\\\\''///");
		System.out.println("  \\\\\\///\\::::/\\\\\\///");
		System.out.println("   \\\\//\\\\\\::///\\\\//");
		System.out.println("    \\/\\\\\\\\..////\\/");
		System.out.println("        \\\\\\\\////");
		System.out.println("          \\\\\\///");
		System.out.println("            \\\\//");
		System.out.println("              \\/);");

		print("Here are the possible planets that you can go to and how far they are from Earth (in light years): ");
		print("The third column indicates the amount of fuel points you need, which will be explained in just a second");
		System.out.println("_________________");
		print("|a. Kepler-186f | 490  | 150 ");
		print("|b. Gliese 581g | 20  | 5 ");
		print("|c. Kepler-22b  | 600  | 250 ");
		print("|d. HD 40307g   | 42  | 80 ");
		System.out.println("_________________");

		print("As you can see, some planets are farther away than other ones, and to get to them");
		print("you need fuel. Right now you have none, but solving certain obstacles can get you");
		print("bonus rewards like fuel points. Here, try one: ");

		print("What is the cube root of -125 ?");
		print ("a. 5");
		print("b. -5");
		print("c. -25");
		print("d. 25");

		char answer = IBIO.inputChar("Your answer: ");

		while(answer != 'b') {
			print("That is incorrect! Please try again ");
			answer = IBIO.inputChar("Your answer: "); 
		}

		print("Well done! U sure are a math genius.");
		print("Your reward: " + (fuelPoints += 5) + " fuel points!");

		System.out.println("_________________");
		print("|a." + Planets.FOURTHPLANET.returnName() + " | 490  | 150 ");
		print("|b. HD 40307g   |    42      |   80");
		print("|c. Gliese 581g | 20  | 5 ");
		print("|d. Kepler-22b  | 600  | 250 ");
		System.out.println("_________________");

		char[] arrayOfChars = {'a', 'b', 'c', 'd', 'e'};



		print("Now let's get to it!");

		char whereToGo = IBIO.inputChar("Where are we headed? " + "(" + fuelPoints + " fuel points" + ") ");

		int fuelPointsNeeded = destination(whereToGo);

		while (canTravel(fuelPoints, fuelPointsNeeded) == false) {

			print("Not enough fuel points!!");

			whereToGo = IBIO.inputChar("Where are we headed? " + "(" + fuelPoints + " fuel points" + ") ");

			fuelPointsNeeded = destination(whereToGo);

		}

		print("Let's go!" );
		fuelPoints -= fuelPointsNeeded;
		firstPlanet();

	}

	char a = ' ';
	char b = ' ';
	char c = ' ';
	char d = ' ';
	char e = ' ';
	char f = ' ';
	char g = ' ';
	char h = ' ';
	char i = ' ';

	public void firstPlanet() {
		otherSong.interrupt();
		zeldaSong.start();
		progress = 2;
		System.out.println("                                                                      ..;===+.");
		System.out.println("                                                                  .:=iiiiii=+=");
		System.out.println("                                                               .=i))=;::+)i=+,");
		System.out.println("                                                            ,=i);)I)))I):=i=;");
		System.out.println("                                                         .=i==))))ii)))I:i++");
		System.out.println("                                                       +)+))iiiiiiii))I=i+:'");
		System.out.println("                                  .,:;;++++++;:,.       )iii+:::;iii))+i='");
		System.out.println("                               .:;++=iiiiiiiiii=++;.    =::,,,:::=i));=+'");
		System.out.println("                             ,;+==ii)))))))))))ii==+;,      ,,,:=i))+=:");
		System.out.println("                           ,;+=ii))))))IIIIII))))ii===;.    ,,:=i)=i+");
		System.out.println("                          ;+=ii)))IIIIITIIIIII))))iiii=+,   ,:=));=,");
		System.out.println("                        ,+=i))IIIIIITTTTTITIIIIII)))I)i=+,,:+i)=i+");
		System.out.println("                       ,+i))IIIIIITTTTTTTTTTTTI))IIII))i=::i))i='");
		System.out.println("                      ,=i))IIIIITLLTTTTTTTTTTIITTTTIII)+;+i)+i`");
		System.out.println("                      =i))IIITTLTLTTTTTTTTTIITTLLTTTII+:i)ii:'");
		System.out.println("                     +i))IITTTLLLTTTTTTTTTTTTLLLTTTT+:i)))=,");
		System.out.println("                     =))ITTTTTTTTTTTLTTTTTTLLLLLLTi:=)IIiii;");
		System.out.println("                    .i)IIITTTTTTTTLTTTITLLLLLLLT);=)I)))))i;");
		System.out.println("                    :))IIITTTTTLTTTTTTLLHLLLLL);=)II)IIIIi=:");
		System.out.println("                    :i)IIITTTTTTTTTLLLHLLHLL)+=)II)ITTTI)i=");
		System.out.println("                    .i)IIITTTTITTLLLHHLLLL);=)II)ITTTTII)i+");
		System.out.println("                    =i)IIIIIITTLLLLLLHLL=:i)II)TTTTTTIII)i'");
		System.out.println("                  +i)i)))IITTLLLLLLLLT=:i)II)TTTTLTTIII)i;");
		System.out.println("                +ii)i:)IITTLLTLLLLT=;+i)I)ITTTTLTTTII))i;");
		System.out.println("               =;)i=:,=)ITTTTLTTI=:i))I)TTTLLLTTTTTII)i;");
		System.out.println("             +i)ii::,  +)IIITI+:+i)I))TTTTLLTTTTTII))=,");
		System.out.println("           :=;)i=:,,    ,i++::i))I)ITTTTTTTTTTIIII)=+'");
		System.out.println("         .+ii)i=::,,   ,,::=i)))iIITTTTTTTTIIIII)=+");
		System.out.println("        ,==)ii=;:,,,,:::=ii)i)iIIIITIIITIIII))i+:'");
		System.out.println("       +=:))i==;:::;=iii)+)=  `:i)))IIIII)ii+'");
		System.out.println("     .+=:))iiiiiiii)))+ii;");
		System.out.println("    .+=;))iiiiii)));ii+");
		System.out.println("   .+=i:)))))))=+ii+");
		System.out.println("  .;==i+::::=)i=;");
		System.out.println("  ,+==iiiiii+,");
		System.out.println("  `+=+++;`");
		int[] gameInfo = { fuelPoints, cash, tempOfEarth, progress };
		char save = IBIO.inputChar("Do you want to save the game now? (y/n) ");

		if (save == 'y') {
			writeInfo(progress, gameInfo);
		}

		// readInfo();
		loadingClause();
		print("Welcome to Tau Ceti e! This planet is the closest one to planet Earth and is in the \"habitable\" zone.");
		print("But the planet has a very low concentration of oxygen. You cannot survive without a gas mask. See if");
		print("you can find one somewhere here. Here is a world map of this planet to help you get to different places.");

		TicTacToe();

	}

	public void board() {

		System.out.println("  1   2   3");
		System.out.println("1  " +     a +    " | " +    b +     " | " );
		System.out.println("---------------------------------------");
		System.out.println("2  " +     d +    " | " +    e +     " | " );
		System.out.println("----------------------------------------");
		System.out.println("3  " +     g +    " | " +    h +     " | " );

	}

	public void TicTacToe() {
		char turn = 'x';
		while (!finished ())
		{
			board ();
			movePlayer (turn);
			fuelPoints = f;
			/* if (turn == 'x')
         turn = 'o';
       else
         turn = 'x'; */
		}
		print("Congratulations! You have successfully explored Tau Ceti e! As a reward, you get 50 fuel points!You currently have ");
		print((fuelPoints += 50) + " fuel points. These are the places you can visit");
		System.out.println("_________________");
		print("|a." + Planets.FOURTHPLANET.returnName() + " | 490  | 150 ");
		print("|b. HD 40307g   |    42      |   80");
		print("|c. Kepler-22b  | 600  | 250 ");
		System.out.println("_________________");
		char whereToGo = IBIO.inputChar("Where are we headed? ");
		int fuelPointsNeeded = destination(whereToGo);

		while (canTravel(fuelPoints, fuelPointsNeeded) == false) {

			print("Not enough fuel points!!");

			whereToGo = IBIO.inputChar("Where are we headed? " + "(" + fuelPoints + " fuel points" + ") ");

			fuelPointsNeeded = destination(whereToGo);

		}
		
		while (planets.size() < 3) {

		switch(whereToGo) {
		case 'b': 
			planets.add("firstplanet");
			print("Let's go!");
			fuelPoints -= fuelPointsNeeded;
			SecondPlanet o = new SecondPlanet();
			o.setCash(MainGame.cash);
			o.setFuelPoints(MainGame.fuelPoints);
			o.intro();
			break;
		case 'a': 
			planets.add("secondplanet");
			print("Let's go!");
			ThirdPlanet c = new ThirdPlanet();
			c.setCash(MainGame.cash);
			c.setFuelPoints(MainGame.fuelPoints);
			c.intro();
			fuelPoints -= fuelPointsNeeded;
			break;
		case 'c': 
			planets.add("thirdplanet");
			print("Let's go!");
			fuelPoints -= fuelPointsNeeded;
			break;
		}
		
		}
		


	}



	public boolean finished() {
		//complete the other 7 winning conditions
		/*
		 *   1   2   3
       1  a | b | c
       ------------
       2  d | e | f
       ------------
       3  g | h | i
		 */
		if (a != ' ' && b!= ' ' && d!= ' ' && e!= ' ' && g!= ' ' && h!= ' ' && c!= ' ' && f != ' ' && i != ' ' ) {
			return true;
		}
		else 
			return false;
	}

	public void movePlayer(char ab) {
		/*
		 *   1   2   3
      1  a | b | c
      ------------
      2  d | e | f
      ------------
      3  g | h | i
		 */

		int x = IBIO.inputInt("Enter the x-coordinate of the place where you want to go: ");
		int y = IBIO.inputInt("Enter the y-coordinate of the place where you want to go: ");

		while (!moveValid(x, y)) {
			System.out.println("Invalid move!");
			x = IBIO.inputInt("Enter the x-coordinate of the place where you want to go: ");
			y = IBIO.inputInt("Enter the y-coordinate of the place where you want to go: ");
		}

		if (x == 1 && y == 1) {
			a = ab;
			System.out.println("You entered " + x +", " + y);
			 firstPlace();
		}
		else if (x == 2 && y == 1) {
			b = ab;
			System.out.println("You entered " + x + ", " + y);
			secondPlace();
		}
		else if (x == 3 && y == 1 ){ 
			seventhPlace();
		}
		else if (x == 1 && y == 2){
			d = ab;
			System.out.println("You entered " + x +", " + y);
			thirdPlace();
		}
		else if (x == 2 && y == 2) {
			e = ab;
			System.out.println("You entered " + x + ", " +y);
			 fourthPlace();
		}
		else if (x == 3 && y == 3) {
			eighthPlace();
		}
		else if ( x== 3 && y == 2) {
			 ninethPlace();
		}
		else if ( x== 1 && y == 3){
			g = ab;
			System.out.println("You entered " + x +", " + y);
			fifthPlace();
		}
		else if ( x == 2 && y == 3){
			h = ab;
			System.out.println("You entered " + x +", " + y);
			sixthPlace();
		}
	}

	public void ninethPlace() {
        print("Off limits!");
        noEntryBanner();
	}
	public void eighthPlace() {
        print("Off limits!");
        noEntryBanner();
	}
	public void seventhPlace() {
        print("Off limits");
        noEntryBanner();
	}
	public void noEntryBanner() {
		System.out.println("##    ##  #######     ######## ##    ## ######## ########  ##    ## ");
		System.out.println("###   ## ##     ##    ##       ###   ##    ##    ##     ##  ##  ##  ");
		System.out.println("####  ## ##     ##    ##       ####  ##    ##    ##     ##   ####   ");
		System.out.println("## ## ## ##     ##    ######   ## ## ##    ##    ########     ##    ");
		System.out.println("##  #### ##     ##    ##       ##  ####    ##    ##   ##      ##    ");
		System.out.println("##   ### ##     ##    ##       ##   ###    ##    ##    ##     ##    ");
		System.out.println("##    ##  #######     ######## ##    ##    ##    ##     ##    ## ");
	}
	public void sixthPlace() {
		
		System.out.println("  ad8888888888ba");
		System.out.println(" dP'         `\"8b,");
		System.out.println(" 8  ,aaa,       \"Y888a     ,aaaa,     ,aaa,  ,aa,");
		System.out.println(" 8  8' `8           \"88baadP\"\"\"\"YbaaadP\"\"\"YbdP\"\"Yb");
		System.out.println(" 8  8   8              \"\"\"        \"\"\"      \"\"    8b");
		System.out.println(" 8  8, ,8         ,aaaaaaaaaaaaaaaaaaaaaaaaddddd88P");
		System.out.println(" 8  `\"\"\"'       ,d8\"\"");
		System.out.println(" Yb,         ,ad8\"    ");
		System.out.println("  \"Y8888888888P\"");

		print("You found a key! This key is very precious. It must belong to a door of some type.");
		items.add("key");


	}
	
	public void gateAsciiArt() {
		System.out.println("         A                                                                                            A");
		System.out.println("        / \\                                                                                          / \\");
		System.out.println("       _\\_/_                                         /\\  /\\                                         _\\_/_");
		System.out.println("      / _ __\\                                 /\\     \\/  \\/     /\\                                 / _ __\\");
		System.out.println("      \\_____/                                 \\/    <``><''>    \\/                                 \\_____/");
		System.out.println("       |___|                           /\\    <``>    TT  TT    <''>    /\\                           |___|");
		System.out.println("      /     \\                          \\/     TT     ||  ||     TT     \\/                          /     \\");
		System.out.println("     /       \\                  /\\    <``>    ||     ||  ||     ||    <''>    /\\                  /       \\");
		System.out.println("  __/____ ____\\__               \\/     TT    <||>._.<||  ||>._.<||>    TT     \\/               __/____ ____\\__");
		System.out.println(" /_______________\\       /\\    <``>    ||>.=\"\"||,.-. ||  || .-.,||\"\"=.<||    <''>    /\\       /_______________\\");
		System.out.println(" )_______________(       \\/     TT    <||,.-.<||\"   \"||  ||\"   \"||>.-.,||>    TT     \\/       )_______________(");
		System.out.println("  |_|___|___|___|       <``>    ||>.=\"\"||\"   \"||     ||  ||     ||\"   \"||\"\"=.<||    <''>       |_|___|___|___|");
		System.out.println("  |___|___|___|_| /\\     TT    <||,.-.<||     ||\"-_-\"||  ||\"-_-\"||     ||>.-.,||>    TT     /\\ |___|___|___|_|");
		System.out.println("  |_|___|___|___| \\/     ||>.=\"\"||\"   \"||\"-_-\"||> .=<||  ||>=. <||\"-_-\"||\"   \"||\"\"=.<||     \\/ |_|___|___|___|");
		System.out.println("  |___|___|___|_|<``>    ||,.-.<||     ||> .=<||>\"   ||__||   \"<||>=. <||     ||>.-.,||    <''>|___|___|___|_|");
		System.out.println("  |_|___|___|___| TT    <||\"   \"||\"-_-\"||.\"   ||     | /\\\\|     ||   \".||\"-_-\"||\"   \"||>    TT |_|___|___|___|");
		System.out.println("  |___|___|___|_| ||  ,* ||     ||> .=<||>    ||     ||()||     ||    <||>=. <||     || *,  || |___|___|___|_|");
		System.out.println("  |_|___|___|___|_||>*  ,||\"-_-\"||.\"   ||     ||     | __/|     ||     ||   \".||\"-_-\"||,  *<||_|_|___|___|___|");
		System.out.println("  |___|___|___|_|__|,.-.<||> .=<||>    ||     ||     ||  ||     ||     ||    <||>=. <||>.-.,|__|___|___|___|_|");
		System.out.println("   )|___|___|___| ||`   \"||.\"   ||     ||     ||   ,<||  ||>,   ||     ||     ||   \".||\"   '|| |_|___|___|___|");
		System.out.println("  /\\__|___|___|_| ||\"    ||>    ||     ||     ||>,\",\"||  ||\",\",<||     ||     ||    <||    \"|| |___|___|___|_|");
		System.out.println("  |_|___|___|___| ||\"-_-\"||     ||     ||   ,<|| .-. ||  || .-. ||>,   ||     ||     ||\"-_-\"|| |_|___|___|___|");
		System.out.println("  |___|___|___|_| ||> .=<||     ||     ||>,\" \"||\"   \"||  ||\"   \"||\" \",<||     ||     ||>=. <|| |___|___|___|_|");
		System.out.println("  |_|___|___|___|_||>\"   ||     ||>,_,<|| .-.<||     ||  ||     ||>.-. ||>,_,<||     ||   \"<||_|_|___|___|___|");
		System.out.println("  |___|___|___|_|__|     ||>,_,<|| .-. ||\"   \"||\"-_-\"||  ||\"-_-\"||\"   \"|| .-. ||>,_,<||     |__|___|___|___|_|");
		System.out.println("  |_|___|___|___| ||>,_,<|| .-. ||\"   \"||     ||  .=<||  ||>=.  ||     ||\"   \"|| .-. ||>,_,<|| |_|___|___|___|");
		System.out.println("  |___|___|___|_| || .-. ||\"   \"||     ||\"-_-\"||>\"   ||  ||   \"<||\"-_-\"||     ||\"   \"|| .-. || |___|___|___|_|");
		System.out.println("  |_|___|___|___| ||\"   \"||     ||\"-_-\"||  .=<||     ||  ||     ||>=.  ||\"-_-\"||     ||\"   \"|| |_|___|___|___|");
		System.out.println("  |___|___|___|_| ||     ||\"-_-\"||  .=<||>\"   ||     ||  ||     ||   \"<||>=.  ||\"-_-\"||     || |___|___|___|_|");
		System.out.println("  |_|___|___|___|_||\"-_-\"||  .=<||>\"   ||     ||     ||  ||     ||     ||   \"<||>=.  ||\"-_-\"||_|_|___|___|___|");
		System.out.println("  |___|___|___|_|__|  .=<||>\"   ||     ||     ||     ||  ||     ||     ||     ||   \"<||>=.  |__|___|___|___|_|");
		System.out.println("  |_|___|___|___| ||>\"   ||     ||     ||     ||     ||  ||     ||     ||     ||     ||   \"<|| |_|___|___|___|");
		System.out.println("  |___|___|___|_| ||     ||     ||     ||     ||     ||  ||     ||     ||     ||     ||     || |___|___|___|_|");
		System.out.println("  |_|___|___|___| ||>___<||>___<||>___<||>___<||>___<||  ||>___<||>___<||>___<||>___<||>___<|| |_|___|___|___|");
		System.out.println("  |___|___|___|_| \"-----------------------------------\"  \"-----------------------------------\" |___|___|___dv|");
	}
	public void fifthPlace() {
		
		gateAsciiArt();
        
		if (items.contains("key")) { // if the items list contains "key" it means that the user has already obtained the key from one of the other places and can open the door now. This demonstrates the usefulness and importance of lists in my game since I use them frequently to check if certain items are there before executing certain code.
			print("Unlocking the door...");
			print("You found 30 Epsilons!");
			print("As you are about to exit, you find a scroll that has a date of 8400 BC on it. You can't understand ");
			print("the handwriting, so you decide to keep it with you and have someone else read it.");
			print("Suddenly, the writing transforms into English alphabets and the letter is an account of the Five Years ");
			print("conflict between 2 races of aliens. ");
			char choice = IBIO.inputChar("Proceed indoors? (y/n)");
			if (choice == 'y' || choice == 'Y') {
				indoors();
			}
			
			else 
				;
			cash += 30;
		}
		else {
			print("This place is locked. The aliens have some secrets hidden here, and that is why this place has been locked for the last 3 centuries.");
		}

	}
	
	public void indoors() {
	      print("There is a coded message on the wall. Use the following answer key to decode it");
	  	  print ("Key: \"=a, @=b, #=c, $=d, %=e ^=f, [=g, *=h, )=i, (=j, ]=k, &=l,");
		  print ("{=m, }=n, <=o, >=p, \\=q, /=r |=s, !=t, ~=u, ;=v, _=w, +=x, 8=y, 3=z");
		  print("The message reads: |*)%$ \"*%$");
		  String answer = IBIO.inputString("Your answer: ");
		  
		  while (!answer.equalsIgnoreCase("shield ahead")) {
			  print("That is not the right answer!");
			  print ("{=m, }=n, <=o, >=p, \\=q, /=r |=s, !=t, ~=u, ;=v, _=w, +=x, 8=y, 3=z");
			  print("The message reads: |*)%$ \"*%$");
			  answer = IBIO.inputString("Your answer: ");  
		  }
		  
		  print("You got it! You proceed ahead to find a shield that is over a million years old! And has sacred powers!");
		  print("It has been added as a weapon to your inventory!");
		  items.add("shield");
	}
	
	public void fourthPlace() {

		loadingClause();
		print("You found some resources. 10 fuel points for you!");
		fuelPoints += 10;

	}
	public void thirdPlace() {
		
		System.out.println("");
		System.out.println("                                  ()_");
		System.out.println("                                  || `-._.-.");
		System.out.println("                                  ||_    .'");
		System.out.println("                                  || `-.'");
		System.out.println("             +                    ||                    +");
		System.out.println("             |                    ||                    |");
		System.out.println("            / \\               _,,.||.__                / \\");
		System.out.println("           /___\\           ,-'         `-.            /___\\");
		System.out.println("     ______|===|_________,'               `.__________|===|______");
		System.out.println("    /      |===|        /                   \\         |===|      \\");
		System.out.println("   /___________________/                     \\____________________\\");
		System.out.println("   |  +-----+ +-----+  |`-._              _.-'|  +-----+ +-----+  |");
		System.out.println("   |  ||_|_|| ||_|_||  | |. `'----------'' .| |  ||_|_|| ||_|_||  |");
		System.out.println("   |  ||_|_|| ||_|_||  | |:  | ||    :| |  :| |  ||_|_|| ||_|_||  |");
		System.out.println("---| %%---%%+ +%%%--+  | |:__| ||____:| |__:| |  +-%%%-+ %%---%%% |---");
		System.out.println("   |%%%%_%%%%_%%%%%____| |/  | || -  :| |  \\| |___%%%%%_%%%%_%%%%%|");
		System.out.println("     `|   %|   `|'   |`.`-._ | |'    `| |__.-'.'|  %|'   `|   %|MJP");
		System.out.println("                     |`.`-._``---....---''_.-'.'|");
		System.out.println("                     `. `-._``---....---''_.-' .'");
		System.out.println("                       `.   ``---....---''   .'");
		System.out.println("                         `.                .'");
		System.out.println("                           `.            .'");
		System.out.println("                             `.        .'");
		System.out.println("                              ().    .()");
		System.out.println("/\\_/\\_/\\_/\\_/\\_/\\_/\\_/\\_/\\_/\\_||:    :||_/\\_/\\_/\\_/\\_/\\_/\\_/\\_/\\_/\\_/\\");
		System.out.println("||=||=||=||=||=||=||=||=||=||=||:    :||=||=||=||=||=||=||=||=||=||=||");
		System.out.println("\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"      \"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"");
		System.out.println("");
		System.out.println("----------------------------------------------------------------------");
		if (people.contains("Luciano")) {
		print("You are near Luciano's house now. As you are walking, you hear");
		print("an alien running towards you. ");
		}
		else {
			people.add("Luciano");
			print("It reads Luciano on the wall. Let's go talk to him.");
			print("There is an alien headed in your direction!");
		}
		
		System.out.println("         __.,,------.._");
		System.out.println("      ,'\"   _      _   \"`.");
		System.out.println("     /.__, ._  -=- _\"`    Y");
		System.out.println("    (.____.-.`      \"\"`   j");
		System.out.println("     VvvvvvV`.Y,.    _.,-'       ,     ,     ,");
		System.out.println("        Y    ||,   '\"\\         ,/    ,/    ./");
		System.out.println("        |   ,'  ,     `-..,'_,'/___,'/   ,'/   ,");
		System.out.println("   ..  ,;,,',-'\"\\,'  ,  .     '     ' \"\"' '--,/    .. ..");
		System.out.println(" ,'. `.`---'     `, /  , Y -=-    ,'   ,   ,. .`-..||_|| ..");
		System.out.println("ff\\\\`. `._        /f ,'j j , ,' ,   , f ,  \\=\\ Y   || ||`||_..");
		System.out.println("l` \\` `.`.\"`-..,-' j  /./ /, , / , / /l \\   \\=\\l   || `' || ||...");
		System.out.println(" `  `   `-._ `-.,-/ ,' /`\"/-/-/-/-\"'''\"`.`.  `'.\\--`'--..`'_`' || ,");
		System.out.println("            \"`-_,',  ,'  f    ,   /      `._    ``._     ,  `-.`'//         ,");
		System.out.println("          ,-\"'' _.,-'    l_,-'_,,'          \"`-._ . \"`. /|     `.'\\ ,       |");
		System.out.println("        ,',.,-'\"          \\=) ,`-.         ,    `-'._`.V |       \\ // .. . /j");
		System.out.println("        |f\\\\               `._ )-.\"`.     /|         `.| |        `.`-||-\\\\/");
		System.out.println("        l` \\`                 \"`._   \"`--' j          j' j          `-`---'");
		System.out.println("         `  `                     \"`,-  ,'/       ,-'\"  /");
		System.out.println("                                 ,'\",__,-'       /,, ,-'");
		System.out.println("                                 Vvv'            VVv'");
		
		print("You can a) dodge \n b) offer a handshake \n c) spread your arms and welcome him \n d) call for Luciano ");
		char choice = IBIO.inputChar("What will you do? ");

		if (choice == 'A' || choice == 'a') {
			print("You: Phew! That was close.");
		}

		else if (choice == 'b' || choice == 'B') {
			print("The alien attacked you and you died.");
			char userChoice = IBIO.inputChar("Restart from last checkpoint? (y/n)");

			if (restart(userChoice)) {
				thirdPlace();
			}

			else {
				print("Your score: " + score);
				System.exit(0);
			}
		}

		else if (choice == 'c' || choice == 'C') {
			print("The alien was testing your courage. Now, he feels happy that you welcomed him");
			print("instead of running away like the others do. That's the mark of a true warrior.");
		}

		else 
			print("You: Luciano! Luciano!");

		print("Luciano: Tommy! Bad dog! Welcome " + name);
		print("You: Your dog scared me to death, Luciano.");
		print("Luciano: Sorry about that. He's a bit aggressive. Anyways, let me show you around");

		tourOfLucianoHome();

	}

	public void tourOfLucianoHome() {
		print("Luciano: This is my sister, Claudia.");
		System.out.println("         ,-.        ____");
		System.out.println("       ,-. /       ()__ \\____");
		System.out.println("      /  //           _-()__ \\-_");
		System.out.println("      \\  ||  ,-.    _-     , /  -_");
		System.out.println("       \\  \\\\/  |   _-      ./     -_");
		System.out.println("        \\ ,-. /   /\"\\  /\"\\        _-");
		System.out.println("        ,-. //    \\O/  \\O/       _-");
		System.out.println("       /  // `.     ,-.         _-");
		System.out.println("       \\  ||`.,-.   `._;       _-");
		System.out.println("        \\  \\\\/  |`.   -_      _-");
		System.out.println("         \\  /  /`. `. /////\\\\\\\\");
		System.out.println("          \\   /   `. /  ,--,  /");
		System.out.println("           \\  `.    |   `,  \\ |");
		System.out.println("            `.  `.  /    :  / /       _-.");
		System.out.println("              `.  `.    ,` / |      _- . \\");
		System.out.println("                `.  `.,`  /  /    _- .  \\/");
		System.out.println("                  `.     |  |   _- .  \\ /");
		System.out.println("                  | `.   /  / _- .  \\  /");
		System.out.println("                 /    `._)  /-  . \\   /");
		System.out.println("                |           `.   \\  ,`");
		System.out.println("               /              \\   ,`");
		System.out.println("               |                ,`");
		System.out.println("               /              ,`");
		System.out.println("               \\             /");
		System.out.println("                |           |");
		System.out.println("                 \\         / \\");
		System.out.println("                  \\       /   \\");
		System.out.println("                   |     |     \\");
		System.out.println("                   |     |\\     \\");
		System.out.println("                   |     | \\     \\");
		System.out.println("                   |     |  \\     \\");
		System.out.println("                  /      |   \\     \\");
		System.out.println("  .-=\":`-._      /       |    |     \\");
		System.out.println(" (.-=\":.   `-._.`        |  _.`      \\");
		System.out.println("  (.-=\":.                 \\           \\");
		System.out.println("   (.-=\":.      .-=\"`._   / .-=\"`._   /");
		System.out.println("    (._,~=\"\"\"=~(_,~=:....` (_,~=:....`");
		print("You: Hello. ");
		print("Claudia: Hey. It's fascinating to get to meet a human. I have heard a lot about you.");
		print("You: Yes, I am sure you have. I am looking for a place where my species could reside.");
		print("Claudia: Ah yes. I've been studying your planet for a while and the state of your planet");
		print("does not look very good. Anyways, I have to go now. See ya!");
		print("A few moments later...");
		try {
			Thread.sleep (3000);
		} catch (Exception e) {
			;
		}
		print("Luciano: Isn't the place nice?");

		print("a. The place is wonderful. \n b. Meh. I had a better home on Earth. \n c. It's average. \n d. It's awful.");

		char choice = IBIO.inputChar("Your response: ");

		if (choice == 'a' || choice == 'A') {
			print("Luciano: Appreciate it, mate! Here's 30 Epsilons from my sister!");
			cash += 30;
		}

		else if (choice == 'b' || choice == 'B') {
			print("Luciano: Too bad your planet is almost on the verge of destruction.");
		}

		else if (choice == 'c' || choice == 'C') {
			print("Luciano: I thought you'd like it. Oh well, its still way ahead of any of your architectural wonders on Earth");
		}
		else {
			print("Luciano: I see. Get out of my house now. I don't want to see you again. ");
		}

	}

	public void secondPlace() {

		print("You found 30 Epsilons lying around! ");
		cash += 30;

	}
	public void firstPlace() {
		progress = 3;
		char save = IBIO.inputChar("Do you want to save the game now? (y/n) ");
		int[] gameInfo = { fuelPoints, cash, tempOfEarth, progress };

		if (save == 'y') {
			writeInfo(progress, gameInfo);
		}
		loadingClause();
		System.out.println("                  xl\"\"``\"\"lx");
		System.out.println("                 X8Xxx..xxX8X");
		System.out.println("                 8X8bdX8bd8X8");
		System.out.println("                dX8Xbd8XbdX8Xb");
		System.out.println("               dX8Xbd8X8XbdX8Xb");
		System.out.println("              dX8Xbd8X8X8XbdX8Xb");
		System.out.println("            .dX8Xbd8X8X8X8XbdX8Xb.");
		System.out.println("          .d8X8Xbd8X8X8X8X8XbdX8X8b.");
		System.out.println("      _.-dX8X8Xbd8X8X8X8X8X8XdbX8X8Xb-._");
		System.out.println("   .-d8X8X8X8bdX8X8X8X8X8X8X8X8db8X8X8X8b-.");
		System.out.println(".-d8X8X8X8X8bdX8X8X8X8X8X8X8X8X8db8X8X8-RG-b-.");
		System.out.println("             `-'");
		print("You have reached the Monster Volcano. This volcano is ten times the size of");
		print("Mount Everest and very active! At the top of the volcano, you can find the");
		print("most precious resource on the planet. Reaching the top will award you with");
		print("precious fuel points and cash. Although, legend says that anyone who has dared");
		print("to climb the volcano has never come back...so tread carefully.");

		char choice = IBIO.inputChar("Proceed? (y/n) ");

		while(invalidChoice(choice)) {
			print("Enter y or n. ");
			choice = IBIO.inputChar ("Proceed? ");
		}

		if (choice == 'y' || choice == 'Y') {
			volcano();
		}
		else {
			print("Scared? I'd be scared too if I were you.");
		}
	}

	public void volcano() {
		print("While climbing the volcano, you spot an alien! ");
		char userChoice = IBIO.inputChar("Do you want to approach him? ");

		if (userChoice == 'y' || userChoice == 'Y') {
			approachAlien();
		}
		else 
			;
		print("Well, we better get going now. ");
		loadingClause();
		print("And here we are! At the top of Mount Vitalstatistix. But where is the treasure?");

        volcanoGod();
		score += 5;
	}

	public void volcanoGod() {
		
		System.out.println("                                                           / /");
		System.out.println("                                                        | | |  /");
		System.out.println("                                                         \\|_|_/");
		System.out.println("                                                       ,--/.__/--'");
		System.out.println("                       _.-/   _   \\-._                    .'|");
		System.out.println("                     .'::(_,-' `-._)::`.                  |:|");
		System.out.println("                    (:::::::::::::::::::)                .':|");
		System.out.println("                     \\_:::;;;::::;;;:::/    /            |::|");
		System.out.println("             \\        ,---'..\\::/..`-.'    /             |::|");
		System.out.println("              \\       \\_;:....|'...:_ )   /             .'=||");
		System.out.println("               \\.       )---. )_.--< (   /'             ||=||");
		System.out.println("                \\\\     //|:: /--\\:::\\\\\\ //             _||= |");
		System.out.println("                 \\\\   ||::\\:|----|:/:||/--.______,--==' \\ - /");
		System.out.println("          -._     \\`.  \\\\:|:|-- -|:\\:/-.,,\\\\  .----'//'_.`-'");
		System.out.println("      \\.     `-.   \\ \\ _|:|:|-- -|::||::\\,,||-'////---' |/'");
		System.out.println("       \\\\       `._)\\ / |\\/:|-/|--\\:/|. :\\_,'---'       /");
		System.out.println("        \\\\_      /,,\\/:.'\\\\/-.'`-.-//  \\ |");
		System.out.println("        /`\\-    //,,,' |-.\\-'\\--/|-/ ./| |             /");
		System.out.println("         /||-   ||, /| |\\. |.-==-.| . /| |            | /");
		System.out.println(" __  |    /||-  ||,,\\- | .  \\;;;;/ .  .':/         _,-=/-'");
		System.out.println("/  \\//    /||-  ' `,-|::\\ . \\,..,/   /: /         /.-'");
		System.out.println(",--||      /||-/.-.'  \\  `._ `--' _.' .'|        //");
		System.out.println(".--||`.    /||//\\ '   |-'.___`___' _,'|//       /;");
		System.out.println("  /\\| \\     ///\\ /     \\\\_`-.`--`-'_==|'       /;'");
		System.out.println(" / |'\\ \\.   //\\ /       \\_\\__)\\`==-_'_|       / /");
		System.out.println("  .'  \\.=`./|\\ /          \\`-- \\--._/_/------( /");
		System.out.println("       \\.=| `_/|-          |\\`-| -/| `--------'");
		System.out.println("        \\\\` ./|-/-         |\\`-| |-|     ________");
		System.out.println("         `--\\ |=|'        _|\\`-| |-|----'.-._ ..\\`-.");
		System.out.println("             -|-|-     .-':`-;-| |./.-.-( | ||..|=-\\\\");
		System.out.println("             `'= /'   / ,--.:|-| ||_|_|_|_|-'__ .`-._)");
		System.out.println("              /|-|- .' /\\ \\ \\|-` \\\\ ____,---'  `-. ..|");
		System.out.println("               /\\=\\/..'\\ \\_>-'`-\\ \\'              \\ .|");
		System.out.println("               `,-':/\\`.>' |\\/ \\/\\ \\              `- |");
		System.out.println("               //::/\\ \\/_/|-' \\/| \\ `.            / ||");
		System.out.println("              / (:|\\ \\/) \\ \\|.'-'  `-\\\\          |;:|\\_");
		System.out.println("             || |:`-/:.'-|-' \\|       \\\\          `;_\\-`-._");
		System.out.println("             \\\\=/:_/::/\\| \\|          |\\\\            `-._ =`-._");
		System.out.println("              \\_)' |:|                | //               `--.__`-.");
		System.out.println("                   |:|                                         )\\|");
		System.out.println("                   /;/                                         / (\\_");
		System.out.println("                  / /                                         |\\\\;;_`-.");
		System.out.println("                _/ /                                          ' `---\\.-\\");
		System.out.println("               /::||");
		System.out.println("              /:::/");
		System.out.println("             //;;'             ");
		System.out.println("             `-'");
		print("HA! HA! HA! You think you are going to get it that easily.");
		print("You: What was that?!! Whoever you are, come forward!!");
		print("I AM RIGHT HERE. You have shown yourself to a great warrior!");
		print("You have managed to climb my mountain!! But, the real challenge is to get out of here!");
		print("You: Ummm you mean I can't go back now?!");
		print("You are a very intelligent lad I must say! HA! HA! HA!");
		print("To get out of here, we are going to play a little guessing game!");
		print("I am going to think of a number between 1 to 200. You have to guess that number within 7 tries.");
		print("Depending on how many tries it takes you to successfully get the number determines your reward! Kapish?");
		print("You: Bring it on!");
		print("I was looking for a Kapash...Let's start!!");

		randomNumberGame();
	}

	public void randomNumberGame() {

		int number = (int) (Math.random() * 200) + 1;
		print("I have picked a number between 1 to 200");
		int numberOfTurns = 1;
		int guess = IBIO.inputInt("Guess # " + numberOfTurns + ": ");

		while (guess != number && numberOfTurns <= 7) {
			if (guess < number) {
				System.out.println("Too low!");
			}

			else if (guess > number) {
				System.out.println("Too high!");
			}
			numberOfTurns++;

			guess = IBIO.inputInt("Guess # " + numberOfTurns + ": ");

		}

		print("Lucky you, human. You got it in " + numberOfTurns + " guesses.");
		if (numberOfTurns == 1) {
			fuelPoints += 7;
		}
		else if (numberOfTurns == 2) {
			fuelPoints += 6;
		}
		else if (numberOfTurns == 3) {
			fuelPoints += 5;
		}
		else if (numberOfTurns == 4) {
			fuelPoints += 4;
		}
		else if (numberOfTurns == 5) {
			fuelPoints += 3;
		}
		else if (numberOfTurns == 6) {
			fuelPoints += 2;
		}
		else if (numberOfTurns == 7) {
			fuelPoints += 1;
		}
		else {
			print("Since you could not get the number within 7 tries, you shall die.");
			print("You died...");
			char choice = IBIO.inputChar("Do you want to restart from the last checkpoint? (y/n) ");
			if (restart(choice)) {
				randomNumberGame();
			}
			else {
				print("You have failed in your mission, warrior. ");
				print("Your score: " + score);
				char c = IBIO.inputChar("Play again? (y/n)");
				if (c == 'Y' || c == 'y') {
					new MainGame();
				}
				else {
					print("Goodbye then!");
					System.exit(0);
				}
			}
		}



	}

	public boolean restart(char c) {
		if (c == 'y' || c == 'Y') {
			return true;
		}
		else {
			return false;
		}
	}

	public void approachAlien() {
		if (people.contains("Luciano")) {
			print("Its Luciano!");
			print("You: Hey Luciano!");
			print("Hey, person! What are you doing here?");
		}
		else {
			print("You: Hey! Listen I need some help. ");
			print("Alien: Who are you?");
			print("You: I am a human from Planet Earth! My planet is getting destroyed");
			print("and I need to find another planet for my species to survive on.");
			print("Alien: I see. Well, you are welcome to stay here if you'd like.");
			print("You: Thank you so much! By the way, my name is " + name);
			print("Alien: Nice to meet you, " + name + ". I am Luciano. But what are you here for?");
		people.add("Luciano");
		}
		
		System.out.println("       _..._");
		System.out.println("     .'     '.");
		System.out.println("    /`\\     /`\\    |\\");
		System.out.println("   (__|     |__)|\\  \\\\  /|");
		System.out.println("   (     \"     ) \\\\ || //");
		System.out.println("    \\         /   \\\\||//");
		System.out.println("     \\   _   /  |\\|`  /");
		System.out.println("      '.___.'   \\____/");
		System.out.println("       (___)    (___)");
		System.out.println("     /`     `\\  / /");
		System.out.println("    |         \\/ /");
		System.out.println("    | |     |\\  /");
		System.out.println("    | |     | \"`");
		System.out.println("    | |     |");
		System.out.println("    | |     |");
		System.out.println("    |_|_____|");
		System.out.println("   (___)_____)");
		System.out.println("   /    \\   |");
		System.out.println("  /   |\\|   |");
		System.out.println(" //||\\\\  Y  |");
		System.out.println("|| || \\\\ |  |");
		System.out.println("|/ \\\\ |\\||  |");
		System.out.println("    \\||__|__|");
		System.out.println("     (___|___)");
		System.out.println("     /   A   \\");
		System.out.println("    /   / \\   \\");
		System.out.println("   \\___/   \\___/");

		print("a. Just exploring the planet. \n b. I want to get to the top of this volcano \n c. None of your business. \n d. Oh, umm...");
		char choice = IBIO.inputChar("Your response: ");

		if (choice == 'a' || choice == 'A') {
			print("Alien: Very well, then. I liked what I saw in you. I always used to think ");
			print("that humans are the most outdated race in history. You guys can't do anything well.");
			print("But I have seen something in you that I like. Here, have some cash.");

			System.out.println("Bonus cash: 10 epsilons.");
			cash += 10;
		}

		else if (choice == 'b' || choice == 'B') {
			print("Alien: There is a treasure that lies over at the top. But millions of aliens ");
			print("have tried to get to the top and everyone has failed. It'd be entertaining to see");
			print("a pesky human like you even get halfway across the mountain. See ya around!");
		}

		else if (choice == 'c' || choice == 'C') {
			print("Alien: Okay. You know, I was going to lend you some cash in order to help your effort");
			print("in saving your species. But since you have to be so secretive about yourself, never mind. ");
		}

		else 
			print("Alien: I understand. Well, see you around then!");
	}

	public boolean moveValid(int aa, int bb) {
		if (aa > 2 && bb > 3) {
			return false;
		}

		if (aa == 1 && bb == 1 && a != ' ') {
			System.out.println("Already visited that position!");
			return false;
		}
		else if (aa == 2 && bb == 1 && b != ' ') {
			System.out.println("Already visited that position!");
			return false;
		}
		else if (aa == 3 && bb == 1 && c != ' ') {
			System.out.println("Already visited that position!");
			return false;
		}
		else if (aa == 1 && bb == 2 && d != ' ') {
			System.out.println("Already visited that position!");
			return false;
		}
		else if (aa == 2 && bb == 2 && e != ' ') {
			System.out.println("Already visited that position!");
			return false;
		}
		else if (aa== 3 && bb == 2 && f != ' ') {
			System.out.println("Already visited that position!");
			return false;
		}
		else if ( aa == 1 && bb == 3 && g == 'x') {
			if (items.contains("key")) {
				;
			}
			else {
			System.out.println("Already visited that position!");
			return false;
			}
		}
		else if ( aa == 2 && bb == 3 && h != ' ') {
			System.out.println("Already visited that position!");
			return false;
		}
		else if ( aa == 3 && bb == 3 && i != ' ') {
			System.out.println("Already visited that position!");
			return false;
		}
		return true;
	}

	public int destination(char a) {

		int fuelPointsNeeded;

		if (a == 'a') {
			fuelPointsNeeded = Planets.THIRDPLANET.returnFuelPoints();
			return fuelPointsNeeded;
		}
		else if (a == 'b') {
			fuelPointsNeeded = Planets.SECONDPLANET.returnFuelPoints();
			return fuelPointsNeeded;
		}
		else if (a == 'c') {
			fuelPointsNeeded = Planets.FIRSTPLANET.returnFuelPoints();
			return fuelPointsNeeded;
		}
		else {
			fuelPointsNeeded = Planets.FOURTHPLANET.returnFuelPoints();
			return fuelPointsNeeded;
		}
	}

	public void loadingClause() {
		System.out.print("Loading");
		for (int i = 0; i < 4; i++) {
			System.out.print(".");
			try {
				Thread.sleep(1000);
			} catch(Exception e) {
				;
			}
		}
		System.out.println();
	}

	public boolean canTravel(int a, int b) {

		if (a < b) {
			return false;
		}

		else {
			return true;
		}
	}

	public void print(String a) {

		for (int i = 0; i < a.length(); i++) {
			System.out.print("" + a.charAt(i)); 

			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				;
			}

		}

		System.out.println("");



	}

	public enum Planets {
		/* Planet Distance from Earth (in light-years)
        Kepler-186f 490
  Gliese 581g 20
  Kepler-22b 600
  HD 40307g 42
  Tau Ceti e 11.905 */

		// FIRSTPLANET("Tau Ceti e", 11.905, 5),
		FIRSTPLANET("Gliese 581g", 20, 5),
		SECONDPLANET("HD 40307g", 42, 80),
		THIRDPLANET("Kepler-186f", 490, 150),
		FOURTHPLANET("Kepler-22b", 600, 250);

		String planetName;
		double distanceFromEarth;
		int fuelPointsNeeded;

		Planets(String planetName, double distanceFromEarth, int fuelPointsNeeded) {
			this.planetName = planetName;
			this.distanceFromEarth = distanceFromEarth;
			this.fuelPointsNeeded = fuelPointsNeeded;
		}

		public String returnName() {
			return planetName;
		}

		public double returnDistance() {
			return distanceFromEarth;
		}

		public int returnFuelPoints() {
			return fuelPointsNeeded;
		}

	}

	/*Runnable runnable = new Runnable() {
  public void run() {

  }
 }; */

	@Override
	public void run() {

		intro();

	}

	public void respawn(int p, int c, int f) {
		ThirdPlanet t;
		if (p == 1) 
			move();
		else if (p == 2) {
			firstPlanet();
		}
		else if (p == 0) {
			intro();
		}
		else if (p == 4) {
			SecondPlanet s = new SecondPlanet();
			s.setCash(c);
			s.setFuelPoints(f);
			s.secondPlace();
		}
			
		else if (p == 5) {
			SecondPlanet s = new SecondPlanet();
			s.setCash(c);
			s.setFuelPoints(f);
			s.secondPlace();
		}
		
		else if (p == 10) {
			day1();
		}
		else if (p == 8) {
			ThirdPlanet z = new ThirdPlanet();
			z.setFuelPoints(f);
			z.setCash(c);
			z.intro();
		}
		else if (p == 9) {
			ThirdPlanet z = new ThirdPlanet();
			z.setFuelPoints(f);
			z.setCash(c);
			z.searchForest();
		}

	}

	public boolean invalidChoice(char a) {
		if (a != 'n' || a != 'N' || a != 'y' || a != 'Y')
			return false;
		else 
			return true;

	}

	/* public void doomsdayClock() {

  Date today = new Date();
  Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 2015);
      calendar.set(Calendar.MONTH, Calendar.MAY );
      calendar.set(Calendar.DAY_OF_MONTH, 16);
      calendar.set(Calendar.HOUR_OF_DAY, 14);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      System.out.println("Today:  "
           + SimpleDateFormat.getDateTimeInstance().format(today));
      System.out.println("Target: "
           + SimpleDateFormat.getDateTimeInstance().format(calendar.getTime()));
      System.out.println();
      long togo = (calendar.getTimeInMillis() - today.getTime()) / 1000;
      // togo in seconds
      System.out.println("Seconds to go: " + (togo % 60));
      togo = togo / 60;
      // togo in minutes
      System.out.println("Minutes to go: " + (togo % 60));
      togo = togo / 60;
      // togo in hours
      System.out.println("Hours to go:   " + (togo % 24));
      togo = togo / 24;
      // togo in days
      System.out.println("Days to go:    " + (togo % 7));
      togo = togo / 7;
      // togo in weeks
      System.out.println("Weeks to go:   " + (togo)); 
     } */

	public void Timer() {
		long DAY_IN_MS = 1000 * 60 * 60 * 24;
		Date destruct = new Date(System.currentTimeMillis() + (4 * DAY_IN_MS));
	}

}




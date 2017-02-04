import java.util.*;

public class ThirdPlanet extends MainGame {

	List<String> weapons = new ArrayList<>();
	List<String> items = new ArrayList<>();
	List<String> places = new ArrayList<>();
	static int money = 0;
	static int fuelPts = 0;
	
	public static void main(String args[]) {
		ThirdPlanet o = new ThirdPlanet();
		o.intro();
	}

	Thread blackFlag = new Thread() {
		public void run() {
			try {
				startSong("Assassin_39_s_Creed_4_Black_Flag_OST_-_Main_Theme.wav");
				Thread.yield();
			}
			catch (Exception e) {
				print("Audio file couldn't be played!");
			}
		}
	};
	
	Thread victorySong = new Thread() {
		public void run() {
		try {
			startSong("Far_Cry_3_-_Main_Theme_Soundtrack_OST_mp3cut.wav");
			Thread.yield();
		} catch (Exception e) {
			print("Song could not be played");
		}
		}
	};

	public void intro() {

		progress = 8;
		char save = IBIO.inputChar("Do you want to save the game now? (y/n) ");
		int[] gameInfo = { fuelPts, money, tempOfEarth, progress };

		if (save == 'y' || save == 'Y') {
			writeInfo(progress, gameInfo);
		}

		acSong.interrupt();
		zeldaSong.interrupt();
		blackFlag.start();
		System.out.println("                                                .");
		System.out.println("                         *");
		System.out.println("           .                       .                   .");
		System.out.println("                              .               ,\"`.");
		System.out.println("    .                   _____                 `..'");
		System.out.println("                   _,odO8O8888bo._         .");
		System.out.println("                ,odOoOoOoOOOO8O888bo.                  *");
		System.out.println("        *     ,dOoOoOoOoOoOO8O8O88@8@b.           .");
		System.out.println("            ,d8o:o:o:o:o:ooOoOOOO8O88@8b.");
		System.out.println("           do:o:o:o:o:o:ooooOoOO8O88@8@@@b");
		System.out.println("  .       do::.:.:.:.:.::o:ooooOOOO8888@@@b           .");
		System.out.println("         .o::::.:.:.:.::::o:ooOoOO8O88@@@@@. .");
		System.out.println("      .  |::.:.. . . ..:.::o:ooOoOO8O88@8@@|");
		System.out.println("         |o::.:.. . ..:.::o:ooOoOO8O88@8@@@|");
		System.out.println("         |::.:.. . . ..:.::o:ooOoOO8O88@8@@|");
		System.out.println("         'o::::.:.:.:.::::o:ooOoOO8O88@@@@@'     .         *");
		System.out.println("          Y8::.:.:.:.:.::o:ooooOOOO8888@@@P");
		System.out.println("          `Y8:o:o:o:o:o:ooooOoOO8O88@8@@@P'");
		System.out.println("            `Y:o:o:o:o:o:ooOoOOOO8O88@8@P     .        .");
		System.out.println("   .        . `YOoOoOoOoOoOO8O8O88@8@@P'");
		System.out.println("                `YoOoOoOoOOOO8O8888@P'     .");
		System.out.println("                   `\"*YO8O8888@P*\"'");
		System.out.println("          .                          *             .       .");
		System.out.println("  ");
		System.out.println("                               .                      .");
		System.out.println("      *            .                       .        ");

		print("You have successfully landed on Kepler 186-f! This planet is a bit different compared");
		print("to all the other planets you have been to. It's because this planet does not have any");
		print("intelligent life form. But legend does say that a monster the twice the size of Mt.Everest");
		print("roams the lands of this planet and does not allow any other species to thrive. While completing");
		print("your quests, you might catch a glimpse of this monster. To move on, you need to ultimately ");
		print("defeat this monster and claim this planet as yours. After that, you can start developing buildings ");
		print("and once you have the basic buildings ready, we will get started with a new form of civilization.");
		TicTacToe();
	}

	public void firstPlace() {
		loadingClause();
		print("You have arrived to face your very first quest. This quest requires you to search for a letter that was ");
		print("left here by one of the aliens that visited this place before. It has information regarding the Jackdaw,");
		print("an ancient knife hidden somewhere here. If you can find it, the knife will replace your current knife and ");
		print("is able to do more damage. You will need it to defeat the horror that awaits you towards the end of your journey");
		print("Good luck...");

		knife();
	}

	public void knife() {
		char choice = ' ';
		while (places.size() <= 4) {
			print("a) The Broken Shellelaigh \nb) The Tomb of Tatarus \nc) The Fields of Farrat \nd) The Haunted Forest \ne) Head to the exit");
			choice = IBIO.inputChar("Where are we headed? ");

			switch (choice) {
			case 'a':
			case 'A':
				places.add("BrokenShellelagih");
				a = 'X';
				broken();
				break;
			case 'b':
			case 'B':
				places.add("Tomb");
				b = 'X';
				tomb();
				break;
			case 'c':
			case 'C':
				places.add("TheFielsofFarrat");
				c = 'X';
				field();
				break;
			case 'd':
			case 'D':
				places.add("Forrest");
				d = 'X';
				forest();
				break;
			case 'e':
			case 'E':
				return;
			}
		}
	}
	private void forest() {

		System.out.println("       |  |   \\\\|.'    |  |    \\\\|.'   |  |     \\\\|.'  |  |");
		System.out.println("       | ||   \\` /   _.| ||,!  \\` /   _| ||\\,!  \\` /   | ||");
		System.out.println("       ||||`. f |_.-'.'||||\\`. f |_.-'.|||| \\`. f |_.-'||||");
		System.out.println("       | ||\\ \\|! ,-'   | || \\ \\|! ,-'M | ||  \\ \\|! ,-' | ||");
		System.out.println("       || |W`. ||  N   || |  `. ||   N || |   `. ||    || |");
		System.out.println("       ||||H `. |  W   ||||  H`. |   W ||||    `. |    ||||");
		System.out.println("       || |N  |L|  M   || |  N |L|   M || |    N|L|    || |");
		System.out.println("       | ||W  ||]  H   | ||  W ||]   H | ||    W||]    | ||");
		System.out.println("       ||||M  [ I  W   ||||  M [ I   W ||||    M[ I    ||||");
		System.out.println("       || |H  I |  M   || |  H I |   M || |    HI |    || |");
		System.out.println("       | [|N, !l| ,H\\  | [| /N,!l|  ,H\\| [|   /N!l|    | [|");
		System.out.println("       || |   '-`      || |    '-`     || |     '-`    || |");
		System.out.println("      / '| \\          / '| \\          / '| \\          / '| \\");

		if (places.contains("TheFielsofFarrat") && places.contains("Tomb") && places.contains("BrokenShellelagih")) {
			print("This is the last place in your conquest. The letter must be somewhere here.");
			print("Yes, there it is! You found the letter! The letter says the following: ");
		}
		else {

			print("You are now in the forest. The forest has very dense and tall trees, allowing no sunlight to come through");
			print("You need a flashlight to look for the letter. You can get the flashlight by winning at a mini guessing game");
			int random = (int)(Math.random() * 10) + 1;

			print("I have guessed a number between 1 and 10. You have 3 chances to get it right. If you lose, you cannot continue");
			print("as you won't get the flashlight. ");
			
			for (int i = 1; i <= 3; i++) {

			int userGuess = IBIO.inputInt("Guess #" + i + ": ");

			if (userGuess == random) {
				print("That is correct!");
				print("The flashlight is yours!");
				items.add("flashlight");
				searchForest();
				break;
			}

			else {
				print("Restarting from last checkpoint...");
				forest();
				break;
			}
		}
		}

	}

	public void searchForest() {

		progress = 9;
		char save = IBIO.inputChar("Do you want to save the game now? (y/n) ");
		int[] gameInfo = { fuelPts, money, tempOfEarth, progress };

		if (save == 'y' || save == 'Y') {
			writeInfo(progress, gameInfo);
		}

		List<String> p = new ArrayList<>();
		while (p.size() <= 3) {
			print("a) Look in the ditch \nb) Look in the nearby cottage \nc) Search for it in the mud");
			char choice = IBIO.inputChar("Your choice: ");

			while (choice != 'a' && choice != 'A' && choice != 'b' && choice != 'B' && choice != 'c' && choice != 'C') {
				print("Not a valid choice");
				print("a) Look in the ditch \nb) Look in the nearby cottage \nc) Search for it in the mud");
				choice = IBIO.inputChar("Your choice: ");
			}

			if (choice == 'a' || choice == 'A') {
				p.add("ditch");
				print("Searching... ");
				sleep(3000);
				print("Nothing here...");
			}

			else if (choice == 'b' || choice == 'B') {

				p.add("cottage");
				cottage();
			}

			else {
				p.add("mud");
				print("Searching in the mud...");
				sleep(3000);
				print("Nothing here either");
			}
		}

	}

	public void TicTacToe() {
		char turn = 'x';
		while (!finished ())
		{
			board ();
			movePlayer (turn);
			/* if (turn == 'x')
      turn = 'o';
    else
      turn = 'x'; */
		}
		blackFlag.interrupt();
		victorySong.start();
		print("You have claimed this planet as yours, warrior. You have proven yourselves to be worthy of the saviour of the people");
		print("Congratulations and thanks for playing the game!");
		char choice = IBIO.inputChar("Play again? (y/n)");
		
		if (choice == 'y' || choice == 'Y') {
			(new Thread(new MainGame())).start();
		}
		else {
			System.exit(0);
		}

	}

	public void cottage() {
		loadingClause();

		System.out.println("                             +&-");
		System.out.println("                           _.-^-._    .--.");
		System.out.println("                        .-'   _   '-. |__|");
		System.out.println("                       /     |_|     \\|  |");
		System.out.println("                      /               \\  |");
		System.out.println("                     /|     _____     |\\ |");
		System.out.println("                      |    |==|==|    |  |");
		System.out.println("  |---|---|---|---|---|    |--|--|    |  |");
		System.out.println("  |---|---|---|---|---|    |==|==|    |  |");
		System.out.println(" ^jgs^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

		print("You: This cottage looks to be very old. I wonder who lived here? ");
		print("Stop in your tracks, you human! ");
		print("You: What? Who is it? ");
		print("I am the ghost of this cottage. I have roamed this house since ages now.");
		System.out.println("                                          .\"\"--.._");
		System.out.println("                                           []      `'--.._");
		System.out.println("                                           ||__           `'-,");
		System.out.println("                                         `)||_ ```'--..       \\");
		System.out.println("                     _                    /|//}        ``--._  |");
		System.out.println("                  .'` `'.                /////}              `\\/");
		System.out.println("                 /  .\"\"\".\\              //{///    ");
		System.out.println("                /  /_  _`\\\\            // `||");
		System.out.println("                | |(_)(_)||          _//   ||");
		System.out.println("                | |  /\\  )|        _///\\   ||");
		System.out.println("                | |L====J |       / |/ |   ||");
		System.out.println("               /  /'-..-' /    .'`  \\  |   ||");
		System.out.println("              /   |  :: | |_.-`      |  \\  ||");
		System.out.println("             /|   `\\-::.| |          \\   | ||");
		System.out.println("           /` `|   /    | |          |   / ||");
		System.out.println("         |`    \\   |    / /          \\  |  ||");
		System.out.println("        |       `\\_|    |/      ,.__. \\ |  ||");
		System.out.println("        /                     /`    `\\ ||  ||");
		System.out.println("       |           .         /        \\||  ||");
		System.out.println("       |                     |         |/  ||");
		System.out.println("       /         /           |         (   ||");
		System.out.println("      /          .           /          )  ||");
		System.out.println("     |            \\          |             ||");
		System.out.println("    /             |          /             ||");
		System.out.println("   |\\            /          |              ||");
		System.out.println("   \\ `-._       |           /              ||");
		System.out.println("    \\ ,//`\\    /`           |              ||");
		System.out.println("     ///\\  \\  |             \\              ||");
		System.out.println("    |||| ) |__/             |              ||");
		System.out.println("    |||| `.(                |              ||");
		System.out.println("    `\\\\` /`                 /              ||");
		System.out.println("       /`                   /              ||");
		System.out.println("      /                     |              ||");
		System.out.println("     |                      \\              ||");
		System.out.println("    /                        |             ||");
		System.out.println("  /`                          \\            ||");
		System.out.println("/`                            |            ||");
		System.out.println("`-.___,-.      .-.        ___,'            ||");
		System.out.println("         `---'`   `'----'`");
		print("What do you seek here? ");
		print("a) I am looking for a letter \nb) I am a big fan of yours \nc) I came here by mistake ");

		char userChoice = IBIO.inputChar("Your choice: ");

		if (userChoice == 'a' || userChoice == 'A') {
			print("Ghost: A letter?! There is no letter here! Be gone! ");
		}

		else if (userChoice == 'b' || userChoice == 'B') {
			print("Ghost: Oh really?! I am delighted to find a fan of mine! In fact, I am so happy, ");
			print("that I am ready to give you a signed ghost sword of mine. Here you go, lad!");
			weapons.add("ghostSword");
		}

		else if (userChoice == 'c' || userChoice == 'C') {
			print("Ghost: Alright, now be gone!"); 
		}

		print("a) You can fight the ghost \nb) You can leave the place");
		userChoice = IBIO.inputChar("Your choice: ");

		if (userChoice == 'a') {
			fightGhost(); 
		}

		else 
			;

	}

	public void fightGhost() {

	}

	private void field() {

		if (places.contains("Forest") && places.contains("Tomb") && places.contains("BrokenShellelagih")) {
			print("This is the last place in your conquest. The letter must be somewhere here.");
			print("Yes, there it is! You found the letter! The letter says the following: ");
		}

		else {
			loadingClause();
			print("You are now at the Fields of Farrat. These fields are said to be a very sacred place. As you are looking around ");
			print("for the letter, you spot a glimpse of the monster! You decide on if you want to fight him right now or get more powerful");
			print("and come back later. ");

			char choice = IBIO.inputChar("Fight him? (y/n)");
			if (choice == 'y') {
				fight();
			}

			else {
				print("Continuing on...you find some resources (30 fuel points). You also find a scrambled word: edi.");
				fuelPts += 30;
				print("It says die! The monster knows of your presence and could attack you any second! You have to be ready");
				print("with your supplies and weapons! "); 
				char userChoice = IBIO.inputChar("Continue? (y/n)");

				if (userChoice == 'y') {
					print("You found a laser pistol left by one of the monster's henchmen!");
					weapons.add("laserpistol");
				}

				else 
					;
			}

		}

	}

	public void fight() {
		loadingClause();
	}

	private void tomb() {

		if (places.contains("TheFielsofFarrat") && places.contains("Forest") && places.contains("BrokenShellelagih")) {
			print("This is the last place in your conquest. The letter must be somewhere here.");
			print("Yes, there it is! You found the letter! The letter says the following: take a hold of the knife and ");
			print("the monster will be killed. You found the knife buried right beneath the letter and as soon as your hold");
			print("it, a tremendous shake shakes up the entire planet. The monster has died. ");
		}

		else {
			loadingClause();
			print("You have reached The Tomb of Tatarus. ");
			print("Once you go inside, you find a tablet of text inside that is engraved into the wall.");
			print("It seems to contain a hidden message...it says \"7A4K iels aeahd\"");
			String decode = IBIO.inputString("Decode this message: ");

			while (!decode.equalsIgnoreCase("Ak47 lies ahead")) {
				print("Incorrect! Try again!");
				decode = IBIO.inputString("Decode again: ");
			}

			print("Correct! Your weapons awaits you ahead...");
			weapon();

		}

	}

	public void weapon() {

		items.add("gun");
		print("Seems like this was an ambush!");
		print("You see henchmen of the great monster all around you. Let's see how you utilize your new weapon!");
		print("Right now, your weapon only has 6 bullets but there are 12 enemies. You need to shoot in strategic");
		print("spots to be able to win.");
		print("a) North \nb) North-West \nc) North-East");
		char choice = IBIO.inputChar("Which direction: ");
		while (choice != 'a' && choice != 'A' && choice != 'b' && choice != 'B' && choice != 'c' && choice != 'C') {
			print("Not a valid choice!");
			choice = IBIO.inputChar("Which direction: ");
		}

		if (choice == 'a' || choice == 'A') {
			print("You shot right in front of you and the bullet only hit one soldier. All the other soldiers killed you. ");
			print("Respawning...");
			weapon();
		}

		else if (choice == 'b' || choice == 'B') {
			print("There was a fire barrel in that direction which ignited upon your bullet and killed all of the henchmen!");
			print("Now that is called 12 kills in 1 shot! As a reward, you get 50 fuel points!");
			fuelPts += 50;
		}
		else {
			print("There was a box tied to the ropes in that direction. Your shot hit the rope and the box fell on 6 of the soldiers");
			print("and killed them! There are 6 left and you have 5 bullets left.");
			print("a) South \nb) North-West \nc) North-East");
			choice = IBIO.inputChar("Which direction: ");
			if (choice == 'a' || choice == 'A') {
				print("There was a fire barrel in that direction and killed all the soldiers! 30 fuel points for you!");
				fuelPts += 30;
			}

			else {
				print("The soldiers killed you! Restarting from last checkpoint...");
				weapon();
			}

		}
		// solve this
	}

	private void broken() {

		if (places.contains("TheFielsofFarrat") && places.contains("Tomb") && places.contains("Forest")) {
			print("This is the last place in your conquest. The letter must be somewhere here.");
			print("Yes, there it is! You found the letter! The letter says the following: ");
			print("Go to the Mountain. You will find it there. ");
			print("By it, the letter must be referring to the knife!");
			print("Let's go!");
			mountain();
		}

		else {
			loadingClause();
			print("You found 30 Epsilons!");
			cash += 30;
		}

	}

	public void mountain() {
		loadingClause();
	}

	/* public void secondPlace() {

 } */

	public void sleep(int n) {

		try {

			Thread.sleep(n);

		}  catch (Exception e) {
			; 
		}

	}

	/* public void thirdPlace() {

 } */

	public void fourthPlace() {
		loadingClause();
		print("You have arrived at the next quest. This quest requires you to battle one of the monster's evil minions ");
		print("in order to get some information regarding the monster out of them. ");
		print("Minion: Hey you! If you have even a little bit of courage, then come and fight us! Humans like you are ");
		print("too inferior to our race anyways. Ha ha ha!");
		print("You: Bring it on!");
		diceGame();
	}

	public void diceGame() {
		print("Beat the minion leader in a mini dice game. Each of you will throw a pair of dice and get 5 turns to do so.");
		print("The goal of the game is to rack up 42 points. However, they must be exactly 42 points. Or you can skip a turn ");
		print("and have the other person go above 42 points to guarantee you a victory! Let's begin!");

		int turnCounter = 0;
		int totalPointsUser = 0;
		int totalPointsAlien = 0;
		while(!gOver(totalPointsUser, totalPointsAlien)) {
			if (turnCounter % 2 == 0) {
				totalPointsUser = userTurn();
			}
			else
				totalPointsAlien = alienTurn();
			turnCounter++;
		}

		print("Game over!");

		char w = win(totalPointsUser, totalPointsAlien);

		if (w == 'u') {
			print("You won!");
			cash += 100;
		}

		else {
			print("You lost!");
			print("Restarting from last checkpoint...");
			diceGame();
		}
	}

	public int userTurn() {
		int userdice1 = (int) (Math.random() * 6) + 1;
		int userdice2 = (int) (Math.random() * 6) + 1;
		int totalPointsUser = userdice1 + userdice2;
		print("Rolling dice...");
		print("You rolled a " + userdice1 + " and a " + userdice2);
		return totalPointsUser;
	}

	public int alienTurn() {
		int aliendice1 = (int) (Math.random() * 6) + 1;
		int aliendice2 = (int) (Math.random() * 6) + 1;
		int totalPointsAlien = aliendice1 + aliendice2;
		print("Alien is rolling dice...");
		print("Alien rolled a " + aliendice1 + " and a " + aliendice2);
		return totalPointsAlien;
	}

	public char win(int u, int a) {
		if (u < 42 && a > 42) {
			return 'u';
		}

		else if (u == 42 && a < 42) {
			return 'u';
		}

		else if (u < 42 && a == 42) {
			return 'a';
		}

		else if (u > 42 && a < 42) {
			return 'a';
		}

		else 
			return ' ';


	}

	public boolean gOver(int u, int a) {
		if (u > 42 || a > 42) {
			return true;
		}

		else if (u == 42 || a == 42) {
			return true;
		}

		else
			return false;
	}

	public void fifthPlace() {
		loadingClause();
		print("You found 60 Epsilons!");
		money += 60;

	}

	public void sixthPlace() {

		SecondPlanet o = new SecondPlanet();
		o.cave();

	}

	/* public void seventhPlace() {

 } */

	public void eighthPlace() {
		print("You found 30 Epsilons!");
	}

	public void ninethPlace() {
		loadingClause();
		print("This is the store! You can buy the following weapons!");
		print("a) Spas shotgun (50 Epsilons) \nb) Flamethrower (60 Epsilons) \nc) Crossbow (100 Epsilons)");
		char choice = IBIO.inputChar("Your choice: ");
		validateWeapon(choice, money);
	}

	public void validateWeapon(char c, int m) {
		if (c == 'a') {
			if (m < 100) {
				print("Not enough money!");
				ninethPlace();
			}
			else {
				print("Crossbow aquired!");
				weapons.add("crossbow");
			}
		}
		else if (c == 'b') {
			if (m < 50) {
				print("Not enough money!");
				ninethPlace();
			}
			else {
				print("Shotgun aquired");
				weapons.add("shotgun");
			}
		}
		else {
			if (m < 60) {
				print("Not enough money!");
				ninethPlace();
			}
			else {
				print("Shotgun aquired!");
				weapons.add("shotgun");
			}
		}
	}

	public int getFuelPoints() {
		return fuelPts;
	}

	public void setFuelPoints(int newPts) {
		fuelPts = newPts;
	}

	public void setCash(int c) {
		money = c;
	}

}

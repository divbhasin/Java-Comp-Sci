import java.util.*;

import javax.swing.JOptionPane;
public class SecondPlanet extends MainGame {
	
	static int fuelPts = 0;
	static int cashNew = 0;
	static List<String> places = new ArrayList<>();
	static List<String> items = new ArrayList<>();
	
	@Override
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
		print("Congratulations! You have successfully explored HD 40307g! As a reward, you get 100 fuel points! You currently have");
		fuelPoints = getFuelPoints() + 100;
		print((fuelPoints) + " fuel points. These are the places you can visit");
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
		case 'a': 
			planets.add("secondplanet");
			print("Let's go!");
			fuelPoints -= fuelPointsNeeded;
			SecondPlanet o = new SecondPlanet();
			break;
		case 'b': 
			planets.add("firstplanet");
			print("Let's go!");
			ThirdPlanet c = new ThirdPlanet();
			c.setCash(SecondPlanet.cashNew);
			c.setFuelPoints(SecondPlanet.fuelPts);
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


	public void intro() {
		zeldaSong.interrupt();
		acSong.start();

	    progress = 4;
	    char save = IBIO.inputChar("Do you want to save the game now? (y/n) ");
	    int[] gameInfo = { fuelPoints, cashNew, tempOfEarth, progress };
	    
	    if (save == 'y' || save == 'Y') {
	    	writeInfo(progress, gameInfo);
	    }

		fuelPts = f;
		System.out.println("Fuel points is " + f);
		print("Welcome to HD 40307 g! This planet is located about 42 light years from Earth.");
		print("It would take us 42 years to reach this planet even if we travel at the speed");
		print("of light, the fastest thing in the entire universe! But lucky for you, you have");
		print("a gun that creates wormholes. Without further a do, let's get to it.");
		TicTacToe();
	}
	
	@Override
	public void firstPlace() {
		loadingClause();
		
		System.out.println("                                  .-.");
		System.out.println("                                 /___\\");
		System.out.println("                                 |___|");
		System.out.println("                                 |]_[|");
		System.out.println("                                 / I \\");
		System.out.println("                              JL/  |  \\JL");
		System.out.println("   .-.                    i   ()   |   ()   i                    .-.");
		System.out.println("   |_|     .^.           /_\\  LJ=======LJ  /_\\           .^.     |_|");
		System.out.println("._/___\\._./___\\_._._._._.L_J_/.-.     .-.\\_L_J._._._._._/___\\._./___\\._._._");
		System.out.println("       ., |-,-| .,       L_J  |_| [I] |_|  L_J       ., |-,-| .,        .,");
		System.out.println("       JL |-O-| JL       L_J%%%%%%%%%%%%%%%L_J       JL |-O-| JL        JL");
		System.out.println("IIIIII_HH_'-'-'_HH_IIIIII|_|=======H=======|_|IIIIII_HH_'-'-'_HH_IIIIII_HH_");
		System.out.println("-------[]-------[]-------[_]----\\.=I=./----[_]-------[]-------[]--------[]-");
		System.out.println(" _/\\_  ||\\\\_I_//||  _/\\_ [_] []_/_L_J_\\_[] [_] _/\\_  ||\\\\_I_//||  _/\\_  ||\\");
		System.out.println(" |__|  ||=/_|_\\=||  |__|_|_|   _L_L_J_J_   |_|_|__|  ||=/_|_\\=||  |__|  ||-");
		System.out.println(" |__|  |||__|__|||  |__[___]__--__===__--__[___]__|  |||__|__|||  |__|  |||");
		System.out.println("IIIIIII[_]IIIII[_]IIIIIL___J__II__|_|__II__L___JIIIII[_]IIIII[_]IIIIIIII[_]");
		System.out.println(" \\_I_/ [_]\\_I_/[_] \\_I_[_]\\II/[]\\_\\I/_/[]\\II/[_]\\_I_/ [_]\\_I_/[_] \\_I_/ [_]");
		System.out.println("./   \\.L_J/   \\L_J./   L_JI  I[]/     \\[]I  IL_J    \\.L_J/   \\L_J./   \\.L_J");
		System.out.println("|     |L_J|   |L_J|    L_J|  |[]|     |[]|  |L_J     |L_J|   |L_J|     |L_J");
		System.out.println("|_____JL_JL___JL_JL____|-||  |[]|     |[]|  ||-|_____JL_JL___JL_JL_____JL_J");
		System.out.println("                       '-''--'--'-----'--'--''-'");
		
		print("You have reached the temple of the emperor of Ferrous. It is said that the emperor");
		print("of Ferrous was the most vicious leader on the face of this planet. Right before the");
		print("aliens were about to go extinct, the emperor put his life on the line in order to save");
		print("twenty five of the total alien population at the time. Right now, this planet is home to");
		print("several thousand aliens. Many of them worship the great emperor of Ferrous. Your task is ");
		print("to gain the emperor's trust and gain the city's trust as well, since these people will help");
		print("you along your journey, specially to defeat the Monstzilla. But you will be told about that later.");
		
		char choice = IBIO.inputChar("Proceed? (y/n)");
		
		if (choice == 'n') {
            return;
		}
		else {
			print("Let's go!");
		}
		
		temple();
	}
	
	public void temple() {
		
		System.out.println("  _");
		System.out.println("  \\\\");
		System.out.println("   \\\\_          _.-._");
		System.out.println("    X:\\        (_/ \\_)");
		System.out.println("    \\::\\       ( ==  )");
		System.out.println("     \\::\\       \\== /");
		System.out.println("    /X:::\\   .-./`-'\\.--.");
		System.out.println("    \\\\/\\::\\ / /     (    l");
		System.out.println("     ~\\ \\::\\ /      `.   L.");
		System.out.println("       \\/:::|         `.'  `");
		System.out.println("       /:/\\:|          (    `.");
		System.out.println("       \\/`-'`.          >    )");
		System.out.println("              \\       //  .-'");
		System.out.println("               |     /(  .'");
		System.out.println("               `-..-'_ \\  \\");
		System.out.println("               __||/_ \\ `-'");
		System.out.println("              / _ \\ #  |");
		System.out.println("             |  #  |#  |   ");
		System.out.println("             |  #  |#  |     ");
		
		print("Guard: Stand in your tracks, you pesky little human. What work do you seek here?");
		print("a) I want to meet the emperor \nb) I want to go to the washroom\nc) I want to become friends with your people \nd) I am the great ruler of Earth");
		char userChoice = IBIO.inputChar("Your response: ");
		
		if (userChoice == 'a' || userChoice == 'A') {
			print("Guard: I haven't gotten to meet the emperor in all the twenty years that I've been working here");
			print("We don't have permission to let outsiders in. If you forcefully try to get in, I will cut off your head");
			print("and feed it to the dogs.");
			whatToDo();
		}
		
		else if (userChoice == 'b' || userChoice == 'B') {
			print("Guard: Sure thing, mate. Go straight and take a right after 100 or so meters.");
		}
		
		else if (userChoice == 'c' || userChoice == 'C') {
			print("Guard: In my 20 years of working here, I have come to notice one thing: whoever");
			print("says that usually wants to assassinate the emperor.");
		}
		else if (userChoice == 'd' || userChoice == 'D') {
			print("Guard: Is it truly you, oh great king of the Earth?");
			print("You: Yes it's really me. I want to save my planet's people from destruction and ");
			print("that is why I've come here.");
			print("Guard: Please come in, sir. I'll escort you to the inner security. They will verify you");
			print("to see if you are really the king of the Earth.");
			loadingClause();
			print("The guards are checking you...and they find out that you are not the king of the Earth");
			print("but merely an imposter! ");
			inner();
		}
	}
	
	public void whatToDo() {
		print("Since the guard isn't letting you in, you have to find an alternate way in.");
		List<String> list = new ArrayList<>();
		while(list.size() <= 2) {
		print("a) Head to the exit \nb) Go to the cloak room \nc) Go the nearby stable");
		char userChoice = IBIO.inputChar("Your choice: ");
		if (userChoice == 'a' || userChoice == 'A') {
			break;
		}

		else if (userChoice == 'b' || userChoice == 'B') {
			list.add("Cloakroom");
			cloak(list);
		}

		else if (userChoice == 'c' || userChoice == 'C') {
			list.add("Stable");
			stable(list);
		}
		
		setFuelPoints(fuelPoints += 30);
		cash += 30;
		
	}

	}
	
	public void key() {
		
		print("There is a passageway! This passageway might lead to the inner courtyards of the emperor's ");
		print("castle. ");
		char choice = IBIO.inputChar("Proceed? (y/n) ");
		
	}
	
	public void cloak(List<String> l) {
		
		System.out.println("                                   _______________________      |");
		System.out.println("                                  |  ________   ________  |     |");
		System.out.println("                                  | |        | |    ___ | |     |");
		System.out.println("                                  | |        | |  ,',.('| |     |");
		System.out.println("                                  | |        | | :  .'  | |     |");
		System.out.println("                                  | |        | | :) _  (| |     |");
		System.out.println("                                  | |        | |  `:_)_,| |     |");
		System.out.println("                                  | |________| |________| |     |");
		System.out.println("                                  |  ________   ________  |     |");
		System.out.println("                                  | |        | |        | |     |");
		System.out.println("                                  | |        | |        | |     |");
		System.out.println("                                  | |        | |        | |     |");
		System.out.println("                                  | |        | |        | |     |");
		System.out.println("                                  | |        | |        | |     |");
		System.out.println("                                  | |________| |________| |     |");
		System.out.println("                                  |_______________________|     |");
		System.out.println("                                                                |");
		System.out.println("                                                                |");
		System.out.println("                   _____________________________________________|");
		System.out.println("                                                                `.");
		System.out.println("                                                                  `.");
		System.out.println("                                                                    `.");
		System.out.println("                                                                      `.");
		System.out.println("                     ..::::::::::::' .:::::::::::::::                   `.");
		System.out.println("                 ..:::::::::::::::' .:::::::::::::::'                     `");
		System.out.println("             ..:::::::::::::::::' .:::::::::::::::::");
		System.out.println("         ..::::::::::::::::::::' .:::::::::::::::::'");
		System.out.println("     ..::::::::::::::::::::::' .:::::::::::::::::::");
		System.out.println(" ..:::::::::::::::::::::::::' .:::::::::::::::::::'");
		System.out.println("..........................  ......................");
		System.out.println(":::::::::::::::::::::::::' .:::::::::::::::::::::'");
		System.out.println(":::::::::::::::::::::::' .:::::::::::::::::::::::");
		System.out.println("::::::::::::::::::::::' .:::::::::::::::::::::::'");
		System.out.println("::::::::::::::::::::' .:::::::::::::::::::::::::");
		System.out.println(":::::::::::::::::::' .:::::::::::::::::::::::::'                ");
		
		print("You are in the cloak room. Let's see if you can a path to go enter into the inner courtyard");
		if (l.contains("Stable")) {
		    char c = IBIO.inputChar("Here is the path that will lead to the inner courtyards. Proceed? (y/n)");
		    if (c == 'y' || c == 'Y') {
		    	inner();
		    }
		    else 
		    	;
		}
		else
			print("Nope. Looks like the room is empty");
	}
	
	public void stable(List<String> l) {
		
		System.out.println("                                  +&-");
		System.out.println("                                _.-^-._    .--.");
		System.out.println("                             .-'   _   '-. |__|");
		System.out.println("                            /     |_|     \\|  |");
		System.out.println("                           /               \\  |");
		System.out.println("                          /|     _____     |\\ |");
		System.out.println("                           |    |==|==|    |  |");
		System.out.println("       |---|---|---|---|---|    |--|--|    |  |");
		System.out.println("       |---|---|---|---|---|    |==|==|    |  |");
		System.out.println("      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		
		print("You are in the stable.");
		
		if (l.contains("Cloakroom")) {
			char c = IBIO.inputChar("Here is the path that will lead to the inner courtyards. Proceed? (y/n)");
			if (c == 'y' || c == 'Y') {
				print("You are in the inner courtyard. But the guards saw you coming in from the pathway");
				print("and they are running after you. ");
		    	inner();
		    }
		    else 
		    	;
		}
		
		else
			;
		
	}
	
	
	public void inner() {
		
		System.out.println("                        ____");
		System.out.println("                       / ___`\\");
		System.out.println("           /|         ( (   \\ \\");
		System.out.println("      |^v^v  V|        \\ \\/) ) )");
		System.out.println("      \\  ____ /         \\_/ / /");
		System.out.println("      ,Y`    `,            / /");
		System.out.println("      ||  -  -)           { }");
		System.out.println("      \\\\   _\\ |           | |");
		System.out.println("       \\\\ / _`\\_         / /");
		System.out.println("       / |  ~ | ``\\     _|_|");
		System.out.println("    ,-`  \\    |  \\ \\  ,//(_}");
		System.out.println("   /      |   |   | \\/  \\| |");
		System.out.println("  |       |   |   | '   ,\\ \\");
		System.out.println("  |     | \\   /  /\\  _/`  | |");
		System.out.println("  \\     |  | |   | ``     | |");
		System.out.println("   |    \\  \\ |   |        | |");
		System.out.println("   |    |   |/   |        / /");
		System.out.println("   |    |        |        | |");
		
		print("Emperor's son: Hey you! Why are you here?");
		print("You: I need your help. In order to save my people, I need to borrow some resources");
		print("from you. These resources are really important for me to save my people. I will");
		print("return them back to you once I am settled.");
		print("Emperor's son: Well, it is a for a good cause. But, then again, you are a human!");
		print("Your race is inferior to ours and I cannot allow you to flourish again!");
		print("Emperor's son's servant: May I interrupt oh lord. I think it would be pretty entertaining");
		print("to watch these humans fail so miserably at life. Besides, what do we have to lose against these");
		print("people.");
		print("Emperor's son: I suppose that's true. Here, have these 50 Epsilons and these 30 fuel points.");
		print("Let's see how you will lose them. ");
		print("Thank you, oh great one!");
	}
	
	@Override
	public void secondPlace() {
		print("You found 30 fuel points lying around!");
		setFuelPoints(fuelPoints += 30);
		progress = 5;
	    char save = IBIO.inputChar("Do you want to save the game now? (y/n) ");
	    int[] gameInfo = { fuelPoints, cashNew, tempOfEarth, progress };
	    
	    if (save == 'y' || save == 'Y') {
	    	writeInfo(progress, gameInfo);
	    }
		
	}
	@Override
	public void thirdPlace() {
		loadingClause();
		print("Answer the following riddle: What two things can you never eat for breakfast?");
		String s = JOptionPane.showInputDialog(null, "Your answer: ");
		
		while (!s.equalsIgnoreCase("breakfast")) {
			JOptionPane.showMessageDialog(null, "Wrong answer!");
			s = JOptionPane.showInputDialog(null, "Your answer: ");
		}
		
		print("You got it! 55 fuel points for you!");
		setFuelPoints(fuelPoints += 50);
	
	}
	@Override
	public void fourthPlace() {
		print("You found 30 Epsilons!");
		cash += 30;
	}
	@Override
	public void fifthPlace() {
		loadingClause();
		print("Alien: Hey you!!! Stop right there!");
		print("You: It's all cool, man. Wh...What do you need?");
		print("Alien: I need your head! I am going to kill you now");
		print("Looks like you have encountered a barbaric alien that wants to kill you.");
		char choice = IBIO.inputChar("Pull your shield out? (y/n)");

		
		if (choice == 'y') {
			print("The alien got scared from the shield! Looks like the shield is a really fearful object in their minds!");
		} 
		
		else {
			print("The alien killed you! Respawning....");
			fifthPlace();
		}
	}
	@Override
	public void sixthPlace() {
		if (!places.contains("seventhplace"))
		print("This place is locked! You need a key to open it."); // solve this
	}
	@Override
	public void seventhPlace() {
		places.add("seventhplace");
		print("There is a cave here! ");
		char choice = IBIO.inputChar("Proceed? (y/n) ");
		if (choice == 'y')
			cave();
		else
			;
	}
	
	Thread caveSong = new Thread() {
		public void run() {
			try {
				startSong("Assassin_39_s_Creed_Brotherhood_Soundtracks_-_07_B.wav");
			} catch (Exception e) {
				print("Couldn't play audio file");
			}
		}
	};
	
	public void cave() {
		caveSong.start();
		
		System.out.println("                                   .---._");
		System.out.println("                                  /==----\\_");
		System.out.println("                 |\\              |8=---()-\\|   |\\");
		System.out.println("         /|      ::\\             |8=-/|\\(_)>_   \\\\");
		System.out.println("         |:\\     `::\\_            \\=/:\\= (_)\\|   |\\");
		System.out.println("         `::|     |::::.            \\;:\\=\\(_)>_  |\\\\");
		System.out.println("          |b\\\\_,_ \\`::::\\             \\:\\=\\( \\/  \\_(");
		System.out.println("          `\\88b`\\|/|'`:::\\   .-----   :8:\\=|`=>_  [d[");
		System.out.println("           \\;\\88b.\\=|///::`-/::;;;;:\\ |8;|=\\( )/   [8[");
		System.out.println("      __    ||/`888b.\\_\\/\\:/:;/'/-\\::\\/( \\|=(=)>_  [d|");
		System.out.println("     //):.. `::|/|\\\"96.\\|//;/|'| /-\\::+\\|-\\=(. )/  [8[");
		System.out.println("    |(/88e::.. `'.|| \"min;/\\\\/8|\\.-|::|8|||=|`='_  `[d|");
		System.out.println("     `-|8888ee::,,,`\\/88utes8P//8|-|::|8||\\=|( )/   ]8[");
		System.out.println("      |:`\"|####b:::/8pq8e/::'`;q8|/::dP'|\\|=|`='_   [d|");
		System.out.println(" .=-. \\::..`\"\"##Gst:q| e|:/..\\:|8|.:/|'/\\/|/|(_)/   ]8[");
		System.out.println("/(,:;:, \\::::.\\#/88q;`;'\\||.:/-//.;/<8\\\\\\^\\||./>    `]d[");
		System.out.println("`8888b::,,_ ::/88q;.`;|d8/`-.]/|./  |8|\\|:|;/.d|     [8|");
		System.out.println("  `\"88###n::-/d8P.\\e/-|d/ _//;;|/   |8(|::(/).8/     ]d[");
		System.out.println("    `\"###o2:1dP;`q./=/d/_//|8888\\   ;8|^\\/`-'8/      [8\\");
		System.out.println("       `\"v7|9q8e;./=/d//=/\\|eeeb|  /dP= =<ee8/       ]d-");
		System.out.println("          \\|9; qe/-d/ .|/=/888|:\\ `--=- =88p'        [8[");
		System.out.println("          (d5b;,/ d/.|/=-\\Oo88|:/                   ,8_\\");
		System.out.println("         _|\\q88| d/ /'=q8|888/:/                    ]d|");
		System.out.println("        _\\\\\\/q8/|8\\_\"\"/////eb|/_                    [8_\\");
		System.out.println("        \\|\\\\<==_(;d888b,.////////--._               ]8|");
		System.out.println("       _/\\\\\\/888p |=\"\"\";;;;`eee.////.;-._          ,8p\\");
		System.out.println("      /,\\\\\\/88p\\ /==/8/.'88`\"\"\"\"88888eeee`.        ]8|");
		System.out.println("    .d||8,\\/p   /-dp_d8|:8:.d\\=\\    `\"\"\"\"|=\\\\     .[8_\\");
		System.out.println("    |8||8||8.-/'d88/8p/|:8:|8b\\=\\        /|\\\\|    ]8p|");
		System.out.println("    |8||8||8b''d.='8p//|:8:'`88b`\\       |||||)   [8'\\");
		System.out.println("    `8b:qb.\\888:e8P/'/P'8:|:8:`888|      |'\\||'  /8p|");
		System.out.println("     q8q\\\\qe---_P;'.'P|:8:|:8:|`q/\\\\     '_///  /8p_\\");
		System.out.println("     _|88b-:==:-d6/P' |8::'|8:| ,|\\||    '-=' .d8p/");
		System.out.println("    |__8Pdb-q888P-'  .:8:| |8:| |/\\||\\     .-e8p/\\|");
		System.out.println("     .-\\888b.__      |:8/' |8:| \\ _|;|,-eee8\\8\\|");
		System.out.println("     \\.-\\'88/88/e.e.e|8/|\\_--.-.-e8|8|88\\8p\\|");
		System.out.println("       .'`-;88]88|8|/':|:\\ `q|8|8|8'-\\| \\|");
		System.out.println("        `' || || |_/(/|;\\)`-\\\\`--,\\|");
		System.out.println("              `' /v\"\"\"' `\"\"\"\"\"\"vVV\\");
		
		print("Hmphhh hmphhh...");
		print("You: What is that? Whoever you are, come out and show yourself.");
		print("Looks like it's a bear");
		print("You will have to kill the bear with your own hands, if you don't already have a weapon.");
		int bearHp = 360;
		int userHp = 360;
		int counter = 0;
		
		while (!gameOver(userHp, bearHp)) {
			counter++;
			if (counter % 2 == 0) {
				int n = (int) (Math.random() * 3);
				userHp = bearTurn(n, userHp);
				
			}
			else {
				bearHp = userTurn(bearHp);
			}
		}
		
		print("Game over!!");
		char w = winner(userHp, bearHp);
		
		if (w == 'u') {
			print("You won!!!! Woohoo!!! 50 fuel points for you!");
			setFuelPoints (fuelPoints += 50);
			print("In reward, you have also achieved the possession of your first weapon! It's a knife.");
			items.add("knife");
		}
		
		else {
			print("Oh no you lost!");
			print ("Restarting from last checkpoint...");
			cave();
		}
		
		
		
	}
	
	public boolean gameOver(int u, int c) {
		if (c <= 0 || u <= 0) {
			return true;
		}
		
		else 
			return false;
	}
	
	public char winner(int u, int c) {
		if (c <= 0 && u > 0) {
			return 'u';
		}
		
		else 
			return 'c';
	}
	
	public int bearTurn(int random, int userHealth) {
		if (random == 1) {
			print("The bear dealt 30 damage with the light attack");
			return userHealth -= 30;
		}
		
		else if (random == 2) {
			print("The bear dealt 60 damage with the medium attack");
			return userHealth -= 60;
		}
		
		else {
			print("The bear dealt 90 damage with the heavy attack!");
			return userHealth -= 90;
		}
	}
	
	public int userTurn(int bearHealth) {
		print("a) Light attack \nb) Medium attack \nc) Heavy attack");
		char choice = IBIO.inputChar("How do you want to attack? ");
		if (choice == 'a' || choice == 'A') {
				print("You dealt 30 damage with the light attack");
				return bearHealth -= 30;
		}
		
		else if (choice == 'b' || choice == 'B') {
			print("You dealt 60 damage with the medium attack");
			return bearHealth -= 60;
		}
		
		else {
			print("You dealt 90 damage with the heavy attack!");
			return bearHealth -= 90;
		}
	}
	@Override
	public void eighthPlace() {
		loadingClause();
		
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
		
		print("Luciano: Hey brother! What are you doing here?");
		print("You: Hey Luciano! This is my next endeavour. Although your planet is a really nice place, I had to ");
		print("move on in my exploration. What are you doing here?");
		print("Luciano: Oh I just came here for a vacation. Been working pretty hard and now I finally had a bunch");
		print("of holidays so I decided to come here. ");
		print("You: Listen is there a way of getting some weapons around here?");
		print("Luciano: Sure just ask the carpenter to build you a weapon store of your own. We even have our own");
		print("signature weapons. Just don't use them on us.");
		print("You: Alright. ");
		setCash(200);
		if (cashNew < 100) 
		JOptionPane.showMessageDialog(null, "You need 100 Epsilons to build a store!");
		
		else {
			int n = JOptionPane.showConfirmDialog(
				    null,
				    "Would you like to build the store now?",
				    "Confirm?",
				    JOptionPane.YES_NO_OPTION);
			while (n == JOptionPane.CLOSED_OPTION) {
				JOptionPane.showMessageDialog(null, "Specify a choice!");
				n = JOptionPane.showConfirmDialog(
					    null,
					    "Would you like to build the store now?",
					    "Confirm?",
					    JOptionPane.YES_NO_OPTION);
			}
			if (n == JOptionPane.YES_OPTION) {
				print("Ok! You have successfully built a store. It is located on this coordinate.");
				places.add("store");
				setCash(cash -= 100);
			}
			else
				print("Fine.");
		}
	}
	
	public void bossBattle() {
		
		print("Oh! In the process of leaving this planet, a group of aliens hijacked your gun and");
		print("took you in as a prisoner! You now have to defeat the boss alien to get out safely!");
		print("You: Uh...what is going on?");
		print("Luciano: Welcome, my friend. ");
		print("You: Luciano?! You mean you-");
		print("Luciano: Yes, it's me. I am the ruler of this planet. Ever since I saw you, I have");
		print("been collecting intel on you. Did you really think I was actually your 'friend'? Haha");
		print("a pesky human like you is a potential danger for an advanced society like ours. You ");
		print("must be demolished...immediately!");
		print("You: Never expected this from you, Luciano. But bring it on!");
		print("Luciano: Your fate will be decided by a best of 5 games of rock paper scissors! ");
		
		RockPaperScissors r = new RockPaperScissors();
		String win = r.returnWinner();
		if (win.equals("user")) {
			setCash(cash += 100);
			print("Your reward: 100 Epsilons");
		}
		
		else {
			print("Restarting from last checkpoint...");
			bossBattle();
		}
		
		
	}
	
	@Override
	public boolean finished() {
		if (a != ' ' && b!= ' ' && c!= ' ' && d!= ' ' && e!= ' ' && f!= ' ' && g!= ' ' && h!= ' ') {
		       return true;
		    }
		      else 
		       return false;
	}
	@Override
	public void ninethPlace() {
		
	}
	
	@Override
	public void respawn(int p, int s, int t) {
		if (p == 1) 
			move();
		else if (p == 2) {
			firstPlanet();
		}
		else if (p == 0) {
			intro();
		}
		else if (p == 4) {
			SecondPlanet o = new SecondPlanet();
			o.setCash(MainGame.cash);
			o.setFuelPoints(MainGame.fuelPoints);
		}
		else if (p == 5) {
			secondPlace();
		}

	}


	public int getFuelPoints() {
		return fuelPts;
	}

	public void setFuelPoints(int newPts) {
		fuelPts = newPts;
	}
	
	public void setCash(int c) {
		cashNew = c;
	}


}



import java.util.Scanner;

import javax.swing.JOptionPane;

public class App {
	IMessageShower messageSystem = new MessageChatConsole();
	IInputGetter inputSystem = new MessageChatConsole();
	public static void main(String args[]){
		App app = new App();
		GameManager gameManager = new GameManager();
		gameManager.setHeroNames(app.setHeroNames(GameManager.partySize));
		app.startGame(gameManager);
	}
	public String[] setHeroNames(int partySize) {
		String[] names = new String[partySize];
		for (int i = 0; i < partySize; i++) {
			String nameTemp = JOptionPane.showInputDialog("please enter "+(i+1)+"."+"Hero's name" );
			names[i] = nameTemp;
		}
		return names;	
	}
	public void startGame(GameManager gameManager) {
		String options[] = {"Enter the Dungeon","Rest"};
		while(!gameManager.isGameOver()) {
			int choice = JOptionPane.showOptionDialog(
					null,
					"What do you want to do?",
					"Camp Area",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]
					);
			switch (choice) {
			case 0: {
				enterDungeon(gameManager);
				break;
			}
			case 1: {
				restAtCamp(gameManager);
				break;
			}
			default:
			}
		}
		JOptionPane.showMessageDialog(null, "all of your heroes knows onto eternity\n Total exp"+gameManager.getHeroesExp(),
				"Gameover", 
				JOptionPane.PLAIN_MESSAGE);
	}
	private void enterDungeon(GameManager gameManager) {
		enterDungeonMessage();
		gameManager.setExitDungeon(false);
		while(true) {
			int choice = dungeonWaitInput(gameManager);
			inDungeonChose(choice, gameManager);
			if(gameManager.getExitDungeon()) break;
		}
		
	}
	private void restAtCamp(GameManager gameManager) {
		String message = "All heroes gathered around fire and chatted a bit, and when it gets dark \n sleep together";
		gameManager.restHeroes();
		messageSystem.showStoryDialogue(message);
	}
	private void enterDungeonMessage() {
		messageSystem.showStoryDialogue("as all of you get in dungeon, the door heavily clossed , all of you trapped right now ");
	}
	private int dungeonWaitInput(GameManager gameManager) {
		waitingInterlupeMessage(gameManager);
		messageSystem.showInterlupeDialog("what do you want to do?");
		messageSystem.showInterlupeDialog("1.Continue the way");
		if(gameManager.getIsEscapePossible())messageSystem.showInterlupeDialog("2.Rest");
		if(gameManager.getIsEscapePossible()) return getInputUntilChosearoundBound(2);
		return getInputUntilChosearoundBound(1);
	}
	private void inDungeonChose(int input, GameManager gameManager) {
		int walkDistance = 5;
		switch (input) {
		case 1:
			messageSystem.showStoryDialogue("your party walked ahead "+walkDistance+"meter");
			gameManager.increaseWalkedDistance(walkDistance);
			if(gameManager.isEncounterOccured()) {
				startEncounter(gameManager);
				gameManager.afterEncounterReset();
			}
			break;
		case 2:	
			messageSystem.showStoryDialogue("your party walk into portal now you are all on camp");
			gameManager.setExitDungeon(true);
			break;
		default:
			break;
		}
		
	}
	private void startEncounter(GameManager gameManager) {
		EncounterManager manager = gameManager.getEncounterManager();
		manager.createEncounter();
		int enemyGroupSize = manager.getEnemyGroup().length;
		messageSystem.showStoryDialogue("when you all walk around corridors encountered "+enemyGroupSize+" enemies");
		while(!manager.isAllEnemyDeath()) {
			messageSystem.showInterlupeDialog(gameManager.getEncounterText());
			if(manager.isHeroTour()){
				playerTourInput(manager,enemyGroupSize);
			}
			else {
				Enemy enemy = (Enemy)manager.getCurrentTour();
				manager.enemyTourAI(enemy);
				messageSystem.showStoryDialogue(enemy.getName()+" Attacked to heroes");
			}
			manager.nextTour();
		}
		int exp = manager.giveEXPtoPlayers();
		messageSystem.showStoryDialogue("all living heroes got "+exp+" experience");
		messageSystem.showStoryDialogue("you all continued to your way");
		
	}
	private void playerTourInput(EncounterManager manager, int enemyGroupSize) {
		Enemy enemyAttacked = null;
		Hero hero = (Hero) manager.getCurrentTour();
		do {
			messageSystem.showInterlupeDialog("who do you want to attack?\n");
			int choice = getInputUntilChosearoundBound(enemyGroupSize);
			enemyAttacked = manager.getEnemyid(choice-1);
		}while(!enemyAttacked.getIsAlive());
		int heroDamage = hero.getAttack();
		manager.AttackNPC(enemyAttacked, heroDamage);
		messageSystem.showStoryDialogue(hero.getName()+" attacked to "+enemyAttacked.getName()+
				"\n and give "+heroDamage+"as damage\n");
	}
	private int getInputUntilChosearoundBound(int bound) {
		//this method get input until get input bound we forced to get
		int input = -1;
		do {
			
			try {
				input = inputSystem.getInput().charAt(0);
			} catch (StringIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				messageSystem.showInterlupeDialog("\nplease enter a value");
				continue;
			}
		}while(input < bound+1 || input == 0);
		return input - '0';
	}
	private void waitingInterlupeMessage(GameManager gameManager) {
		if(gameManager.getIsEscapePossible()) messageSystem.showInterlupeDialog("when your party walking around dungeon you are"
				+ "encountered a portal. It Seems it get you to camp area");
	}
}
class MessageChatConsole implements IMessageShower, IInputGetter {
	Scanner scanner = new Scanner(System.in);
	@Override
	public void showStoryDialogue(String text) {
		System.out.println(text);
		scanner.nextLine();
	}
	@Override
	public void showInterlupeDialog(String text) {
		// TODO Auto-generated method stub
		System.out.println(text);
		
	}
	@Override
	public String getInput() {
		// TODO Auto-generated method stub
		return scanner.nextLine();
	}
	
}

import java.util.Random;

public class GameManager {
	public final static int partySize = 3;
	private int walkedDistance;
	private int encounterDistance;
	private Hero heroParty[];
	private Hero heroCurrent;
	private Boolean isEscapePossible;
	private Boolean exitDungeon;
	private EncounterManager encounterManager;
	public GameManager() {
		instanceHero();
		randomizeEncounterDistance();
		setEncounterManager(new EncounterManager(heroParty));
		checkEscapePossible();
	}
	public int getWalkedDistance() {
		return walkedDistance;
	}
	private void setWalkedDistance(int walkedDistance) {
		this.walkedDistance = walkedDistance;
		checkEscapePossible();
	}
	private void checkEscapePossible() {
		int value = 1;
		int randomSeed = new Random().nextInt(4);
		isEscapePossible  = value == randomSeed;
	}
	public void increaseWalkedDistance(int distanceWalked) {
		setWalkedDistance(distanceWalked+getWalkedDistance());
	}
	public int getEncounterDistance() {
		return encounterDistance;
	}
	public int randomizeEncounterDistance() {
		Random rand = new Random();
		encounterDistance = rand.nextInt(100);
		return encounterDistance;
	}
	public void afterEncounterReset() {
		randomizeEncounterDistance();
		setWalkedDistance(0);
	}
	public boolean isEncounterOccured() {
		return getWalkedDistance() > getEncounterDistance();
	}
	private void instanceHero() {
		//this method instance hero and set current hero as first
		heroParty = new Hero[partySize];
		for (int i = 0; i < heroParty.length; i++) {
			heroParty[i] = new Hero(i);
		}
		setHeroCurrent(heroParty[0]);
	}
	public void setHeroNames(String[] names) {
		for (int i = 0; i < heroParty.length; i++) {
			heroParty[i].setName(names[i]);
		}
	}
	public void restHeroes() {
		for (Hero hero : heroParty) {
			hero.rest();
		}
	}
	public int getHeroesExp() {
		int totalexp = 0;
		for (Hero hero : heroParty) {
			totalexp += hero.getExp();
		}
		return totalexp;
	}
	public Hero getHeroCurrent() {
		return heroCurrent;
	}
	public void setHeroCurrent(Hero heroCurrent) {
		this.heroCurrent = heroCurrent;
	}
	public Boolean getIsEscapePossible() {
		return isEscapePossible;
	}
	public Boolean isGameOver() {
		//it check all three member of party is death
		for (Hero hero : heroParty) {
			if(!hero.getIsAlive()) return true;
		}
		return false;
	}
	public EncounterManager getEncounterManager() {
		return encounterManager;
	}
	public String getEncounterText() {
		StringBuilder buildertest = new StringBuilder("");
		for (Enemy enemy : getEncounterManager().getEnemyGroup()) {
			buildertest.append("\n"+enemy.getStatsText());
		}
		buildertest.append("\n-----------------");
		for (Hero hero : getEncounterManager().getHeroparty()) {
			buildertest.append("\n"+hero.getStatsText());
		}
		return buildertest.toString();
	}
	private void setEncounterManager(EncounterManager encounterManager) {
		this.encounterManager = encounterManager;
	}
	public Boolean getExitDungeon() {
		return exitDungeon;
	}
	public void setExitDungeon(Boolean exitDungeon) {
		this.exitDungeon = exitDungeon;
	}
	
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EncounterManager {
	private Hero[] heroparty;
	private Enemy[] enemyGroup;
	private ArrayList<NPC> tourOrder;
	private int tourOrderID;
	private NPC currentTour;
	public final int encounterCountStart = 4, encounterCountEnd = 6;
	public EncounterManager(Hero[] Heroparty) {
		// TODO Auto-generated constructor stub
		setHeroparty(Heroparty);
		createEncounter();
	}
	public Hero[] getHeroparty() {
		return heroparty;
	}
	public void setHeroparty(Hero[] heroparty) {
		this.heroparty = heroparty;
	}
	public Hero getHerofromid(int id) {
		return getHeroparty()[id];
	}
	public Boolean isGameOver() {
		//it check all three member of party is death
		return isAllDeath(heroparty);
	}
	public Boolean isAllEnemyDeath() {
		//it check all enemy are death 
		return isAllDeath(enemyGroup);
	}
	public void enemyTourAI(Enemy enemy) {
		Attackhero(enemy);
	}
	private void Attackhero(Enemy enemy) {
		// it attack everyone %10 percent or attack one person %90 
		int value = 1;
		int randomseed = new Random().nextInt(10);
		if(value != randomseed) {
			Hero hero;
			do {
				int randominHero = new Random().nextInt(heroparty.length-1);
				hero = getHerofromid(randominHero);
			} while (!hero.getIsAlive());
			AttackNPC(hero, enemy.getAttack());
		}
		else {
			AttackNPC(heroparty, enemy.getAttack());
		}
	}
	public void AttackNPC(NPC[] npcs,int damage) {
		for (NPC npc : npcs) {
			AttackNPC(npc, damage);
		}
	}
	public void AttackNPC(NPC npc,int damage) {
		npc.getDamage(damage);
	}
	public void createEncounter() {
		//it create enemies with random names from file
		Random random = new Random();
		int enemyNumber = random.nextInt(encounterCountStart, encounterCountEnd); 
		setEnemyGroup(new Enemy[enemyNumber]);
		for (int i = 0; i < getEnemyGroup().length; i++) {
			getEnemyGroup()[i] = new Enemy(i);
		}
		setTourOrder();
		setTourOrderID(0);
		setCurrentTour(tourOrder.get(getTourOrderID()));
	}
	private void setTourOrder() {
		tourOrder = new ArrayList<>();
		tourOrder.addAll(Arrays.asList(getHeroparty()));
		tourOrder.addAll(Arrays.asList(getEnemyGroup()));
	}
	public Enemy[] getEnemyGroup() {
		return enemyGroup;
	}
	private void setEnemyGroup(Enemy[] enemyGroup) {
		this.enemyGroup = enemyGroup;
	}
	public Enemy getEnemyid(int id) {
		return getEnemyGroup()[id];
	}
	private Boolean isAllDeath(NPC[] group) {
		for (NPC npc : group) {
			if(npc.getIsAlive()) return false;
		}
		return true;
	}
	public NPC getCurrentTour() {
		return currentTour;
	}
	public void setCurrentTour(NPC currentTour) {
		this.currentTour = currentTour;
	}
	public void nextTour() {
		//it change tour to next NPC on game as long as get alive npc
		NPC tempNPC;
		do {
			int id = getTourOrderID()+1;
			setTourOrderID(id);
			tempNPC = tourOrder.get(getTourOrderID());
		}while(!tempNPC.getIsAlive());
		setCurrentTour(tempNPC);
		
	}
	public int giveEXPtoPlayers() {
		int totalexp = 0;
		for (Enemy enemy : enemyGroup) {
			totalexp += enemy.getExp();
		}
		for (Hero hero : heroparty) {
			hero.increaseExp(totalexp);
		}
		return totalexp;
	}
	public boolean isHeroTour() {
		return currentTour instanceof Hero;
	}
	public int getTourOrderID() {
		return tourOrderID;
	}
	public void setTourOrderID(int tourOrderID) {
		this.tourOrderID = tourOrderID;
		if(tourOrderID >=tourOrder.size()) this.tourOrderID = 0;
	}
}

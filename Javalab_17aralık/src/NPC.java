import java.text.MessageFormat;
import java.util.Random;

public class NPC {
	protected static float criticmultiple = 2.5f;
	private int id;
	private String name;	
	private int fullHP;
	private int hp;
	private int attack;
	private float criticalRate;
	private int exp;

	private Boolean isAlive;
	public NPC(int id) {
		setId(id);
	}
	public int getAttack() {
		//it check is attack critical and return it with multipler
		int tempAttack;
		if(isCritical()) tempAttack = (int) (attack * criticmultiple);
		else tempAttack = attack;
		return tempAttack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		if(hp <= 0) {
			setIsAlive(false);
			hp = 0;
		}
		this.hp = hp;
	}
	public float getCriticalRate() {
		return (float)(criticalRate)/100;
	}
	public void setCriticalRate(int criticalRate) {
		if(criticalRate >100 || criticalRate < 0) return;
		this.criticalRate = criticalRate;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	protected Boolean isCritical() {
		//it check our attack is critical
		Random random = new Random();
		int randomseed = random.nextInt(100);
		return randomseed < criticalRate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void getDamage(int damage) {
		setHp(getHp()-damage);
	}
	public Boolean getIsAlive() {
		return isAlive;
	}
	protected void setIsAlive(boolean b) {
		isAlive = b;
		
	}
	public int getFullHP() {
		return fullHP;
	}
	public void setFullHP(int fullHP) {
		this.fullHP = fullHP;
		setHp(fullHP);
	}
	public String getStatsText() {
		String StatMessage = MessageFormat.format("{0} / HP:{1} Alive: {2}", getName(),getHp(),isAlive);
		return StatMessage;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getStatsText();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

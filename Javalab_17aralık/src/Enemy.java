
public class Enemy extends NPC {
	Enemy(int id){
		super(id);
		build();
	}
	private void build() {
		setFullHP(20);
		setAttack(21);
		setCriticalRate(5);
		setExp(5);
		setIsAlive(true);
		randomiseName();
	}
	public void randomiseName() {
		String name = NameGenerator.getRandomName();
		setName(name);
	}
	@Override
	public String getStatsText() {
		// TODO Auto-generated method stub
		String text = super.getStatsText();
		text += " ENEMY";
		return text;
	}
}

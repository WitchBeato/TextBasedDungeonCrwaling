
public class Hero extends NPC{
	public Hero(int id) {
		super(id);
		build();
	}
	private void build() {
		setFullHP(100);
		setAttack(10);
		setCriticalRate(20);
		setExp(0);
		setIsAlive(true);
	}
	public void increaseExp(int exp) {
		setExp(getExp()+exp);
	}
	public void rest() {
		setHp(getFullHP());
	}
}

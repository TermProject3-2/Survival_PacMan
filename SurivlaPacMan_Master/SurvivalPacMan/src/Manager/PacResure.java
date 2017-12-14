package Manager;

//PacMan 부활에 사용하는 Timer에 파라미터로 넣을 클래스
public class PacResure extends java.util.TimerTask {
	
	ObjectManager objectManager;
	int pacIndex;
	
	public PacResure(ObjectManager objectManager,int pacIndex) {
		this.objectManager = objectManager;
		this.pacIndex = pacIndex;
	}
	
    public void run() {
    	objectManager.resurePac(pacIndex);
    }
}
	

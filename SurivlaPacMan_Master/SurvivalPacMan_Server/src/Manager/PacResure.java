package Manager;

//PacMan ��Ȱ�� ����ϴ� Timer�� �Ķ���ͷ� ���� Ŭ����
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
	

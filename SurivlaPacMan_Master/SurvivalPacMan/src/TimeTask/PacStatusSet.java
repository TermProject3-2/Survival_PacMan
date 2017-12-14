package TimeTask;

import java.util.TimerTask;

import Model.PacMan;

public class PacStatusSet extends TimerTask {

	PacMan pac;
	
	public PacStatusSet(PacMan pac) {
		this.pac = pac;
		
	}
	@Override
	public void run() {
		pac.setStatus(1);
	}

}

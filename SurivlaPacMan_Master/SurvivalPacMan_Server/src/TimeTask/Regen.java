package TimeTask;

import java.util.TimerTask;

import Server.Server;

public class Regen extends TimerTask {

	Server server;
	
	public Regen(Server server) {
		this.server = server;
		
	}

	@Override
	public void run() {
		String regen = new String("REGEN&item");
		if(!server.getVc().isEmpty())
		   server.getVc().get(0).broad_cast(regen);
	}

}

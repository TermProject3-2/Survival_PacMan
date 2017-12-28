package Model;

import Init.MapData;

abstract class ObjectType {
        public int TypeNum;
        public int x;
		public int y;
        public int startX, startY;
        public int status;
        public MapData mapData;



        public abstract void move();
    	public abstract void sendInformationToServer();
    	public abstract void receiveInformationFromServer();
    	
    	
		public int getTypeNum() {
			return TypeNum;
		}
		public void setTypeNum(int typeNum) {
			TypeNum = typeNum;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getStartX() {
			return startX;
		}
		public void setStartX(int startX) {
			this.startX = startX;
		}
		public int getStartY() {
			return startY;
		}
		public void setStartY(int startY) {
			this.startY = startY;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
    	
    	 	
    	
}
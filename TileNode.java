
public class TileNode {

	private int x;
	private int y;
	private int status;
	private int f;
	private int g;
	private int h;
	private TileNode parent;

	
	TileNode(int x, int y){
		this.x = x;
		this.y = y;
		this.status = 0;
		this.f = -1;
		this.g = -1;
		this.f = -1;
		this.parent = null;
	}
	
	// getter and setter for x coordinate
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	// getter and setter for y coordinate
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	/* getter and setter for status
	 * 0 = empty tile
	 * 1 = blocked tile
	 * 2 = start tile
	 * 3 = goal tile
	 * 4 = path tile
	 */
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	// getter and setter for f which is g(n) + h(n)
	public int getF() {
		return f;
	}
	public void setF() {
		this.f = this.g + this.h;
	}
	
	// getter and setter for g(n)
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	
	
	// getter and setter for h(n)
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	
	// getter and setter for parent node
	public TileNode getParent() {
		return parent;
	}
	public void setParent(TileNode parent) {
		this.parent = parent;
	}
	
	
	
	
	
}

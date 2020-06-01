
public class Pair implements Comparable<Pair>{
	private final int x;
	private final double time;
	
	public Pair(int x, double time) {
		this.x = x;
		this.time = time;
	}
	
	public int getScore() {
		return this.x;
	}
	
	public double getTime() {
		return this.time;
	}

	@Override
	public int compareTo(Pair o) {
		if (this == o) {return 0;}
		else if (this.getScore() > o.getScore()) {return -1;}
		else return 1;
	}

}

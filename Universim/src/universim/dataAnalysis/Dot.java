package universim.dataAnalysis;

public class Dot{
	public Double mg;
	public Double bp_rp;
	public Double parallax;
	
	public Dot(Double mg, double bp_rp) {
		this(mg, bp_rp, Double.MAX_VALUE);
	}
	
	public Dot(Double mg, double bp_rp, Double parallax) {
		this.mg = mg;
		this.bp_rp = bp_rp;
		this.parallax = parallax;
	}
	
}

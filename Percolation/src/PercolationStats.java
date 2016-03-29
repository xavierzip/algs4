import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	private double[] rates;
	private int  numberOfTrials;
	private double mean;
	private double varience;
	private double stddev;
	
	public PercolationStats(int N, int T) {
		rates = new double[T];
		numberOfTrials = T;
		mean = 0;
		stddev = 0;
		for (int i = 0; i < T; i++) {
			int count = 0;
			Percolation trial = new Percolation(N);
			while (!trial.percolates()) {
				int rolIdx = StdRandom.uniform(N) + 1;
				int colIdx = StdRandom.uniform(N) + 1;
				if (trial.isOpen(rolIdx, colIdx)) continue;
//				System.out.println("opening " + rolIdx + "," + colIdx);
				trial.open(rolIdx, colIdx);
				count++;
			}
			rates[i] = (double) count / (N * N);
			mean = mean + rates[i] / T;
		}
	}
	
	public double mean() {
		return mean;
	}
	
	public double stddev() {
		varience = 0;
		for (int i = 0; i < numberOfTrials; i++) {
			varience = varience + (rates[i] - mean) * (rates[i] - mean); 
		}
		varience = varience / (numberOfTrials - 1);
		stddev = Math.sqrt(varience);
		return stddev;
	}
	
	public double confidenceLo() {
		return mean - 1.96 * stddev / Math.sqrt(numberOfTrials);
	}
	
	public double confidenceHi() {
		return mean + 1.96 * stddev / Math.sqrt(numberOfTrials);
	}
	
	public static void main (String[] args){
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats exp = new PercolationStats(N, T);
		System.out.println("mean" + "\t = " + exp.mean());
		System.out.println("stddev" + "\t = " + exp.stddev());;
		System.out.println("95% confidence interval" + "\t = " 
				+ exp.confidenceLo() + "," + exp.confidenceHi());
	}
}

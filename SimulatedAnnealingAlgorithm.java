package onLabs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealingAlgorithm {
	static String fileName = "assign100.txt";//file
	static int N = 10;	//number of employees and tasks
	static int[][] c; //the cost of assigning an employee to a task
	static int maxIterations =300;
	static int attemptLimit =10;
	static int t=0;
	static double T=1000;
	static double alfa=0.99;
	
	static void readDataFromFile() throws IOException
	{
		BufferedReader fileReader=null;
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
			
			String firstLine =fileReader.readLine().trim();
			N=Integer.parseInt(firstLine);
			c=new int[N][N];
			int row=0;
			int column=0;
			
			String nextLine =fileReader.readLine();
			while(nextLine !=null) {
				nextLine = nextLine.trim();
				String[] nextLineSplit = nextLine.split(" ");
				int elementsInRow = nextLineSplit.length;
				int whichElementInRow =0;
				while(whichElementInRow < elementsInRow) {
					String readCost = nextLineSplit[whichElementInRow];
					System.out.println("row = "+row);
					System.out.println("column = "+column);
					c[row][column]=Integer.parseInt(readCost);
					if(column<N-1) {
						column++;
					}
					else {
						row++;
						column=0;
					}
					whichElementInRow++;
				}
				nextLine =fileReader.readLine();
			}
			
		}
		finally{
			if(fileReader != null)
				fileReader.close();
		}
		//wydruk
		System.out.println("Employees and tasks = "+N);
		for(int i=0;i<N;i++) {
			for(int y=0;y<N;y++) {
				System.out.printf("%4d",c[i][y]);
			}
			System.out.println();
		}
		
	}
	public static class Solution {
		int fGoal;
		int [][] x;
		
		Solution(){
			x=new int [N][N];
			for(int i=0;i<N;i++) {
				for(int y=0;y<N;y++) {
					x[i][y]=0;
				}
			}
			fGoal =0;
		}
		Solution(Solution v){
			x=new int [N][N];
			for(int i=0;i<N;i++) {
				for(int y=0;y<N;y++) {
					x[i][y]=v.x[i][y];
				}
			}
			fGoal =v.fGoal;
		}
		void calculateFGoal() {
			fGoal =0;
			for(int i=0;i<N;i++) {
				for(int y=0;y<N;y++) {
					fGoal +=c[i][y]*x[i][y];
				}
			}		
		}
		boolean isCorrect() {
			for(int i=0;i<N;i++) {
				int sum =0;
				for(int y=0;y<N;y++) {
					sum +=x[i][y];
				}			
				if(sum !=1) {
					return false;
				}
			}
			for(int j=0;j<N;j++) {
				int sum =0;
				for(int y=0;y<N;y++) {
					sum +=x[y][j];
				}			
				if(sum !=1) {
					return false;
				}				
			}
			return true;
		}
	}
	static Solution createRandomSolution() {
		ArrayList<Integer> al=new ArrayList<Integer>(N);
		for(int k=0;k<N;k++) {
			al.add(k);
		}
		Solution v=new Solution();
		Random rnd =new Random();
		for(int i=0;i<N;i++) {
			int los=rnd.nextInt(al.size());
			v.x[i][al.get(los)]=1;
			al.remove(los);
		}
		v.calculateFGoal();
		return v;
	}

	public static void simulatedAnnealing1() throws IOException
	{
		readDataFromFile();
		sa();

	}
	static int sa() {
		Solution vc= createRandomSolution();
		int f_vc=vc.fGoal;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.printf("%d",vc.x[i][j]);
			}
			System.out.println();
		}
		System.out.println(vc.isCorrect());
		System.out.println("Initial Development Cost = "+f_vc);
		
		Random rnd=new Random();
		do {
			int tryCount =0;
			do {
				Solution vn= selectASolutionFromEnvironment(vc);
				int f_vn=vn.fGoal;
				if(f_vn<f_vc) {
					vc=new Solution(vn);
					f_vc=vc.fGoal;
					System.out.println("better, cost = "+f_vc);
				}
				else {
					if(rnd.nextDouble()<Math.exp(-(f_vn-f_vc)/T)){
						vc=new Solution(vn);
						f_vc=vc.fGoal;
						System.out.println("better rnd, Cost = "+f_vc);
					}
				}
			tryCount++;
			}while(tryCount < attemptLimit);
		T=alfa*T;
		t++;
		}while(t< maxIterations);
		
		return f_vc;		
	}
	static Solution selectASolutionFromEnvironment(Solution v) {
		Solution vr=new Solution(v);
		Random rnd=new Random();
		int randTask1 =rnd.nextInt(N);
		int randTask2 =rnd.nextInt(N);
		int employeeTask1 =0;
		for(int k=0;k<N;k++) {
			if(v.x[k][randTask1]==1) {
				employeeTask1 =k;
				break;
			}
		}
		int employeeTask2 =0;
		for(int k=0;k<N;k++) {
			if(v.x[k][randTask2]==1) {
				employeeTask2 =k;
				break;
			}
		}
		vr.x[employeeTask1][randTask1]=0;
		vr.x[employeeTask2][randTask2]=0;
		vr.x[employeeTask1][randTask2]=1;
		vr.x[employeeTask2][randTask1]=1;
		vr.calculateFGoal();
		return vr;
	}
	
	public static void main(String[]args) throws IOException
	{
		simulatedAnnealing1();
	}

}

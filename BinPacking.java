package onLabs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BinPacking {

		static String fileName = "binpack.txt";
		static int N; //number of items
		static int s[];//object dimensions
		static int B;//container capacity
		static int Popt;//the best result read from the file
		static int x[];//variables what container the item is in
		static int containerVolume[];//how much space is occupied in each bin, let's assume that N bins are the upper bound

		static void readDataFromFile(String fileName) throws IOException{
			try {
				BufferedReader fileReader= new BufferedReader(new FileReader(fileName));
				String firstLine =fileReader.readLine();
				String[] firstLineSplitted = firstLine.split(" ");
				B=Integer.parseInt(firstLineSplitted[0]);
				N=Integer.parseInt(firstLineSplitted[1]);
				Popt=Integer.parseInt(firstLineSplitted[2]);
				s=new int[N];
				x=new int[N];
				containerVolume = new int[N];
				
				for(int k=0;k<N;k++) {
					String nextLine =fileReader.readLine();
					s[k]=Integer.parseInt(nextLine);
				}
			}
			finally {
			
				
			}
	}
	static int bestFit(){
			int containerVolume =1;
			
			for(int k=0;k<N;k++) {
				int pMax =-1;
				int containerOccupancyMax =-1;
				for(int p = 0; p< containerVolume; p++) {
					if(BinPacking.containerVolume[p]+s[k]<=B) {
						if(BinPacking.containerVolume[p]> containerOccupancyMax) {
							//to remember
							containerOccupancyMax = BinPacking.containerVolume[p];
							pMax =p;
						}
					}
				}
				if(pMax != -1) {
					//it will fit into one of the open ones, load it
					x[k]= pMax;
					BinPacking.containerVolume[pMax]+=s[k];
				}
				else {
					containerVolume++;
					x[k]= containerVolume -1;
					BinPacking.containerVolume[containerVolume -1]+=s[k];
				}
			}
			return containerVolume;
	}
	static int worstFit(){
		int containerVolume =1;
		
		for(int k=0;k<N;k++) {
			int pMin =200;
			int containerOccupancyMin =200;
			for(int p = 0; p< containerVolume; p++) {
				if(BinPacking.containerVolume[p]+s[k]<=B) {
					if(BinPacking.containerVolume[p]< containerOccupancyMin) {
						//to remember
						containerOccupancyMin = BinPacking.containerVolume[p];
						pMin =p;
					}
				}
			}
			if(pMin != 200) {
				//it will fit into one of the open ones, load it
				x[k]= pMin;
				BinPacking.containerVolume[pMin]+=s[k];
			}
			else {
				containerVolume++;
				x[k]= containerVolume -1;
				BinPacking.containerVolume[containerVolume -1]+=s[k];
			}
		}
		return containerVolume;
}
	static int firstFit(){
		int containerVolume =1;
		
		for(int k=0;k<N;k++) {
			boolean placed =false;
			for(int p = 0; p< containerVolume; p++) {
				if(BinPacking.containerVolume[p]+s[k]<=B) {
					x[k]=p;
					BinPacking.containerVolume[p]+=s[k];
					placed =true;
					break;
				}
			}
			if(!placed) {
				containerVolume++;
				x[k]= containerVolume -1;
				BinPacking.containerVolume[containerVolume -1]+=s[k];
			}
		}
		return containerVolume;
	}
	static int firstFitDecreasing(){
		int containerVolume =1;
		
		for(int k=0;k<N;k++) {
			boolean placed =false;
			for(int p = 0; p< containerVolume; p++) {
				if(BinPacking.containerVolume[p]+s[k]<=B) {
					x[k]=p;
					BinPacking.containerVolume[p]+=s[k];
					placed =true;
					break;
				}
			}
			if(!placed) {
				containerVolume++;
				x[k]= containerVolume -1;
				BinPacking.containerVolume[containerVolume -1]+=s[k];
			}
		}
		return containerVolume;
	}
	static int nextFit(){
		int containerVolume =1;
		
		for(int k=0;k<N;k++) {
			if(BinPacking.containerVolume[containerVolume -1]+s[k]>B) {
				containerVolume++;
			}
			x[k]= containerVolume -1;
			BinPacking.containerVolume[containerVolume -1]+=s[k];
		}
		return containerVolume;
	}
		
	public static void main(String[] args) throws IOException {
		readDataFromFile(fileName);
		//System.out.println(nextFit());
		//System.out.println(firstFit());
		System.out.println(bestFit());
		//System.out.println(worstFit());
		//System.out.println(firstFitDecreasing());
		
		//56 //49
	}

}

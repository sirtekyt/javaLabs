package onLabs;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TravelingSalesman {
	
	static String fileName = "berlin52tsp.txt";
	static int N = 0; //number of cities
	static int[] route;
	static citiesCoordinates[] cities;
	
	static class citiesCoordinates
	{
		double x;
		double y;
		boolean visited;
		
		citiesCoordinates(double x, double y)
		{
			this.x = x;
			this.y = y;
			this.visited = false;
		}
		
		public double getX()
		{
			return x;
		}
		
		public double getY()
		{
			return y;
		}

		
		public double distanceToCity(citiesCoordinates coords)
		{
	        double x = getX() - coords.getX();
	        double y = getY() - coords.getY();
	        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	    }
		

		public String toString()
		{
			return "City Coordinates: " + this.x + ", " + this.y;
		}
	}
	
	static void getDataFromFile() throws IOException
	{
		File data2= new File(fileName);
		Scanner s = new Scanner(data2);
		
		//skip 3 useless lines
		s.nextLine();
		s.nextLine();
		s.nextLine();
		
		//4 number of city
		N = Integer.parseInt(s.nextLine().split(" ")[1]);
		
		cities = new citiesCoordinates[N];
		s.nextLine();
		s.nextLine();
		
		System.out.println("Points: "+ N);
		
		int i = 0;
		while(i < N)
		{
			String line = s.nextLine();
			String[] vals = line.split(" ");
			
			double x = Double.parseDouble(vals[1]);
			double y = Double.parseDouble(vals[2]);
			
			citiesCoordinates c = new citiesCoordinates(x,y);
			
			cities[i++] = c;
		}
		for(i = 0; i < cities.length; i++)
		{
			System.out.println(i + ": " + cities[i]);
		}	
	}
	
	
	static double routeLength(int[] distances)
	{
		double d = 0.0;
		
		for(int i = 0; i< distances.length -1; i++)
		{
			d += cities[distances[i]].distanceToCity(cities[distances[i+1]]);
		}
		d += cities[distances[distances.length-1]].distanceToCity(cities[distances[0]]);
		
		return d;
	}
	/////////////////////////////MAIN///////////////////////////////////////////////////////
	public static void main(String[] args) throws IOException 
	{
		getDataFromFile();
		route = new int[N];
		route = TSP_NearestNeighbor();
		
		for(int i = 0; i < N; i++)
		{
			System.out.print(route[i] + "->");
		}
		System.out.println(route[0]);
		System.out.println("Route Length " + routeLength(route));
	}
	/////////////////////////////TSP///////////////////////////////////////////////////////
	static int[] TSP_NearestNeighbor()
	{
		int[] tr = new int[N];
		int actualCity = 0;
		tr[0] = actualCity;
		cities[tr[0]].visited = true;
		
		for(int j = 1; j < N; j++)
		{
			double od1 = 9999;
			int m = 1;
			for(int k = 1; k < N; k++)
			{
				if(!cities[k].visited)
				{
					//szukam sasiada
					double d = cities[actualCity].distanceToCity(cities[k]);
					if(d < od1)
					{
						od1 = d;
						m = k;
					}
				}
			}
			tr[j] = m;
			cities[tr[j]].visited = true;
			actualCity = m;
		}
		return tr;
	}


}

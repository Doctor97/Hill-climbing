import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Hill_Climbing {
	
	protected static final File dataset = new File("dataset.txt");
	protected static ArrayList<City> cities = new ArrayList<>();
	
	public static void extract() throws IOException{
		BufferedReader brItem = null;
		try {
			brItem = new BufferedReader(new FileReader(dataset));
			String currentLine;
			int row = 0;
			while ((currentLine = brItem.readLine()) != null) {
				String[] columns = currentLine.split("\\s+");
				City newCity = new City(row, new HashMap<Integer, Integer>());
				newCity.putDistance(columns);
				cities.add(newCity);
				row++;
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public static void start(){
		ArrayList<Integer> path = createRandStartPath();
		//System.out.println(path);
		ArrayList<ArrayList<Integer>> neighbours = getNeighbours(path);
		//pathWeight(path);
		ArrayList<Integer> bestNeighbour = findBestNeighbour(neighbours);
		//System.out.println(bestNeighbour);
		int bestWeight = pathWeight(bestNeighbour);
		//System.out.println(bestWeight);
		//boolean first = true;
		//System.out.println(bestNeighbour + " --- " + bestWeight);
		while(true){
			ArrayList<ArrayList<Integer>> newNeighbours = getNeighbours(bestNeighbour);
			ArrayList<Integer> newBestNeighbour = findBestNeighbour(newNeighbours);
			int newBestWeight = pathWeight(newBestNeighbour);
			if(newBestWeight < bestWeight){
				bestWeight = newBestWeight;
				bestNeighbour = newBestNeighbour;
				//neighbours = newNeighbours;
			}
			else break;
		}
		//System.out.println(bestWeight);
		System.out.println(bestNeighbour + " --- " + bestWeight);
	}
	
	public static ArrayList<Integer> createRandStartPath(){
		Random rand = new Random();
		int startCity = rand.nextInt(cities.size());
		ArrayList<Integer> values = new ArrayList<>(cities.get(startCity).distances.values());
		ArrayList<Integer> path = new ArrayList<>();
		for(int i = 0; i < cities.get(startCity).distances.size(); i++){
			int nextCity = rand.nextInt(values.size());
			if(!path.contains(nextCity)){
				path.add(nextCity);
			}
			else{
				while(path.contains(nextCity)){
					nextCity = rand.nextInt(values.size());
				}
				path.add(nextCity);
			}
		}
		return path;
	}
	
	public static ArrayList<ArrayList<Integer>> getNeighbours(ArrayList<Integer> originalPath){
		ArrayList<ArrayList<Integer>> neighbours = new ArrayList<>();
		for(int i = 0; i < originalPath.size()-1; i++){
			ArrayList<Integer> newPath = new ArrayList<>(originalPath) ;
			int a = newPath.get(i);
			int b = newPath.get(i+1);
			newPath.set(i+1, a);
			newPath.set(i, b);
			neighbours.add(newPath);
		}
		return neighbours;
	}
	
	public static ArrayList<Integer> findBestNeighbour(ArrayList<ArrayList<Integer>> neighbours){
		ArrayList<Integer> weights = new ArrayList<>();
		int min = 1000000;
		ArrayList<Integer> bestNeighbour = new ArrayList<>();
		for(ArrayList<Integer> neighbour : neighbours){
			int weight = pathWeight(neighbour);
			if(weight < min){
				min = weight;
				bestNeighbour = neighbour;
			}
			weights.add(weight);
		}
		System.out.println("Best neighbor" + bestNeighbour + " weight: " + pathWeight(bestNeighbour));
		return bestNeighbour;
	}
	
	public static int pathWeight(ArrayList<Integer> path){
		int totalWeight = 0;
		for(int i = 0; i < path.size()-1; i++){
			int a = path.get(i);
			int b = path.get(i+1);
			City city = cities.get(a);
			int weight = city.distances.get(b);
			totalWeight += weight;
			//System.out.println(weight + " from " + a + " to " + b);
		}
		//System.out.println(totalWeight);
		return totalWeight;	
	}
	
	public static void main(String[] args) throws IOException{
		extract();
		for(int i = 0; i < 1; i++){
			start();
		}	
	}
}

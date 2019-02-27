import java.util.HashMap;

public class City {
	
	public int id;
	public HashMap<Integer, Integer> distances;
	
	public City(int id, HashMap<Integer, Integer> distances){
		this.id = id;
		this.distances = distances;
	}
	
	public void putDistance(String[] columns){
		for(int i = 0; i < columns.length; i++){
			int distance = Integer.parseInt(columns[i]);
			distances.put(i, distance);
		}
	}
	
	@Override
	public String toString(){
		return id+"";
	}
}

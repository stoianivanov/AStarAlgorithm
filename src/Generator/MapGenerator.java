package Generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AStar.*;
import CVS.CVSReader;
public class MapGenerator {
	private String[][] map = null;
	
	public MapGenerator(List<String> map) {
		super();
		this.map = generateMap(map);
	}

	private String[][] generateMap(List<String> map){
		String[][] result = new String[map.size()][map.get(0).length()];
		String cvsSplitBy = ",";
		
		for (int i = 0; i < map.size(); i++) {
			result[i] = map.get(i).split(cvsSplitBy);
		}

		
		return result;
	}
	
	public String getPoint(int x, int y){
		return this.map[0][y] + this.map[x][0];
	}

	private Map<String, Double> getNode(int posX, int posY){
		Map<String, Double> currentMap = new HashMap<String, Double>();
		
		if( !map[posX][posY].equals("N")){
			
			currentMap.put(map[0][posY] + map[posX][0], 0.0);
			//System.out.println(map[0][posY] + map[posX][0]);
			if (!(posX + 1 >= map.length || map[posX +1][posY].equals("N"))){
				if (map[posX +1][posY].equals("~")){

					currentMap.put(map[0][posY] + map[posX + 1][0], 2.0);
				}else {
					//if (map[posX +1][posY].equals(" "))
					currentMap.put(map[0][posY] + map[posX + 1][0], 1.0);
				}
			}
			
			if (!(posY + 1 >= map[0].length || map[posX][posY + 1].equals("N"))){
				if (map[posX][posY + 1].equals("~")){
					currentMap.put(map[0][posY + 1] + map[posX][0], 2.0);
				}else {
					//if (map[posX][posY + 1].equals(" "))
					currentMap.put(map[0][posY + 1] + map[posX][0], 1.0);
				}
			}
			
			
			if (!(posY - 1 < 0 || map[posX][posY - 1].equals("N"))){
				if (map[posX][posY - 1].equals("~")){
					currentMap.put(map[0][posY - 1] + map[posX][0], 2.0);
				}else {
					//if (map[posX][posY - 1].equals(" "))
					currentMap.put(map[0][posY - 1] + map[posX][0], 1.0);
				}
			}
			
			if (!(posX - 1 < 0 || map[posX - 1][posY].equals("N"))){
				if (map[posX - 1][posY].equals("~")){
					currentMap.put(map[0][posY] + map[posX - 1][0], 2.0);
				}else {
					//if (map[posX - 1][posY].equals(" "))
					currentMap.put(map[0][posY] + map[posX - 1][0], 1.0);
				}
			}
			
			if (!(posX - 1 < 0 || map[posX - 1][posY - 1].equals("N") || posY - 1 < 0)){
				if (map[posX - 1][posY - 1].equals("~")){
					currentMap.put(map[0][posY - 1] + map[posX - 1][0], 3.5);
				}else {
					//if (map[posX - 1][posY - 1].equals(" "))
					currentMap.put(map[0][posY - 1] + map[posX - 1][0], 1.5);
				}
			}
			
			
			if (!(posX + 1 >= map.length || map[posX + 1][posY - 1].equals("N") || posY + 1 >= map[0].length)){
				if (map[posX + 1][posY + 1].equals("~")){
					currentMap.put(map[0][posY + 1] + map[posX + 1][0], 3.5);
				}else {
					//if (map[posX +1][posY + 1].equals(" "))
					currentMap.put(map[0][posY + 1] + map[posX + 1][0], 1.5);
				}
			}
			
			if (!(posX + 1 >= map.length || map[posX + 1][posY - 1].equals("N") || posY - 1 < 0)){
				if (map[posX + 1][posY - 1].equals("~")){

					currentMap.put(map[0][posY - 1] + map[posX + 1][0], 3.5);
				}else {
					//if (map[posX +1][posY - 1].equals(" "))

					currentMap.put(map[0][posY - 1] + map[posX + 1][0], 1.5);
				}
			}
			
			if (!(posX - 1 < 0 || posY + 1 >= map[0].length)){
				if (map[posX - 1][posY + 1].equals("N")){
					if (map[posX - 1][posY + 1].equals("~")){
						currentMap.put(map[0][posY + 1] + map[posX - 1][0], 3.5);
					}else {
						//if (map[posX - 1][posY + 1].equals(" "))
						currentMap.put(map[0][posY + 1] + map[posX - 1][0], 1.5);
					}
				}

			}
			
			return currentMap;
		}
		
		return null;
		
	}
	
	public Map<String, Map<String, Double>> getHueristic(){
		Map<String, Map<String, Double>> hueristic = new HashMap<String, Map<String, Double>>();
		

		for(int i = 1; i< map.length; ++i){
			for(int j = 1; j < map[i].length; ++j){
				Map<String, Double>  currentMap = getNode(i,j);
				if (currentMap != null){
					hueristic.put(map[0][j] + map[i][0], currentMap);
				}
			}
			
		}
		return hueristic;
		
	}
	
	
	public void run(){
		GraphAStar<String> graph = new GraphAStar<String>(getHueristic());
		addAllNodes(graph);
		
	}
	
	public void addAllNodes(GraphAStar<String> graph){
		for(int i = 1; i< map.length; ++i){
			for(int j = 1; j < map[i].length; ++j){
				Map<String, Double>  currentMap = getNode(i,j);
				if (currentMap != null){
					graph.addNode(map[0][j] + map[i][0]);
				}
			}
		}
	}
	
	public void addAllEdge(GraphAStar<String> graph){
		for(int i = 1; i < map.length; ++i){
			for(int j = 1; j < map[i].length; ++j){
				addEdge(graph,i,j);
			}
		}
	}
	
	public void addEdge(GraphAStar<String> graph, int posX, int posY){
		
		
		if( !map[posX][posY].equals("N")){
			
			String currentPos = map[0][posY] + map[posX][0];
			if (!(posX + 1 > map.length -1 || map[posX +1][posY].equals("N"))){
				if (map[posX +1][posY].equals("~")){
					graph.addEdge(currentPos, map[0][posY] + map[posX + 1][0], 2.0);
				}else{
					graph.addEdge(currentPos,map[0][posY] + map[posX + 1][0], 1.0);
				}
			}
			
			if (!(posY + 1 > map[0].length - 1 || map[posX][posY + 1].equals("N"))){
			
				if (map[posX][posY + 1].equals("~")){
					graph.addEdge(currentPos,map[0][posY + 1] + map[posX][0], 2.0);
				}else {
					graph.addEdge(currentPos,map[0][posY + 1] + map[posX][0], 1.0);
				}
			}
			
			
			if (!(posY - 1 < 1 || map[posX][posY - 1].equals("N"))){
				if (map[posX][posY - 1].equals("~")){
					graph.addEdge(currentPos,map[0][posY - 1] + map[posX][0], 2.0);
				}else {
					graph.addEdge(currentPos,map[0][posY - 1] + map[posX][0], 1.0);
				}
			}
			
			if (!(posX - 1 < 1 || map[posX - 1][posY].equals("N"))){
				if (map[posX - 1][posY].equals("~")){
					graph.addEdge(currentPos,map[0][posY] + map[posX - 1][0], 2.0);
				}else {
					graph.addEdge(currentPos,map[0][posY] + map[posX - 1][0], 1.0);
				}
			}
			
			if (!(posX - 1 < 1 || posY - 1 < 1)){
				if(!map[posX - 1][posY - 1].equals("N")){
					if (map[posX - 1][posY - 1].equals("~")){
						graph.addEdge(currentPos,map[0][posY - 1] + map[posX - 1][0], 3.5);
					}else {
						graph.addEdge(currentPos,map[0][posY - 1] + map[posX - 1][0], 1.5);
					}
				}

			}
			
			
			if (!(posX + 1 > map.length  - 1|| posY + 1 > map[0].length - 1)){
				if(!map[posX + 1][posY + 1].equals("N")){
					if (map[posX + 1][posY + 1].equals("~")){
						graph.addEdge(currentPos,map[0][posY + 1] + map[posX + 1][0], 3.5);
					}else {
						graph.addEdge(currentPos,map[0][posY + 1] + map[posX + 1][0], 1.5);
					}
				}
				

			}
			
			if (!(posX + 1 > map.length - 1 || posY - 1 < 1)){
				if (!map[posX + 1][posY - 1].equals("N") ){
					if (map[posX + 1][posY - 1].equals("~")){
						graph.addEdge(currentPos,map[0][posY - 1] + map[posX + 1][0], 3.5);
					}else {
						graph.addEdge(currentPos,map[0][posY - 1] + map[posX + 1][0], 1.5);
					}
				}

			}
			
			if (!(posX - 1 < 1 || posY + 1 > map[0].length - 1 )){
				if (!map[posX - 1][posY + 1].equals("N") ){
					if (map[posX - 1][posY + 1].equals("~")){
						graph.addEdge(currentPos,map[0][posY + 1] + map[posX - 1][0], 3.5);
					}else {
						graph.addEdge(currentPos,map[0][posY + 1] + map[posX - 1][0], 1.5);
					}
				}

			}
			

		}
	}
	/*
	public static void main(String[] args) {
		CVSReader r = new CVSReader("/home/stoian/workspace/AStarAlgorithm/src/CVS/soz.csv");
		r.read();
		
		MapGenerator map = new MapGenerator(r.getMap());
		GraphAStar<String> graph = new GraphAStar<String>(map.getHueristic());
		map.addAllNodes(graph);
		map.addAllEdge(graph);
		
		
        AStar<String> aStar = new AStar<String>(graph);
        System.out.println("Print path");
        for (String path : aStar.astar("A6", "H5")) {
            System.out.println(path);
        }
	}
	*/
}

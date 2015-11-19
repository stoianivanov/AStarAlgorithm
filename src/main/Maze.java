package main;

import java.util.ArrayList;
import java.util.List;

import AStar.AStar;
import AStar.GraphAStar;
import CVS.CVSReader;
import Generator.MapGenerator;

public class Maze {
	private CVSReader reader;
	private MapGenerator map;
	
	
	public Maze(String path) {
		this.reader = new CVSReader(path);
		reader.read();
		
		this.map = new MapGenerator(reader.getMap());
	}
	
	
	public List<String> findPath(Point start, Point end){
		List<String> result = new ArrayList<String>();
		
		GraphAStar<String> graph = new GraphAStar<String>(this.map.getHueristic());
		map.addAllNodes(graph);
		map.addAllEdge(graph);
        AStar<String> aStar = new AStar<String>(graph);
        String startP = this.map.getPoint(start.getPointX(), start.getPointY());
        String endP = this.map.getPoint(end.getPointX(), end.getPointY());
        for (String path : aStar.astar(startP, endP)) {
            //System.out.println(path);
            result.add(path);
        }
		
		return result;
	}
	
	
	public static void main(String[] args) {
		Maze maze = new Maze(args[0]);
		Point start = new Point( Integer.parseInt(args[1]),  Integer.parseInt(args[2]));
		Point end = new Point( Integer.parseInt(args[3]),  Integer.parseInt(args[4]));
		
		List<String> path = maze.findPath(start, end);
		
		for (String step : path) {
    			System.out.print(step.toString() + " ");

		}
	
	}

}

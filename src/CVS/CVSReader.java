package CVS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CVSReader {

	private String path;
	private List<String> map;
	
	public CVSReader(String path) {
		super();
		this.path = path;
	}

	public void read(){
		BufferedReader br = null;
		String line = "";
		List<String> map= new ArrayList<String>();
		try {

			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				map.add(line);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		this.map = map;
		System.out.println("Done");
		
	  }
	
	public List<String> getMap (){
		return this.map;
	}
	
	/*
	public static void main(String[] args) {
		CVSReader r = new CVSReader("/home/stoian/workspace/AStarAlgorithm/src/CVS/soz.csv");
		r.read();
	}*/
}

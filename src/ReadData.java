import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadData {
	List<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
	
	
	public static void main(String[] args) {
		ReadData d = new ReadData();
	}
	
	public ReadData() {
		parseData("D:\\OneDrive\\CS\\CS157a\\SeaS\\dataset.csv");
		writeFile();
	}
	
	public void parseData(String fileName) {
		File file = new File(fileName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str = br.readLine();
			ArrayList<String> stringSet;
			//System.out.println(str);
			while((str = br.readLine()) != null) {
				stringSet = new ArrayList<>(Arrays.asList(str.split(",")));
				//System.out.println(str);
				String source = stringSet.get(1).split(" - ")[0];
				stringSet.add(source);
				
				String rawLocation = stringSet.get(1).split(" - ")[1];
				if(rawLocation.contains("source to conf")) {
					stringSet.add(rawLocation.substring(15));
					dataSet.add(stringSet);
				} else if (rawLocation.contains("source to")) {
					stringSet.add(rawLocation.substring(10));
					dataSet.add(stringSet);
				} else if (rawLocation.contains("conf") && rawLocation.contains("to conf")) {
					String[] temp = rawLocation.split(" to conf ");
					ArrayList<String> stringSet2 = new ArrayList<>(stringSet);
					stringSet.add(temp[0].substring(5));
					stringSet2.add(temp[1]);
					dataSet.add(stringSet);
					dataSet.add(stringSet2);
				} else if (rawLocation.contains("conf") && rawLocation.contains("to")) {
					String[] temp = rawLocation.split(" to ");
					ArrayList<String> stringSet2 = new ArrayList<>(stringSet);
					stringSet.add(temp[0].substring(5));
					stringSet2.add(temp[1]);
					dataSet.add(stringSet);
					dataSet.add(stringSet2);
				} else {
					String[] temp = rawLocation.split(" to ");
					ArrayList<String> stringSet2 = new ArrayList<>(stringSet);
					stringSet.add(temp[0]);
					stringSet2.add(temp[1]);
					dataSet.add(stringSet);
					dataSet.add(stringSet2);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeFile() {
		try {
			Writer origin = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("D:\\OneDrive\\CS\\CS157a\\SeaS\\origin.txt")));
			Writer waterbody = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("D:\\OneDrive\\CS\\CS157a\\SeaS\\waterbody.txt")));
			for(List<String> list : dataSet) {
				writeOrigin(origin, list);
				writeWaterbody(waterbody, list);
			}
			try {
				origin.close();
				waterbody.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	private void writeOrigin(Writer writer, List<String> list) {
		try {
			writer.write("\t,");
			writer.write(list.get(list.size() - 2));
			//System.out.println(list.get(list.size() - 2));
			writer.write(",");
			writer.write(list.get(list.size() - 1));
			writer.write("\n");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void writeWaterbody(Writer writer, List<String> list) {
		try {
			writer.write("\t");
			writer.write(list.get(0).substring(2));
			writer.write(",");
			writer.write(list.get(1));
			writer.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}

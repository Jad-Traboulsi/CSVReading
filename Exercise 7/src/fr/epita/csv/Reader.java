package fr.epita.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.nio.file.Files;

public class Reader {
	public static void main(String[] args) throws IOException {
		
		List<String> listOfLines = Files.readAllLines(new File("data.csv").toPath());
		System.out.println(listOfLines);
		List<Data> people = new ArrayList<Data>();
		listOfLines.remove(0);
		for (String line : listOfLines) {
			
			line = line.replaceAll("\"", ""); // remove double quotes from the whole string.
			line = line.replaceAll(" ", ""); // remove double quotes from the whole string.
			String[] l = line.split(",");		
			try {
			people.add(new Data(l[0],l[1], Integer.parseInt(l[2]), Double.parseDouble(l[3]),Double.parseDouble(l[4])));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			
			
		}
		System.out.println(people);
		//for(Data i: people)
			//System.out.println(i.toString());
		//System.out.println(from2dMatrix(to2dMatrix(people)));
		
		Map<Integer,Long> res = getAge2(people);
		System.out.println(res);
		histo(res);
		
		
	}
	// 1 is male 0 is female
	public static double[][] to2dMatrix(List<Data> persons)
	{
		int size = persons.size();
		
		double [][] results = new double[size][4];
		for(int i=0;i<size;i++)
		{
			
			if(persons.get(i).getSex().equals("M"))
				results[i][0]=1;
			else if(persons.get(i).getSex().equals("F"))
				results[i][0]=0;
			else
				results[i][0] = -1;
			results[i][1]=persons.get(i).getAge();
			results[i][2]=persons.get(i).getHeight();
			results[i][3]=persons.get(i).getWeight();
		}
			
		return results;
		
	}
	public static List<Data> from2dMatrix(double[][] persons)
	{
		List<Data> results = new ArrayList<Data>();
		String sex = "";
		int age = 0;
		double height = 0;
		double weight = 0;
		
		for(double[] i:persons)
		{
			if(i[0] == 0)
				sex="F";
			else if (i[0] == 1)
				sex = "M";
			else
				sex = "ERROR";

			age = (int) i[1];
			height = i[2];
			weight = i[3];
			results.add(new Data("",sex,age,height,weight));
		}
		
		
		return results;
		
	}
	public static int[][] getAges(List<Data> persons) {
		
		int size = persons.size();
		int result[][] = new int[size][2];
		

		List<Integer> ages = new ArrayList<Integer>();
		for(Data s: persons){
			ages.add(s.getAge());
		}
		
		for (int i =0;i<size;i++) {
			
			result[i][0] = ages.get(i);
			result[i][1] = Collections.frequency(ages, ages.get(i));
		}
		return result;
	}
	public static Map<Integer,Long> getAge2(List<Data> persons){
		Map<Integer,Long> out = persons.stream().collect(Collectors.groupingBy(Data::getAge,Collectors.counting()));
		SortedMap<Integer,Long> sorted = new TreeMap<>();
		sorted.putAll(out);
		return sorted;
	}
	public static void histo(Map<Integer,Long> in) {

        for (Map.Entry<Integer,Long> entry : in.entrySet()) 
        {
            System.out.print(entry.getKey() + " : ");
            for(int i=0;i<entry.getValue();i++)
            {
            	System.out.print("=");
            }
            System.out.println("");
        }
	}
}

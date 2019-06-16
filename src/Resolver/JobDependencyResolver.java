package Resolver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JobDependencyResolver {
	List<String> inputList;
	List<String> resultList;

	public JobDependencyResolver() {
		inputList = new ArrayList<String>(); 
	}

	public void printResult() {

	}

	public String[] parseLine(String line) {
		String[] tokens = line.split("=>");
		if (tokens.length == 2) {
			tokens[0] = tokens[0].trim();
			tokens[1] = tokens[1].trim();
			System.out.println("Token 1: " + tokens[0] + " " + "Token 2: " + tokens[1]);
		} else {
			System.out.println("Skipped, Invalid Input: " + line);
			return null;
		}
		return tokens;
	}

	public void processInput() {
		InputStreamReader r=new InputStreamReader(System.in);    
		BufferedReader br=new BufferedReader(r); 
		String line = null;
		try {
			while((line = br.readLine()) != null) {
				if (line.equalsIgnoreCase("stop")) {
					break;
				} else {
					String[] tokens = parseLine(line);
					if (tokens != null) {
						//addEdge();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JobDependencyResolver jobResolver = new JobDependencyResolver();
		jobResolver.processInput();
	}
} 

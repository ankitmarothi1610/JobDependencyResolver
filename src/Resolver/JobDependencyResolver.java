package Resolver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

public class JobDependencyResolver {
	List<String[]> inputList;
	
	
	public JobDependencyResolver() {
		inputList = new ArrayList<String[]>();
	}

	public void printResult(List<String> resultList) {
		if (resultList == null || resultList.isEmpty()) {
			System.out.println("Empty Sequence");
		} else {
			System.out.print("Job Sequence: " );
			for (String result: resultList) {
				System.out.print(" " + result);
			}
		}
	}

	public boolean detectCycle(DefaultDirectedGraph<String, DefaultEdge> directedGraph) {
		CycleDetector<String, DefaultEdge> cycleDetector 
	      = new CycleDetector<String, DefaultEdge>(directedGraph);
	    return cycleDetector.detectCycles();
	}
	
	public List<String> scheduleJobs(List<String[]> inputList) {
		List<String> resultList = new ArrayList<String>(inputList.size());
		DefaultDirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
		DepthFirstIterator depthFirstIterator = null;
		for (String[] vertices: inputList) {
			directedGraph.addVertex(vertices[0]);
			directedGraph.addVertex(vertices[1]);
			directedGraph.addEdge(vertices[1], vertices[0]);
		}
		if (detectCycle(directedGraph)) {
			System.out.println("ERROR: The jobs cannot depend on themselves");
			return null;
		}
		depthFirstIterator = new DepthFirstIterator<>(directedGraph);
		while(depthFirstIterator.hasNext()) {
			resultList.add(depthFirstIterator.next().toString());
		}
		return resultList;
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
		List<String> resultList;
		try {
			while((line = br.readLine()) != null) {
				if (line.equalsIgnoreCase("stop")) {
					break;
				} else {
					String[] tokens = parseLine(line);
					if (tokens != null) {
						inputList.add(tokens);
					}
				}
			}
			if (inputList.isEmpty()) {
				System.out.println();
			} else {
				resultList = scheduleJobs(inputList);
				printResult(resultList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		if (inputList != null)
			inputList.clear();
	}
	
	public static void main(String[] args) {
		JobDependencyResolver jobResolver = new JobDependencyResolver();
		jobResolver.processInput();
	}
} 

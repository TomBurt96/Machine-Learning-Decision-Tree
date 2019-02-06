import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class Main {
	
	public static void main(String[] args) throws IOException {
		Problem problem = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("Type the example file name below:");
		String filename = scan.nextLine();
		
		if(filename.equals("Iris-data-discrete.txt")){
			problem = new IrisProblem();
		}else if(filename.equals("WillWait-data.txt")){
			problem = new WillWaitProblem();
		}else if(filename.equals("house-votes-84.data.mod.txt")){
			problem = new HouseVotesProblem();
		}
		else{
			System.out.println("Your filename input is incorrect");
		}
		Set<Example> examples = problem.readExamplesFromCSVFile(new File(filename));
		Set<Example> testing = new HashSet<Example>();
		List<Example> temp = new LinkedList<Example>();
		temp.addAll(examples);
		System.out.println("Type how many of the " + examples.size() + " examples would you like to randomly assign to use for training below:");
		int trainNum = scan.nextInt();
		while(trainNum > examples.size()){
			System.out.println("You cannot choose a training set size greater than the total example set size");
			System.out.println("Type how many examples for training you want to use below:");
			trainNum = scan.nextInt();
		}
		Random rand = new Random();
		for(int i = 0; i < examples.size()-trainNum; i++){
			int index = rand.nextInt(examples.size());
			if(!testing.contains(temp.get(index))){
				testing.add(temp.get(index));
			}else{
				i--;
			}
		}
		if(trainNum != examples.size()){//conditional for dealing with input of training set size equal to the example set size
			examples.removeAll(testing);
		}else{//if they are equal size, then we just reuse all the examples for testing
			testing.addAll(examples);
		}
		DecisionTree tree = new DecisionTreeLearner(problem).learn(examples, problem.inputs, Collections.emptySet());
		tree.test(testing);
	}
	
}

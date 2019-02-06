import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the decision-tree learning algorithm in AIMA Fig 18.5.
 * This is based on ID3 (AIMA p. 758).
 */
public class DecisionTreeLearner extends AbstractDecisionTreeLearner {
	
	/**
	 * Construct and return a new DecisionTreeLearner for the given Problem.
	 */
	public DecisionTreeLearner(Problem problem) {
		super(problem);
	}
	
	/**
	 * Main recursive decision-tree learning (ID3) method.  
	 */
	@Override
	public DecisionTree learn(Set<Example> examples, List<Variable> attributes, Set<Example> parentExamps){//Decision-Tree-Learning function
		if(examples.isEmpty()==true){
			return pluralityValue(parentExamps);//form leaf
		}
		else if(sameClass(examples)){
			String v = uniqueOutputValue(examples);//get the single shared output
			return new DecisionTree(v);//form leaf
		}
		else if(attributes.isEmpty()==true){
			return pluralityValue(examples);//form leaf
		}else{
			Variable A = mostImportantVariable(attributes, examples);//method that returns the variable with the highest entropy
			DecisionTree tree = new DecisionTree(A);
			for(int i = 0; i < A.domain.size(); i++){//iterate over all values of this most important variable
				Set<Example> exs = examplesWithValueForAttribute(examples, A, A.domain.get(i));//get all examples with the specified value in Variable A's domain
				List<Variable> otherVars = new LinkedList<Variable>();
				otherVars.addAll(attributes);//eliminates reference
				otherVars.remove(A);
				DecisionTree subtree = learn(exs,otherVars,examples);
				tree.children.add(subtree);
			}
			return tree;
		}
	}
	
	public boolean sameClass(Set<Example> examples){//function that checks if all examples have the same classification
		String temp = uniqueOutputValue(examples);//uses the uniqueOutputValue method to compute whether all examples are classified the same in a given set of examples
		if(temp == null){//if the string returned is null, then there is not one unique classification for all the examples in teh set
			return false;
		}
		return true;//if its not null, then there is only one classification
	}
	
	/**
	 * Returns the most common output value among a set of Examples,
	 * breaking ties randomly.
	 * I don't do the random part yet.
	 */
	@Override
	protected DecisionTree pluralityValue(Set<Example> examples) {//method that finds the most commonly appeared output in a given example set
		String output = "";//instantiate the max output string
		int max = 0;
		for(String s : problem.output.domain){
			int num = countExamplesWithValueForOutput(examples, s);//find the number of examples each of the ouput values
			if(max == 0 || max < num){//check if greater than maximum value already seen
				max = num;
				output = s;
			}
		}
		return new DecisionTree(output);//return a leaf node with the most common output value of the example set
	}
	
	
	/**
	 * Returns the single unique output value among the given examples
	 * is there is only one, otherwise null.
	 */
	@Override
	protected String uniqueOutputValue(Set<Example> examples) {//method that checks whether all the examples in the example set have the same output or classification
		String temp = "";
	    for(Example e : examples){
	    	if(temp.equals("")){
	    		temp = e.getOutputValue();
	    	}else{
	    		if(!(temp.equals(e.getOutputValue()))){//if there example set contains examples with different classifications, return null
	    			return null;
	    		}
	    	}
	    }
	    return temp;
	}
	
	//
	// Utility methods required by the AbstractDecisionTreeLearner
	//

	/**
	 * Return the subset of the given examples for which Variable a has value vk.
	 */
	@Override
	protected Set<Example> examplesWithValueForAttribute(Set<Example> examples, Variable v, String val) {//returns set of all examples with the given value of a variable V
		Set<Example> s = new HashSet<Example>();
		for(Example e : examples){
			if(e.getInputValue(v).equals(val)){//add if value of variable same as input value of example for the given variable
				s.add(e);
			}
		}
		return s;
	}
	
	/**
	 * Return the number of the given examples for which Variable a has value vk.
	 */
	@Override
	protected int countExamplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {//method to count how many examples have an attribute vk from the domain of a variable v
		int result = 0;
		for (Example e : examples) {
			if (e.getInputValue(a).equals(vk)) {
				result += 1;
			}
		}
		return result;
		
	}

	/**
	 * Return the number of the given examples for which the output has value vk.
	 */
	@Override
	protected int countExamplesWithValueForOutput(Set<Example> examples, String val) {//method that returns the number of examples from the example set that have the given val in the parameter as their ouput
		int count = 0;
		for(Example e : examples){
			if(e.getOutputValue().equals(val)){
				count++;
			}
		}
		return count;
	}

}


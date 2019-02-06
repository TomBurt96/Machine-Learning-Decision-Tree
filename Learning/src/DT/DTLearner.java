import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DTLearner {
	protected Problem problem;
	public DTLearner(Problem problem) {
		this.problem = problem;
	}
	
	public DecisionTree learn(Set<Example> examples, List<Variable> attributes, Set<Example> parentExamps){
		if(examples.isEmpty()==true){
			return PluralityVal(parentExamps);
		}
		//If examples wasn't a set, then we would have to check if all of the examples are the same.
		//But since it is a set, there cannot be examples that are equal, so we check if there is only 
		//one example in the set.
		else if(examples.size()==1){
			String v = "";
			//because there is no get function in a set, I use iteration through examples in order
			//to get the one example left.  This is only a 1 round loop.
			for(Example e : examples){
				v = e.getOutputValue();
			}
			return new DecisionTree(v);
		}
		else if(attributes.isEmpty()==true){
			return PluralityVal(examples);
		}else{
			Variable A = findMax(attributes, examples);
			DecisionTree tree = new DecisionTree(A);
			for(int i = 0; i < A.domain.size(); i++){
				Set<Example> exs = getExamps(A, A.domain.get(i), examples);
				List<Variable> otherVars = new LinkedList<Variable>();
				otherVars.addAll(attributes);
				otherVars.remove(A);
				DecisionTree subtree = learn(exs,otherVars,examples);
				tree.children.add(subtree);
			}
			return tree;
		}
	}
	
	public Set<Example> getExamps(Variable v, String val, Set<Example> examples){
		Set<Example> s = new HashSet<Example>();
		for(Example e : examples){
			boolean contain = e.inputValues.containsKey(v);
			if(contain == true && e.getInputValue(v).equals(val)){
				s.add(e);
			}
		}
		return s;
	}
	
	public DecisionTree PluralityVal(Set<Example> examples){
		String output = "";
		int max = 0;
		for(Example e : examples){
			int num = count(e.getOutputValue(), examples);
			if(output.equals(e.getOutputValue())){//if they are equal, no point in doing anything
				
			}else if(max == 0 || max < num){
				max = num;
				
			}
		}
		return new DecisionTree(output);
	}
	//counts the number of occurences 
	public int count(String val, Set<Example> examples){
		int count = 0;
		for(Example e : examples){
			if(e.getOutputValue().equals(val)){
				count++;
			}
		}
		return count;
	}
	
	//finds and returns the variable with the greatest entropy, 
	//meaning it will do the most.  
	public Variable findMax(List<Variable> attributes, Set<Example> examples){
		Variable maxVar = attributes.get(0);
		double max = 0.0;
		for(int i = 0; i < attributes.size(); i++){
			Variable v = attributes.get(i);
			double sum = H(v, examples);
			if(max == 0.0 || max < sum){
				max = sum;
				maxVar = v;
			}
		}
		return maxVar;
	}	
	//function for finding the Entropy of a Variable
	public double H(Variable v, Set<Example> examples){
		double prob = 0.0;
		double sum = 0.0;
		ArraySet<String> list = v.domain;
		for(String val : list){
			prob = getProb(v, val, examples);
			double i = (prob*log2(prob));
			sum+=i;
		}
		return -sum;
	}
	public double B(double q){
		if(q == 1.0 || q == 0.0){
			return 0.0;
		}else{
			return -(q*log2(q)+(1-q)*log2(1-q));
		}
	}	
	
	protected double log2(double x) {
		return Math.log(x) / Math.log(2.0);
	}	
	public double getProb(Variable v, String val, Set<Example> examples){
		int count = 0;
		for(Example e : examples){
			if(e.getInputValue(v).equals(val)){
				count++;
			}
		}
		double prob = count/examples.size();
		return prob;
	}
	

}

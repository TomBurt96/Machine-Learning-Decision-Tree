import java.util.LinkedList;

public class IrisProblem extends Problem {

	
	public IrisProblem(){
		super(new LinkedList<Variable>(), null);
		Domain domain = new Domain("S", "MS", "ML", "L");
		this.inputs.add(new Variable("SepalLength", domain));
		this.inputs.add(new Variable("SepalWidth", domain));
		this.inputs.add(new Variable("PetalLength", domain));
		this.inputs.add(new Variable("PetalWidth", domain));
		this.output = new Variable("Classification", new Domain("Iris-setosa", "Iris-versicolor", "Iris-virginica"));
		
	}
	
}

public class HouseVotesProblem extends Problem {
	
	public HouseVotesProblem(){
		Domain domain = new Domain("y", "n", "?");
		this.inputs.add(new Variable("handicapped-infants", domain));
		this.inputs.add(new Variable("export-administration-act-south-africa", domain));
		this.inputs.add(new Variable("crime", domain));
		this.inputs.add(new Variable("adoption-of-the-budget-resolution", domain));
		this.inputs.add(new Variable("el-salvador-aid", domain));
		this.inputs.add(new Variable("water-project-cost-sharing", domain));
		this.inputs.add(new Variable("physician-fee-freeze", domain));
		this.inputs.add(new Variable("anti-satellite-test-ban", domain));
		this.inputs.add(new Variable("aid-to-nicaraguan-contras", domain));
		this.inputs.add(new Variable("mx-missile", domain));
		this.inputs.add(new Variable("immigration", domain));
		this.inputs.add(new Variable("duty-free-exports", domain));
		this.inputs.add(new Variable("superfund-right-to-use", domain));
		this.inputs.add(new Variable("education-spending", domain));
		this.inputs.add(new Variable("synfuels-corporation-cutback", domain));
		this.inputs.add(new Variable("religious-groups-in-school", domain));
		
		this.output = new Variable("Party", new Domain("democrat", "republican"));
		
		
	}
}

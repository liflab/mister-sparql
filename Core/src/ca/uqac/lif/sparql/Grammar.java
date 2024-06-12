package ca.uqac.lif.sparql;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.ltl.Every;
import ca.uqac.lif.cep.ltl.Some;
import ca.uqac.lif.cep.ltl.Troolean;

public class Grammar
{
	public static Processor forAllNodes(String var, Processor phi)
	{
		return new Every(var, new GetNodes(), phi);
	}
	
	public static Processor existsNode(String var, Processor phi)
	{
		return new Some(var, new GetNodes(), phi);
	}
	
	public static Processor apply(Function f)
	{
		return new ApplyFunction(f);
	}
	
	public static Function implies(Function phi, Function psi)
	{
		return new FunctionTree(Troolean.IMPLIES_FUNCTION, phi, psi);
	}
}

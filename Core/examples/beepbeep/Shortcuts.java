package beepbeep;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.ltl.After;
import ca.uqac.lif.cep.ltl.Always;
import ca.uqac.lif.cep.ltl.Every;
import ca.uqac.lif.cep.ltl.Some;
import ca.uqac.lif.cep.ltl.Troolean;
import ca.uqac.lif.cep.ltl.TrooleanCast;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.sparql.DottedVariable;
import ca.uqac.lif.sparql.GetNodes;
import ca.uqac.lif.sparql.LabelOf;
import ca.uqac.lif.sparql.ConnectedBy.DirectedConnectedBy;

public class Shortcuts
{
	public static Processor forAllNodes(String var, Processor phi)
	{
		return new Every(var, new GetNodes(), phi);
	}
	
	public static Processor forAllNodes(String var, Function f)
	{
		return new Every(var, new GetNodes(), apply(f));
	}
	
	public static Processor existsNode(String var, Processor phi)
	{
		return new Some(var, new GetNodes(), phi);
	}
	
	public static Processor existsNode(String var, Function f)
	{
		return new Some(var, new GetNodes(), apply(f));
	}
	
	public static Processor apply(Function f)
	{
		return new ApplyFunction(f);
	}
	
	public static Function implies(Function phi, Function psi)
	{
		return new FunctionTree(Troolean.IMPLIES_FUNCTION, cast(phi), cast(psi));
	}
	
	public static Function l(String x)
	{
		return new LabelOf(x);
	}
	
	public static Function dot(String x)
	{
		return new DottedVariable(x);
	}
	
	public static Function connected(String from, String label, String to)
	{
		return new DirectedConnectedBy(from, label, to);
	}
	
	public static Function gt(Function phi, Function psi)
	{
		return new FunctionTree(Numbers.isGreaterThan, phi, psi);
	}
	
	public static Processor G(Processor phi)
	{
		return new Always(phi);
	}
	
	public static Processor G(Function f)
	{
		return new Always(apply(f));
	}
	
	public static Processor X(Processor phi)
	{
		return new After(phi);
	}
	
	public static Processor X(Function f)
	{
		return new After(apply(f));
	}
	
	public static Function cast(Function f)
	{
		if (f.getOutputTypeFor(0).equals(Troolean.Value.class))
		{
			return f;
		}
		return new FunctionTree(TrooleanCast.instance, f);
	}
}

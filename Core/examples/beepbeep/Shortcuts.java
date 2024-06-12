package beepbeep;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.ltl.After;
import ca.uqac.lif.cep.ltl.Always;
import ca.uqac.lif.cep.ltl.Every;
import ca.uqac.lif.cep.ltl.Some;
import ca.uqac.lif.cep.ltl.Sometime;
import ca.uqac.lif.cep.ltl.Troolean;
import ca.uqac.lif.cep.ltl.TrooleanCast;
import ca.uqac.lif.cep.util.Equals;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.sparql.GetNodes;
import ca.uqac.lif.sparql.LabelOf;
import ca.uqac.lif.sparql.Placeholder;
import ca.uqac.lif.sparql.TrooleanAndProcessor;
import ca.uqac.lif.sparql.TrooleanImpliesProcessor;
import ca.uqac.lif.sparql.TrooleanNotProcessor;
import ca.uqac.lif.sparql.ConnectedBy;

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
	
	public static Processor implies(Processor phi, Processor psi)
	{
		return new TrooleanImpliesProcessor(phi, psi);
	}
	
	public static Processor and(Processor phi, Processor psi)
	{
		return new TrooleanAndProcessor(phi, psi);
	}
	
	public static Processor not(Processor phi)
	{
		return new TrooleanNotProcessor(phi);
	}
	
	public static Function l(Object x)
	{
		return LabelOf.l(x);
	}
	
	public static Placeholder<?> dot(String s)
	{
		return new Placeholder.DottedNodePlaceholder(s);
	}
	
	public static Function connected(Object from, Object label, Object to)
	{
		return ConnectedBy.connected(from, label, to);
	}
	
	public static Function eq(Function x, Function y)
	{
		return cast(new FunctionTree(Equals.instance, x, y));
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
	
	public static Processor F(Processor phi)
	{
		return new Sometime(phi);
	}
	
	public static Processor F(Function f)
	{
		return new Sometime(apply(f));
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

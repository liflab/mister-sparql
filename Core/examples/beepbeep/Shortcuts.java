package beepbeep;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.ContextVariable;
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
import ca.uqac.lif.sparql.TrooleanAndProcessor;
import ca.uqac.lif.sparql.TrooleanApplyFunction;
import ca.uqac.lif.sparql.TrooleanImpliesProcessor;
import ca.uqac.lif.sparql.TrooleanNotProcessor;
import ca.uqac.lif.sparql.ConnectedBy.DirectedConnectedBy;
import ca.uqac.lif.sparql.ConnectedBy.UndirectedConnectedBy;
import ca.uqac.lif.sparql.Dot;

public class Shortcuts
{


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
	
	/* -------- Graph-specific functions -------- */

	public static Function l(Object x)
	{
		return new LabelOf(toFunction(x));
	}

	public static Function dot(String s)
	{
		return new Dot(s);
	}

	public static Function connected(Object from, Object label, Object to)
	{
		return new DirectedConnectedBy(toFunction(from), toFunction(label), toFunction(to));
	}
	
	public static Function connectedUndir(Object from, Object label, Object to)
	{
		return new UndirectedConnectedBy(toFunction(from), toFunction(label), toFunction(to));
	}
	
	/* -------- LTL Troolean comparison operators -------- */

	public static Processor eq(Object x, Object y)
	{
		return toProcessor(cast(new FunctionTree(Equals.instance, toFunction(x), toFunction(y))));
	}

	public static Function gt(Function phi, Function psi)
	{
		return new FunctionTree(Numbers.isGreaterThan, phi, psi);
	}

	/* -------- LTL Troolean temporal operators -------- */

	public static Processor G(Object o)
	{
		return new Always(toProcessor(o));
	}

	public static Processor F(Object o)
	{
		return new Sometime(toProcessor(o));
	}

	public static Processor X(Object o)
	{
		return new After(toProcessor(o));
	}

	/* -------- LTL Troolean logical operators -------- */

	public static Function cast(Function f)
	{
		if (f.getOutputTypeFor(0).equals(Troolean.Value.class))
		{
			return f;
		}
		return new FunctionTree(TrooleanCast.instance, f);
	}
	
	/* -------- LTL Troolean quantifiers -------- */
	
	public static Processor forAllNodes(String var, Object o)
	{
		return new Every(var, new GetNodes(), toProcessor(o));
	}

	public static Processor existsNode(String var, Object o)
	{
		return new Some(var, new GetNodes(), toProcessor(o));
	}
	
	/* -------- Utility functions -------- */

	/**
	 * Lifts an arbitrary object into a BeepBeep {@link Function}. The rules for
	 * the conversion are as follows:
	 * <ul>
	 * <li>If the object is already a {@link Function}, it is returned as is;</li>
	 * <li>If the object is a string starting with the character '$', it is
	 * converted into a {@link ContextVariable} with the same name;</li>
	 * <li>Otherwise, the object is converted into a {@link Constant}.</li>
	 * @param o The object to lift
	 * @return The lifted object
	 */
	protected static Function toFunction(Object o)
	{
		if (o instanceof Function)
		{
			return (Function) o;
		}
		if (o instanceof String)
		{
			if (((String) o).startsWith("$"))
			{
				return new ContextVariable((String) o);
			}
		}
		return new Constant(o);
	}

	/**
	 * Lifts an arbitrary object into a BeepBeep {@link Processor}. The rules for
	 * the conversion are as follows:
	 * <ul>
	 * <li>If the object is already a {@link Processor}, it is returned as is;</li>
	 * <li>Otherwise, the object is converted into a {@link TrooleanApplyFunction}.</li>
	 * </ul>
	 * @param o The object to lift
	 * @return The lifted function
	 * @see #toFunction(Object)
	 */
	protected static Processor toProcessor(Object o)
	{
		if (o instanceof Processor)
		{
			return (Processor) o;
		}
		return new TrooleanApplyFunction(toFunction(o));
	}
}

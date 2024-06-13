package ca.uqac.lif.sparql;

import java.util.Set;

import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.ContextVariable;
import ca.uqac.lif.cep.functions.Function;

public abstract class ContextFunction extends Function
{
	@Override
	public int getInputArity()
	{
		return 1;
	}
	
	@Override
	public int getOutputArity()
	{
		return 1;
	}
	
	@Override
	public void getInputTypesFor(Set<Class<?>> set, int index)
	{
		if (index == 1)
		{
			set.add(KnowledgeGraph.class);
		}
	}
	
	/**
	 * Gets the value of a variable from a {@link Context} object, or a default
	 * value if the variable is not in the context or if the context is null.
	 * @param c The context
	 * @param key The key to look for
	 * @param default_value The default value
	 * @return The value of the variable in the context, or the default value
	 */
	protected static Object evaluateFromContext(Function f, Object input, Context c)
	{
		Object[] inputs = new Object[] {input};
		Object[] outputs = new Object[1];
		f.evaluate(inputs, outputs, c, null);
		return outputs[0];
	}
	
	protected static Object evaluateFromValuation(Function f, Object input, Valuation nu)
	{
		if (f instanceof ContextVariable)
		{
			ContextVariable cv = (ContextVariable) f;
			return nu.get(cv.getName());
		}
		else if (f instanceof Constant)
		{
			return ((Constant) f).getValue();
		}
		return null;
	}
}

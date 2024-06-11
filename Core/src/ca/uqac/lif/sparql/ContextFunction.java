package ca.uqac.lif.sparql;

import java.util.Set;

import ca.uqac.lif.cep.Context;
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
	protected static Object getFromContext(Context c, Object key, Object default_value)
	{
		if (c == null)
		{
			return default_value;
		}
		return c.getOrDefault(key, default_value);
	}
}

package ca.uqac.lif.sparql;

import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.EventTracker;
import ca.uqac.lif.cep.functions.UnaryFunction;

public class Dot extends UnaryFunction<KnowledgeGraph,Object>
{
	protected final String m_name;
	
	public Dot(String name)
	{
		super(KnowledgeGraph.class, Object.class);
		m_name = name;
	}
	
	@Override
	public void evaluate(Object[] inputs, Object[] outputs, Context c, EventTracker t)
	{
		KnowledgeGraph g = (KnowledgeGraph) inputs[0];
		Object o = c.get(m_name);
		if (!(o instanceof GraphNode))
		{
			outputs[0] = null;
			return;
		}
		GraphNode node = (GraphNode) o;
		outputs[0] = g.getNode(node.getId());
	}

	@Override
	public String toString()
	{
		return "." + m_name;
	}

	@Override
	public Object getValue(KnowledgeGraph x)
	{
		return null;
	}
}

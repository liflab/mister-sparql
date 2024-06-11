package ca.uqac.lif.sparql;

import java.util.Collection;

import ca.uqac.lif.cep.functions.UnaryFunction;

@SuppressWarnings("rawtypes")
public class GetEdges extends UnaryFunction<KnowledgeGraph,Collection>
{
	public GetEdges()
	{
		super(KnowledgeGraph.class, Collection.class);
	}

	@Override
	public Collection getValue(KnowledgeGraph g)
	{
		return g.getEdges();
	}
	
	@Override
	public GetEdges duplicate(boolean with_state)
	{
		return new GetEdges();
	}
}

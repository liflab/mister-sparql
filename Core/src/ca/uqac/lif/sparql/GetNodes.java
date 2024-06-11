package ca.uqac.lif.sparql;

import java.util.Collection;

import ca.uqac.lif.cep.functions.UnaryFunction;

@SuppressWarnings("rawtypes")
public class GetNodes extends UnaryFunction<KnowledgeGraph,Collection>
{
	public GetNodes()
	{
		super(KnowledgeGraph.class, Collection.class);
	}

	@Override
	public Collection getValue(KnowledgeGraph g)
	{
		return g.getNodes();
	}
	
	@Override
	public GetNodes duplicate(boolean with_state)
	{
		return new GetNodes();
	}
}

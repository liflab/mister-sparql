/*
    Basic processing of SPARQL queries in Java
    Copyright (C) 2024 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cep.sparql;

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.sparql.KnowledgeGraph;

/**
 * A BeepBeep {@link Function} that applies an update operation on a knowledge
 * graph, producing another knowledge graph in return.
 */
public abstract class GraphUpdateFunction extends UnaryFunction<KnowledgeGraph,KnowledgeGraph>
{
	/**
	 * Creates a new instance of the function.
	 */
	public GraphUpdateFunction()
	{
		super(KnowledgeGraph.class, KnowledgeGraph.class);
	}
	
	@Override
	public final KnowledgeGraph getValue(KnowledgeGraph g)
	{
		return update(g.duplicate());
	}
	
	/**
	 * Updates a graph by applying a transformation to it.
	 * @param g The graph
	 * @return The <em>same</em> graph, to which a transformation has been
	 * applied
	 */
	protected abstract KnowledgeGraph update(KnowledgeGraph g);
}

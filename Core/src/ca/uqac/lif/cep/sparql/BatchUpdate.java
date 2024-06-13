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

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.sparql.KnowledgeGraph;

/**
 * Applies a list of updates to a graph in batch. The function takes care of
 * not creating useless copies of intermediate graphs since only the final
 * result matters.
 */
public class BatchUpdate extends GraphUpdateFunction
{
	/**
	 * The list of updates to apply in batch to a given graph.
	 */
	protected final List<GraphUpdateFunction> m_functions;
	
	/**
	 * Creates a new instance of the function.
	 * @param functions The list of updates to apply in batch to a given graph
	 */
	public BatchUpdate(List<GraphUpdateFunction> functions)
	{
		super();
		m_functions = new ArrayList<>();
		for (GraphUpdateFunction f : functions)
		{
			add(m_functions, f);
		}
	}
	
	@Override
	protected KnowledgeGraph update(KnowledgeGraph g)
	{
		for (GraphUpdateFunction f : m_functions)
		{
			g = f.update(g);
		}
		return g;
	}
	
	/**
	 * Adds a graph update function to a list of such functions. The method
	 * "flattens" the list in case the function to add is itself a
	 * {@link BatchUpdate}.
	 * @param list The list of functions to which the function is to be added
	 * @param f The function to add
	 */
	protected static void add(List<GraphUpdateFunction> list, GraphUpdateFunction f)
	{
		if (f instanceof BatchUpdate)
		{
			for (GraphUpdateFunction in_f : ((BatchUpdate) f).m_functions)
			{
				add(list, in_f);
			}
		}
	}
}

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

import ca.uqac.lif.sparql.KnowledgeGraph;

/**
 * Changes the data of a labeled node in a knowledge graph.
 */
public class SetNodeData extends GraphUpdateFunction
{
	/**
	 * The ID of the node to modify.
	 */
	protected final long m_id;

	/**
	 * The data to set to the node.
	 */
	protected final Object m_data;

	/**
	 * Creates a new instance of the function.
	 * @param id The ID of the node to modify
	 * @param data The data to set to the node
	 * @param duplicate Set to {@code true} to duplicate the graph first,
	 * {@code false} to apply the function on the graph itself
	 */
	public SetNodeData(long id, Object data)
	{
		super();
		m_id = id;
		m_data = data;
	}

	@Override
	protected KnowledgeGraph update(KnowledgeGraph g)
	{
		return g.setNodeData(m_id, m_data);
	}
}

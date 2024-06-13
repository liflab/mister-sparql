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
 * Deletes a node from a knowledge graph.
 */
public class DeleteNode extends GraphUpdateFunction
{
	/**
	 * The ID of the node to delete.
	 */
	protected final long m_id;

	/**
	 * Creates a new instance of the function.
	 * @param id The ID of the node to delete
	 * @param duplicate Set to {@code true} to duplicate the graph first,
	 * {@code false} to apply the function on the graph itself
	 */
	public DeleteNode(long id)
	{
		super();
		m_id = id;
	}

	@Override
	protected KnowledgeGraph update(KnowledgeGraph g)
	{
		return g.deleteNode(m_id);
	}
}

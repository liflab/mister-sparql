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
package ca.uqac.lif.sparql;

/**
 * Asserts that two nodes in a knowledge graph are connected by an edge
 * with a given label. There are two variants of this assertion, depending
 * on whether the edge is directed or not.
 * @author Sylvain Hallé
 */
public abstract class ConnectedBy implements GraphAssertion
{
	/**
	 * Creates a new assertion that two nodes are connected by an edge with a given
	 * label.
	 * 
	 * @param from
	 *          The variable standing for the source node of the connection
	 * @param label
	 *          The label of the edge connecting the two nodes
	 * @param to
	 *          The variable standing for the destination node of the connection
	 * @return The assertion
	 */
	public static GraphAssertion connected(String from, String label, String to)
	{
		return new DirectedConnectedBy(from, label, to);
	}
	
	/**
	 * Creates a new assertion that two nodes are connected by an edge with a given
	 * label, regardless of the direction of the edge.
	 * 
	 * @param from
	 *          The variable standing for the source node of the connection
	 * @param label
	 *          The label of the edge connecting the two nodes
	 * @param to
	 *          The variable standing for the destination node of the connection
	 * @return The assertion
	 */
	public static GraphAssertion connectedUndir(String from, String label, String to)
	{
		return new UndirectedConnectedBy(from, label, to);
	}

	/**
	 * The variable standing for the source node of the connection.
	 */
	protected final String m_from;

	/**
	 * The label of the edge connecting the two nodes.
	 */
	protected final String m_label;

	/**
	 * The variable standing for the destination node of the connection.
	 */
	protected final String m_to;

	/**
	 * Creates a new assertion that two nodes are connected by an edge with a given
	 * label.
	 * 
	 * @param from
	 *          The variable standing for the source node of the connection
	 * @param label
	 *          The label of the edge connecting the two nodes
	 * @param to
	 *          The variable standing for the destination node of the connection
	 * @return The assertion
	 */
	public ConnectedBy(String from, String label, String to)
	{
		super();
		m_from = from;
		m_label = label;
		m_to = to;
	}
	
	public static class DirectedConnectedBy extends ConnectedBy
	{
		public DirectedConnectedBy(String from, String label, String to)
		{
			super(from, label, to);
		}

		@Override
		public Boolean evaluate(KnowledgeGraph graph, Valuation nu)
		{
			Object from = nu.getOrDefault(m_from, m_from);
			Object edge = nu.getOrDefault(m_label, m_label);
			Object to = nu.getOrDefault(m_to, m_to);
			return graph.matches(from, edge, to);
		}
	}
	
	public static class UndirectedConnectedBy extends ConnectedBy
	{
		public UndirectedConnectedBy(String from, String label, String to)
		{
			super(from, label, to);
		}

		@Override
		public Boolean evaluate(KnowledgeGraph graph, Valuation nu)
		{
			Object from = nu.getOrDefault(m_from, m_from);
			Object edge = nu.getOrDefault(m_label, m_label);
			Object to = nu.getOrDefault(m_to, m_to);
			return graph.matches(from, edge, to) || graph.matches(to, edge, from);
		}
	}
}

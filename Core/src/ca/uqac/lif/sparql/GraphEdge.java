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
 * Represents an edge in a knowledge graph.
 * @author Sylvain Hallé
 */
public abstract class GraphEdge
{
	/**
	 * The identifier of the source node of this edge.
	 */
	private final long m_from;
	
	/**
	 * The identifier of the destination node of this edge.
	 */
	private final long m_to;
	
	/**
	 * The label of this edge.
	 */
	private final String m_label;
	
	/**
	 * Creates a new edge.
	 * 
	 * @param from
	 *          The source node
	 * @param label
	 *          The label of this edge
	 * @param to
	 *          The destination node
	 */
	protected GraphEdge(long from, String label, long to)
	{
		super();
		m_from = from;
		m_to = to;
		m_label = label;
	}
	
	/**
	 * Gets the source node of this edge.
	 * 
	 * @return The source node
	 */
	public long getFrom()
	{
		return m_from;
	}
	
	/**
	 * Gets the destination node of this edge.
	 * 
	 * @return The destination node
	 */
	public long getTo()
	{
		return m_to;
	}
	
	/**
	 * Gets the label of this edge.
	 * 
	 * @return The label
	 */
	public String getLabel()
	{
		return m_label;
	}
	
	@Override
	public int hashCode()
	{
		return (int) (m_from + m_to + m_label.hashCode());
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof GraphEdge))
		{
			return false;
		}
		GraphEdge e = (GraphEdge) o;
		return m_from == e.m_from && m_to == e.m_to && m_label.compareTo(e.m_label) == 0;
	}
	
	@Override
	public String toString()
	{
		return m_from + "-[" + m_label + "]-> " + m_to;
	}
}

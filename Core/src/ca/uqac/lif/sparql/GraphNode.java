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
 * Represents a node in a knowledge graph.
 * @author Sylvain Hallé
 */
public class GraphNode 
{
	/**
	 * The unique identifier of this node.
	 */
	protected final long m_id;
	
	/**
	 * The data associated with this node.
	 */
	protected Object m_data;
	
	/**
	 * Creates a new node.
	 * @param id The unique identifier of this node
	 * @param data The data associated with this node
	 */
	public GraphNode(long id, Object data)
	{
		super();
		m_id = id;
		m_data = data;
	}
	
	/**
	 * Sets the data associated with this node.
	 * 
	 * @param o
	 *          The data
	 */
	public void setData(Object o)
	{
		m_data = o;
	}
	
	/**
	 * Gets the data associated with this node.
	 * 
	 * @return The data
	 */
	public Object getData()
	{
		return m_data;
	}
	
	/**
	 * Gets the unique identifier of this node.
	 * 
	 * @return The identifier
	 */
	public long getId()
	{
		return m_id;
	}
	
	@Override
	public String toString()
	{
		return m_id + ":" + m_data.toString();
	}
}

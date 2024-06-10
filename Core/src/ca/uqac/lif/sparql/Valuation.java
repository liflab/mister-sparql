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

import java.util.HashMap;
import java.util.Map;

/**
 * Associates variables to either nodes or edges in a knowledge graph.
 * @author Sylvain Hallé
 */
public class Valuation
{
	/**
	 * The mapping of variables to node IDs.
	 */
	protected final Map<String,Object> m_map;
	
	/**
	 * Creates a new empty valuation.
	 */
	public Valuation()
	{
		super();
		m_map = new HashMap<String,Object>();
	}
	
	/**
	 * Creates a new valuation by copying an existing one.
	 * @param v The existing valuation
	 */
	public Valuation(Valuation nu)
	{
		this();
		m_map.putAll(nu.m_map);
	}
	
	/**
	 * Adds a variable to this valuation.
	 * 
	 * @param variable
	 *          The variable
	 * @param value
	 *          The value associated with the variable
	 * @return This valuation
	 */
	public Valuation add(String variable, Object value)
	{
		m_map.put(variable, value);
		return this;
	}
	
	/**
	 * Gets the object associated with a variable.
	 * 
	 * @param variable The variable
	 * @return The object, or {@code null} if the variable is not in the
	 * valuation
	 */
	public Object get(String variable)
	{
		return m_map.getOrDefault(variable, null);
	}
	
	/**
	 * Gets the object associated with a variable, or a default value if the
	 * variable is not in the valuation.
	 * 
	 * @param variable
	 *          The variable
	 * @param default_value
	 *          The default value
	 * @return The object, or the default value if the variable is not in the
	 *         valuation
	 */
	public Object getOrDefault(String variable, Object default_value)
	{
		return m_map.getOrDefault(variable, default_value);
	}
	
	@Override
	public String toString()
	{
		return m_map.toString();
	}
}

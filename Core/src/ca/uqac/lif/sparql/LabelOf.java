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
 * A function extracting the label of a node designated by a variable.
 * @author Sylvain Hallé
 */
public class LabelOf implements GraphFunction<Object>
{
	/**
	 * Creates a new instance of this function.
	 * 
	 * @param variable
	 *          The variable
	 * @return The instance 
	 * */
	public static LabelOf l(String variable)
	{
		return new LabelOf(variable);
	}
	
	/** 
	 * The value of the constant.
	 */
	protected final String m_variable;

	/**
	 * Creates a new constant.
	 * 
	 * @param value
	 *          The value of the constant
	 */
	public LabelOf(String variable)
	{
		super();
		m_variable = variable;
	}

	@Override
	public Object evaluate(KnowledgeGraph g, Valuation nu)
	{
		Object o = nu.get(m_variable);
		if (o == null || !(o instanceof GraphNode))
		{
			return null;
		}
		GraphNode node = (GraphNode) o;
		return node.getData();
	}
}

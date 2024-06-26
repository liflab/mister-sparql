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

import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.EventTracker;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.ContextVariable;
import ca.uqac.lif.cep.functions.Function;

/**
 * A function extracting the label of a node designated by a variable.
 * @author Sylvain Hallé
 */
public class LabelOf extends ContextFunction implements GraphFunction<Object>
{
	/**
	 * Creates a new instance of this function.
	 * 
	 * @param variable
	 *          The variable
	 * @return The instance 
	 * */
	public static LabelOf l(String s)
	{
		Function p_variable = s.startsWith("$") ? new ContextVariable(s) : new Constant(s); 
		return new LabelOf(p_variable);
	}
	
	/** 
	 * The value of the variable.
	 */
	protected final Function m_variable;

	/**
	 * Creates a new constant.
	 * 
	 * @param value
	 *          The value of the constant
	 */
	public LabelOf(Function variable)
	{
		super();
		m_variable = variable;
	}

	@Override
	public Object evaluate(KnowledgeGraph g, Valuation nu)
	{
		Object o = evaluateFromValuation(m_variable, g, nu);
		if (o == null || !(o instanceof GraphNode))
		{
			return null;
		}
		GraphNode node = (GraphNode) o;
		return node.getData();
	}
	
	@Override
	public Class<?> getOutputTypeFor(int index)
	{
		if (index == 0)
		{
			return Object.class;
		}
		return null;
	}

	@Override
	public Function duplicate(boolean with_state)
	{
		return new LabelOf(m_variable);
	}

	@Override
	public void evaluate(Object[] inputs, Object[] outputs, Context c, EventTracker t)
	{
		KnowledgeGraph g = (KnowledgeGraph) inputs[0];
		Object o = evaluateFromContext(m_variable, g, c);
		if (o == null || !(o instanceof GraphNode))
		{
			outputs[0] = null;
		}
		GraphNode node = (GraphNode) o;
		outputs[0] = node.getData();
	}
}

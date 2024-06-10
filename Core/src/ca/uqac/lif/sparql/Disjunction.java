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

import java.util.ArrayList;
import java.util.List;

/**
 * A disjunction of graph assertions.
 * @author Sylvain Hallé
 */
public class Disjunction extends NaryConnective
{
	/**
	 * Creates a new disjunction.
	 * 
	 * @param operands
	 *          The operands of the disjunction
	 */
	public static GraphAssertion or(Object... operands)
	{
		if (operands.length == 1)
		{
			return (GraphAssertion) operands[0];
		}
		List<GraphAssertion> ops = new ArrayList<GraphAssertion>();
		for (Object op : operands)
		{
			if (op instanceof Disjunction)
			{
				for (GraphAssertion child : ((Disjunction) op).m_operands)
				{
					ops.add(child);
				}
			}
			else
			{
				ops.add((GraphAssertion) op);
			}
		}
		return new Disjunction(ops);
	}
	
	/**
	 * Creates a new implication.
	 * 
	 * @param left
	 *          The left operand
	 * @param right
	 *          The right operand
	 */
	public static GraphAssertion implies(GraphAssertion left, GraphAssertion right)
	{
		return or(Negation.not(left), right);
	}

	/**
	 * Creates a new disjunction.
	 * @param operands The operands of the disjunction
	 */
	public Disjunction(GraphAssertion ... operands)
	{
		super(operands);
	}

	/**
	 * Creates a new disjunction.
	 * 
	 * @param operands
	 *          The operands of the disjunction
	 */
	public Disjunction(List<GraphAssertion> operands)
	{
		super(operands);
	}

	@Override
	public Boolean evaluate(KnowledgeGraph g, Valuation nu)
	{
		for (GraphAssertion child : m_operands)
		{
			if (child.evaluate(g, nu))
			{
				return true;
			}
		}
		return false;
	}
}

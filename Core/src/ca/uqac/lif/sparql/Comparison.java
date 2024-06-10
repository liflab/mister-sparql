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
 * An abstract assertion applying a comparison on the return value of two
 * graph functions.
 * @author Sylvain Hallé
 */
public abstract class Comparison implements GraphAssertion
{
	/**
	 * The left operand of the comparison.
	 */
	/*@ non_null @*/ protected final GraphFunction<?> m_left;

	/**
	 * The right operand of the comparison.
	 */
	/*@ non_null @*/ protected final GraphFunction<?> m_right;

	/**
	 * Creates a new comparison between two graph functions.
	 * 
	 * @param left
	 *          The left operand
	 * @param right
	 *          The right operand
	 */
	public Comparison(/*@ non_null @*/ GraphFunction<?> left, /*@ non_null @*/ GraphFunction<?> right)
	{
		super();
		m_left = left;
		m_right = right;
	}

	@Override
	public Boolean evaluate(KnowledgeGraph g, Valuation nu)
	{
		Object left_val = m_left.evaluate(g, nu);
		Object right_val = m_right.evaluate(g, nu);
		return compare(left_val, right_val);
	}

	/**
	 * Compares two objects.
	 * 
	 * @param left
	 *          The left operand
	 * @param right
	 *          The right operand
	 * @return {@code true} if the comparison is satisfied, {@code false}
	 * otherwise
	 */
	protected abstract boolean compare(Object left, Object right);
}

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
 * Represents a numerical comparison between two graph functions.
 * @author Sylvain Hallé
 */
public abstract class NumberComparison extends Comparison
{
	public static IsGreaterThan gt(Object left, Object right)
	{
		GraphFunction<?> left_function, right_function;
		if (left instanceof GraphFunction)
		{
			left_function = (GraphFunction<?>) left;
		}
		else
		{
			left_function = new Constant<Object>(left);
		}
		if (right instanceof GraphFunction)
		{
			right_function = (GraphFunction<?>) right;
		}
		else
		{
			right_function = new Constant<Object>(right);
		}
		return new IsGreaterThan(left_function, right_function);
	}

	/**
	 * Creates a new comparison between two graph functions.
	 * @param left The left operand
	 * @param right The right operand
	 */
	public NumberComparison(GraphFunction<?> left, GraphFunction<?> right)
	{
		super(left, right);
	}

	@Override
	protected boolean compare(Object left, Object right)
	{
		if (left instanceof Number && right instanceof Number)
		{
			Number l = (Number) left;
			Number r = (Number) right;
			return compareNumbers(l, r);
		}
		return false;
	}

	/**
	 * Compares two numbers.
	 * @param left The first number
	 * @param right The second number
	 * @return {@code true} if the numbers satisfy the comparison, {@code false}
	 * otherwise
	 */
	protected abstract boolean compareNumbers(Number left, Number right);

	/**
	 * A function that checks if the result of two graph functions is greater than
	 * the result of another.
	 */
	public static class IsGreaterThan extends NumberComparison
	{
		/**
		 * Creates a new comparison between two graph functions.
		 * @param left The left operand
		 * @param right The right operand
		 */
		public IsGreaterThan(GraphFunction<?> left, GraphFunction<?> right)
		{
			super(left, right);
		}

		@Override
		protected boolean compareNumbers(Number left, Number right)
		{
			return left.doubleValue() > right.doubleValue();
		}
	}
}

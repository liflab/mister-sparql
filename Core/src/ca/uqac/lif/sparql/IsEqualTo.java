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
 * A function that checks if the result of two graph functions is equal.
 * @author Sylvain Hallé
 */
public class IsEqualTo implements GraphAssertion
{
	public static IsEqualTo eq(Object ... arguments)
	{
		GraphFunction<?>[] functions = new GraphFunction<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++)
		{
			if (arguments[i] instanceof GraphFunction)
			{
				functions[i] = (GraphFunction<?>) arguments[i];
			}
			else
			{
				functions[i] = new Constant<Object>(arguments[i]);
			}
		}
		return new IsEqualTo(functions);
	}
	
	/**
	 * The operands to compare for equality.
	 */
	protected final GraphFunction<?>[] m_arguments;

	/**
	 * Creates a new assertion that checks if the result of multiple graph
	 * functions is equal.
	 * 
	 * @param arguments The operands to compare for equality
	 */
	@SuppressWarnings("rawtypes")
	public IsEqualTo(GraphFunction ... arguments)
	{
		super();
		m_arguments = arguments;
	}

	@Override
	public Boolean evaluate(KnowledgeGraph g, Valuation nu)
	{
		Object last_val = m_arguments[0].evaluate(g, nu);
		for (int i = 1; i < m_arguments.length; i++)
		{
			Object val = m_arguments[i].evaluate(g, nu);
			if (!equalObjects(last_val, val))
			{
				return false;
			}
			last_val = val;
		}
		return true;
	}

	/**
	 * Checks if two objects are equal. This method is necessary because
	 * the equals() method in Java does not handle cases where one object
	 * is null and the other is not.
	 * @param o1 The first object
	 * @param o2 The second object
	 * @return {@code true} if the objects are equal, {@code false} otherwise
	 */
	public static boolean equalObjects(Object o1, Object o2)
	{
		if ((o1 == null) != (o2 == null))
		{
			return false;
		}
		if (o1 == null)
		{
			return true;
		}
		if (o1 instanceof Number && o2 instanceof Number)
		{
			return ((Number) o1).doubleValue() == ((Number) o2).doubleValue();
		}
		if (o1 instanceof String && o2 instanceof String)
		{
			return ((String) o1).compareTo(((String) o2)) == 0;
		}
		return o1.equals(o2);
	}
}

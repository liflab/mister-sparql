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
 * A negation of a graph assertion.
 * @author Sylvain Hallé
 */
public class Negation implements GraphAssertion
{
	/**
	 * Creates a new negation.
	 * 
	 * @param operand
	 *          The operand of the negation
	 */
	public static GraphAssertion not(GraphAssertion operand)
	{
		return new Negation(operand);
	}
	
	/**
	 * The operand of the negation.
	 */
	protected final GraphAssertion m_operand;
	
	/**
	 * Creates a new negation.
	 * @param operand The operand of the negation
	 */
	public Negation(GraphAssertion operand)
	{
		super();
		m_operand = operand;
	}
	
	@Override
	public Boolean evaluate(KnowledgeGraph g, Valuation nu)
	{
		return !m_operand.evaluate(g, nu);
	}
}

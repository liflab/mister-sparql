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

import java.util.Collection;

/**
 * Represents a quantifier. It is possible to quantify over either edges or
 * nodes in a graph.
 * 
 * @author Sylvain Hallé
 */
public abstract class Quantifier<T> implements GraphAssertion
{
	/**
	 * The variable over which to quantify
	 */
	protected final String m_variable;
	
	/**
	 * The assertion to evaluate.
	 */
	protected final GraphAssertion m_phi;
	
	/**
	 * Creates a new node quantifier
	 * 
	 * @param variable
	 *          The variable over which to quantify
	 * @param phi
	 *          The assertion to evaluate
	 */
	public Quantifier(String variable, GraphAssertion phi)
	{
		super();
		m_variable = variable;
		m_phi = phi;
	}
	
	/**
	 * Gets the values over which the quantifier should range.
	 * @return A collection of values
	 */
	protected abstract Collection<T> getDomain(KnowledgeGraph g);
}

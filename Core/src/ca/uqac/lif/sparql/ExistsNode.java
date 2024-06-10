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
 * Represents a node quantifier that asserts a property for at least one nodes
 * in a graph.
 * @author Sylvain Hallé
 */
public class ExistsNode extends NodeQuantifier
{
	/**
	 * Creates an assertion for some node in a graph.
	 * 
	 * @param variable
	 *          The variable over which to quantify
	 * @param phi
	 *          The assertion to evaluate
	 */
	public static ExistsNode existsNode(String variable, GraphAssertion phi)
	{
		return new ExistsNode(variable, phi);
	}

	/**
	 * Creates an assertion for some node in a graph.
	 * 
	 * @param variable
	 *          The variable over which to quantify
	 * @param phi
	 *          The assertion to evaluate
	 */
	public ExistsNode(String variable, GraphAssertion phi)
	{
		super(variable, phi);
	}

	@Override
	public Boolean evaluate(KnowledgeGraph graph, Valuation nu)
	{
		for (GraphNode n : graph.getNodes())
		{
			Valuation nu_prime = new Valuation(nu);
			nu_prime.add(m_variable, n);
			if (m_phi.evaluate(graph, nu_prime))
			{
				return true;
			}
		}
		return false;
	}
}

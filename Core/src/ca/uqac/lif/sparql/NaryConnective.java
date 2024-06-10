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

import java.util.List;

/**
 * A Boolean connective that takes an arbitrary number of operands.
 * @author Sylvain Hallé
 */
public abstract class NaryConnective implements GraphAssertion
{
	/**
	 * The operands of this connective.
	 */
	/*@ non_null @*/ protected final GraphAssertion[] m_operands;

	public NaryConnective(GraphAssertion ... children)
	{
		super();
		m_operands = children;
	}

	public NaryConnective(List<GraphAssertion> children)
	{
		super();
		m_operands = children.toArray(new GraphAssertion[children.size()]);
	}
}
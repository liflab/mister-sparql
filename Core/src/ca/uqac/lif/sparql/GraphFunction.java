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

public interface GraphFunction<T>
{
	/**
	 * Evaluates the function on a given knowledge graph, starting from an
	 * empty valuation.
	 * @param graph The knowledge graph
	 * @return The result of the evaluation
	 */
	public default T evaluate(KnowledgeGraph graph)
	{
		return evaluate(graph, new Valuation());
	}

	/**
	 * Evaluates the function on a given knowledge graph, starting from a
	 * given valuation.
	 * @param graph The knowledge graph
	 * @param nu The valuation
	 * @return The result of the evaluation
	 */
	public T evaluate(KnowledgeGraph graph, Valuation nu);
}

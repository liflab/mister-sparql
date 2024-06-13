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
import ca.uqac.lif.cep.ltl.Troolean;

/**
 * Asserts that two nodes in a knowledge graph are connected by an edge
 * with a given label. There are two variants of this assertion, depending
 * on whether the edge is directed or not.
 * <p>
 * The function can be used directly to form assertions, or as a BeepBeep
 * {@link Function} object that can be given as a parameter to processors.
 * @author Sylvain Hallé
 */
public abstract class ConnectedBy extends ContextFunction implements GraphAssertion 
{
	/**
	 * Creates a new assertion that two nodes are connected by an edge with a given
	 * label.
	 * 
	 * @param from
	 *          The variable standing for the source node of the connection
	 * @param label
	 *          The label of the edge connecting the two nodes
	 * @param to
	 *          The variable standing for the destination node of the connection
	 * @return The assertion
	 */
	public static DirectedConnectedBy connected(String from, String label, String to)
	{
		Function p_from = from.startsWith("$") ? new ContextVariable(from) : new Constant(from);
		Function p_label = from.startsWith("$") ? new ContextVariable(label) : new Constant(label);
		Function p_to = from.startsWith("$") ? new ContextVariable(to) : new Constant(to);
		return new DirectedConnectedBy(p_from, p_label, p_to);
	}

	/**
	 * Creates a new assertion that two nodes are connected by an edge with a given
	 * label, regardless of the direction of the edge.
	 * 
	 * @param from
	 *          The variable standing for the source node of the connection
	 * @param label
	 *          The label of the edge connecting the two nodes
	 * @param to
	 *          The variable standing for the destination node of the connection
	 * @return The assertion
	 */
	public static UndirectedConnectedBy connectedUndir(String from, String label, String to)
	{
		Function p_from = from.startsWith("$") ? new ContextVariable(from) : new Constant(from);
		Function p_label = from.startsWith("$") ? new ContextVariable(label) : new Constant(label);
		Function p_to = from.startsWith("$") ? new ContextVariable(to) : new Constant(to);
		return new UndirectedConnectedBy(p_from, p_label, p_to);
	}

	/**
	 * The variable standing for the source node of the connection.
	 */
	protected final Function m_from;

	/**
	 * The label of the edge connecting the two nodes.
	 */
	protected final Function m_label;

	/**
	 * The variable standing for the destination node of the connection.
	 */
	protected final Function m_to;

	/**
	 * Creates a new assertion that two nodes are connected by an edge with a given
	 * label.
	 * 
	 * @param from
	 *          The variable standing for the source node of the connection
	 * @param label
	 *          The label of the edge connecting the two nodes
	 * @param to
	 *          The variable standing for the destination node of the connection
	 * @return The assertion
	 */
	public ConnectedBy(Function from, Function label, Function to)
	{
		super();
		m_from = from;
		m_label = label;
		m_to = to;
	}

	@Override
	public Class<?> getOutputTypeFor(int index)
	{
		if (index == 0)
		{
			return Boolean.class;
		}
		return null;
	}

	public static class DirectedConnectedBy extends ConnectedBy
	{
		public DirectedConnectedBy(Function from, Function label, Function to)
		{
			super(from, label, to);
		}

		@Override
		public Boolean evaluate(KnowledgeGraph graph, Valuation nu)
		{
			Object from = evaluateFromValuation(m_from, graph, nu);
			Object edge = evaluateFromValuation(m_label, graph, nu);
			Object to = evaluateFromValuation(m_to, graph, nu);
			return graph.matches(from, edge, to);
		}

		@Override
		public DirectedConnectedBy duplicate(boolean with_state)
		{
			return new DirectedConnectedBy(m_from, m_label, m_to);
		}

		@Override
		public void evaluate(Object[] inputs, Object[] outputs, Context c, EventTracker t)
		{
			KnowledgeGraph g = (KnowledgeGraph) inputs[0];
			Object from = evaluateFromContext(m_from, inputs[0], c);
			Object edge = evaluateFromContext(m_label, inputs[0], c);
			Object to = evaluateFromContext(m_to, inputs[0], c);
			outputs[0] = g.matches(from, edge, to) ? Troolean.Value.TRUE : Troolean.Value.FALSE;
		}
	}

	public static class UndirectedConnectedBy extends ConnectedBy
	{
		public UndirectedConnectedBy(Function from, Function label, Function to)
		{
			super(from, label, to);
		}

		@Override
		public Boolean evaluate(KnowledgeGraph graph, Valuation nu)
		{
			Object from = evaluateFromValuation(m_from, graph, nu);
			Object edge = evaluateFromValuation(m_label, graph, nu);
			Object to = evaluateFromValuation(m_to, graph, nu);
			return graph.matches(from, edge, to) || graph.matches(to, edge, from);
		}

		@Override
		public UndirectedConnectedBy duplicate(boolean with_state)
		{
			return new UndirectedConnectedBy(m_from, m_label, m_to);
		}

		@Override
		public void evaluate(Object[] inputs, Object[] outputs, Context c, EventTracker t)
		{
			KnowledgeGraph g = (KnowledgeGraph) inputs[0];
			Object from = evaluateFromContext(m_from, inputs[0], c);
			Object edge = evaluateFromContext(m_label, inputs[0], c);
			Object to = evaluateFromContext(m_to, inputs[0], c);
			outputs[0] = (g.matches(from, edge, to) || g.matches(to, edge, from)) ? Troolean.Value.TRUE : Troolean.Value.FALSE;
		}
	}
}

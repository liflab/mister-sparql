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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KnowledgeGraph
{
	/**
	 * Counter for the unique identifiers of graphs.
	 */
	protected static long s_idCounter = 0;
	
	/**
	 * A unique numerical identifier given to the graph.
	 */
	protected final long m_id;
	
	/**
	 * The nodes of the graph.
	 */
	/*@ non_null @*/ protected final Map<Long,GraphNode> m_nodes;

	/**
	 * The edges of the graph. The key in this map is the identifier of the
	 * source node of the edge.
	 */
	/*@ non_null @*/ protected final Map<Long,Set<GraphEdge>> m_edges;
	
	/**
	 * Creates a new empty knowledge graph and assigns it a unique identifier.
	 */
	public KnowledgeGraph()
	{
		this(s_idCounter++);
	}

	/**
	 * Creates a new empty knowledge graph.
	 * @param id The unique identifier of the graph
	 */
	public KnowledgeGraph(long id)
	{
		this(id, new HashMap<Long,GraphNode>(), new HashMap<Long,Set<GraphEdge>>());
	}

	public KnowledgeGraph(long id, /*@ non_null @*/ Map<Long,GraphNode> nodes, /*@ non_null @*/ Map<Long,Set<GraphEdge>> edges)
	{
		super();
		m_id = id;
		m_nodes = nodes;
		m_edges = edges;
	}
	
	/**
	 * Gets the unique identifier of this graph.
	 * 
	 * @return The identifier
	 */
	public long getId()
	{
		return m_id;
	}

	/**
	 * Adds a node to the graph.
	 * 
	 * @param node
	 *          The node to add
	 * @return This graph
	 */
	/*@ non_null @*/ public KnowledgeGraph add(/*@ non_null @*/ GraphNode node)
	{
		m_nodes.put(node.getId(), node);
		return this;
	}

	/**
	 * Adds an edge to the graph.
	 * 
	 * @param edge
	 *          The edge to add
	 * @return This graph
	 */
	/*@ non_null @*/ public KnowledgeGraph add(/*@ non_null @*/ GraphEdge edge)
	{
		if (!m_edges.containsKey(edge.getFrom()))
		{
			m_edges.put(edge.getFrom(), new HashSet<GraphEdge>());
		}
		m_edges.get(edge.getFrom()).add(edge);
		return this;
	}

	/**
	 * Connects two nodes with an edge.
	 * 
	 * @param from
	 *          The source node
	 * @param label
	 *          The label of the edge
	 * @param to
	 *          The destination node
	 * @return This graph
	 */
	public KnowledgeGraph connect(long from, String label, long to)
	{
		add(new GraphEdge(from, label, to));
		return this;
	}

	/**
	 * Gets the nodes of the graph.
	 * 
	 * @return The nodes
	 */
	/*@ non_null @*/ public Collection<GraphNode> getNodes()
	{
		return m_nodes.values();
	}

	/**
	 * Gets the edges of the graph.
	 * 
	 * @return The edges
	 */
	/*@ non_null @*/ public Collection<GraphEdge> getEdges()
	{
		Set<GraphEdge> edges = new HashSet<GraphEdge>();
		for (Set<GraphEdge> edge_set : m_edges.values())
		{
			edges.addAll(edge_set);
		}
		return edges;
	}

	/**
	 * Gets the edges of the graph that are incident on a given node.
	 * 
	 * @param id
	 *          The identifier of the node
	 * @return The edges
	 */
	public Collection<GraphEdge> getEdges(long id)
	{
		return m_edges.getOrDefault(id, new HashSet<GraphEdge>());
	}

	/**
	 * Gets the node with a given identifier.
	 * 
	 * @param id
	 *          The identifier
	 * @return The node, or null if it does not exist
	 */
	public GraphNode getNode(long id)
	{
		return m_nodes.get(id);
	}

	/**
	 * Gets the number of nodes in the graph.
	 * 
	 * @return The number of nodes
	 */
	public int size()
	{
		return m_nodes.size();
	}

	public boolean matches(Object from, Object label, Object to)
	{
		if (to == null || label == null || from == null)
		{
			return false;
		}
		Set<GraphNode> set_to = new HashSet<GraphNode>();
		Set<GraphEdge> set_edge = new HashSet<GraphEdge>();
		Set<GraphNode> set_from = new HashSet<GraphNode>();
		if (from instanceof GraphNode)
		{
			set_from.add((GraphNode) from);
		}
		else
		{
			for (GraphNode node : m_nodes.values())
			{
				if (IsEqualTo.equalObjects(node.getData(), from))
				{
					set_from.add(node);
				}
			}
		}
		if (label instanceof GraphEdge)
		{
			set_edge.add((GraphEdge) label);
		}
		else
		{
			for (GraphEdge edge : getEdges())
			{
				if (IsEqualTo.equalObjects(edge.getLabel(), label))
				{
					set_edge.add(edge);
				}
			}
		}
		if (to instanceof GraphNode)
		{
			set_to.add((GraphNode) to);
		}
		else
		{
			for (GraphNode node : m_nodes.values())
			{
				if (IsEqualTo.equalObjects(node.getData(), to))
				{
					set_to.add(node);
				}
			}
		}
		for (GraphNode from_node : set_from)
		{
			for (GraphEdge edge : set_edge)
			{
				for (GraphNode to_node : set_to)
				{
					if (edge.getFrom() == from_node.getId() && edge.getTo() == to_node.getId())
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}

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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class KnowledgeGraph
{
	/**
	 * Counter for the unique identifiers of graphs.
	 */
	private static long s_idCounter = 0;
	
	/**
	 * A unique numerical identifier given to the graph.
	 */
	private final long m_id;
	
	/**
	 * The nodes of the graph.
	 */
	/*@ non_null @*/ private final Map<Long,GraphNode> m_nodes;

	/**
	 * The edges of the graph. The key in this map is the identifier of the
	 * source node of the edge.
	 */
	/*@ non_null @*/ private final Map<Long,Set<GraphEdge>> m_edges;
	
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
		super();
		m_id = id;
		m_nodes = new HashMap<>();
		m_edges = new HashMap<>();
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
	/*@ non_null @*/ public KnowledgeGraph addNode(long id, Object data)
	{
		setNodeData(id, data);
		return this;
	}

	/**
	 * Adds an edge to the graph.
	 * 
	 * @param edge
	 *          The edge to add
	 * @return This graph
	 */
	/*@ non_null @*/ public KnowledgeGraph addEdge(long from, String label, long to)
	{
		WritableGraphEdge edge = new WritableGraphEdge(from, label, to);
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
		return addEdge(from, label, to);
	}
	
	/**
	 * Creates a copy of this graph.
	 * @return A copy of the graph
	 */
	public KnowledgeGraph duplicate()
	{
		KnowledgeGraph g = new KnowledgeGraph();
		g.m_nodes.putAll(m_nodes);
		for (Map.Entry<Long,Set<GraphEdge>> entry : m_edges.entrySet())
		{
			Set<GraphEdge> new_set = new HashSet<GraphEdge>();
			new_set.addAll(entry.getValue());
			g.m_edges.put(entry.getKey(), new_set);
		}
		return g;
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
	 * Changes the data associated to a node ID. If the node with given ID does
	 * not exist, it is created; otherwise, the instance of the node with that ID
	 * is erased from the graph and is replaced by the newly created node. This
	 * operation does not change any of the edges associated to the node with
	 * given ID.
	 * @param id The ID of the node
	 * @param data The data inside the node
	 * @return The new node instance
	 */
	public GraphNode setNodeData(long id, Object data)
	{
		WritableGraphNode node = new WritableGraphNode(id, data);
		m_nodes.put(id, node);
		return node;
	}
	
	/**
	 * Changes the label associated to an edge. The edge to modify is specified
	 * the triplet (ID<sub>from</sub>, label, ID<sub>to</sub>). If the edge with
	 * given triplet does not exist, it is created; otherwise, the instance of the
	 * edge with that triplet is erased from the graph and is replaced by the
	 * newly created edge.
	 * @param from The ID of the node at the source of the edge
	 * @param label The current label of the edge
	 * @param to The ID of the node at the target of the edge
	 * @param new_label The new label to give to the edge
	 * @return The new edge instance
	 */
	public GraphEdge setEdgeData(long from, String label, long to, String new_label)
	{
		if (!m_edges.containsKey(from))
		{
			m_edges.put(from, new HashSet<GraphEdge>());
		}
		Set<GraphEdge> edges = m_edges.get(from);
		Iterator<GraphEdge> it = edges.iterator();
		while (it.hasNext())
		{
			GraphEdge e = it.next();
			if (e.getFrom() == from && e.getLabel().compareTo(label) == 0 && e.getTo() == to)
			{
				it.remove();
				// We break since there is at most one edge with given triplet
				break;
			}
		}
		WritableGraphEdge edge = new WritableGraphEdge(from, new_label, to);
		edges.add(edge);
		return edge;
	}
	
	/**
	 * Deletes an edge from the graph.  The edge to modify is specified
	 * the triplet (ID<sub>from</sub>, label, ID<sub>to</sub>).
	 * @param from The ID of the node at the source of the edge
	 * @param label The current label of the edge
	 * @param to The ID of the node at the target of the edge
	 * @return {@code true} if an edge was deleted, {@code false} otherwise
	 */
	public boolean deleteEdge(long from, String label, long to)
	{
		if (!m_edges.containsKey(from))
		{
			return false;
		}
		Set<GraphEdge> edges = m_edges.get(from);
		Iterator<GraphEdge> it = edges.iterator();
		while (it.hasNext())
		{
			GraphEdge e = it.next();
			if (e.getFrom() == from && e.getLabel().compareTo(label) == 0 && e.getTo() == to)
			{
				it.remove();
				// We break since there is at most one edge with given triplet
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Deletes a node with given ID. All edges connected to that node in either
	 * direction are also deleted.
	 * @param id The ID of the node to delete
	 * @return {@code true} if a node was deleted, {@code false} otherwise
	 */
	public boolean deleteNode(long id)
	{
		if (!m_nodes.containsKey(id))
		{
			return false;
		}
		m_nodes.remove(id);
		m_edges.remove(id);
		for (Map.Entry<Long,Set<GraphEdge>> entry : m_edges.entrySet())
		{
			Iterator<GraphEdge> it = entry.getValue().iterator();
			while (it.hasNext())
			{
				GraphEdge e = it.next();
				if (e.getFrom() == id || e.getTo() == id)
				{
					it.remove();
				}
			}
		}
		return true;
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
	
	public static class WritableGraphNode extends GraphNode
	{
		protected WritableGraphNode(long id, Object data)
		{
			super(id, data);
		}
	}
	
	public static class WritableGraphEdge extends GraphEdge
	{
		protected WritableGraphEdge(long from, String label, long to)
		{
			super(from, label, to);
		}
	}
}

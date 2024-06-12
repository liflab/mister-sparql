package ca.uqac.lif.sparql;

import ca.uqac.lif.cep.Context;

public abstract class Placeholder<T>
{
	public static DottedNodePlaceholder dot(String name)
	{
		return new DottedNodePlaceholder(name);
	}
	
	public static Placeholder<?> getNodePlaceholder(Object o)
	{
		if (o instanceof NodePlaceholder || o instanceof DottedNodePlaceholder)
		{
			return (Placeholder<?>) o;
		}
		if (o instanceof String)
		{
			String s = (String) o;
			return s.startsWith("$") ? new NodePlaceholder(s) : new LabelPlaceholder(s);
		}
		return null;
	}
	
	protected final String m_name;
	
	public Placeholder(String name)
	{
		super();
		m_name = name;
	}
	
	public abstract T getValue(KnowledgeGraph g, Valuation nu);
	
	public abstract T getValue(KnowledgeGraph g, Context c);
	
	/**
	 * A placeholder standing for a label.
	 */
	public static class LabelPlaceholder extends Placeholder<String>
	{
		/**
		 * Creates a new label placeholder.
		 * @param name The name of the placeholder
		 */
		public LabelPlaceholder(String name)
		{
			super(name);
		}

		@Override
		public String getValue(KnowledgeGraph g, Valuation nu)
		{
			return m_name;
		}

		@Override
		public String getValue(KnowledgeGraph g, Context c)
		{
			return m_name;
		}
	}
	
	/**
	 * A placeholder standing for a graph node.
	 */
	public static class NodePlaceholder extends Placeholder<GraphNode>
	{
		/**
		 * Creates a new node placeholder.
		 * @param name The name of the placeholder
		 */
		public NodePlaceholder(String name)
		{
			super(name);
		}

		@Override
		public GraphNode getValue(KnowledgeGraph g, Valuation nu)
		{
			if (nu == null)
			{
				return null;
			}
			return (GraphNode) nu.getOrDefault(m_name, null);
		}

		@Override
		public GraphNode getValue(KnowledgeGraph g, Context c)
		{
			if (c == null)
			{
				return null;
			}
			return (GraphNode) c.getOrDefault(m_name, null);
		}
	}
	
	/**
	 * A placeholder standing for a dotted graph node.
	 */
	public static class DottedNodePlaceholder extends Placeholder<GraphNode>
	{
		/**
		 * Creates a new node placeholder.
		 * @param name The name of the placeholder
		 */
		public DottedNodePlaceholder(String name)
		{
			super(name);
		}

		@Override
		public GraphNode getValue(KnowledgeGraph g, Valuation nu)
		{
			if (nu == null)
			{
				return null;
			}
			GraphNode node = (GraphNode) nu.getOrDefault(m_name, null);
			if (node == null)
			{
				return null;
			}
			return g.getNode(node.getId());
		}

		@Override
		public GraphNode getValue(KnowledgeGraph g, Context c)
		{
			if (c == null)
			{
				return null;
			}
			GraphNode node = (GraphNode) c.getOrDefault(m_name, null);
			if (node == null)
			{
				return null;
			}
			return g.getNode(node.getId());
		}
	}
}

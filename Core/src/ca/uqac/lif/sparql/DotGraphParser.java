package ca.uqac.lif.sparql;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DotGraphParser implements GraphParser
{
	/**
	 * Pattern to match a node in the input.
	 */
	protected static final Pattern s_nodePattern = Pattern.compile("(\\d+) \\[label=\"([^\"]*)\"\\];*");

	/**
	 * Pattern to match an edge in the input.
	 */
	protected static final Pattern s_edgePattern = Pattern.compile("(\\d+) -> (\\d+) \\[label=\"([^\"]*)\"\\];*");

	/**
	 * Creates a new parser.
	 */
	public DotGraphParser()
	{
		super();
	}

	@Override
	public KnowledgeGraph parse(InputStream is)
	{
		KnowledgeGraph g = new KnowledgeGraph();
		Scanner scanner = new Scanner(is);
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine().trim();
			if (line.isEmpty() || line.startsWith("#"))
			{
				// Ignore blank and comment lines
				continue;
			}
			Matcher node_matcher = s_nodePattern.matcher(line);
			if (node_matcher.matches())
			{
				long id = Long.parseLong(node_matcher.group(1));
				String label = node_matcher.group(2);
				g.add(new GraphNode(id, readFromString(label)));
				continue;
			}
			Matcher edge_matcher = s_edgePattern.matcher(line);
			if (edge_matcher.matches())
			{
				long source = Long.parseLong(edge_matcher.group(1));
				long dest = Long.parseLong(edge_matcher.group(2));
				String label = edge_matcher.group(3);
				g.add(new GraphEdge(source, label, dest));
				continue;
			}
		}
		scanner.close();
		return g;
	}

	protected static Object readFromString(String s)
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			// Not an integer
		}
		try
		{
			return Double.parseDouble(s);
		}
		catch (NumberFormatException e2)
		{
			// Not a double
		}
		return s;
	}
}

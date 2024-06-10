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

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Renders a knowledge graph as a DOT graph.
 * @author Sylvain Hallé
 * @see http://www.graphviz.org/doc/info/lang.html
 */
public class DotGraphRenderer implements GraphRenderer
{
	@Override
	public void render(KnowledgeGraph g, OutputStream os)
	{
		PrintStream ps = new PrintStream(os);
		ps.println("digraph G {");
		for (GraphNode node : g.getNodes())
		{
			ps.println(node.getId() + " [label=\"" + node.getData() + "\"];");
		}
		for (GraphEdge edge : g.getEdges())
		{
			ps.println(edge.getFrom() + " -> " + edge.getTo() + " [label=\"" + edge.getLabel() + "\"];");
		}
		ps.println("}");
	}
}

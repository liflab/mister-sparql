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
package plain;

import java.util.Scanner;

import static ca.uqac.lif.sparql.Conjunction.and;
import static ca.uqac.lif.sparql.ConnectedBy.connectedUndir;
import static ca.uqac.lif.sparql.Disjunction.implies;
import static ca.uqac.lif.sparql.ExistsNode.existsNode;
import static ca.uqac.lif.sparql.ForAllNodes.allNodes;
import static ca.uqac.lif.sparql.IsEqualTo.eq;
import static ca.uqac.lif.sparql.LabelOf.l;

import ca.uqac.lif.sparql.GraphAssertion;
import ca.uqac.lif.sparql.GraphEdge;
import ca.uqac.lif.sparql.GraphNode;
import ca.uqac.lif.sparql.KnowledgeGraph;

/**
 * Recognizes whether a pattern represents a valid
 * <a href="https://en.wikipedia.org/wiki/Polyomino">polyomino</a>. A polyomino
 * is a plane geometric figure formed by joining one or more equal squares edge
 * to edge. In this example, a polyomino is represented as a graph whose nodes
 * correspond to cells in a grid, and edges correspond to adjacency between
 * cells. The validity condition is the fact that every cell marked with an "X"
 * is connected to another cell marked with an "X". (Note that this condition
 * does not recognize the single polyomino of size 1.)
 * <p>
 * Examples of polyominoes:
 * <table>
 * <tr>
 * <td><pre>X
 * X
 * X
 * X</pre></td>
 * <td><pre> XX
 * XX</pre></td>
 * </tr>
 * </table>
 * @author Sylvain Hallé
 */
public class Polyomino
{
	/**                
	 * Runs the example.
	 * @param args Command-line arguments (none is expected)
	 */
	public static void main(String[] args)
	{
		// Read a polyomino from a string and create a graph
		String polyomino = "X " + "\n"
										 + " X ";
		KnowledgeGraph g = createGraph(polyomino);
		
		// Express the condition that the polyomino is connected
		GraphAssertion connected = allNodes("$x", implies(eq(l("$x"), "X"),
				existsNode("$y", and(eq(l("$y"), "X"), connectedUndir("$x", "", "$y")))));

		// Check if the polyomino is connected
		System.out.println(connected.evaluate(g));
	}
	
	/**
	 * Creates a graph from a polyomino string
	 * 
	 * @param polyomino
	 *          The polyomino string
	 * @return The graph
	 */
	protected static KnowledgeGraph createGraph(String polyomino)
	{
		int width = 0, height = 0;
		Scanner scanner = new Scanner(polyomino);
		KnowledgeGraph g = new KnowledgeGraph();
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if (width == 0)
			{
				width = line.length();
			}
			for (int i = 0; i < line.length(); i++)
			{
				String c = line.substring(i, i + 1);
				g.add(new GraphNode(getId(height, i, width), c));
			}
			height++;
		}
		scanner.close();
		// Create horizontal adjacency
		for (int line = 0; line < height; line++)
		{
			for (int col = 0; col < width - 1; col++)
			{
				g.add(new GraphEdge(getId(line, col, width), "", getId(line, col + 1, width)));
			}
		}
		// Create vertical adjacency
		for (int line = 0; line < height - 1; line++)
		{
			for (int col = 0; col < width; col++)
			{
				g.add(new GraphEdge(getId(line, col, width), "", getId(line + 1, col, width)));
			}
		}
		return g;
	}
	
	/**
	 * Gets the identifier of a node in a grid, given its line and column
	 * and the width of the grid (indices start at 0).
	 * @param line The line
	 * @param col The column
	 * @param width The width of the grid
	 * @return The identifier
	 */
	protected static int getId(int line, int col, int width)
	{
		return line * width + col;
	}
}

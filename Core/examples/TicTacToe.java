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
import ca.uqac.lif.sparql.DotGraphParser;
import ca.uqac.lif.sparql.GraphAssertion;
import ca.uqac.lif.sparql.KnowledgeGraph;

import static ca.uqac.lif.sparql.Conjunction.and;
import static ca.uqac.lif.sparql.ConnectedBy.connected;
import static ca.uqac.lif.sparql.ExistsEdge.existsEdge;
import static ca.uqac.lif.sparql.ExistsNode.existsNode;
import static ca.uqac.lif.sparql.IsEqualTo.eq;
import static ca.uqac.lif.sparql.LabelOf.l;

/**
 * Example of a simple Tic-Tac-Toe game expressed as a graph. The winning
 * condition is expressed as an assertion over the graph: there exists a chain
 * of three nodes with the same label that are linked by edges with the same
 * label.
 */
public class TicTacToe
{
	/**
	 * The parser used to parse DOT files.
	 */
	protected static final DotGraphParser s_parser = new DotGraphParser();
	
	/**
	 * Runs the example.
	 * @param args Command-line arguments (none is expected)
	 */
	public static void main(String[] args)
	{
		// Parse the graph with empty node labels from the DOT file
		KnowledgeGraph g = s_parser.parse(TicTacToe.class.getResourceAsStream("data/tic-tac-toe.dot"));
		
		// Set labels for the nodes to represent the game state
		String grid = "XO "
                + "OXO"
                + "  O";
		for (int i = 0; i < 9; i++)
			g.getNode(i + 1).setData(grid.substring(i, i + 1));
		
		/* Express the winning condition for some player. The condition is that
		 * there exists a chain of three nodes with the same label that are
		 * linked by edges with the same label. */
		GraphAssertion win = existsNode("$x", existsNode("$y", existsNode("$z",
				existsEdge("$s", existsEdge("$t", and(
						eq(l("$x"), l("$y"), l("$z")),
						eq(l("$s"), l("$t")),
						connected("$x", "$s", "$y"),
						connected("$y", "$t", "$z")
				))))));
		
		// Check if the state is a win for some player
		System.out.println(win.evaluate(g));
	}
}

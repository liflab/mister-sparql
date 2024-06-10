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

import static ca.uqac.lif.sparql.ConnectedBy.connected;
import static ca.uqac.lif.sparql.Disjunction.implies;
import static ca.uqac.lif.sparql.ForAllNodes.allNodes;
import static ca.uqac.lif.sparql.NumberComparison.gt;
import static ca.uqac.lif.sparql.LabelOf.l;

public class SortedDag
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
		KnowledgeGraph g = s_parser.parse(SortedDag.class.getResourceAsStream("data/sorted-dag.dot"));
		
		// Express the condition that the graph is a sorted DAG
		GraphAssertion sorted = allNodes("$x", allNodes("$y",
				implies(connected("$x", "", "$y"), gt(l("$y"), l("$x")))));
		
		// Evaluate the assertion
		System.out.println(sorted.evaluate(g));
	}
}

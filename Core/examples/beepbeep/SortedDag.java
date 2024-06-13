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
package beepbeep;

import static beepbeep.Shortcuts.*;
import static ca.uqac.lif.cep.Connector.connect;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pushable;
import ca.uqac.lif.cep.io.Print.Println;
import ca.uqac.lif.sparql.DotGraphParser;
import ca.uqac.lif.sparql.KnowledgeGraph;

/**
 * Variant of {@link plain.SortedDag} expressed as a BeepBeep function.
 */
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
		/* Parse the graph with empty node labels from the DOT file. */
		KnowledgeGraph g = s_parser.parse(SortedDag.class.getResourceAsStream("../data/sorted-dag.dot"));

		/* Express the condition that the graph is a sorted DAG. */
		Processor sorted = forAllNodes("$x", forAllNodes("$y",
				implies(
						toProcessor(connected("$x", "", "$y")), 
						toProcessor(gt(l("$y"), l("$x"))))));

		/* Evaluate the assertion. */
		Println print = new Println();
		connect(sorted, print);
		Pushable p = sorted.getPushableInput();
		p.push(g);
	}

}

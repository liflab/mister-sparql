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
import ca.uqac.lif.sparql.KnowledgeGraph;

/**
 * Evaluates the LTL-QG formula stipulating that there exists a node
 * that has the same label in every state.
 */
public class OnlyNeighborsChange
{

	public static void main(String[] args)
	{
		Processor phi = G(
				forAllNodes("$x",
						forAllNodes("$y",
								implies(not(eq("$x", "$y")),
										X(implies(
												and(not(eq(l("$x"), l(dot("$x")))), not(eq(l("$y"), l(dot("$y"))))),
												connected("$x", "r", "$y"))
												)))
						));
		Println print = new Println();
		connect(phi, print);
		Pushable p = phi.getPushableInput();
		KnowledgeGraph g = new KnowledgeGraph();
		{
			/* Create and push a first graph */
			g = g.duplicate()
					.addNode(0, "A")
					.addNode(1, "B")
					.addNode(2, "C")
					.addEdge(0, "r", 1)
					.addEdge(1, "r", 0);
			p.push(g);
		}
		{
			/* Create and push a second graph. Node 2 changes to Z but this does not
			 * violate the property as no other node changed its label. */
			g = g.duplicate()
					.setNodeData(2, "Z");
			p.push(g);
		}
		{
			/* Create and push a third graph. */
			g = g.duplicate()
					.setNodeData(0, "F");
			p.push(g);
		}
	}

}

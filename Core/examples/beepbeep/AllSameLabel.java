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
public class AllSameLabel
{

	public static void main(String[] args)
	{
		Processor phi = G(forAllNodes("$x", X(eq(l("$x"), l(dot("$x"))))));
		Println print = new Println();
		connect(phi, print);
		Pushable p = phi.getPushableInput();
		{
			/* Create and push a first graph */
			KnowledgeGraph g = new KnowledgeGraph(0);
			g.addNode(0, "A");
			g.addNode(1, "B");
			g.addEdge(0, "r", 1);
			p.push(g);
		}
		{
			/* Create and push a second graph. Note that the node IDs are reversed
			 * with respect to the previous graph, but this does not matter for
			 * the property to evaluate. */
			KnowledgeGraph g = new KnowledgeGraph(1);
			g.addNode(0, "A");
			g.addNode(1, "B");
			g.addEdge(1, "r", 0);
			p.push(g);
		}
		{
			/* Create and push a third graph. This graph does not satisfy the
			 * condition, causing the temporal formula to become false. */
			KnowledgeGraph g = new KnowledgeGraph(2);
			g.addNode(0, "A");
			g.addNode(1, "C");
			g.addEdge(1, "z", 0);
			p.push(g);
		}
	}

}

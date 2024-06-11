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

import static ca.uqac.lif.cep.Connector.connect;

import ca.uqac.lif.cep.Pushable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.io.Print.Println;
import ca.uqac.lif.cep.ltl.Always;
import ca.uqac.lif.sparql.ConnectedBy.DirectedConnectedBy;
import ca.uqac.lif.sparql.GraphEdge;
import ca.uqac.lif.sparql.GraphNode;
import ca.uqac.lif.sparql.KnowledgeGraph;

/**
 * Evaluates the LTL-QG formula stipulating that in every graph, there is
 * always a directed "r" edge from a node labeled "A" to a node labeled "B".
 */
public class AlwaysAB
{

	public static void main(String[] args)
	{
		Always always = new Always(
				new ApplyFunction(new DirectedConnectedBy("A", "r", "B")));
		Println print = new Println();
		connect(always, print);
		Pushable p = always.getPushableInput();
		{
			/* Create and push a first graph */
			KnowledgeGraph g = new KnowledgeGraph();
			g.add(new GraphNode(0, "A"));
			g.add(new GraphNode(1, "B"));
			g.add(new GraphEdge(0, "r", 1));
			p.push(g);
		}
		{
			/* Create and push a second graph. Note that the node IDs are reversed
			 * with respect to the previous graph, but this does not matter for
			 * the property to evaluate. */
			KnowledgeGraph g = new KnowledgeGraph();
			g.add(new GraphNode(1, "A"));
			g.add(new GraphNode(0, "B"));
			g.add(new GraphEdge(1, "r", 0));
			p.push(g);
		}
		{
			/* Create and push a third graph. This graph does not satisfy the
			 * condition, causing the temporal formula to become false. */
			KnowledgeGraph g = new KnowledgeGraph();
			g.add(new GraphNode(1, "A"));
			g.add(new GraphNode(0, "B"));
			g.add(new GraphEdge(1, "z", 0));
			p.push(g);
		}
	}

}

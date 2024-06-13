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

import static org.junit.Assert.*;

import org.junit.Test;

public class KnowledgeGraphTest
{
	@Test
	public void testCopy1()
	{
		KnowledgeGraph g1 = new KnowledgeGraph()
				.addNode(0, "A")
				.addNode(1, "B")
				.addEdge(0, "a", 1);
		KnowledgeGraph g2 = g1.duplicate();
		assertEquals(g1.getNode(0), g2.getNode(0));
		assertEquals(g1.getNode(1), g2.getNode(1));
	}
	
	@Test
	public void testCopySetNodeData1()
	{
		KnowledgeGraph g1 = new KnowledgeGraph()
				.addNode(0, "A")
				.addNode(1, "B")
				.addEdge(0, "x", 1);
		KnowledgeGraph g2 = g1.duplicate()
				.setNodeData(0, "C");
		assertNotEquals(g1.getNode(0), g2.getNode(0));
		assertEquals(g1.getNode(1), g2.getNode(1));
	}
	
	@Test
	public void testCopyDeleteNode1()
	{
		KnowledgeGraph g1 = new KnowledgeGraph()
				.addNode(0, "A")
				.addNode(1, "B")
				.addNode(2, "C")
				.addEdge(0, "x", 1)
				.addEdge(1, "y", 2);
		KnowledgeGraph g2 = g1.duplicate()
				.deleteEdge(0, "x", 1);
		assertEquals(g1.getNode(0), g2.getNode(0));
		assertEquals(g1.getNode(1), g2.getNode(1));
		assertTrue(g1.matches("A", "x", "B"));
		assertFalse(g2.matches("A", "b", "B"));
	}
}

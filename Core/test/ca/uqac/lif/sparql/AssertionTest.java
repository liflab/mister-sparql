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

import static ca.uqac.lif.sparql.Conjunction.and;
import static ca.uqac.lif.sparql.ConnectedBy.connected;
import static ca.uqac.lif.sparql.Disjunction.implies;
import static ca.uqac.lif.sparql.Disjunction.or;
import static ca.uqac.lif.sparql.ExistsNode.existsNode;
import static ca.uqac.lif.sparql.ForAllNodes.allNodes;
import static ca.uqac.lif.sparql.IsEqualTo.eq;
import static ca.uqac.lif.sparql.LabelOf.l;
import static ca.uqac.lif.sparql.Negation.not;

@SuppressWarnings("unused")
public class AssertionTest
{
	/**
	 * The parser used to parse DOT files.
	 */
	protected static final DotGraphParser s_parser = new DotGraphParser();
	
	@Test
	public void test1()
	{
		KnowledgeGraph g = s_parser.parse(AssertionTest.class.getResourceAsStream("data/graph1.dot"));
		assertEquals(2, g.size());
		GraphAssertion a = allNodes("$x", allNodes("$y", implies(connected("$x", "r", "$y"), connected("$y", "s", "$x"))));
		assertTrue(a.evaluate(g));
	}
	
	@Test
	public void test2()
	{
		KnowledgeGraph g = s_parser.parse(AssertionTest.class.getResourceAsStream("data/graph1.dot"));
		assertEquals(2, g.size());
		GraphAssertion a = allNodes("$x", allNodes("$y", implies(connected("$x", "r", "$y"), connected("$y", "t", "$x"))));
		assertFalse(a.evaluate(g));
	}
	
	@Test
	public void test3()
	{
		KnowledgeGraph g = s_parser.parse(AssertionTest.class.getResourceAsStream("data/graph1.dot"));
		GraphAssertion a = allNodes("$x", existsNode("$y", implies(and(eq(l("$x"), "A"), connected("$x", "r", "$y")), eq(l("$y"), "B"))));
		assertTrue(a.evaluate(g));
	}
	
	@Test
	public void test4()
	{
		KnowledgeGraph g = s_parser.parse(AssertionTest.class.getResourceAsStream("data/graph1.dot"));
		GraphAssertion a = allNodes("$x", allNodes("$y", implies(and(eq(l("$x"), "A"), connected("$x", "r", "$y")), eq(l("$y"), "C"))));
		assertFalse(a.evaluate(g));
	}
}

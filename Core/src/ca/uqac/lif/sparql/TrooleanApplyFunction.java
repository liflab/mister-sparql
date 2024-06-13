package ca.uqac.lif.sparql;

import java.util.Queue;

import ca.uqac.lif.cep.SynchronousProcessor;
import ca.uqac.lif.cep.functions.Function;

public class TrooleanApplyFunction extends SynchronousProcessor
{
	protected Object m_return;
	
	protected final Function m_comp;
	
	public TrooleanApplyFunction(Function comp)
	{
		super(1, 1);
		m_comp = comp;
		m_return = null;
	}
	
	@Override
	protected boolean compute(Object[] inputs, Queue<Object[]> outputs)
	{
		if (m_return != null)
		{
			outputs.add(new Object[] {m_return});
			return true;
		}
		Object[] out = new Object[1];
		m_comp.evaluate(inputs, out, m_context, m_eventTracker);
		m_return = out[0];
		outputs.add(new Object[] {m_return});
		return true;
	}
	
	@Override
	public TrooleanApplyFunction duplicate(boolean with_state)
	{
		TrooleanApplyFunction taf = new TrooleanApplyFunction(m_comp);
		if (with_state)
		{
			taf.m_return = m_return;
		}
		return taf;
	}
}

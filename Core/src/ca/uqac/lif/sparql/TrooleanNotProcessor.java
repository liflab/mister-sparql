package ca.uqac.lif.sparql;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pushable;
import ca.uqac.lif.cep.UniformProcessor;
import ca.uqac.lif.cep.ltl.Troolean;
import ca.uqac.lif.cep.ltl.Troolean.Value;
import ca.uqac.lif.cep.tmf.SinkLast;

public class TrooleanNotProcessor extends UniformProcessor
{
	protected final Processor m_operand;

	protected final SinkLast m_sink;

	protected final Pushable m_pushable;

	protected Value m_verdict;

	public TrooleanNotProcessor(Processor op)
	{
		super(1, 1);
		m_operand = op;
		m_sink = new SinkLast();
		Connector.connect(m_operand, m_sink);
		m_pushable = m_operand.getPushableInput();
		m_verdict = Value.INCONCLUSIVE;
	}
	
	@Override
	public void setContext(Context c)
	{
		super.setContext(c);
		m_operand.setContext(c);
	}
	
	@Override
	public void setContext(String key, Object value)
	{
		super.setContext(key, value);
		m_operand.setContext(key, value);
	}

	@Override
	protected boolean compute(Object[] inputs, Object[] outputs)
	{
		if (m_verdict == Value.INCONCLUSIVE)
		{
			m_pushable.push(inputs[0]);
			Value left = Troolean.trooleanValue(m_sink.getLast()[0]);
			m_verdict = Troolean.not(left);
		}
		outputs[0] = m_verdict;
		return true;
	}

	@Override
	public TrooleanNotProcessor duplicate(boolean with_state)
	{
		TrooleanNotProcessor p = new TrooleanNotProcessor(m_operand.duplicate(with_state));
		return p;
	}
}

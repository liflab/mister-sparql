package ca.uqac.lif.sparql;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pushable;
import ca.uqac.lif.cep.UniformProcessor;
import ca.uqac.lif.cep.ltl.Troolean;
import ca.uqac.lif.cep.ltl.Troolean.Value;
import ca.uqac.lif.cep.tmf.SinkLast;

public class TrooleanImpliesProcessor extends UniformProcessor
{
	protected final Processor m_left;
	
	protected final Processor m_right;
	
	protected final SinkLast m_leftSink;
	
	protected final SinkLast m_rightSink;
	
	protected final Pushable m_leftPushable;
	
	protected final Pushable m_rightPushable;
	
	protected Value m_verdict;
	
	public TrooleanImpliesProcessor(Processor left, Processor right)
	{
		super(1, 1);
		m_left = left;
		m_right = right;
		m_leftSink = new SinkLast();
		m_rightSink = new SinkLast();
		Connector.connect(m_left, m_leftSink);
		Connector.connect(m_right, m_rightSink);
		m_leftPushable = m_left.getPushableInput();
		m_rightPushable = m_right.getPushableInput();
		m_verdict = Value.INCONCLUSIVE;
	}
	
	@Override
	public void setContext(Context c)
	{
		super.setContext(c);
		m_left.setContext(c);
		m_right.setContext(c);
	}
	
	@Override
	public void setContext(String key, Object value)
	{
		super.setContext(key, value);
		m_left.setContext(key, value);
		m_right.setContext(key, value);
	}

	@Override
	protected boolean compute(Object[] inputs, Object[] outputs)
	{
		if (m_verdict == Value.INCONCLUSIVE)
		{
			m_leftPushable.push(inputs[0]);
			m_rightPushable.push(inputs[0]);
			Value left = Troolean.trooleanValue(m_leftSink.getLast());
			Value right = Troolean.trooleanValue(m_rightSink.getLast());
			m_verdict = Troolean.implies(left, right);
		}
		outputs[0] = m_verdict;
		return true;
	}

	@Override
	public TrooleanImpliesProcessor duplicate(boolean with_state)
	{
		TrooleanImpliesProcessor p = new TrooleanImpliesProcessor(m_left.duplicate(with_state), m_right.duplicate(with_state));
		return p;
	}
}

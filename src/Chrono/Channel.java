package Chrono;

public class Channel {
	public enum Sensor {NONE, EYE, GATE, PAD}
	public enum TriggerType {START, FINISH, UNASSIGNED};
	private boolean enabled;
	private boolean connected;
	private int channelIndex;
	private Sensor m_sensorType;
	private TriggerType m_triggerType;

	public TriggerType getTriggerType() {
		return m_triggerType;
	}

	public void setTriggerType(TriggerType m_triggerType) {
		this.m_triggerType = m_triggerType;
	}

	public Channel(int channelIndex) {
		setEnabled(false);
		this.connected = false;
		this.channelIndex = channelIndex;
		this.m_sensorType = Sensor.NONE;
		if(this.channelIndex%2 == 0)
			this.m_triggerType = TriggerType.FINISH;
		else
			this.m_triggerType = TriggerType.START;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isConnected() {
		return connected;
	}

	public void connect(Sensor sensor) {
		connected = true;
		m_sensorType = sensor;
	}
	
	public void disconnect() {
		connected = false;
		m_sensorType = Sensor.NONE;
	}

	public int getChannelIndex() {
		return channelIndex;
	}

	public Sensor getSensorType() {
		return m_sensorType;
	}
}

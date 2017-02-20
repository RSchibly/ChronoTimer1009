package Chrono;

public class Channel {
	public enum Sensor {NONE, EYE, GATE, PAD}
	
	private boolean enabled;
	private boolean connected;
	private int channelIndex;
	private Sensor m_type;

	public Channel(int channelIndex) {
		setEnabled(false);
		this.connected = false;
		this.channelIndex = channelIndex;
		this.m_type = Sensor.NONE;
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
		m_type = sensor;
	}
	
	public void disconnect() {
		connected = false;
		m_type = Sensor.NONE;
	}

	public int getChannelIndex() {
		return channelIndex;
	}

	public Sensor getSensorType() {
		return m_type;
	}

}

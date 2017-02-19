package Chrono;

public class Channel {
	private boolean enabled;
	private boolean connected;
	private int channelIndex;

	public Channel(int channelIndex) {
		setEnabled(false);
		setConnected(false);
		this.channelIndex = channelIndex;
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

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public int getChannelIndex() {
		return channelIndex;
	}
}

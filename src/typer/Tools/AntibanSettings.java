package typer.Tools;


public class AntibanSettings {

	private int breaks_per_hour_min;
	private int breaks_per_hour_max;
	private int break_length_min;
	private int break_length_max;
	private int type_speed_delay_min;
	private int type_speed_delay_max;
	private int delay_time_delay_min;
	private int delay_time_delay_max;
	private int stop_timer_time;
	private int hostCount;

	public int getHostCount() {
		return hostCount;
	}

	public void setHostCount(int hostCount) {
		this.hostCount = hostCount;
	}

	public int getBreaks_per_hour_min() {
		return breaks_per_hour_min;
	}

	public void setBreaks_per_hour_min(int breaks_per_hour_min) {
		this.breaks_per_hour_min = breaks_per_hour_min;
	}

	public int getBreaks_per_hour_max() {
		return breaks_per_hour_max;
	}

	public void setBreaks_per_hour_max(int breaks_per_hour_max) {
		this.breaks_per_hour_max = breaks_per_hour_max;
	}

	public int getBreak_length_min() {
		return break_length_min;
	}

	public void setBreak_length_min(int break_length_min) {
		this.break_length_min = break_length_min;
	}

	public int getBreak_length_max() {
		return break_length_max;
	}

	public void setBreak_length_max(int break_length_max) {
		this.break_length_max = break_length_max;
	}

	public int getType_speed_delay_min() {
		return type_speed_delay_min;
	}

	public void setType_speed_delay_min(int type_speed_delay_min) {
		this.type_speed_delay_min = type_speed_delay_min;
	}

	public int getType_speed_delay_max() {
		return type_speed_delay_max;
	}

	public void setType_speed_delay_max(int type_speed_delay_max) {
		this.type_speed_delay_max = type_speed_delay_max;
	}

	public int getDelay_time_delay_min() {
		return delay_time_delay_min;
	}

	public void setDelay_time_delay_min(int delay_time_delay_min) {
		this.delay_time_delay_min = delay_time_delay_min;
	}

	public int getDelay_time_delay_max() {
		return delay_time_delay_max;
	}

	public void setDelay_time_delay_max(int delay_time_delay_max) {
		this.delay_time_delay_max = delay_time_delay_max;
	}

	public int getStop_timer_time() {
		return stop_timer_time;
	}

	public void setStop_timer_time(int stop_timer_time) {
		this.stop_timer_time = stop_timer_time;
	}
}
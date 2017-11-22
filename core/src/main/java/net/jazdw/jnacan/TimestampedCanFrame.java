/*
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 * @author Jared Wiltshire
 */

package net.jazdw.jnacan;

import net.jazdw.jnacan.c.can_frame;
import net.jazdw.jnacan.c.timeval;

/**
 * A can frame with a microsecond resolution timestamp
 * Timestamp is in seconds and microseconds since 1 January 1970
 *
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 *
 * @author Jared Wiltshire
 */
public class TimestampedCanFrame extends CanFrame {
	long seconds;
	long microseconds;

	public TimestampedCanFrame(can_frame jnaFrame, timeval time) {
		super(jnaFrame);
		this.seconds = time.tv_sec.longValue();
		this.microseconds = time.tv_usec.longValue();
	}

	/**
	 * Converts to Unix epoc time
	 *
	 * @return milliseconds since 1 January 1970
	 */
	public long toEpocTime() {
		return Utils.microsToMillis(seconds, microseconds);
	}

	@Override
	public String toString() {
		return String.format("%010d.%06d %s", seconds, microseconds, super.toString());
	}

	public long getSeconds() {
		return seconds;
	}

	public TimestampedCanFrame setSeconds(long seconds) {
		this.seconds = seconds;
		return this;
	}

	public long getMicroseconds() {
		return microseconds;
	}

	public TimestampedCanFrame setMicroseconds(long microseconds) {
		this.microseconds = microseconds;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		TimestampedCanFrame that = (TimestampedCanFrame) o;

		if (seconds != that.seconds) {
			return false;
		}
		return microseconds == that.microseconds;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (int) (seconds ^ (seconds >>> 32));
		result = 31 * result + (int) (microseconds ^ (microseconds >>> 32));
		return result;
	}
}

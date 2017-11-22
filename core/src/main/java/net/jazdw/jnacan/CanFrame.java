/*
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 * @author Jared Wiltshire
 */

package net.jazdw.jnacan;

import java.util.Arrays;

import net.jazdw.jnacan.c.can_frame;

/**
 * Represents a CAN frame
 * DLC is set automatically from the length of data
 *
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 *
 * @author Jared Wiltshire
 */
public class CanFrame implements CanMessage<can_frame> {
	CanId id;
	byte[] data;

	protected CanFrame(can_frame jnaFrame) {
		setData(new byte[jnaFrame.can_dlc]);
		this.id = new CanId(jnaFrame.can_id);
		System.arraycopy(jnaFrame.data, 0, this.data, 0, this.data.length);
	}

	public CanFrame() {
		this(0);
	}

	public CanFrame(int id) {
		this(id, new byte[0]);
	}

	public CanFrame(int id, byte... data) {
		this(new CanId(id), data);
	}

	public CanFrame(CanId id, byte... data) {
		if (data.length > 8) {
			throw new IllegalArgumentException("Data larger than 8 bytes");
		}
		this.data = data;
		this.id = id;
	}

	public CanFrame(int id, int... data) {
		this(new CanId(id), data);
	}

	public CanFrame(CanId id, int... data) {
		if (data.length > 8) {
			throw new IllegalArgumentException("Data larger than 8 bytes");
		}

		byte[] byteData = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			byteData[i] = (byte) data[i];
		}

		this.data = byteData;
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see net.jazdw.jnacan.CanMessage#toJnaType()
	 */
	@Override
	public can_frame toJnaType() {
		byte[] jnaData = new byte[8];
		System.arraycopy(this.data, 0, jnaData, 0, this.data.length);
		return new can_frame(id.getId(), (byte) data.length, jnaData);
	}

	public void setData(byte... data) {
		if (data.length > 8) {
			throw new IllegalArgumentException("Data larger than 8 bytes");
		}
		this.data = data;
	}

	public void setId(int id) {
		this.id.setId(id);
	}

	@Override
	public String toString() {
		String ret = id + " [" + data.length + "] ";
		for (int i = 0; i < data.length; i++) {
			ret += String.format("%02X ", data[i]);
		}
		return ret;
	}

	public CanId getId() {
		return id;
	}

	public CanFrame setId(CanId id) {
		this.id = id;
		return this;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CanFrame canFrame = (CanFrame) o;

		if (id != null ? !id.equals(canFrame.id) : canFrame.id != null) {
			return false;
		}
		return Arrays.equals(data, canFrame.data);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + Arrays.hashCode(data);
		return result;
	}
}

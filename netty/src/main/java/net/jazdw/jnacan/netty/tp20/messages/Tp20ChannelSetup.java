/*
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 * @author Jared Wiltshire
 */
package net.jazdw.jnacan.netty.tp20.messages;

/**
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 *
 * @author Jared Wiltshire
 */
public class Tp20ChannelSetup implements Tp20Message {
	Tp20OpCode opCode;
	byte blockSize;
	/**
	 * Time to wait for ACK in microseconds. Should be greater than 4*T3
	 */
	int t1;
	/**
	 * Interval between packets in microseconds
	 */
	int t3;

	private int decodeTiming(byte t) {
		byte scale = (byte) (t >> 6);
		byte value = (byte) (t & 0x3F);
		switch (scale) {
		case 0x0: // 100 usec
			return value * 100;
		case 0x1: // 1 msec
			return value * 1000;
		case 0x2: // 10 msec
			return value * 10000;
		case 0x3: // 100 msec
			return value * 100000;
		default:
			throw new UnsupportedOperationException();
		}
	}

	private byte encodeTiming(int usec) {
		if (usec > 6300000) {
			throw new IllegalArgumentException("Can't be greater than 6300000 microseconds");
		}
		if (usec < 100) {
			throw new IllegalArgumentException("Can't be less than 100 microseconds");
		}

		if (usec <= 6300) {
			return (byte) (usec / 100);
		}
		if (usec <= 63000) {
			return (byte) ((0x01 << 6) | (usec / 1000));
		}
		if (usec <= 630000) {
			return (byte) ((0x02 << 6) | (usec / 10000));
		}
		return (byte) ((0x03 << 6) | (usec / 100000));
	}

	@Override
	public byte[] encode() {
		byte[] data = new byte[6];
		data[0] = opCode.value();
		data[1] = blockSize;
		data[2] = encodeTiming(t1);
		data[3] = (byte) 0xFF;
		data[4] = encodeTiming(t3);
		data[5] = (byte) 0xFF;
		return data;
	}

	@Override
	public void decode(byte[] data) {
		if (data.length != 6) {
			throw new IllegalArgumentException("Channel setup data length should be 6 bytes");
		}

		opCode = Tp20OpCode.fromValue(data[0]);
		switch (opCode) {
		case CHANNEL_SETUP:
		case CONNECTION_ACKNOWLEDGE:
			break;
		default:
			throw new IllegalArgumentException("Wrong op code for channel setup");
		}

		blockSize = data[1];
		t1 = decodeTiming(data[2]);
		t3 = decodeTiming(data[4]);
	}

	@Override
	public Tp20OpCode getOpCode() {
		return opCode;
	}

	public Tp20ChannelSetup setOpCode(Tp20OpCode opCode) {
		this.opCode = opCode;
		return this;
	}

	public byte getBlockSize() {
		return blockSize;
	}

	public Tp20ChannelSetup setBlockSize(byte blockSize) {
		this.blockSize = blockSize;
		return this;
	}

	public int getT1() {
		return t1;
	}

	public Tp20ChannelSetup setT1(int t1) {
		this.t1 = t1;
		return this;
	}

	public int getT3() {
		return t3;
	}

	public Tp20ChannelSetup setT3(int t3) {
		this.t3 = t3;
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

		Tp20ChannelSetup that = (Tp20ChannelSetup) o;

		if (blockSize != that.blockSize) {
			return false;
		}
		if (t1 != that.t1) {
			return false;
		}
		if (t3 != that.t3) {
			return false;
		}
		return opCode == that.opCode;
	}

	@Override
	public int hashCode() {
		int result = opCode != null ? opCode.hashCode() : 0;
		result = 31 * result + (int) blockSize;
		result = 31 * result + t1;
		result = 31 * result + t3;
		return result;
	}
}

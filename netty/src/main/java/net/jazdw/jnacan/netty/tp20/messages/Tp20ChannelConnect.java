/*
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 * @author Jared Wiltshire
 */
package net.jazdw.jnacan.netty.tp20.messages;

import net.jazdw.jnacan.CanId;
import net.jazdw.jnacan.netty.tp20.Tp20ApplicationType;

/**
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 *
 * @author Jared Wiltshire
 */
public class Tp20ChannelConnect implements Tp20Message {
	byte destination;
	Tp20OpCode opCode;
	CanId txId;
	CanId rxId;
	Tp20ApplicationType applicationType;

	@Override
	public byte[] encode() {
		byte[] data = new byte[7];
		data[0] = destination;
		data[1] = opCode.value();

		// TODO EFF supported?
		int txId = this.txId.getSFFid();
		int rxId = this.rxId.getSFFid();

		data[2] = (byte) txId;
		data[3] = (byte) ((1 << 4) | (txId >> 8));
		data[4] = (byte) rxId;
		data[5] = (byte) (rxId >> 8);
		data[6] = applicationType.value();
		return data;
	}

	@Override
	public void decode(byte[] data) {
		if (data.length != 7) {
			throw new IllegalArgumentException("Channel connect data length should be 7 bytes");
		}

		opCode = Tp20OpCode.fromValue(data[1]);
		switch (opCode) {
		case CHANNEL_REPLY_APPLICATION_TYPE_UNAVAILABLE:
		case CHANNEL_REPLY_APPLICATION_TYPE_UNSUPPORTED:
		case CHANNEL_REPLY_OUT_OUT_RESOURCES:
		case CHANNEL_REPLY_POSITIVE:
		case CHANNEL_REQUEST:
			break;
		default:
			throw new IllegalArgumentException("Wrong op code for channel connect");
		}

		destination = data[0];
		txId = new CanId(((data[3] & 0x0F) << 8) | data[2]);
		rxId = new CanId(((data[5] & 0x0F) << 8) | data[4]);
		applicationType = Tp20ApplicationType.fromValue(data[6]);
	}

	public byte getDestination() {
		return destination;
	}

	public Tp20ChannelConnect setDestination(byte destination) {
		this.destination = destination;
		return this;
	}

	@Override
	public Tp20OpCode getOpCode() {
		return opCode;
	}

	public Tp20ChannelConnect setOpCode(Tp20OpCode opCode) {
		this.opCode = opCode;
		return this;
	}

	public CanId getTxId() {
		return txId;
	}

	public Tp20ChannelConnect setTxId(CanId txId) {
		this.txId = txId;
		return this;
	}

	public CanId getRxId() {
		return rxId;
	}

	public Tp20ChannelConnect setRxId(CanId rxId) {
		this.rxId = rxId;
		return this;
	}

	public Tp20ApplicationType getApplicationType() {
		return applicationType;
	}

	public Tp20ChannelConnect setApplicationType(Tp20ApplicationType applicationType) {
		this.applicationType = applicationType;
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

		Tp20ChannelConnect that = (Tp20ChannelConnect) o;

		if (destination != that.destination) {
			return false;
		}
		if (opCode != that.opCode) {
			return false;
		}
		if (txId != null ? !txId.equals(that.txId) : that.txId != null) {
			return false;
		}
		if (rxId != null ? !rxId.equals(that.rxId) : that.rxId != null) {
			return false;
		}
		return applicationType == that.applicationType;
	}

	@Override
	public int hashCode() {
		int result = (int) destination;
		result = 31 * result + (opCode != null ? opCode.hashCode() : 0);
		result = 31 * result + (txId != null ? txId.hashCode() : 0);
		result = 31 * result + (rxId != null ? rxId.hashCode() : 0);
		result = 31 * result + (applicationType != null ? applicationType.hashCode() : 0);
		return result;
	}
}

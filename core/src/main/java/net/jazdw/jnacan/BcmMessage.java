/*
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 * @author Jared Wiltshire
 */
package net.jazdw.jnacan;

import java.util.*;

import net.jazdw.jnacan.Utils.ReverseEnumMap;
import net.jazdw.jnacan.Utils.ValueEnum;
import net.jazdw.jnacan.c.CLibrary;
import net.jazdw.jnacan.c.bcm_msg;
import net.jazdw.jnacan.c.bcm_msg_head;
import net.jazdw.jnacan.c.can_frame;

/**
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 *
 * @author Jared Wiltshire
 */
public class BcmMessage implements CanMessage<bcm_msg_head> {
	BcmOperation operation;
	EnumSet<BcmFlag> flags = EnumSet.noneOf(BcmFlag.class);
	int count = 0;
	long interval1 = 0;
	long interval2 = 0;
	CanId id = null;
	List<CanFrame> frames = Collections.emptyList();

	public BcmMessage() {
	}

	public BcmMessage(BcmOperation operation, int id) {
		this(operation, new CanId(id));
	}

	public BcmMessage(BcmOperation operation, CanId id) {
		this.operation = operation;
		this.id = id;
	}

	protected BcmMessage(bcm_msg_head msgHead) {
		operation = BcmOperation.fromValue(msgHead.opcode);
		flags = BcmFlag.fromValue(msgHead.flags);
		count = msgHead.count;
		interval1 = Utils.timevalToMillis(msgHead.ival1);
		interval2 = Utils.timevalToMillis(msgHead.ival2);
		id = new CanId(msgHead.can_id);
		frames = new ArrayList<CanFrame>(msgHead.nframes);

		if (msgHead instanceof bcm_msg) {
			bcm_msg msg = (bcm_msg) msgHead;
			for (int i = 0; i < msg.nframes; i++) {
				frames.add(new CanFrame(msg.frames[i]));
			}
		}
	}

	public enum BcmOperation implements ValueEnum<Integer> {
		// transmit path
		TX_SETUP(CLibrary.TX_SETUP), TX_DELETE(CLibrary.TX_DELETE), TX_READ(CLibrary.TX_READ), TX_SEND(CLibrary.TX_SEND),
		// receive path
		RX_SETUP(CLibrary.RX_SETUP), RX_DELETE(CLibrary.RX_DELETE), RX_READ(CLibrary.RX_READ),
		// response codes
		TX_STATUS(CLibrary.TX_STATUS), TX_EXPIRED(CLibrary.TX_EXPIRED),
		RX_STATUS(CLibrary.RX_STATUS), RX_TIMEOUT(CLibrary.RX_TIMEOUT), RX_CHANGED(CLibrary.RX_CHANGED);

		private int value;
		private static ReverseEnumMap<Integer, BcmOperation> map = ReverseEnumMap.create(BcmOperation.class);

		BcmOperation(int value) {
			this.value = value;
		}

		@Override
		public Integer value() {
			return value;
		}

		public static BcmOperation fromValue(Integer value) {
			return map.get(value);
		}
	}

	public enum BcmFlag implements ValueEnum<Integer> {
		SETTIMER(CLibrary.SETTIMER), STARTTIMER(CLibrary.STARTTIMER),
		TX_COUNTEVT(CLibrary.TX_COUNTEVT), TX_ANNOUNCE(CLibrary.TX_ANNOUNCE), TX_CP_CAN_ID(CLibrary.TX_CP_CAN_ID), TX_RESET_MULTI_IDX(
				CLibrary.TX_RESET_MULTI_IDX),
		RX_FILTER_ID(CLibrary.RX_FILTER_ID), RX_RTR_FRAME(CLibrary.RX_RTR_FRAME), RX_CHECK_DLC(CLibrary.RX_CHECK_DLC), RX_NO_AUTOTIMER(
				CLibrary.RX_NO_AUTOTIMER), RX_ANNOUNCE_RESUME(CLibrary.RX_ANNOUNCE_RESUME);

		private int value;

		BcmFlag(int value) {
			this.value = value;
		}

		@Override
		public Integer value() {
			return value;
		}

		public static EnumSet<BcmFlag> fromValue(Integer value) {
			if (value == 0) {
				return EnumSet.noneOf(BcmFlag.class);
			}
			List<BcmFlag> list = new ArrayList<BcmFlag>();
			for (BcmFlag flag : BcmFlag.values()) {
				if ((value & flag.value) != 0) {
					list.add(flag);
				}
			}
			return EnumSet.copyOf(list);
		}
	}

	public void setFrames(CanFrame... frames) {
		this.frames = Arrays.asList(frames);
	}

	/* (non-Javadoc)
	 * @see net.jazdw.jnacan.CanMessage#toJnaType()
	 */
	@Override
	public bcm_msg_head toJnaType() {
		bcm_msg_head msg;
		if (frames.size() > 0) {
			bcm_msg msgWithFrames = new bcm_msg();
			msgWithFrames.frames = new can_frame[frames.size()];

			for (int i = 0; i < frames.size(); i++) {
				msgWithFrames.frames[i] = frames.get(i).toJnaType();
			}
			msg = msgWithFrames;
		} else {
			msg = new bcm_msg_head();
		}

		msg.opcode = operation.value();

		int flagsValue = 0;
		for (BcmFlag flag : flags) {
			flagsValue |= flag.value();
		}
		msg.flags = flagsValue;

		msg.count = count;
		msg.ival1 = Utils.millisToTimeval(interval1);
		msg.ival2 = Utils.millisToTimeval(interval2);
		msg.can_id = id == null ? 0 : id.getId();
		msg.nframes = frames.size();

		return msg;
	}

	/**
	 * @param canId
	 */
	public void setId(int canId) {
		id = new CanId(canId);
	}

	public BcmOperation getOperation() {
		return operation;
	}

	public BcmMessage setOperation(BcmOperation operation) {
		this.operation = operation;
		return this;
	}

	public EnumSet<BcmFlag> getFlags() {
		return flags;
	}

	public BcmMessage setFlags(EnumSet<BcmFlag> flags) {
		this.flags = flags;
		return this;
	}

	public int getCount() {
		return count;
	}

	public BcmMessage setCount(int count) {
		this.count = count;
		return this;
	}

	public long getInterval1() {
		return interval1;
	}

	public BcmMessage setInterval1(long interval1) {
		this.interval1 = interval1;
		return this;
	}

	public long getInterval2() {
		return interval2;
	}

	public BcmMessage setInterval2(long interval2) {
		this.interval2 = interval2;
		return this;
	}

	public CanId getId() {
		return id;
	}

	public BcmMessage setId(CanId id) {
		this.id = id;
		return this;
	}

	public List<CanFrame> getFrames() {
		return frames;
	}

	public BcmMessage setFrames(List<CanFrame> frames) {
		this.frames = frames;
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

		BcmMessage that = (BcmMessage) o;

		if (count != that.count) {
			return false;
		}
		if (interval1 != that.interval1) {
			return false;
		}
		if (interval2 != that.interval2) {
			return false;
		}
		if (operation != that.operation) {
			return false;
		}
		if (flags != null ? !flags.equals(that.flags) : that.flags != null) {
			return false;
		}
		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		return frames != null ? frames.equals(that.frames) : that.frames == null;
	}

	@Override
	public int hashCode() {
		int result = operation != null ? operation.hashCode() : 0;
		result = 31 * result + (flags != null ? flags.hashCode() : 0);
		result = 31 * result + count;
		result = 31 * result + (int) (interval1 ^ (interval1 >>> 32));
		result = 31 * result + (int) (interval2 ^ (interval2 >>> 32));
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + (frames != null ? frames.hashCode() : 0);
		return result;
	}
}

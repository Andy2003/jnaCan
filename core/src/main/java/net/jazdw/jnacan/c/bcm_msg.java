package net.jazdw.jnacan.c;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class bcm_msg extends bcm_msg_head {
	/** C type : can_frame[0] */
	public can_frame[] frames = new can_frame[0];
	
	public bcm_msg() {
		super();
	}
	
	public bcm_msg(Pointer ptr) {
        super(ptr);
    }
	
	protected List<? > getFieldOrder() {
		return Arrays.asList("opcode", "flags", "count", "ival1", "ival2", "can_id", "nframes", "frames");
	}
	/**
	 * @param opcode C type : __u32<br>
	 * @param flags C type : __u32<br>
	 * @param count C type : __u32<br>
	 * @param ival1 C type : timeval<br>
	 * @param ival2 C type : timeval<br>
	 * @param can_id C type : canid_t<br>
	 * @param nframes C type : __u32<br>
	 * @param frames C type : can_frame[0]
	 */
	public bcm_msg(int opcode, int flags, int count, timeval ival1, timeval ival2, int can_id, int nframes, can_frame frames[]) {
		super();
		this.opcode = opcode;
		this.flags = flags;
		this.count = count;
		this.ival1 = ival1;
		this.ival2 = ival2;
		this.can_id = can_id;
		this.nframes = nframes;
		this.frames = frames;
	}
	public static class ByReference extends bcm_msg_head implements Structure.ByReference {
		
	};
	public static class ByValue extends bcm_msg_head implements Structure.ByValue {
		
	};
}

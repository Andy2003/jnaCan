package net.jazdw.jnacan.c;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
/**
 * struct can_filter - CAN ID based filter in can_register().<br>
 * @can_id:   relevant bits of CAN ID which are not masked out.<br>
 * @can_mask: CAN mask (see description)<br>
 * * Description:<br>
 * A filter matches, when<br>
 * *          <received_can_id> & mask == can_id & mask<br>
 * * The filter can be inverted (CAN_INV_FILTER bit set in can_id) or it can<br>
 * filter for error frames (CAN_ERR_FLAG bit set in mask).<br>
 * <i>native declaration : can.h:101</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class can_filter extends Structure {
	/** C type : canid_t */
	public int can_id;
	/** C type : canid_t */
	public int can_mask;
	public can_filter() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("can_id", "can_mask");
	}
	/**
	 * @param can_id C type : canid_t<br>
	 * @param can_mask C type : canid_t
	 */
	public can_filter(int can_id, int can_mask) {
		super();
		this.can_id = can_id;
		this.can_mask = can_mask;
	}
	public static class ByReference extends can_filter implements Structure.ByReference {
		
	};
	public static class ByValue extends can_filter implements Structure.ByValue {
		
	};
}
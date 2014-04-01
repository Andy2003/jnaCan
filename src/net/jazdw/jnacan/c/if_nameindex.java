package net.jazdw.jnacan.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : /usr/include/net/if.h:32</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class if_nameindex extends Structure {
	/** 1, 2, ... */
	public int if_index;
	/**
	 * null terminated name: "eth0", ...<br>
	 * C type : char*
	 */
	public Pointer if_name;
	public if_nameindex() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("if_index", "if_name");
	}
	/**
	 * @param if_index 1, 2, ...<br>
	 * @param if_name null terminated name: "eth0", ...<br>
	 * C type : char*
	 */
	public if_nameindex(int if_index, Pointer if_name) {
		super();
		this.if_index = if_index;
		this.if_name = if_name;
	}
	public static class ByReference extends if_nameindex implements Structure.ByReference {
		
	};
	public static class ByValue extends if_nameindex implements Structure.ByValue {
		
	};
}

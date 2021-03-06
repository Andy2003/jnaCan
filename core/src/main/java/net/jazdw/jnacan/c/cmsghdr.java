package net.jazdw.jnacan.c;

import java.util.Arrays;
import java.util.List;

import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.sun.jna.Structure;
/**
 * Structure used for storage of ancillary data object information.<br>
 * <i>native declaration : bits/socket.h:214</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class cmsghdr extends Structure {
    public static class cmsgtimeval extends Structure {
        public cmsgtimeval() {
            super();
        }
        public cmsghdr hdr;
        public timeval time;
        @Override
        protected List<?> getFieldOrder() {
            return Arrays.asList("hdr", "time");
        }
    }
	/**
	 * Length of data in cmsg_data plus length<br>
	 * of cmsghdr structure.<br>
	 * !! The type should be socklen_t but the<br>
	 * definition of the kernel is incompatible<br>
	 * with this.
	 */
	public NativeSize cmsg_len;
	/** Originating protocol. */
	public int cmsg_level;
	/** Protocol specific type. */
	public int cmsg_type;
	public cmsghdr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("cmsg_len", "cmsg_level", "cmsg_type");
	}
	/**
	 * @param cmsg_len Length of data in cmsg_data plus length<br>
	 * of cmsghdr structure.<br>
	 * !! The type should be socklen_t but the<br>
	 * definition of the kernel is incompatible<br>
	 * with this.<br>
	 * @param cmsg_level Originating protocol.<br>
	 * @param cmsg_type Protocol specific type.
	 */
	public cmsghdr(NativeSize cmsg_len, int cmsg_level, int cmsg_type) {
		super();
		this.cmsg_len = cmsg_len;
		this.cmsg_level = cmsg_level;
		this.cmsg_type = cmsg_type;
	}
	public static class ByReference extends cmsghdr implements Structure.ByReference {
		
	};
	public static class ByValue extends cmsghdr implements Structure.ByValue {
		
	};
}

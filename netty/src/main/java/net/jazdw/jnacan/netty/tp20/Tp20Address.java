/*
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 * @author Jared Wiltshire
 */
package net.jazdw.jnacan.netty.tp20;

import java.net.SocketAddress;

import net.jazdw.jnacan.CanInterface;

/**
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 *
 * @author Jared Wiltshire
 */
public class Tp20Address extends SocketAddress {
	private static final long serialVersionUID = -6101090718909393720L;

	CanInterface canInterface;
	byte destination;

	public Tp20Address(CanInterface canInterface, byte destination) {
		if (canInterface == null) {
			throw new NullPointerException();
		}
		if (canInterface.isAllInterface()) {
			throw new IllegalArgumentException("A CAN interface must be specified");
		}
		this.canInterface = canInterface;
		this.destination = destination;
	}

	public CanInterface getCanInterface() {
		return canInterface;
	}

	public Tp20Address setCanInterface(CanInterface canInterface) {
		this.canInterface = canInterface;
		return this;
	}

	public byte getDestination() {
		return destination;
	}

	public Tp20Address setDestination(byte destination) {
		this.destination = destination;
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

		Tp20Address that = (Tp20Address) o;

		if (destination != that.destination) {
			return false;
		}
		return canInterface != null ? canInterface.equals(that.canInterface) : that.canInterface == null;
	}

	@Override
	public int hashCode() {
		int result = canInterface != null ? canInterface.hashCode() : 0;
		result = 31 * result + (int) destination;
		return result;
	}
}

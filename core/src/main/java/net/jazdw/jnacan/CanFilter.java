/*
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 * @author Jared Wiltshire
 */

package net.jazdw.jnacan;

import net.jazdw.jnacan.c.can_filter;

/**
 * Specifies a filter for incoming CAN frames
 *
 * The IDs of incoming CAN frames are masked then compared to the filters ID.
 * See Linux SocketCan documentation for more information
 *
 * Copyright (C) 2014 Jared Wiltshire. All rights reserved.
 *
 * @author Jared Wiltshire
 */
public class CanFilter {
	CanId id;
	CanId mask;

	public CanFilter(int id, int mask) {
		this(new CanId(id), new CanId(mask));
	}

	public CanFilter(CanId id, int mask) {
		this(id, new CanId(mask));
	}

	public CanFilter(CanId id, CanId mask) {
		this.id = id;
		this.mask = mask;
	}

	/**
	 * Inverts the operation of this filter
	 */
	public void setInverted() {
		id.setInverted();
	}

	/**
	 * Clears the inversion of this filter
	 */
	public void clearInverted() {
		id.clearInverted();
	}

	/**
	 * @return true if filter is inverted
	 */
	public boolean isInverted() {
		return id.isInverted();
	}

	public can_filter toJnaType() {
		return new can_filter(id.getId(), mask.getId());
	}

	public void copyTo(can_filter filter) {
		filter.can_id = id.getId();
		filter.can_mask = mask.getId();
	}

	public CanId getId() {
		return id;
	}

	public CanFilter setId(CanId id) {
		this.id = id;
		return this;
	}

	public CanId getMask() {
		return mask;
	}

	public CanFilter setMask(CanId mask) {
		this.mask = mask;
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

		CanFilter canFilter = (CanFilter) o;

		if (id != null ? !id.equals(canFilter.id) : canFilter.id != null) {
			return false;
		}
		return mask != null ? mask.equals(canFilter.mask) : canFilter.mask == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (mask != null ? mask.hashCode() : 0);
		return result;
	}
}

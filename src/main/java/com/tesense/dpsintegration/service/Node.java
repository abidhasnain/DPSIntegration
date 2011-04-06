package com.tesense.dpsintegration.service;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class Node {
	private Location location;

	public Node(Double latitude, Double longitude) {
		location = new Location();
		location.setLatitude(new Double(latitude));
		location.setLongitude(new Double(longitude));
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}

package com.tesense.dpsintegration.test;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.Assert;

import com.tesense.dpsintegration.Location;
import com.tesense.dpsintegration.Profile;
import com.tesense.dpsintegration.ProfileController;
import com.tesense.dpsintegration.service.Node;
import com.tesense.dpsintegration.service.Sensor;

public class GetRouteTest {

	@Test
	public void testGetRoute() {
		List<Sensor> sensors = new ArrayList<Sensor>();

		Node node1 = new Node(57.7057, 11.9879);
		sensors.add(new Sensor("sensor1", 50, node1));
		sensors.add(new Sensor("sensor2", 60, node1));
		sensors.add(new Sensor("sensor3", 70, node1));
		
		Node node2 = new Node(57.6986, 11.9703);
		sensors.add(new Sensor("sensor4", 50, node2));
		sensors.add(new Sensor("sensor5", 60, node2));
		sensors.add(new Sensor("sensor6", 70, node2));
		
		Node node3 = new Node(57.7009, 11.9698);
		sensors.add(new Sensor("sensor7", 50, node3));
		sensors.add(new Sensor("sensor8", 60, node3));
		sensors.add(new Sensor("sensor9", 70, node3));
		
		Node node4 = new Node(57.6552,12.0166);
		sensors.add(new Sensor("sensor1", 50, node4));
		sensors.add(new Sensor("sensor2", 60, node4));
		sensors.add(new Sensor("sensor3", 70, node4));
		
		Node node5 = new Node(57.6582,12.0311);
		sensors.add(new Sensor("sensor4", 50, node5));
		sensors.add(new Sensor("sensor5", 60, node5));
		sensors.add(new Sensor("sensor6", 70, node5));
		
		Node node6 = new Node(57.6488,12.0143);
		sensors.add(new Sensor("sensor7", 50, node6));
		sensors.add(new Sensor("sensor8", 60, node6));
		sensors.add(new Sensor("sensor9", 70, node6));
		
		//Profile profile = new Profile("TestProfile", "D:\\Carp\\Workarea\\TestProfile");

		List<Location> coordinates = ProfileController.getRoute(sensors, 50);
		Assert.assertNotNull(coordinates);
		Assert.assertTrue(coordinates.size() != 0);
		for (Location coordinate : coordinates) {
			Assert.assertNotNull(coordinate);
		}
	}

}

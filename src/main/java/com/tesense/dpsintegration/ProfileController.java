package com.tesense.dpsintegration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tesense.dpsintegration.service.NodeDetailForOrder;
import com.tesense.dpsintegration.service.Sensor;
import com.tesenso.dpsintegration.utility.CSVParser;
import com.tesenso.dpsintegration.utility.FileMonitor;
import com.tesenso.dpsintegration.utility.TestFileChangeListener;

@RooWebScaffold(path = "profiles", formBackingObject = Profile.class)
@RequestMapping("/profiles")
@Controller
public class ProfileController {
	@Autowired
	public static Profile profile;
	
	public static final String LOGIX_DPS_PATH = "D:/Carp/logix32/WLI32.EXE";
	public static final String FILE_PARAM = "/f";
	public static final String HIDDEN_PARAM = "/h";
	public static final String SCHED_PARAM = "/s";
	public static final String PROFILE_PARAM = "/x";
	
	public static final String WORKAREA_PATH = "D:/Carp/Workarea";
	public static final String USEROUT_PATH = "/userout.csv";
	public static final String WLI32_PATH = "/wli32.fin";

	public static String generateOrderFile(List<NodeDetailForOrder> nodes) {

		File testOrderFile = null;
		FileWriter writer = null;
		try {
			testOrderFile = new File("TestOrderFile.csv");
			writer = new FileWriter(testOrderFile);

			writer.append("action");
			writer.append(';');
			writer.append("callref");
			writer.append(';');
			writer.append("ordref");
			writer.append(';');
			writer.append("name");
			writer.append(';');
			writer.append("CD");
			writer.append(';');
			writer.append("Prod1");
			writer.append(';');
			writer.append("address1");

			for (NodeDetailForOrder node : nodes) {
				String latitude = node.getNode().getLocation().getLatitude().toString();
				String longitude = node.getNode().getLocation().getLongitude().toString();
				String randomRef = new Integer(nodes.indexOf(node) + 1).toString();
				String containerCount = Integer.toString(node.getContainerCount());

				writer.append('\n');
				writer.append("A"); //Type
				writer.append(';');// Action
				writer.append(randomRef);
				writer.append(';');// Call Reference
				writer.append(randomRef);
				writer.append(';');// Order Reference
				writer.append("Customer Foo");
				writer.append(';');// Customer Name
				writer.append("C");
				writer.append(';');// Type
				writer.append(containerCount);
				writer.append(';');// Container COUNT
				writer.append(latitude + " " + longitude); // Address 1
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return testOrderFile.getAbsolutePath();

		// return null;
	}

	public static List<NodeDetailForOrder> filterSensors(List<Sensor> sensors,
			long threshold) {

		List<NodeDetailForOrder> nodeDetailForOrders = new ArrayList<NodeDetailForOrder>();
		for (Sensor sensor : sensors) {
			if (sensor.getFillLevel() >= threshold) {
				NodeDetailForOrder currentNode = new NodeDetailForOrder(sensor.getNode());
				if (nodeDetailForOrders.contains(currentNode)) {
					int i = nodeDetailForOrders.indexOf(currentNode);
					NodeDetailForOrder newNodeDetailForOrder = nodeDetailForOrders.get(i);
					newNodeDetailForOrder.setContainerCount(newNodeDetailForOrder.getContainerCount() + 1);
				} else {
					NodeDetailForOrder newNodeDetailForOrder = new NodeDetailForOrder();
					newNodeDetailForOrder.setNode(currentNode.getNode());
					newNodeDetailForOrder.setContainerCount(1);
					nodeDetailForOrders.add(newNodeDetailForOrder);
				}
			}
		}
		return nodeDetailForOrders;
	}

	public static List<Location> getLocations(ArrayList<TestFileChangeListener> fileChangeListeners) {

		boolean isFilesChanged = false;
		while (!isFilesChanged) {
			System.out.println("not changed yet");
			isFilesChanged = true;
			for (TestFileChangeListener fileChangeListener : fileChangeListeners) {
				System.out.println(fileChangeListener.isFileChanged());
				isFilesChanged = isFilesChanged	&& fileChangeListener.isFileChanged();
			}
		}
		
		String useroutCompletePath = WORKAREA_PATH+ "/" + profile.getName()+USEROUT_PATH;
		List userOut = null;
		
		try {
			userOut = CSVParser.parseLocation(new File(useroutCompletePath));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		CSVParser.getLocation(userOut);
		
		
	

		return null;
	}

	public static List<Location> getRoute(List<Sensor> sensors, long threshold) {

		profile = new Profile();
		profile.setName("testprofile");
		String orderPath = generateOrderFile(filterSensors(sensors, threshold));
		ArrayList<TestFileChangeListener> fileChangeListeners = generateOutput(orderPath);

		return getLocations(fileChangeListeners);
	}

	private static ArrayList<TestFileChangeListener> generateOutput(String orderPath) {
		//input params to be pass to the logixIE
		String DPSExeInputParameters = 	LOGIX_DPS_PATH+" "+ 						//LogixIE.exe path
										FILE_PARAM+" "+     						// "/f" telling for file next
										orderPath +" "+     						//  order file path
										HIDDEN_PARAM+SCHED_PARAM+PROFILE_PARAM+" "+ // "/h" instruct for hidden work, ""
																					// "/s" instruct to schedule
																					// "/x" tell which profile to excess
										WORKAREA_PATH+ "/" + profile.getName();		// "/x" is followed by profile path
		
		System.out.println(DPSExeInputParameters);
		
		String currProfileUserOutPath = WORKAREA_PATH + "/" + profile.getName() + USEROUT_PATH; 
		String currProfileWli32Path = WORKAREA_PATH + "/" + profile.getName() + WLI32_PATH;
		
		//created to look for change in userout.csv
		FileMonitor monitor = FileMonitor.getInstance();
		TestFileChangeListener userOutChangeListener = new TestFileChangeListener();
		
		TestFileChangeListener wli32ChangeListener = new TestFileChangeListener();
		monitor.addFileChangeListener(userOutChangeListener, currProfileUserOutPath, 1000);
		monitor.addFileChangeListener(wli32ChangeListener, currProfileWli32Path, 1000);
		
		
		ArrayList<TestFileChangeListener> fileChangeListeners = new ArrayList<TestFileChangeListener>();
		fileChangeListeners.add(userOutChangeListener);
		fileChangeListeners.add(wli32ChangeListener);
		try {
			Runtime r = Runtime.getRuntime();
			Process p	 = null;
			p = r.exec(DPSExeInputParameters);
			
		} catch (Exception e) {
			System.out.println("error===" + e.getMessage());
			e.printStackTrace();
		}
		
		
		return fileChangeListeners;
	}
}
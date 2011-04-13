package com.tesense.dpsintegration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tesense.dpsintegration.service.NodeDetailForOrder;
import com.tesense.dpsintegration.service.Sensor;
import com.tesenso.dpsintegration.utility.FileMonitor;
import com.tesenso.dpsintegration.utility.FileMonitor.FileChangeListener;
import com.tesenso.dpsintegration.utility.TestFileChangeListener;

@RooWebScaffold(path = "profiles", formBackingObject = Profile.class)
@RequestMapping("/profiles")
@Controller
public class ProfileController {

	public static String generateOrderFile(List<NodeDetailForOrder> nodes) {

		// String path = new String();
		//
		// HSSFWorkbook workbook = new HSSFWorkbook();
		//
		// HSSFSheet firstSheet = workbook.createSheet();
		//
		// HSSFRow headerRow = firstSheet.createRow(0);
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

			// (headerRow.createCell(9)).setCellValue(new
			// HSSFRichTextString("ADDRESS"));
			// (headerRow.createCell(10)).setCellValue(new
			// HSSFRichTextString("address4"));
			// (headerRow.createCell(5)).setCellValue(new
			// HSSFRichTextString("Customer Info 1"));

			for (NodeDetailForOrder node : nodes) {
				// HSSFRow dataRow =
				// firstSheet.createRow(nodes.indexOf(node)+1);
				String randomRef = new Integer(nodes.indexOf(node)+1)
						.toString();
				String containerCount = Integer.toString(node
						.getContainerCount());
				writer.append('\n');
				writer.append("A");	writer.append(';');// Action
				writer.append(randomRef);writer.append(';');// Call Reference
				writer.append(randomRef);writer.append(';');// Order Reference
				writer.append("Customer Foo");writer.append(';');// Customer Name
				writer.append("C");writer.append(';');// Type
				writer.append(containerCount);writer.append(';');// Container COUNT
				writer.append(node.getNode().getLocation().getLatitude()
						.toString()
						+ " "
						+ node.getNode().getLocation().getLongitude()
								.toString()); // Address 1
				// (dataRow.createCell(5)).setCellValue(new
				// HSSFRichTextString("Customer Info 1")); // Customer Info 1
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

		// try {
		// FileOutputStream fos = null;
		// File file = new File("TestOrderFile.csv");
		//
		//
		//
		//
		//
		// fos = new FileOutputStream(file);
		//
		// workbook.write(fos);
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// if (fos != null) {
		// try {
		// fos.flush();
		// fos.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// //*/

		//System.out.println(testOrderFile.getAbsolutePath());
		return testOrderFile.getAbsolutePath();

		// return null;
	}

	public static List<NodeDetailForOrder> filterSensors(List<Sensor> sensors,
			long threshold) {

		List<NodeDetailForOrder> nodeDetailForOrders = new ArrayList<NodeDetailForOrder>();
		for (Sensor sensor : sensors) {
			if (sensor.getFillLevel() >= threshold) {
				NodeDetailForOrder currentNode = new NodeDetailForOrder(
						sensor.getNode());
				if (nodeDetailForOrders.contains(currentNode)) {
					int i = nodeDetailForOrders.indexOf(currentNode);
					NodeDetailForOrder newNodeDetailForOrder = nodeDetailForOrders
							.get(i);
					newNodeDetailForOrder
							.setContainerCount(newNodeDetailForOrder
									.getContainerCount() + 1);
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
	
	public static List<Location> getLocations(String path) {
		FileMonitor monitor = FileMonitor.getInstance();
		TestFileChangeListener fileChangeListener = new TestFileChangeListener();
		monitor.addFileChangeListener(fileChangeListener, path, 1000);
		
		while(!fileChangeListener.isFileChanged()){
			System.out.println("not changed yet");
		}
		
		monitor.
		return null;
	}
	

	public static List<Location> getRoute(List<Sensor> sensors, long threshold) {

		String orderPath = generateOrderFile(filterSensors(sensors, threshold));
		String outputPath = generateOutput(orderPath);
		
		

		return getLocations(outputPath);
	}

	private static String generateOutput(String orderPath) {
		String DPSExeInputParameters = generateDPSInputParameters(orderPath);
		System.out.println(DPSExeInputParameters);
		try {
			Runtime r = Runtime.getRuntime();
			Process p = null;
			p = r.exec(DPSExeInputParameters);
		} catch (Exception e) {
			System.out.println("error===" + e.getMessage());
			e.printStackTrace();
		}
		// check .fin file, if contains errors then handle it accordingly
		return "D:\\Carp\\Workarea\\TestProfile\\userout.csv";
	}

	private static String generateDPSInputParameters(String orderPath) {

		// below variables should be members of some class so that profile path
		// variable could be accessed later on in order to locate the generated
		// userout.csv
		String LogixExePath = "\"D:\\Carp\\logix32\\WLI32.EXE\""; // Future: set
																	// it in
																	// some
																	// global
																	// configuration
																	// later and
																	// read from
																	// there.
		String importOrders = " /f \"" + orderPath + "\""; // Future: implement
															// better mechanism
															// of adding /f
															// before order file
															// path, read from
															// some place global
															// as above
		String miscParameters = " /h /s /x "; // Future: same as above, build a
												// string. Mind the space in the
												// beginning and the end!
		String profilePath = "\"D:\\Carp\\Workarea\\TESTPROFILE\""; //
		// Future: set it in some global configuration later and read from
		// there.

		return LogixExePath + importOrders + miscParameters
				+ profilePath;
	}
}
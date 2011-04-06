package com.tesense.dpsintegration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tesense.dpsintegration.service.NodeDetailForOrder;
import com.tesense.dpsintegration.service.Sensor;

@RooWebScaffold(path = "profiles", formBackingObject = Profile.class)
@RequestMapping("/profiles")
@Controller
public class ProfileController {

	public static String generateOrderFile(List<NodeDetailForOrder> nodes) {
		
		String path = new String();
		
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet firstSheet = workbook.createSheet();

		HSSFRow headerRow = firstSheet.createRow(0);
		(headerRow.createCell(0)).setCellValue(new HSSFRichTextString("Type"));
		(headerRow.createCell(1)).setCellValue(new HSSFRichTextString("Action"));
		(headerRow.createCell(2)).setCellValue(new HSSFRichTextString("Refrence"));
		(headerRow.createCell(3)).setCellValue(new HSSFRichTextString("Customer Name"));
		(headerRow.createCell(4)).setCellValue(new HSSFRichTextString("Address 1"));
		(headerRow.createCell(5)).setCellValue(new HSSFRichTextString("Customer Info 1"));
		(headerRow.createCell(6)).setCellValue(new HSSFRichTextString("Container"));


		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("CreateExcelDemo.xls"));
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "/CreateExcelDemo.xls";
	}
	
	public static List<NodeDetailForOrder> filterSensors(List<Sensor> sensors, long threshold) {
		
		List<NodeDetailForOrder> nodeDetailForOrders = new ArrayList<NodeDetailForOrder>();
		for (Sensor sensor : sensors) {
			if(sensor.getFillLevel() >= threshold){
				NodeDetailForOrder currentNode = new NodeDetailForOrder(sensor.getNode());
				if(nodeDetailForOrders.contains(currentNode)){
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
	
	public static List<Location> getRoute(List<Sensor> sensors, long threshold) {
		String orderPasth = generateOrderFile(filterSensors(sensors, threshold));
		
		
		return null;
	}
}
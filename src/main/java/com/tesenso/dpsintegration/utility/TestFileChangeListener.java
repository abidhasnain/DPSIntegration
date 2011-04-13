package com.tesenso.dpsintegration.utility;

import com.tesenso.dpsintegration.utility.FileMonitor.FileChangeListener;

public class TestFileChangeListener implements FileChangeListener {

	private boolean isFileChanged;
	
	public TestFileChangeListener() {
		isFileChanged = false;
	}
	@Override
	public void fileChanged(String fileName) {
		isFileChanged = true;
		 System.out.println ("File changed: " + fileName);
	}
	public boolean isFileChanged() {
		return isFileChanged;
	}	
}

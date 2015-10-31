package com.social.upload;

import java.io.File;

import org.apache.commons.fileupload.disk.DiskFileItem;

public class CustomFileItem extends DiskFileItem {

	public CustomFileItem(String fieldName, String contentType,
			boolean isFormField, String fileName, int sizeThreshold,
			File repository) {
		super(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);
	}
	
	@Override
	public File getTempFile() {
		return super.getTempFile();
	}
	
	

}

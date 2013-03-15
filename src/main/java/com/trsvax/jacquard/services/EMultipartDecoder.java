package com.trsvax.jacquard.services;

import java.util.List;

import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;

public interface EMultipartDecoder extends MultipartDecoder {
	public List<UploadedFile> getFileUploads(String parameterName);
}

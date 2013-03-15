// Copyright 2007, 2008, 2010, 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.trsvax.jacquard.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;
import org.apache.tapestry5.upload.internal.services.ParametersServletRequestWrapper;
import org.apache.tapestry5.upload.internal.services.UploadedFileItem;
import org.apache.tapestry5.upload.services.UploadSymbols;
import org.apache.tapestry5.upload.services.UploadedFile;


/**
 * Implementation of multipart decoder for servlets. This implementation is perthread scope.
 */
public class EMultipartDecoderImpl implements EMultipartDecoder, ThreadCleanupListener
{
    private final Map<String, List<UploadedFileItem>> uploads = CollectionFactory.newMap();

    private final FileItemFactory fileItemFactory;

    private final long maxRequestSize;

    private final long maxFileSize;

    private final String requestEncoding;

    private FileUploadException uploadException;

    public EMultipartDecoderImpl(

            FileItemFactory fileItemFactory,

            @Symbol(UploadSymbols.REQUESTSIZE_MAX)
            long maxRequestSize,

            @Symbol(UploadSymbols.FILESIZE_MAX)
            long maxFileSize,

            @Symbol(SymbolConstants.CHARSET)
            String requestEncoding)
    {
        this.fileItemFactory = fileItemFactory;
        this.maxRequestSize = maxRequestSize;
        this.maxFileSize = maxFileSize;
        this.requestEncoding = requestEncoding;
    }

    public UploadedFile getFileUpload(String parameterName)
    {
    	UploadedFile file = null;
    	if ( uploads.containsKey(parameterName)) {
    		if ( uploads.get(parameterName) != null && uploads.get(parameterName).size() > 0 ) {
    			file = uploads.get(parameterName).get(0);
    		}
    	}
        return file;
    }
    
    public List<UploadedFile> getFileUploads(String parameterName)
    {
    	List files = uploads.get(parameterName);
        return files;
    }

    public HttpServletRequest decode(HttpServletRequest request)
    {
        try
        {
            request.setCharacterEncoding(requestEncoding);
        } catch (UnsupportedEncodingException ex)
        {
            throw new RuntimeException(ex);
        }

        List<FileItem> fileItems = parseRequest(request);

        return processFileItems(request, fileItems);
    }

    public void threadDidCleanup()
    {
        for ( List<UploadedFileItem> items : uploads.values())
        {
        	for ( UploadedFileItem item : items ) 
        	{
                item.cleanup();
        	}
        }
    }

    @SuppressWarnings("unchecked")
    protected List<FileItem> parseRequest(HttpServletRequest request)
    {
        try
        {
            return createFileUpload().parseRequest(request);
        } catch (FileUploadException ex)
        {
            uploadException = ex;

            return Collections.emptyList();
        }
    }

    protected ServletFileUpload createFileUpload()
    {
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);

        // set maximum file upload size
        upload.setSizeMax(maxRequestSize);
        upload.setFileSizeMax(maxFileSize);

        return upload;
    }

    protected HttpServletRequest processFileItems(HttpServletRequest request, List<FileItem> fileItems)
    {
        if (uploadException == null && fileItems.isEmpty())
        {
            return request;
        }

        ParametersServletRequestWrapper wrapper = new ParametersServletRequestWrapper(request);

        // First add parameters from the request
        for (Object e : request.getParameterMap().entrySet())
        {
            Map.Entry<String, String[]> ee = (Map.Entry<String, String[]>) e;
            for (String s : ee.getValue())
                wrapper.addParameter(ee.getKey(), s);
        }

        for (FileItem item : fileItems)
        {
            if (item.isFormField())
            {
                String fieldValue;

                try
                {

                    fieldValue = item.getString(requestEncoding);
                } catch (UnsupportedEncodingException ex)
                {
                    throw new RuntimeException(ex);
                }

                wrapper.addParameter(item.getFieldName(), fieldValue);
            } else
            {
                wrapper.addParameter(item.getFieldName(), item.getName());
                addUploadedFile(item.getFieldName(), new UploadedFileItem(item));
            }
        }

        return wrapper;
    }

    protected void addUploadedFile(String name, UploadedFileItem file)
    {
    	if ( ! uploads.containsKey(name)) {
    		uploads.put(name, new ArrayList<UploadedFileItem>());
    	}
        uploads.get(name).add(file);
    }

    public FileUploadException getUploadException()
    {
        return uploadException;
    }
}

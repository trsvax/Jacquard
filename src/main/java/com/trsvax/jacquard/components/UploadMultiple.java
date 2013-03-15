// Copyright 2007, 2008, 2009, 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.trsvax.jacquard.components;

import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.FieldValidationSupport;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.corelib.mixins.RenderDisabled;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FieldValidatorDefaultSource;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.trsvax.jacquard.services.EMultipartDecoder;

/**
 * A component to upload a file.
 */
@SuppressWarnings({"UnusedDeclaration"})
@Events(EventConstants.VALIDATE)
public class UploadMultiple extends AbstractField
{
    public static final String MULTIPART_ENCTYPE = "multipart/form-data";

    /**
     * The uploaded file. Note: This is only guaranteed to be valid while processing the form submission. Subsequently
     * the content may have been cleaned up.
     */
    @Parameter(required = true, principal = true, autoconnect = true)
    private List<UploadedFile> value;

    /**
     * The object that will perform input validation. The "validate:" binding prefix is generally used to provide this
     * object in a declarative fashion.
     */
    @Parameter(defaultPrefix = BindingConstants.VALIDATE)
    @SuppressWarnings("unchecked")
    private FieldValidator<Object> validate;

    @Environmental
    private ValidationTracker tracker;

    @Inject
    private EMultipartDecoder decoder;

    @Environmental
    private FormSupport formSupport;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    @Inject
    private ComponentResources resources;

    @Inject
    private Locale locale;

    @Inject
    private FieldValidationSupport fieldValidationSupport;

    @SuppressWarnings("unused")
    @Mixin
    private RenderDisabled renderDisabled;

    @Inject
    @Path("classpath:/org/apache/tapestry5/upload/components/upload.js")
    private Asset uploadScript;

    @Inject
    private Request request;

    @Environmental
    private JavaScriptSupport javaScriptSupport;

    /**
     * Computes a default value for the "validate" parameter using {@link FieldValidatorDefaultSource}.
     */
    final Binding defaultValidate()
    {
        return defaultProvider.defaultValidatorBinding("value", resources);
    }

    public UploadMultiple()
    {
    }

    // For testing
    UploadMultiple(List<UploadedFile> value, FieldValidator<Object> validate, EMultipartDecoder decoder, ValidationTracker tracker,
           ComponentResources resources, FieldValidationSupport fieldValidationSupport)
    {
        this.value = value;
        if (validate != null) this.validate = validate;
        this.decoder = decoder;
        this.tracker = tracker;
        this.resources = resources;
        this.fieldValidationSupport = fieldValidationSupport;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    protected void processSubmission(String controlName)
    {
        List <UploadedFile> uploads = decoder.getFileUploads(controlName);

        for ( UploadedFile uploaded : uploads ) {
	        if (uploaded != null)
	        {
	            if (uploaded.getFileName() == null || uploaded.getFileName().length() == 0) uploaded = null;
	        }
	
	        try
	        {
	            fieldValidationSupport.validate(uploaded, resources, validate);
	        } catch (ValidationException ex)
	        {
	            tracker.recordError(this, ex.getMessage());
	        }
        }

        value = uploads;
    }

    /**
     * Render the upload tags.
     *
     * @param writer Writer to output markup
     */
    protected void beginRender(MarkupWriter writer)
    {
        formSupport.setEncodingType(MULTIPART_ENCTYPE);

        writer.element("input", "type", "file", "name", getControlName(), "id", getClientId());

        validate.render(writer);

        resources.renderInformalParameters(writer);

        decorateInsideField();

        // TAPESTRY-2453
        if (request.isXHR())
        {
            javaScriptSupport.importJavaScriptLibrary(uploadScript);
            javaScriptSupport.addInitializerCall("injectedUpload", getClientId());
        }
    }

    public void afterRender(MarkupWriter writer)
    {
        writer.end();
    }

    public List<UploadedFile> getValue()
    {
        return value;
    }

    UploadMultiple injectDecorator(ValidationDecorator decorator)
    {
        setDecorator(decorator);

        return this;
    }

    UploadMultiple injectRequest(Request request)
    {
        this.request = request;

        return this;
    }

    UploadMultiple injectFormSupport(FormSupport formSupport)
    {
        // We have our copy ...
        this.formSupport = formSupport;

        // As does AbstractField
        setFormSupport(formSupport);

        return this;
    }

    UploadMultiple injectFieldValidator(FieldValidator<Object> validator)
    {
        this.validate = validator;

        return this;
    }
}

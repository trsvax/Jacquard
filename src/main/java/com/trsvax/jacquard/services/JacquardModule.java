package com.trsvax.jacquard.services;

import java.util.Calendar;
import java.util.Date;

import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.TranslatorSource;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.javascript.ExtensibleJavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptModuleConfiguration;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.apache.tapestry5.services.javascript.ModuleManager;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.InjectionProvider2;

import com.trsvax.jacquard.anotations.JacquardJavaScriptStack;
import com.trsvax.jacquard.services.translators.DateTranslator;
import com.trsvax.jacquard.services.worker.JSR303PassivateWorker;
import com.trsvax.jacquard.services.worker.JSR303Worker;

public class JacquardModule {
	
    @SuppressWarnings("unchecked")
	public static void bind(ServiceBinder binder) {
        binder.bind(JavaScriptStack.class, ExtensibleJavaScriptStack.class).withMarker(JacquardJavaScriptStack.class).withId("JacquardJavaScriptStack");
    	binder.bind(WebService.class,WebServiceImpl.class);

    }
    
	public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("jq", "com.trsvax.jacquard"));
	}
	
	public static void contributeBindingSource(
			MappedConfiguration<String, BindingFactory> configuration) {
		configuration.addInstance("page", PageBindingFactory.class);

	}
	
	@Contribute(TranslatorSource.class)
	public static void contributeTranslatorSource(
			@SuppressWarnings("rawtypes") MappedConfiguration<Class, Translator> configuration,
			@Autobuild DateTranslator.Builder builder){
		
		DateTranslator dateTranslator = builder.setName("Date").setType(Date.class).build();	
		configuration.add(dateTranslator.getType(), dateTranslator.addAttribute("placeholder", "mm/dd/yyyy"));
		
		DateTranslator calendarTranslator = builder.setType(Calendar.class).setName("calendar").build();
		configuration.add(calendarTranslator.getType(),calendarTranslator);
	
	}
	
    @Contribute(ModuleManager.class)
    public static void setupModules(MappedConfiguration<String, Object> configuration,
                                        @Path("/META-INF/resources/webjars/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js")
                                        Resource datepicker) {
    	configuration.add("webjars/datepicker", new JavaScriptModuleConfiguration(datepicker));
    }
    
    @Contribute(JavaScriptStack.class)
    @JacquardJavaScriptStack
    public static void setupCoreJavaScriptStack(OrderedConfiguration<StackExtension> configuration) {
    		configuration.add("webjars/datepicker", StackExtension.module("webjars/datepicker"));
    		configuration.add("webjars/datepickercss",StackExtension.stylesheet("/META-INF/resources/webjars/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.css"));
    	
    }
    
    @Contribute(JavaScriptStackSource.class)
    public static void provideBuiltinJavaScriptStacks(MappedConfiguration<String, JavaScriptStack> configuration,
                                                      @JacquardJavaScriptStack JavaScriptStack jacquardJavaScriptStack)
    {
        configuration.add("JacquardJavaScriptStack", jacquardJavaScriptStack);
    }
    
    
    
    public static void contributeValueEncoderSource(
            MappedConfiguration<Class<?>, ValueEncoderFactory<?>> configuration)
    {
        configuration.add(Date.class, new ValueEncoderFactory<Date>() {

			@Override
			public ValueEncoder<Date> create(Class<Date> type) {
				return new ValueEncoder<Date>() {

					@Override
					public String toClient(Date value) {
						return String.valueOf(value.getTime());
					}

					@Override
					public Date toValue(String clientValue) {
						return new Date(new Long(clientValue));
					}
				};
			}
		});
        
		
    }
    
 
    
	@Contribute(ComponentClassTransformWorker2.class)
	public static void provideCommitAfterAnnotationSupport(OrderedConfiguration<ComponentClassTransformWorker2> configuration) {
			configuration.addInstance("JSR303Worker", JSR303Worker.class, "after:*");
			configuration.addInstance("JSR303PassivateWorker", JSR303PassivateWorker.class, "before:PageActivationContext");
	}
	
   @Contribute(InjectionProvider2.class)
    public static void provideStandardInjectionProviders(OrderedConfiguration<InjectionProvider2> configuration, 
    		SymbolSource symbolSource, AssetSource assetSource)
    {
        configuration.addInstance("Page", PageInjectionProvider.class);
 
    }


}

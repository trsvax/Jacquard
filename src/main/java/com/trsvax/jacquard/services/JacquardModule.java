package com.trsvax.jacquard.services;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.FileCleaner;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.ioc.services.ServiceOverride;
import org.apache.tapestry5.services.BeanBlockContribution;
import org.apache.tapestry5.services.BeanBlockSource;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.EditBlockContribution;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.LinkCreationHub;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.upload.services.MultipartDecoder;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class JacquardModule
{
	private static final AtomicBoolean needToAddShutdownListener = new AtomicBoolean(true);
	
    public static void bind(ServiceBinder binder) {
		binder.bind(BindingFactory.class,PagerBindingFactory.class).withId("PagerBindingFactory");
		binder.bind(BindingFactory.class,CellBindingFactory.class).withId("CellBindingFactory");
		binder.bind(InitOnce.class,InitOnceImpl.class);
		binder.bind(EnvSetup.class, EnvSetupImpl.class);
        binder.bind(UserContentService.class,UserContentImpl.class);
		binder.bind(Monitor.class,MonitorOK.class).withId("MonitorOK");
		binder.bind(URLStatePersistenceStrategy.class,URLStatePersistenceStrategyImpl.class);
		binder.bind(MultiLinkHub.class);
    }
    		
   public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration,
			final EnvSetup environmentSetup, final Environment environment, final InitOnce initOnce) {

		MarkupRendererFilter environmentFilter = new MarkupRendererFilter() {		
			public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer) {
				environmentSetup.push();
				renderer.renderMarkup(writer);				
				environmentSetup.pop();
			}		
		};
		MarkupRendererFilter initOnceFilter = new MarkupRendererFilter() {		
			public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer) {
				renderer.renderMarkup(writer);				
				JavaScriptSupport javaScriptSupport = environment.peekRequired(JavaScriptSupport.class);
				for ( String script : initOnce.getScripts() ) {
					javaScriptSupport.addScript(script);
				}
			}		
		};
		configuration.add("EnvironmentFilter", environmentFilter,"after:JavaScriptSupport");
		configuration.add("InitOnceFilter", initOnceFilter,"after:JavaScriptSupport");
	}
   
   public void contributePersistentFieldManager(MappedConfiguration<String, PersistentFieldStrategy> configuration,
		   LinkCreationHub linkCreationHub,
		   URLStatePersistenceStrategy strategy) {
	   configuration.add("url",strategy);
   }
   
	@Contribute(ServiceOverride.class)
	public static void setupApplicationServiceOverrides(MappedConfiguration<Class,Object> configuration, 
			@Local EMultipartDecoder multipartDecoder) {
		configuration.add(MultipartDecoder.class, multipartDecoder);
	}
    
    
	@Contribute(ComponentClassTransformWorker2.class)   
	public static void  provideWorkers(OrderedConfiguration<ComponentClassTransformWorker2> workers) {	
		workers.addInstance("AddMixinWorker", AddMixinWorker.class);
		workers.addInstance("JQuerySubscribeWorker", JQuerySubscribeWorker.class);
		workers.overrideInstance("PageActivationContext", PageActivationContextWorkerOverride.class);
	}
	
	public static void contributeBindingSource(MappedConfiguration<String, BindingFactory> configuration,
    		@InjectService("PagerBindingFactory") BindingFactory pagerBindingFactory,
    		@InjectService("CellBindingFactory") BindingFactory cellBindingFactory
    		) {
        configuration.add("paged", pagerBindingFactory);
        configuration.add("cell",cellBindingFactory);

    }
	
	public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("tj", "com.trsvax.jacquard"));
	}
	
	@Scope(ScopeConstants.PERTHREAD)
    public static EMultipartDecoder buildEMultipartDecoder(PerthreadManager perthreadManager,

                                                         RegistryShutdownHub shutdownHub,

                                                         @Autobuild
                                                         EMultipartDecoderImpl multipartDecoder)
    {
        // This is proabably overkill since the FileCleaner should catch temporary files, but lets
        // be safe.
        perthreadManager.addThreadCleanupListener(multipartDecoder);

        if (needToAddShutdownListener.getAndSet(false))
        {
            shutdownHub.addRegistryShutdownListener(new Runnable()
            {
                public void run()
                {
                    FileCleaner.exitWhenFinished();
                }
            });
        }

        return multipartDecoder;
    }
	
	
	public static void contributeTypeCoercer(Configuration<CoercionTuple> configuration) {
		configuration.add( CoercionTuple.create(Long.class, Date.class, new Coercion<Long, Date>() {

			@Override
			public Date coerce(Long value) {
				return new Date(value);
			}
		}));
		configuration.add( CoercionTuple.create(Date.class, Long.class, new Coercion<Date, Long>() {

			@Override
			public Long coerce(Date value) {
				return new Long(value.getTime());
			}
		}));
		configuration.add( CoercionTuple.create(Date.class, String.class, new Coercion<Date, String>() {

			@Override
			public String coerce(Date value) {
				return new Long(value.getTime()).toString();
			}
		}));
	}
	
	
	@Contribute(BeanBlockSource.class)
	public static void provideDefaultBeanBlocks(Configuration<BeanBlockContribution> configuration) {
		addEditBlock(configuration, "datetime");
		addEditBlock(configuration, "html");
	}
	
	private static void addEditBlock(Configuration<BeanBlockContribution> configuration, String dataType) {
		addEditBlock(configuration, dataType, dataType);
	}
	
	private static void addEditBlock(Configuration<BeanBlockContribution> configuration, String dataType, String blockId) {
		configuration.add(new EditBlockContribution(dataType, "tj/JacquardPropertyEditBlocks", blockId));
	}
	
}

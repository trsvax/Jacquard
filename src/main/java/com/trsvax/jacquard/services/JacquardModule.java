package com.trsvax.jacquard.services;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.LinkCreationHub;
import org.apache.tapestry5.services.LinkCreationListener;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class JacquardModule
{
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
    
    
	@Contribute(ComponentClassTransformWorker2.class)   
	public static void  provideWorkers(OrderedConfiguration<ComponentClassTransformWorker2> workers) {	
		workers.addInstance("AddMixinWorker", AddMixinWorker.class);
		workers.addInstance("JQuerySubscribeWorker", JQuerySubscribeWorker.class);
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
}

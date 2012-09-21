package com.trsvax.jacquard.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import com.trsvax.jacquard.annotations.Subscribe;

public abstract class AbstractSubscribeWorker  implements ComponentClassTransformWorker2 {
	private final JavaScriptSupport javaScriptSupport;
	
	public AbstractSubscribeWorker(JavaScriptSupport javaScriptSupport) {
		this.javaScriptSupport = javaScriptSupport;
	}

	public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
		List<PlasticMethod> methods = plasticClass.getMethodsWithAnnotation(Subscribe.class);
		if ( methods != null && methods.size() > 0 ) {
			Map<String, Subscribe> events = new HashMap<String, Subscribe>();
			for ( PlasticMethod method : methods ) {
				Subscribe subscribe = method.getAnnotation(Subscribe.class);
				if ( subscribe.type().isAssignableFrom(this.getClass())) {
					OnEvent event = method.getAnnotation(OnEvent.class);
					String name = method.getMethodIdentifier().substring(
							method.getMethodIdentifier().lastIndexOf(".")+3).replaceAll("\\(\\)", "").toLowerCase();
					if ( event != null ) {
						name = event.value();
					}
					if ( subscribe.event() != null && subscribe.event().length() > 0 ) {
						//name = subscribe.event();
					}
					events.put(name,subscribe);
				}
			}
			if ( events.size() > 0 ) {
				subscribe(events, plasticClass.introduceMethod(TransformConstants.AFTER_RENDER_DESCRIPTION));
				model.addRenderPhase(AfterRender.class);
			}
		}
	}

	public void subscribe(final Map<String, Subscribe> events, final PlasticMethod method) {
		method.addAdvice( new MethodAdvice() {
			
			public void advise(MethodInvocation invocation) {
				ComponentResources resources = invocation.getInstanceContext().get(ComponentResources.class);

				for (  Entry<String, Subscribe> entry: events.entrySet() ) {
					String name = entry.getKey();
					Subscribe subscribe = entry.getValue();
					String url = resources.createEventLink(name).toURI();
					javaScriptSupport.addScript(script(name, url, subscribe));
				}
				invocation.proceed();
				
			}
		});		
	}
	
	public abstract String script(String name, String url, Subscribe subscribe);

}

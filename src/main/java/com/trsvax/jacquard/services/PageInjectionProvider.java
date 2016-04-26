package com.trsvax.jacquard.services;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.internal.transform.ReadOnlyComponentFieldConduit;
import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.InstanceContext;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.transform.InjectionProvider2;

public class PageInjectionProvider implements InjectionProvider2 {
	
    private final ComponentSource componentSource;

    private final ComponentClassResolver resolver;

    private final PerthreadManager perThreadManager;

    public PageInjectionProvider(ComponentSource componentSource, ComponentClassResolver resolver, PerthreadManager perThreadManager)
    {
        this.componentSource = componentSource;
        this.resolver = resolver;
        this.perThreadManager = perThreadManager;
    }

	@Override
	public boolean provideInjection(PlasticField field, ObjectLocator locator, MutableComponentModel componentModel) {
		return addInjectedPage(field);
		//return true;
	}

	
	private boolean addInjectedPage(PlasticField field)
    {
       // InjectPage annotation = field.getAnnotation(InjectPage.class);

        //field.claim(annotation);

       // String pageName = annotation.value();

        String fieldName = field.getName();

        try {
        	String injectedPageName =  resolver.resolvePageClassNameToPageName(field.getTypeName());
            field.setConduit(new InjectedPageConduit(field.getPlasticClass().getClassName(), fieldName, injectedPageName));
            return true;
        } catch (IllegalArgumentException e) {
        	return false;
        }

    }
	
	private final class InjectedPageConduit extends ReadOnlyComponentFieldConduit
    {
        private final String injectedPageName;

        private final ObjectCreator<Object> pageValue = perThreadManager.createValue(new ObjectCreator<Object>() {
            @Override
            public Object createObject() {
                return componentSource.getPage(injectedPageName);
            }
        });

        private InjectedPageConduit(String className, String fieldName,
                                    String injectedPageName)
        {
            super(className, fieldName);

            this.injectedPageName = injectedPageName;
        }

        public Object get(Object instance, InstanceContext context)
        {
            return pageValue.createObject();
        }
    }
}

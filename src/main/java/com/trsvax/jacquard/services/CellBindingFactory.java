package com.trsvax.jacquard.services;

import java.lang.annotation.Annotation;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.Environment;

import com.trsvax.jacquard.environment.GridCellEnviroment;

public class CellBindingFactory implements BindingFactory {
	public final Environment environment;
	
	public CellBindingFactory(Environment environment) {
		this.environment = environment;
	}

	@Override
	public Binding newBinding(String description, ComponentResources container,
			ComponentResources component, String expression, Location location) {
		return new CellBinding(description,container,component,expression,location);
	}
	
	
	
	public class CellBinding implements Binding {
		private final String expression;
		

		public CellBinding(String description, ComponentResources container,
				ComponentResources component, String expression,
				Location location) {
			this.expression = expression;
		}

		@Override
		public <T extends Annotation> T getAnnotation(Class<T> annotation) {
			GridCellEnviroment gridCellEnviroment = environment.peekRequired(GridCellEnviroment.class);
			return gridCellEnviroment.model().getAnnotation(annotation);
		}

		@Override
		public Object get() {
			GridCellEnviroment gridCellEnviroment = environment.peekRequired(GridCellEnviroment.class);
			PropertyModel model = gridCellEnviroment.model();
			String expression = this.expression;
			if ( expression.contains(".")) {
				String[] args = expression.split("\\.");
				model = model.model().get(args[0]);
				expression = args[1];
			}
			if ( expression == null || expression.length() == 0 || expression.equals("value")) {
				assert(model != null );
				return model.getConduit().get(gridCellEnviroment.object());
			}
			if ( expression.equals("object")) {
				return gridCellEnviroment.object();
			}
			if ( expression.equals("datatype")) {
				return model.getDataType();
			}
			if ( expression.equals("simplename")) {
				return model.getConduit().getPropertyType().getSimpleName();
			}
			return null;
		}

		@Override
		public Class<?> getBindingType() {	
			return get() == null ? null : get().getClass();		
		}

		@Override
		public boolean isInvariant() {
			return false;
		}

		@Override
		public void set(Object object) {
			//can't do this
		}

	}


}

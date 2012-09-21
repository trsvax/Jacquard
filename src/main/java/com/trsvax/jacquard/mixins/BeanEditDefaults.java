package com.trsvax.jacquard.mixins;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Visitor;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.slf4j.Logger;

import com.trsvax.jacquard.annotations.BeanEditEnviromentInterface;
import com.trsvax.jacquard.environment.BeanEditEnvironment;

public class BeanEditDefaults {

	@BindParameter
	private String reorder;

	@BindParameter
	private String submitLabel;

	@Inject
	private Environment environment;

	@Inject
	private ComponentResources resources;

	@Inject
	private Logger logger;

	private Element form;

	private String newLabel;

	@SetupRender
	@SuppressWarnings("unchecked")
	void setupRender(MarkupWriter writer) {
		String defaultLabel = resources.getContainerResources().getMessages().get("submit-label");
		logger.info("default {}", defaultLabel);

		if (submitLabel != null && submitLabel.equals(defaultLabel)) {
			form = writer.element("mixing", "name", this.getClass().getName());
			String type = resources.getContainerResources().getBoundType("object").getSimpleName();
			newLabel = resources.getPage().getComponentResources().getMessages().format("submit-label", type);
			//environment.peek(LayoutEnvironment.class).setTitle(newLabel);
		}

		Class<Object> beantype = resources.getContainerResources().getBoundType("object");
		BeanEditEnviromentInterface enviromentType = (BeanEditEnviromentInterface) beantype.getAnnotation(BeanEditEnviromentInterface.class);
		if (enviromentType != null) {
			BeanEditEnvironment defaults = environment.peek(enviromentType.value());
			if (defaults != null) {
				if (reorder == null) {
					reorder = defaults.getOrder();
				}
			}
		}

	}

	@AfterRender
	void afterRender(MarkupWriter writer) {
		if (form != null) {
			writer.end();
			form.visit(new Visitor() {

				public void visit(Element element) {
					if (element.getName().equals("input")) {
						String value = element.getAttribute("value");
						String type = element.getAttribute("type");
						if (type != null && type.equals("submit")
								&& value != null && value.equals(submitLabel)) {
							element.forceAttributes("value", newLabel);
						}
					}

				}
			});
		}
	}

	@CleanupRender
	void cleanupRender() {
		if (form != null) {
			form.pop();
		}
	}

}

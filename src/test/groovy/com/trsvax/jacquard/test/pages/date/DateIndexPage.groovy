package com.trsvax.jacquard.test.pages.date

import geb.Page;

class DateIndexPage extends Page {
	static url = "date";
	
	static content = {
		heading { $("h1").text() }
	}
	

	

}

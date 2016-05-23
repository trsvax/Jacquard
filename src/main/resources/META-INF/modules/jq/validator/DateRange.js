(function() {
  define(["t5/core/dom", "t5/core/events"], function(dom, events) {
	  	  
	  addErrors = function (form,error) {
		  $(form).before(
	        '<div class="alert alert-danger alert-dismissable">'+
	            '<button type="button" class="close" ' + 
	                    'data-dismiss="alert" aria-hidden="true">' + 
	                '&times;' + 
	            '</button>' + 
	            error +
	         '</div>');		  
	  }
	  
	    dom.onDocument(events.form.validate, function(event, memo) {
	    	var constraints = JSON.parse(event.nativeEvent.target.dataset.daterangeJson).constraints;
	    	
	    	for ( var i = 0; i < constraints.length; i++ ) {
	    		var constraint = constraints[i];
	    		
		    	var startDate = new Date(document.getElementById(constraint.start).value);
		    	var endDate = new Date(document.getElementById(constraint.end).value);
		    	
		    	if ( startDate == null || endDate == null ) {
		    		return;
		    	}
		    	if ( ! startDate < endDate ) {
		    		memo.error = true;
		    		addErrors(event.nativeEvent.target, constraint.message);
		    	}
	    	}	    	
	   });
  });
}).call(this);
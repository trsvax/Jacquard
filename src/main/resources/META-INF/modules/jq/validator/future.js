(function() {
  define(["t5/core/dom", "t5/core/events"], function(dom, events) {
	  	  
	  
	    dom.onDocument(events.field.validate, "[data-validate-future]", function(event, memo) {
	        var today = new Date();
	        if ( new Date(memo.translated) < today) {
	          memo.error = (this.attr("data-validate-future")) || "IN THE PAST";
	          return false;
	        }
	      });
	    
  });
}).call(this);
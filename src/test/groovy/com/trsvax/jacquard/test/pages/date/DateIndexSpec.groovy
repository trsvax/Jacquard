package com.trsvax.jacquard.test.pages.date

import geb.spock.GebReportingSpec

import org.apache.tapestry5.test.Jetty7Runner

import spock.lang.Shared

class DateIndexSpec extends GebReportingSpec {
	@Shared
	def runner;

	def setupSpec() {
			runner = new Jetty7Runner("src/test/webapp", "/", 8080, 8081);
			runner.start()
	}

	def cleanupSpec() {
			runner.stop()
	}
	
	def "Page Test" () {
		when: to DateIndexPage;
		
		then:
			heading == "Date"
	}

}

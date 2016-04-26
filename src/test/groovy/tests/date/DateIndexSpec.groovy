package tests.date

import geb.spock.GebSpec

import org.apache.tapestry5.test.Jetty7Runner

import spock.lang.Shared

import pages.date.DateIndexPage

class DateIndexSpec extends GebSpec {
	
	@Shared
	def runner;

    def setup() {
        runner = new Jetty7Runner("src/test/webapp", "/jacquard", 8080, 8081);
                runner.start()
    }

    def cleanup() {
        runner.stop()
    }

    def "Date Index"() {
        when:
        to DateIndexPage


        then:
        sectionTitles == ["Date"]
    }
}
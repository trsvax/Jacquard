package pages.date

import geb.Page



class DateIndexPage extends Page {
    static url = "date" 

    static at = { $("h1").text() == "Date" } 

    static content = {
        sectionTitles { $("h1")*.text() } 
    }
}
package grails.plugins.swaggerdoc

import grails.plugins.swaggerdoc.SwaggerDataService

class ApiController {

    SwaggerDataService swaggerDataService

    def index(){

    }

    def resources() {
        String swaggerDocument = null
        try {
            swaggerDocument = swaggerDataService.generateSwaggerDocument()
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/json")
        render swaggerDocument
    }
}

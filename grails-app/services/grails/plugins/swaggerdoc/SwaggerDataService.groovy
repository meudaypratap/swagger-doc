package grails.plugins.swaggerdoc

import com.fasterxml.jackson.core.JsonProcessingException
import grails.core.GrailsApplication
import io.swagger.models.Swagger
import io.swagger.servlet.Reader
import io.swagger.util.Json

class SwaggerDataService {
    GrailsApplication grailsApplication
    Swagger swagger

    String generateSwaggerDocument() {
        return getListingJson(scanSwaggerResources())
    }

    private Swagger scanSwaggerResources() {
        List<Class> swaggerResources = grailsApplication.controllerClasses*.clazz.findAll { it.isAnnotationPresent(io.swagger.annotations.Api) }
        if (swaggerResources) {
            Reader.read(swagger, new HashSet<Class<?>>(swaggerResources));
        }
        return swagger;
    }

    private String getListingJson(Swagger swagger) {
        String resultantJSON = '';
        if (swagger != null) {
            try {
                resultantJSON = Json.mapper().writeValueAsString(swagger);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return resultantJSON;
    }
}

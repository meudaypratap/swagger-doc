package grails.plugins.swaggerdoc

import grails.plugins.Plugin
import io.swagger.models.Contact
import io.swagger.models.Info
import io.swagger.models.License
import io.swagger.models.Swagger
import io.swagger.models.auth.ApiKeyAuthDefinition
import io.swagger.models.auth.In
import io.swagger.models.auth.OAuth2Definition

class SwaggerDocGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.1.10 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Swagger Doc" // Headline display name of the plugin
    def author = "Uday Pratap Singh"
    def authorEmail = "uday.singh@tothenew.com"
    def description = '''\
Add swagger documentation for your APIs
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/swagger-doc"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() {
        { ->
            // TODO Implement runtime spring config (optional)
            def swaggerConfig = grailsApplication.config.swagger
            swaggerOAuth(OAuth2Definition) {
                type = swaggerConfig.securityDefinitions.oauth.type ?: "oauth2"
                authorizationUrl = swaggerConfig.securityDefinitions.oauth.authorizationUrl ?: ''
                tokenUrl = swaggerConfig.securityDefinitions.oauth.tokenUrl ?: ''
                flow = swaggerConfig.securityDefinitions.oauth.flow ?: ''
                scopes = swaggerConfig.securityDefinitions.oauth.scopes ?: [:]
            }
            swaggerApiKeyAuth(ApiKeyAuthDefinition, swaggerConfig.securityDefinitions.apiKeyAuth.name ?: '', swaggerConfig.securityDefinitions.apiKeyAuth.in ?: In.HEADER)

            swaggerContact(Contact) {
                name = swaggerConfig.info.contact.name ?: ''
                url = swaggerConfig.info.contact.url ?: ''
                email = swaggerConfig.info.contact.email ?: ''
            }
            swaggerLicense(License) {
                name = swaggerConfig.info.license.name ?: ''
                url = swaggerConfig.info.license.url ?: ''
            }
            swaggerInfo(Info) {
                contact = ref('swaggerContact')
                license = ref('swaggerLicense')
                description = swaggerConfig.info.description ?: ''
                version = swaggerConfig.info.version ?: ''
                title = swaggerConfig.info.title ?: ''
                termsOfService = swaggerConfig.info.termsOfService ?: ''
            }
            swagger(Swagger) {
                info = ref('swaggerInfo')
                host = swaggerConfig.host ?: ''
                basePath = swaggerConfig.basePath ?: ''
                schemes = swaggerConfig.schemes ?: []
                consumes = swaggerConfig.consumes ?: []
                Map securityDefinitionConfig = [:]
                if (swaggerConfig.securityDefinitions.apiKeyAuth.name) {
                    securityDefinitionConfig['api_key'] = ref('swaggerApiKeyAuth')
                }
                if (swaggerConfig.securityDefinitions.oauth.flow) {
                    securityDefinitionConfig['auth'] = ref('swaggerOAuth')
                }
                if (securityDefinitionConfig) {
                    securityDefinitions = securityDefinitionConfig
                }
            }
        }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}

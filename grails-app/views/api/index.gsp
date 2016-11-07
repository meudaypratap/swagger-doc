<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Account Application API</title>
    <asset:stylesheet src="application.css"/>

</head>

<body class="swagger-section">
<div id='header'>
    <div class="swagger-ui-wrap">
        <a id="logo" href="${createLink(uri: '/')}">
            <asset:image class="logo__img" alt="swagger" height="30" width="30" src="logo.png"/>
            <span class="logo__title">Account Application API</span>
        </a>

        <form id='api_selector'>
            <div class='input'><input placeholder="/api" id="input_baseUrl" name="baseUrl" type="text"/></div>

            <div id='auth_container'></div>

            <div class='input'><a id="explore" class="header__btn" href="#" data-sw-translate>Explore</a></div>
        </form>
    </div>
</div>

<div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div>

<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
<asset:javascript src="application.js"/>
<asset:deferredScripts/>
<script type="text/javascript">
    $(function () {
        var url = "${createLink(controller:'api',action:'resources')}";

        hljs.configure({
            highlightSizeThreshold: 5000
        });

        // Pre load translate...
        if (window.SwaggerTranslator) {
            window.SwaggerTranslator.translate();
        }
        window.swaggerUi = new SwaggerUi({
            url: url,
            dom_id: "swagger-ui-container",
            supportedSubmitMethods: ['get', 'post', 'put', 'delete', 'patch'],
            onComplete: function (swaggerApi, swaggerUi) {
                if (typeof initOAuth == "function") {
                    initOAuth({
                        clientId: "your-client-id",
                        clientSecret: "your-client-secret-if-required",
                        realm: "your-realms",
                        appName: "your-app-name",
                        scopeSeparator: " ",
                        additionalQueryStringParams: {}
                    });
                }

                if (window.SwaggerTranslator) {
                    window.SwaggerTranslator.translate();
                }
            },
            onFailure: function (data) {
                log("Unable to Load SwaggerUI");
            },
            docExpansion: "none",
            jsonEditor: false,
            defaultModelRendering: 'schema',
            showRequestHeaders: false
        });

        window.swaggerUi.load();

        function log() {
            if ('console' in window) {
                console.log.apply(console, arguments);
            }
        }
    });
</script>
</body>
</html>
package com.experts.core.biller.statemachine.api.rovo.awsxray.routes.api;

import static com.experts.core.biller.statemachine.api.rovo.awsxray.routes.SqlQueryRoute.SQL_QUERY;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.MediaType;

import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.api.beans.DetermineFileName;
import com.experts.core.biller.statemachine.api.rovo.awsxray.security.SpringSecurityContextLoader;

public class SampleFileRoute extends BaseAPIRouteBuilder {

    @Override
    protected void defineRoute() throws Exception {

        rest("/files")

                .get("/")
                    .bindingMode(RestBindingMode.json)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .description("Sample service which lists uploaded files")

                    .param()
                        .name("limit")
                        .type(RestParamType.query)
                        .dataType("int")
                        .defaultValue("30")
                        .required(false)
                        .description("Optional parameter that specifies the maximum number of entries to return")
                    .endParam()
                    .param()
                        .name("offset")
                        .type(RestParamType.query)
                        .dataType("int")
                        .defaultValue("0")
                        .required(false)
                        .description("Optional parameter that specifies the starting position to return entries from")
                    .endParam()

                    .route().routeId("api-available-files")
                        .log("Processing list files request")
                        .bean(SpringSecurityContextLoader.class)
                        .policy("authenticated")


                        .log("List files request processed")
                .endRest()

                .get("/{file_uuid}")
                    .bindingMode(RestBindingMode.json)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .description("Sample external service invocation")

                    .param()
                        .name("file_uuid")
                        .type(RestParamType.path)
                        .dataType("string")
                        .defaultValue(null)
                        .required(true)
                        .description("Unique identifier of the file requested")
                    .endParam()

                    .route().routeId("api-retrieve-file")
                        .log("Processing get file request")
                        .bean(SpringSecurityContextLoader.class)
                        .policy("authenticated")

                        .log("Get file request processed")
                .endRest()

                .post("/")
                    .consumes(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .description("Sample file upload endpoint")

                    .route().routeId("api-file-upload")
                        .log("Processing incoming file")
                        .inOnly(SQL_QUERY)
                        .bean(SpringSecurityContextLoader.class)
                        .policy("authenticated")

                        .bean(DetermineFileName.class)


                        .log("File upload completed")
                .endRest();
    }
}

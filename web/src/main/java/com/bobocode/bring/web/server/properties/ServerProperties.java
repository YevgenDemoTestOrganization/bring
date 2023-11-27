package com.bobocode.bring.web.server.properties;

import com.bobocode.bring.core.annotation.Component;
import com.bobocode.bring.core.annotation.Value;
import lombok.Data;

/**
 * Configuration properties for the web server.
 *
 * <p>
 * The values for these properties are typically defined in configuration files
 * and injected using the {@code @Value} annotation.
 * </p>
 *
 * <p>
 * Instances of this class are used to centralize configuration settings for the web
 * server within the application.
 * </p>
 *
 * @see Value
 *
 * @author Blyzhnytsia Team
 * @since 1.0
 */

@Data
@Component
public class ServerProperties {


    /**
     * The port on which the web server will listen for incoming requests.
     */
    @Value("server.port")
    private int port;

    /**
     * The context path for the web server. It represents the base path for all
     * requests handled by the server.
     */
    @Value("server.contextPath")
    private String contextPath;

    /**
     * Indicates whether stack traces should be included in error responses.
     * {@code true} if stack traces should be included, {@code false} otherwise
     */
    @Value("server.withStackTrace")
    private boolean withStackTrace;

    /**
     * The folder where static resources are located for the web server.
     */
    @Value("server.staticFolder")
    private String staticFolder;


    /**
     * The regular expression used for matching static URLs served by the web server.
     */
    @Value("server.regexStaticUrl")
    private String regexStaticUrl;

    /**
     * The URL delimiter used in the application
     */
    @Value("server.urlDelimiter")
    private String urlDelimiter;
}

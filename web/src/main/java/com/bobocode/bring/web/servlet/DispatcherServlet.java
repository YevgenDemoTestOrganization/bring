package com.bobocode.bring.web.servlet;

import com.bobocode.bring.core.anotation.Component;
import com.bobocode.bring.core.anotation.RequestMapping;
import com.bobocode.bring.web.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DispatcherServlet extends FrameworkServlet {
    public static final String BRING_CONTEXT = "BRING_CONTEXT";
    private final List<BringServlet> bringServlets;

    public DispatcherServlet(List<BringServlet> bringServlets) {
        this.bringServlets = bringServlets;
    }

    //    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        ThreadLocal<String> id = new ThreadLocal<>();
//        id.set(UUID.randomUUID().toString());
//        req.setAttribute("id", id);
//        System.out.println("I am in doGet of DispatcherServlet");
//        var bringContext = (BringApplicationContext) req.getServletContext().getAttribute(BRING_CONTEXT);

//        Map<String, Object> allBeans = bringContext.getAllBeans();
//        StringBuilder mapAsString = new StringBuilder();
//        allBeans.keySet().stream().map(key -> key + "=" + allBeans.get(key) + ", ").forEach(mapAsString::append);
//
//        resp.addHeader("Trace-Id", id.get());
//
//        var writer = resp.getWriter();
//        writer.println(mapAsString);
//        writer.flush();
//    }

    @Override
    public void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        String requestPath = getRequestPath(req);
        bringServlets.stream()
                .map(bringServlet -> processBringServlet(bringServlet, requestPath))
                .findFirst()
                .ifPresent(response -> performResponse(response, resp));
    }

    @SneakyThrows
    public void performResponse(Object response, HttpServletResponse resp) {
        PrintWriter writer = resp.getWriter();
        writer.print(response);
        writer.flush();
    }

    @SneakyThrows
    public Object processBringServlet(BringServlet bringServlet, String requestPath) {
        List<ServletRequestParams> servletParamsList = getServletParams(bringServlet.getClass());
        for (ServletRequestParams params : servletParamsList) {
            if (requestPath.equals(params.path())) {
                Method method = params.method();
                return method.invoke(bringServlet);
            }
        }
        return String.format("This application has no explicit mapping for '%s'", requestPath);
    }

    public List<ServletRequestParams> getServletParams(Class<?> clazz) {
        List<ServletRequestParams> servletRequestParamsList = new ArrayList<>();
        if (clazz.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
            String requestMappingPath = annotation.path();

            for (Method method : clazz.getMethods()) {
                if (method.getAnnotation(GetMapping.class) != null) {
                    String getMappingPath = method.getAnnotation(GetMapping.class).path();
                    ServletRequestParams servletRequestParams = new ServletRequestParams(method, requestMappingPath + getMappingPath);
                    servletRequestParamsList.add(servletRequestParams);
                }
            }
        }
        return servletRequestParamsList;
    }

    public String getRequestPath(HttpServletRequest req) {
        String contextPath = req.getContextPath();
        String requestURI = req.getRequestURI();
        if (requestURI.startsWith(contextPath)) {
            requestURI = requestURI.replace(contextPath, "");
        }
        return requestURI;
    }

    public record ServletRequestParams(Method method, String path) {
    }
}

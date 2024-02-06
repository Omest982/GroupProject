package org.example.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@NoArgsConstructor
public class UrlTool {

    public String getFullUrlPrefix(HttpServletRequest request){
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString()).build();

        String scheme = uriComponents.getScheme();       // http / https
        String serverName = request.getServerName();     // hostname.com
        int serverPort = request.getServerPort();        // 80
        //String contextPath = request.getContextPath();   // /app

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://");
        url.append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        //url.append(contextPath);

        return url.toString();
    }
}

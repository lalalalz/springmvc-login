package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManger {

    private final static ConcurrentHashMap<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {

        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie cookie = new Cookie("mySessionId", sessionId);
        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest request) {

        Cookie sessionCookie = findCookie(request, "mySessionId");

        if (sessionCookie == null) {
            return null;
        }

        return sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest request, String cookieName) {
        Cookie sessionCookie = findCookie(request, cookieName);

        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {

        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}

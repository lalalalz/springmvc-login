package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session == null) {
            return "세션이 없습니다.";
        }

//        log.info("sessionId={}", session.getId());
//        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
//        log.info("creationTime={}", new Date(session.getCreationTime()));
//        log.info("lastAccessedTime={}", session.getLastAccessedTime());
//        log.info("isNew={}", session.isNew());


        // 글로벌 설정 적용 확인
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());

        // 세션 단위 설정이 글로벌 설정을 덮어쓰는지 확인
        session.setMaxInactiveInterval(1800);
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());


        return "세션 출력";
    }
}

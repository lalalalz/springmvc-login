package hello.login.web.session;


import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {

    SessionManger sessionManger = new SessionManger();

    @Test
    void sessionTest() {

       // 세션 생성 (서버)
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManger.createSession(member, response);

        // 세션 전달 (클라이언트)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션 조회 (서버)
        Object result = sessionManger.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션 만료 (서버)
        sessionManger.expire(request, "mySessionId");
        Object expiredSession = sessionManger.getSession(request);
        assertThat(expiredSession).isNull();
    }

}

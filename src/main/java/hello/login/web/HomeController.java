package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        // 전달된 쿠키가 없을 때
        if (memberId == null) {
            return "home";
        }

        Member findMember = memberRepository.findById(memberId);

        // 회원 정보 X
        if (findMember == null) {
            return "home";
        }

        // 로그인 성공
        model.addAttribute("member", findMember);
        return "loginHome";
    }
}
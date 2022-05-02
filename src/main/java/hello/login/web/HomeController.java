package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManger sessionManger;

//    @GetMapping("/")
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

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        Member findMember = (Member) sessionManger.getSession(request);

        // 전달된 쿠키가 없을 때, 해당 세션 ID에 등록된 회원 정보가 없을 때
        if (findMember == null) {
            return "home";
        }

        // 로그인 성공
        model.addAttribute("member", findMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home";
        }

        Member findMember = (Member) session.getAttribute("loginMember");

        // 전달된 쿠키가 없을 때, 해당 세션 ID에 등록된 회원 정보가 없을 때
        if (findMember == null) {
            return "home";
        }

        // 로그인 성공
        model.addAttribute("member", findMember);
        return "loginHome";
    }

@GetMapping("/")
public String homeLoginV4(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                          HttpServletRequest request,
                          Model model) {

    HttpSession session = request.getSession(false);

    if (session == null) {
        return "home";
    }

    Member findMember = (Member) session.getAttribute("loginMember");

    // 전달된 쿠키가 없을 때, 해당 세션 ID에 등록된 회원 정보가 없을 때
    if (findMember == null) {
        return "home";
    }

    // 로그인 성공
    model.addAttribute("member", findMember);
    return "loginHome";
}
}
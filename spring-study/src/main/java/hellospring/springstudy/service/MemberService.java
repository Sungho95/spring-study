package hellospring.springstudy.service;

import hellospring.springstudy.domain.Member;
import hellospring.springstudy.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Ctr + shift + t 단축키를 통해 Create Test

//@Service // 스프링이 서비스임을 알게 해주는 어노테이션
//@Component
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // 생성자에 @Autowired를 사용하면 객체 생성 시점에 스프링 컨테이너에서
    // 해당 스프링 빈을 찾아서 주입함. 생성자가 1개만 있을 경우 생략 가능
//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {

//        long start = System.currentTimeMillis();
//        try {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join = " + timeMs + "ms");
//        }
    }


    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
//        long start = System.currentTimeMillis();
//        try {
        return memberRepository.findAll();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("findMembers = " + timeMs + "ms");
//        }
    }

    /**
     * 아이디 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

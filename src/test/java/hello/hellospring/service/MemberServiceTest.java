package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        // given 상황이 주어지고
        Member member = new Member();
        member.setName("jisu");

        // when 실행했을 때
        Long saveId = memberService.join(member);

        // then 결과가 나와야해
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member).isEqualTo(findMember); // 성공
    }

    @Test
    void join_중복() {
        Member member1 = new Member();
        member1.setName("jisu");

        Member member2 = new Member();
        member2.setName("jisu");

        memberService.join(member1);
        //memberService.join(member2);  // 실패
    }

    @Test
    void join_중복_에러문구확인() {
        Member member1 = new Member();
        member1.setName("jisu");

        Member member2 = new Member();
        member2.setName("jisu");

        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            // 실패라 아래 코드 실행됨 (에러메세지가 맞는가, 실패뜸)
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
    }

    @Test
    void join_중복_에러종류확인() {
        // given 상황이 주어지고 (이름 중복)
        Member member1 = new Member();
        member1.setName("jisu");

        Member member2 = new Member();
        member2.setName("jisu");

        // when 실행했을 때
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); // 해당 exception이 맞는가, 성공뜸 (nullpoint면 실패)
    }
}
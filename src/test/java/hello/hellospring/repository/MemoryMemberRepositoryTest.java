package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach  // 모든 실행이 끝나고 처리뙴
    public void afterEach() {
        repository.clearStore();  // 테스트 시 누적된 데이터 초기화
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("jisu");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //System.out.println("result = " + (result == member)); 아래처럼 가능
        //Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {

        Member member1 = new Member();
        member1.setName("jisu");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("jisun");
        repository.save(member2);

        Member result = repository.findByName("jisu").get();


        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {

        Member member1 = new Member();
        member1.setName("jisu");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("jisun");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}

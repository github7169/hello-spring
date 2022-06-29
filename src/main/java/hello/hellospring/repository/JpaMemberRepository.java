package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // JPA를 사용하려면 EntityManager를 주입받아야함

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // PK 기반이 아닌 컬럼은 createQuery 사용 필요
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList().stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // select m 으로 선언하여 굳이 컬럼을 선언하거나 매핑해주지 않아도 됨
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}

package hello.hellospring.repository;


import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

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
        Member member = em.find(Member.class , id); // 조회할 타입과 식별자 PK값
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {   // PK값이 아닐 때는 JPQL을 작성 하여야한다.
        List<Member> result = em.createQuery("select m from Member m where m.name= :name" , Member.class)
                .setParameter("name" , name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() { // JPQL 쿼리 (Member Entity 객체 자제를 조회한다.)
        return em.createQuery("select m from Member m" , Member.class).getResultList();
    }
}

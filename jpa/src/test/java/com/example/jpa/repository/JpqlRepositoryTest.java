package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Address;
import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Order;
import com.example.jpa.entity.Product;
import com.example.jpa.entity.Team2;

@SpringBootTest
public class JpqlRepositoryTest {

    @Autowired
    private Team2Repository team2Repository;

    @Autowired
    private Member2Repository member2Repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Team2 team2 = Team2.builder().name("team" + i).build();
            team2Repository.save(team2);

            Member2 member2 = Member2.builder()
                    .userName("User" + i)
                    .age(i)
                    .team2(team2)
                    .build();
            member2Repository.save(member2);

            Product product = Product.builder()
                    .name("제품" + i)
                    .price(i * 1000)
                    .stockAmount(i * 5)
                    .build();
            productRepository.save(product);
        });
    }

    @Test
    public void orderInsertTset() {

        Address address = new Address();
        address.setCity("서울시 종로구");
        address.setStreet("151-11");
        address.setZipcode("11017");

        IntStream.rangeClosed(1, 3).forEach(i -> {
            Member2 member2 = Member2.builder().id(1L).build();
            Product product = Product.builder().id(2L).build();

            Order order = Order.builder()
                    .member2(member2)
                    .product(product)
                    .homeaddress(address)
                    .build();
            orderRepository.save(order);
        });
    }

    @Test
    public void findMembersTest() {
        // member2Repository.findByMembers().forEach(member -> {
        // System.out.println(member);
        // });
        System.out.println(member2Repository.findByMembers(Sort.by("age")));

        List<Object[]> list = member2Repository.findByMembers2();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects)); // 오브젝트 배열은 결과값이 주소형태로 나와서 Arrays.toString(objects) 사용
            System.out.printf("username = %s, age = %d\n", objects[0], objects[1]);
        }
    }

    @Test
    public void testFindByHome() {
        System.out.println(orderRepository.findByHomeaddress());
    }

    @Test
    public void testFindOrders() {
        List<Object[]> list = orderRepository.findByOrders();

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
            Member2 member2 = (Member2) objects[0];
            Product product = (Product) objects[1];
            Long id = (Long) objects[2];

            System.out.println(member2);
            System.out.println(product);
            System.out.println(id);
        }
    }

    @Test
    public void testFindByAgeList() {
        System.out.println(member2Repository.findByAgeList(5));
        System.out.println(member2Repository.findByTeamEqualList(Team2.builder().id(2L).build()));
        System.out.println(member2Repository.findByTeamIdEqual(2L));
    }

    @Test
    public void testAggregate() {

        List<Object[]> list = member2Repository.aggregate();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects)); // [10, 55, 5.5, 10, 1]
            System.out.println("회원수 " + objects[0]);
            System.out.println("나이 합 " + objects[1]);
            System.out.println("나이 평균 " + objects[2]);
            System.out.println("최대 나이 " + objects[3]);
            System.out.println("최소 나이 " + objects[4]);
        }
    }

    @Test
    public void testJoin() {
        // 조인 후 Member2 결과만 가져오기
        System.out.println(member2Repository.findByTeamMember("team1"));

        // 조인 후 Member2, Team2 결과 가져오기
        List<Object[]> list = member2Repository.findByTeamMember2("team2");
        for (Object[] objects : list) {
            Member2 member2 = (Member2) objects[0];
            Team2 team2 = (Team2) objects[1];
            System.out.println(member2);
            System.out.println(team2);
        }
    }
}

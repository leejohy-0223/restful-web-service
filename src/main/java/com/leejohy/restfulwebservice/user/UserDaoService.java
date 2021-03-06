package com.leejohy.restfulwebservice.user;

// 비즈니스 로직은 Service에 추가

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "leejohy", new Date(), "pass1", "701010-1111111")); // 기본은 현재 시간
        users.add(new User(2, "alice", new Date(), "pass2", "801010-1111111"));
        users.add(new User(3, "lucid", new Date(), "pass3", "901010-1111111"));
    }

    // 전체 사용자 조회
    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    // 개별 사용자 조회
    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    // iterator를 통한 유저 삭제
    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}

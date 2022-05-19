package org.zsz.algorithms.test.entity;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Zhang Shengzhe
 * @create 2021-04-05 14:01
 */
@Data
@AllArgsConstructor
public class Person implements Comparable<Person> {

    private int age;
    private String name;

    public Person(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Person person) {
        return age - person.age;
    }

}
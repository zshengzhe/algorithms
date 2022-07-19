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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return age == person.age && Objects.equal(name, person.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(age, name);
  }

}
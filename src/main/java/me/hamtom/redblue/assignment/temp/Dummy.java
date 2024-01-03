package me.hamtom.redblue.assignment.temp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Dummy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // IDENTITY 전략 사용
    Long id;

    String name;
}

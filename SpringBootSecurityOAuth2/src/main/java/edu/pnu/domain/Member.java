package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
	@Id
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)	// enum의 값을 순서가 있는 인덱스가 아닌 문자열로 저장
	private Role role;
	private Boolean enabled;
}

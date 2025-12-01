package com.rubypaper.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
//@NamedEntityGraph(name = "Member.boardList", attributeNodes = @NamedAttributeNode("boardList"))
public class Member {
	@Id
	@Column(name = "mid")
	private String id;
	private String password;
	private String name;
	private String role;
	
	// @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "member")
	@BatchSize(size = 3)
	private List<Board> boardList = new ArrayList<>();
}

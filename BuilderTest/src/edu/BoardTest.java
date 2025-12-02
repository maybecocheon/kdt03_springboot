package edu;

class Board {
	private String name;
	private String title;
	private String content;
	
	public Board() {
		
	}
	public Board(String name, String title, String content) {
		this.name = name;
		this.title = title;
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Board [name=" + name + ", title=" + title + ", content=" + content + "]";
	}
	
	public static BoardBuilder builder() {
		return new BoardBuilder();
	}
	
	static class BoardBuilder {
		private String name;
		private String title;
		private String content;
		
		public BoardBuilder name(String name) {
			this.name = name;
			return this;
		}
		public BoardBuilder title(String title) {
			this.title = title;
			return this;
		}
		public BoardBuilder content(String content) {
			this.content = content;
			return this;
		}
		public Board build() {
			return new Board(name, title, content);
		}
	}
}

public class BoardTest {
	public static void main(String[] args) {
		Board b = Board.builder().name("n").title("t").content("c").build();
		System.out.println(b);
	}
}

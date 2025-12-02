<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="/css/style.css" rel="stylesheet" type="text/css"/>
	<title>글 상세</title>
</head>
<body>
	<div class="container1">
		<h3>게시글 상세</h3>
		<hr/>
		<input name="seq" type="hidden" value="${ board.seq }"/>
		<table>
			<tr>
				<td bgcolor="orange" width="70">제목</td>
				<td align="left" width="200">${ board.title }</td>
			</tr>
			<tr>
				<td bgcolor="orange">작성자</td>
				<td align="left">${ board.writer }</td>
			</tr>
			<tr>
				<td bgcolor="orange">내용</td>
				<td align="left">${ board.content }</td>
			</tr>
			<tr>
				<td bgcolor="orange">등록일</td>
				<td align="left">
					<fmt:formatDate value="${ board.createDate }" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td bgcolor="orange">조회수</td>
				<td align="left">${ board.cnt }</td>
			</tr>
			</table>
		<hr/>
		<a href="updateBoard?seq=${ board.seq }">글 수정</a>&nbsp;&nbsp;&nbsp;
		<a href="deleteBoard?seq=${ board.seq }">글 삭제</a>&nbsp;&nbsp;&nbsp;
		<a href="getBoardList">글 목록</a>		
	</div>
</body>
</html>
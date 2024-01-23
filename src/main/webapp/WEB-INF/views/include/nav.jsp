<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.goodee.library.member.MemberVo" %>

<link href="<c:url value='/resources/css/include/nav.css'/>" rel="stylesheet" type="text/css">

   <nav>
    <div id="nav_wrap">
    
      <%-- 세션 앞에 MemberVo로 형변환 --%>
      <%
        MemberVo loginedMember = (MemberVo)session.getAttribute("loginMember");
        if(loginedMember == null) {
      %>
      <div class="menu">
        <ul>
           <li>
              <a href="<c:url value='/member/login'/>">로그인</a>
           </li>
           <li>
              <a href="<c:url value='/member/create'/>">회원가입</a>
           </li>
        </ul>
      </div>
      <%} else {%>
      <div style="text-align: right; margin-bottom: 1%; color: #307004;">
        <c:out value="${loginMember.m_name} 님 환영합니다♥" />
      </div>
      <div class="menu">
        <ul>
           <li>
              <a href="<c:url value='/member/logout'/>">로그아웃</a>
           </li>
           <li>
              <a href="<c:url value='/member/${loginMember.m_no}'/>">계정수정</a>
           </li>
           <li>
              <a href="<c:url value='/member'/>">회원목록</a>
           </li>
           <li>
            <a href="<c:url value='/book/create'/>">도서등록</a>
           </li>
           <li>
            <a href="<c:url value='/book'/>">도서목록</a>
           </li>
        </ul>
      </div>
      <div class="search">
         <form action="<c:url value='/book'/>" name="search_book_form" method="get">
            <input type="text" name="b_name" placeholder="검색하고자 하는 도서 이름을 입력하세요.">
            <input type="submit" value="검색">
         </form>
      </div>
      <%} %>
      

    </div>
 </nav>  
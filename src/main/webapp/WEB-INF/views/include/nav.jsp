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
      <div class="menu">
        <ul>
           <li>
              <a href="<c:url value='/member/logout'/>">로그아웃</a>
           </li>
           <li>
              <a href="<c:url value='/'/>">계정수정</a>
           </li>
           <li>
              <a href="<c:url value='/member'/>">회원목록</a>
           </li>
        </ul>
      </div>
      <div class="search">
        <form>
           <input type="text">
           <input type="button" value="검색">
        </form>
      </div>
      <%} %>
      

    </div>
 </nav>  
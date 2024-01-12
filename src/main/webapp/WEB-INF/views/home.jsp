<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>도서관리시스템</title>
<link href="<c:url value='/resources/css/home.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/css/include/common.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/css/include/header.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/css/include/nav.css'/>" rel="stylesheet" type="text/css">
</head>
<body>

  <header>
     <div id="header_wrap">
        <div class="title">   
           <h3>
              <a class="user" href="<c:url value='/' />">구디 도서관</a>
           </h3>
        </div>
     </div>
  </header>
  <nav>
     <div id="nav_wrap">
        <div class="menu">
           <ul>
              <li>
                 <a>로그인</a>
              </li>
              <li>
                 <a>회원가입</a>
              </li>
           </ul>
        </div>
        <div class="search">
           <form>
              <input type="text">
              <input type="button" value="검색">
           </form>
        </div>
     </div>
  </nav>
  <section>
     <div id="section_wrap">
        <div class="word">
           <h3>HOME</h3>
        </div>
     </div>
  </section>

</body>
</html>
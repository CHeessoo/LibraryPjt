<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link href="<c:url value='/resources/css/member/login_form.css' />" rel="stylesheet" type="text/css">
</head>
<body>
   <jsp:include page="../include/header.jsp"/>
   <jsp:include page="../include/nav.jsp"/>   
   <section>
      <div id="section_wrap">
         <div class="word">
            <h3>로그인</h3>
         </div>
         <div class="login_form">
            <form action="<c:url value='/member/login' />" name="login_form" method="post">
               <input type="text"   name="m_id"    placeholder="아이디"> <br>
               <input type="password"   name="m_pw"    placeholder="비밀번호"> <br>
               <input type="button"   value="로그인" onclick="loginForm();"> 
               <input type="reset"   value="초기화">               
            </form>
         </div>
         <div class="find_password_create_account">
            <a> 비밀번호 찾기 </a>
            <a href="<c:url value='/member/create'/>">회원가입</a>
         </div>
      </div>
   </section>      
   <script type="text/javascript">
      function loginForm() {
         let form = document.login_form;
         if (form.m_id.value == '') {
            alert('아이디를 입력하세요.');
            form.m_id.focus();   
         } else if (form.m_pw.value == '') {
            alert('비밀번호를 입력하세요.');
            form.m_pw.focus();
         } else {
            form.submit();
         }
      }
   </script>
</body>
</html>
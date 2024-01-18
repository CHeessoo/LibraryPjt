<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link href="<c:url value='/resources/css/member/find_password_form.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
   <jsp:include page="../include/header.jsp"/>
   <jsp:include page="../include/nav.jsp"/>
   <section>
      <div id="section_wrap">
         <div class="word">
            <h3>비밀번호 찾기</h3>
            <p>(메일 주소로 새로운 비밀번호를 보내드립니다.)</p>
         </div>
         <div class="find_password_form">
            <form action="<c:url value='/member/findPassword' />" name="find_password_form" method="post">
               <input type="text" name="m_id" placeholder="아이디를 입력하세요."> <br>
               <input type="text" name="m_name" placeholder="이름을 입력하세요."> <br>
               <input type="text" name="m_mail" placeholder="메일주소를 입력하세요."> <br>
               <input type="button" value="비밀번호 찾기" onclick="findPassword();"> 
               <input type="reset" value="초기화">
            </form>
         </div>
         <div class="create_account_login">
            <a href="<c:url value='/member/create' />">회원가입</a>
            <a href="<c:url value='/member/login' />">로그인</a>
         </div>
      </div>
   </section>
   <script type="text/javascript">
      function findPassword() {
         let form = document.find_password_form;
         if (form.m_id.value == '') {
            alert('아이디를 입력하세요.');
            form.m_id.focus();   
         } else if (form.m_name.value == '') {
            alert('이름을 입력하세요.');
            form.m_name.focus();
         } else if (form.m_mail.value == '') {
            alert('메일주소를 입력하세요.');
            form.m_mail.focus();
         } else {
            form.submit();
         }
      }
   </script>   
</body>
</html>
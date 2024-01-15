<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="<c:url value='/resources/css/member/create.css' />" rel="stylesheet" type="text/css">
</head>
<body>

  <%-- header.jsp, nav.jsp 파일 인클루드해서 사용 --%>
  <jsp:include page="../include/header.jsp" />
  <jsp:include page="../include/nav.jsp" />
 
   <section>
      <div id="section_wrap">
         <div class="word">
            <h3>회원가입</h3>
         </div><br>
         <div class="create_account_form">
            <form name="create_account_form" action="<c:url value='/member/create' />" method="post">
               <input type="text" name="m_id" placeholder="아이디"> <br>
               <input type="password" name="m_pw" placeholder="비밀번호"> <br>
               <input type="password" name="m_pw_again" placeholder="비밀번호 확인"> <br>
               <input type="text" name="m_name" placeholder="이름"> <br>
               <select name="m_gender">
                  <option value="">성별 선택</option>
                  <option value="M">남성</option>
                  <option value="W">여성</option>
               </select> <br>
               <input type="email" name="m_mail" placeholder="이메일" ><br>
               <input type="text" name="m_phone" placeholder="전화번호"> <br>
               <input type="button" value="회원가입" onclick="createMemberForm();"> 
               <input type="reset" value="초기화">
            </form>
         </div>
         <div class="login">
            <a href="<c:url value='' />">로그인</a>
         </div>
      </div>
   </section>   
   
   <script type="text/javascript">
   
     function createMemberForm() {
       
       // form의 name을 기준으로 가져올 수 있음
       let form = document.create_account_form;
       
       if (form.m_id.value == '') {           // 아이디 입력란의 값이 빈문자열인 경우
          alert('아이디를 입력하세요.');
          form.m_id.focus();
       } else if (form.m_pw.value == '') {
          alert('비밀번호를 입력하세요.');
          form.m_pw.focus();
       } else if (form.m_pw_again.value == '') {
          alert('비밀번호 확인을 입력하세요.');
          form.m_pw_again.focus();
       } else if (form.m_pw.value != form.m_pw_again.value) {
          alert('비밀번호가 일치하는지 확인하세요.');
          form.m_pw.focus();
       } else if (form.m_name.value == '') {
          alert('이름을 입력하세요.');
          form.m_name.focus();
       } else if (form.m_gender.value == '') {
          alert('성별을 선택하세요.');
          form.m_gender.focus();
       } else if (form.m_mail.value == '') {
          alert('메일 주소를 입력하세요.');
          form.m_mail.focus();
       } else if (form.m_phone.value == '') {
          alert('전화번호를 입력하세요.');
          form.m_phone.focus();
       } else {
          form.submit();
       }
    }
   
   </script>
   
</body>
</html>
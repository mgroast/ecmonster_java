<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header">
    <div id="headerLogo">
        <img src="${pageContext.request.contextPath}/public/img/common/logo.png">
    </div>
    <div id="headerSearch">
        <input type="text" placeholder="サイト内検索"/>
        <button class="submit" type="submit"></button>
    </div>
    <div id="headerUser">
        <a href="${pageContext.request.contextPath}/fc/auth/signin">
            ログイン
        </a>
    </div>
    <div id="headerCart">
        カート
    </div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/css/common.css" rel="stylesheet">
    <title>ECMonster</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/bodyHeader.jsp" %>
    
    <p>あなたにオススメの商品</p>
    <div class="item">
        <a href="${pageContext.request.contextPath}/fc/item?id=1">
            <img src="${pageContext.request.contextPath}/public/img/item/1/Thumbnail.png">
        </a>
        <div class="itemName">モンスター1</div>
        <div class="itemStar">★★☆☆☆</div>
        <div class="itemPrice">1000G</div>
    </div>
    
    <p>最近チェックした商品</p>
    
    <p>週間売上ランキング</p>
    
    <%@ include file="/WEB-INF/views/common/bodyFooter.jsp" %>
</body>
</html>
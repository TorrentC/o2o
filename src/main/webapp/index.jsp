<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h2>Hello World!</h2>
<a href="shopadmin/shopoperation">注册店铺</a>
<a href="shopadmin/shoplist">店铺列表</a>
<a href="shopadmin/shopmanagement">店铺管理</a>

<select id="a">
<%--    <option value="1">2</option>--%>
</select>

<select name="" id="b"></select>

<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
</script>
<script>
    $(function () {
        // $("select").append("<option value=\"1\">3</option>");

        $.get("shopadmin/getshopinitinfo", function(data){

            for(var key in data["shopCategoryList"]){
                // alert(data["shopCategoryList"][key]["shopCategoryName"])
                $("#a").append("<option value="+data["shopCategoryList"][key]["shopCategoryId"]+">"+data["shopCategoryList"][key]["shopCategoryName"]+"</option>");
            }
            
            for (var key in data["areaList"]) {
                $("#b").append("<option value="+data["areaList"][key]["areaId"]+">"+data["areaList"][key]["areaName"]+"</option>")
            }
        });
    })
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
    <script src='https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-10.1.1.js'></script>
</head>
<body>
<ccm-parkhaus-10-1-1
        server_url="http://localhost:8080/Parkhaus_war_exploded/Parkhaus"
        extra_buttons='["sum", "avg", "Besucher", "Preis"]'
        extra_charts='["Anzahl", "Parkverhalten"]'
></ccm-parkhaus-10-1-1>

<div id='chart'></div>

</body>
</html>
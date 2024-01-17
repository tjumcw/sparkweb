<!-- speech.ftl -->

<!DOCTYPE html>
<html>
<head>
    <title>Speech Details</title>
    <link rel="stylesheet" href="/css/speech.css">
</head>
<body>

<h1>Speech Details</h1>

<!-- Accessing document fields in FreeMarker -->
<div class="wrapper">
    <p>Speech ID: <span>${_id}</span></p>
    <p>Speaker: <span>${speaker}</span></p>
    <p>Speech Text: <span>${text}</span></p>
</div>

<!-- You can access other fields similarly -->

</body>
</html>

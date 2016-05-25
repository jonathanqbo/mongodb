<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $("#submit").click(function(){
        $.post(
        	"/post/helloworld",
		    JSON.stringify({
		        "name": $("#who:checked").val()
		    }),
		    function(data, status){
		        $("#greeting").html(data);
		    },
		    "json"
		    );
    });
});
</script>
</head>
<body>

	<div>Hello, World! </div>
	<div id="greeting" style="color: red; font-size: 20pt;"></div>
	
	<div>
		Who do you want to say hi with?<br>
		<#list names as name>
			<input type="radio" name="who" value="${name}" id="who">${name}</input>
		</#list>
	</div>
	
	<div>
		<button id="submit">Submit</button>
	</div>

</body>
</html>

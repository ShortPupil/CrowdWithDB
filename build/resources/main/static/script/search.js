function mysubmit(){
var key_input=document.getElementById("key_input");
var search_key=key_input.value;
var key_input2=document.getElementById("key_input2");
key_input2.value=search_key;
var form=document.getElementById("search_form");
form.submit();
}

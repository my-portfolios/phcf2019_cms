function stringToDate(date){
	var year = date.substr(0,4);
	var month = date.substr(5,2) - 1;
	var day = date.substr(8,2);
	
	var hour, min;
	
	var resultDate = new Date(year, month, day);
	
	if(date.length > 10) {
		hour = date.substr(11,2);
		min = date.substr(14,2);
		resultDate = new Date(year, month, day, hour, min);
	}
	
	return resultDate;
}

function dateToString(date){
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	
	if(month < 10) month = "0" + month;
	if(day < 10) day = "0" + day;
	
	return year + "-" + month + "-" + day;
}

function dateFormatChange(date,delims){
	var year = date.substr(0,4);
	var month = date.substr(5,2);
	var day = date.substr(8,2);
	
	return year + delims + month + delims + day;
}

function setCookie(cName, cValue, cDay){
    var expire = new Date();
    expire.setDate(expire.getDate() + cDay);
    cookies = cName + '=' + escape(cValue) + '; path=/ ';
    if(typeof cDay != 'undefined') cookies += ';expires=' + expire.toGMTString() + ';';
    document.cookie = cookies;
}

function getCookie(cName) {
    cName = cName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cName);
    var cValue = '';
    if(start != -1){
        start += cName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cValue = cookieData.substring(start, end);
    }
    return unescape(cValue);
}

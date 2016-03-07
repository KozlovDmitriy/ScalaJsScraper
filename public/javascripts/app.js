$(function() {
    $('#scrap').click(ajaxCall);
});

var ajaxCall = function() {
    var pages = ace.edit("editor").getValue()
    var scrap = ace.edit("scrapeEditor").getValue()
    var ajaxCallBack = {
        success : onSuccess,
        error : onError
    }

    jsRoutes.controllers.Application.scrap(pages, scrap).ajax(ajaxCallBack);
};

var  onSuccess = function(data) {
    alert(data);
}

var onError = function(error) {
    alert('Error: ' +  error);
}

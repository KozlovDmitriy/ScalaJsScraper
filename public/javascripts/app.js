$(function() {
    $('#scrap').click(ajaxCall);
});

var ajaxCall = function() {
    var pages = ace.edit("editor").getValue();
    var scrap = ace.edit("scrapeEditor").getValue();
    var ajaxCallBack = {
        success : onSuccess,
        error : onError
    };
    jsRoutes.controllers.Application.scrap(pages, scrap).ajax(ajaxCallBack);
};

const parseRow = (row) => (
    `<tr>
        <td style="max-width: 1024px;">${row}</td>
        <td>
            <a href="${row}" class="btn btn-xs btn-success download"><i class="glyphicon glyphicon-download-alt"></i></a>
        </td>
    </tr>`
);

var onSuccess = function(data) {
    const $panel = $('#downloadForm');
    const $table = $panel.find('.table');
    $panel.removeClass('hidden');
    for (let key in data) {
        $table.prepend(parseRow(data[key]));
    }
};

const dangerAlert = (message) => (
   `<div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        ${(new Date()).toString().split(' ').splice(4,1).join(' ')}:&nbsp;<strong>Error!</strong>&nbsp;${message}
    </div>`
);

var onError = function(error) {
    $('#errorAlerts').prepend(dangerAlert(error.responseText));
};

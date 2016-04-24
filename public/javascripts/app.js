$(function() {
    $('#scrap').click(ajaxCall);
    $('#downloadAll').click(downloadAll);
    $('.download').click(download);
});

const download = function(e) {
    e.stopPropagation();
    e.preventDefault();
    const link = $(this).attr('href');
    const parts = link.split('/');
    const fileName = parts[parts.length - 1];
    JSZipUtils.getBinaryContent(link, function (err, data) {
        if(err) {
            $(this).parent('tr').addClass('danger');
            console.error( err ); // or handle the error
            return;
        }
        $(this).parent('tr').addClass('success');
        location.href=`data:application/${fileName.split('.').last()};base64,`+data;
    }.bind(this));
};

const downloadAll = function() {
    const zip = new JSZip();
    let i = 0;
    const links = [];
    $('a.download').each(function() {
        const link = $(this).attr('href');
        links.push(link);
    });
    const tryLoadZip = () => {
        if (i === links.length) {
            content = zip.generate();
            location.href="data:application/zip;base64,"+content;
        }
    };
    links.forEach( link => {
        const parts = link.split('/');
        const fileName = parts[parts.length - 1];
        JSZipUtils.getBinaryContent(link, function (err, data) {
            ++i;
            if(err) {
                $('.link-row').eq(i-1).addClass('danger');
                console.error( err ); // or handle the error
                return;
            }
            $('.link-row').eq(i-1).addClass('success');
            zip.file(fileName, data, {binary:true});
            tryLoadZip();
        });
    });
};

const ajaxCall = function() {
    var pages = ace.edit("editor").getValue();
    var scrap = ace.edit("scrapeEditor").getValue();
    var ajaxCallBack = {
        success : onSuccess,
        error : onError
    };
    jsRoutes.controllers.Application.scrap(pages, scrap).ajax(ajaxCallBack);
};

const parseRow = (row) => (
    `<tr class="link-row">
        <td style="max-width: 1024px;">${row}</td>
        <td>
            <a href="${row}" class="btn btn-xs btn-success download"><i class="glyphicon glyphicon-download-alt"></i></a>
        </td>
    </tr>`
);

const onSuccess = function(data) {
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

const onError = function(error) {
    $('#errorAlerts').prepend(dangerAlert(error.responseText));
};

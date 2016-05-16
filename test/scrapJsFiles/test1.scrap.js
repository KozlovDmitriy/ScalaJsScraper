function scrap(url, document) {
    var result = [];
    var arr = document.attr('img','src');
    for(var i in arr) {
        result.push( document.absolute(arr[i], url) )
    }
    return result;
}
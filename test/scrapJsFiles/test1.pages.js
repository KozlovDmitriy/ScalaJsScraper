function getPages() {
    var url = 'http://www.kartinki24.ru/';
    var document = new Document(url);
    var result = [];
    var arr = document.attr('.droplinebar a','href');
    for(var i in arr) {
        result.push( document.absolute(arr[i], 'http://www.kartinki24.ru/') )
    }
    return result;
}
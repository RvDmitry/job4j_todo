function validate() {
    if ($('#desc').val() === '') {
        alert('Введите описание задания');
        return false;
    }
    return true;
}
function send() {
    if (!validate()) {
        return false;
    }
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/items',
        data: 'desc=' + $('#desc').val(),
        dataType: 'text'
    }).done(function(data) {
        display(data);
        $('#desc').val('');
    }).fail(function(err){
        alert(err);
    });
}
function load() {
    let find = document.getElementById("findAll").checked;
    if (find === true) {
        find = 'all';
    }
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/items',
        data: 'find=' + find,
        dataType: 'text'
    }).done(function(data) {
        $("#table td").parent().remove();
        let json = JSON.parse(data);
        $.each(json, function(index, value) {
            display(value);
        });
    }).fail(function(err){
        alert(err);
    });
}
function display(data) {
    let item = JSON.parse(data);
    let status = '';
    let complete = '';
    let formatter = new Intl.DateTimeFormat("ru", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
        second: "numeric"
    });
    if (item.done === true) {
        status = '<span class="glyphicon glyphicon-ok"></span>';
    } else {
        status = '<span class="glyphicon glyphicon-remove"></span>';
        complete = '<input type="button" class="btn btn-primary" id="' + item.id + '" onclick="done(id)" value="Завершить">';
    }
    $('#table tr:last').after('<tr>' +
        '<td style="vertical-align: middle">' + item.id +'</td>' +
        '<td style="vertical-align: middle">' + item.description +'</td>' +
        '<td style="vertical-align: middle">' + formatter.format(new Date(item.created)) +'</td>' +
        '<td style="vertical-align: middle">' + status +'</td>' +
        '<td>' + complete +'</td>' +
        '</tr>');
}
function done(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/items',
        data: 'id=' + id,
        dataType: 'text'
    }).done(function() {
        load();
    }).fail(function(err){
        alert(err);
    });
}
$(document).ready(function() {
    load();
});
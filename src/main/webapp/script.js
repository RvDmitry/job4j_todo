function validate() {
    if ($('#desc').val() === '') {
        alert('Введите описание задания');
        return false;
    }
    let s = $('#cIds').val().length;
    if(s === 0) {
        alert('Вы не выбрали категорию.');
        return false;
    }
    return true;
}
function send() {
    if (!validate()) {
        return false;
    }
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/todo/items',
        contentType: "application/json",
        data: JSON.stringify({
            desc: $('#desc').val(),
            cats: $('#cIds').val()
        }),
        success: function() {
            $('#desc').val('');
            loadItems();
        },
        error: function(err) {
            alert(err);
        }
    });
}
function loadItems() {
    let find = document.getElementById("findAll").checked;
    if (find === true) {
        find = 'all';
    }
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/items',
        data: 'find=' + find,
        dataType: 'json'
    }).done(function(responseJson) {
        $("#table td").parent().remove();
        $.each(responseJson, function(index, value) {
            display(index, value);
        });
    }).fail(function(err){
        alert(err);
    });
}
function display(index, data) {
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
        status = '<i class="fa fa-check-square-o"></i>';
    } else {
        status = '<i class="fa fa-minus-square-o"></i>';
        complete = '<input type="button" class="btn btn-primary" id="' + item.id + '" onclick="done(id)" value="Завершить">';
    }
    let cats = '';
    item.categories.forEach(function callback(currentValue) {
        cats += currentValue.name + '<br>';
    });
    $('#table tr:last').after('<tr>' +
        '<td style="vertical-align: middle">' + index +'</td>' +
        '<td style="vertical-align: middle">' + item.description +'</td>' +
        '<td style="vertical-align: middle">' + formatter.format(new Date(item.created)) +'</td>' +
        '<td style="vertical-align: middle">' + cats +'</td>' +
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
        loadItems();
    }).fail(function(err){
        alert(err);
    });
}
$(document).ready(function() {
    loadItems();
    loadCats();
});